/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TeamDev Ltd.
 */
public class TabbedPane extends JPanel {

    private final List<Tab> tabs;
    private final TabCaptions captions;
    private final JComponent contentContainer;

    public TabbedPane() {
        this.captions = new TabCaptions();
        this.tabs = new ArrayList<Tab>();
        this.contentContainer = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());
        add(captions, BorderLayout.NORTH);
        add(contentContainer, BorderLayout.CENTER);
    }

    public void disposeAllTabs() {
        for (Tab tab : getTabs()) {
            disposeTab(tab);
        }
    }

    private void disposeTab(Tab tab) {
        tab.getCaption().setSelected(false);
        tab.getContent().dispose();
        removeTab(tab);
        if (hasTabs()) {
            Tab firstTab = getFirstTab();
            firstTab.getCaption().setSelected(true);
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.setVisible(false);
                window.dispose();
            }
        }
    }

    private Tab findTab(TabCaption item) {
        for (Tab tab : getTabs()) {
            if (tab.getCaption().equals(item)) {
                return tab;
            }
        }
        return null;
    }

    public void addTab(final Tab tab) {
        TabCaption caption = tab.getCaption();
        caption.addPropertyChangeListener("CloseButtonPressed", new TabCaptionCloseTabListener());
        caption.addPropertyChangeListener("TabSelected", new SelectTabListener());

        TabContent content = tab.getContent();
        content.addPropertyChangeListener("TabClosed", new TabContentCloseTabListener());

        captions.addTab(caption);
        tabs.add(tab);
        validate();
        repaint();
    }

    private boolean hasTabs() {
        return !tabs.isEmpty();
    }

    private Tab getFirstTab() {
        return tabs.get(0);
    }

    private List<Tab> getTabs() {
        return new ArrayList<Tab>(tabs);
    }

    public void removeTab(Tab tab) {
        TabCaption tabCaption = tab.getCaption();
        captions.removeTab(tabCaption);
        tabs.remove(tab);
        validate();
        repaint();
    }

    public void addTabButton(TabButton button) {
        captions.addTabButton(button);
    }

    public void selectTab(Tab tab) {
        TabCaption tabCaption = tab.getCaption();
        TabCaption selectedTab = captions.getSelectedTab();
        if (selectedTab != null && !selectedTab.equals(tabCaption)) {
            selectedTab.setSelected(false);
        }
        captions.setSelectedTab(tabCaption);
    }

    private class TabCaptionCloseTabListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            TabCaption caption = (TabCaption) evt.getSource();
            Tab tab = findTab(caption);
            disposeTab(tab);
        }
    }

    private class SelectTabListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            TabCaption caption = (TabCaption) evt.getSource();
            Tab tab = findTab(caption);
            if (caption.isSelected()) {
                selectTab(tab);
            }
            if (!caption.isSelected()) {
                TabContent content = tab.getContent();
                contentContainer.remove(content);
                contentContainer.validate();
                contentContainer.repaint();
            } else {
                final TabContent content = tab.getContent();
                contentContainer.add(content, BorderLayout.CENTER);
                contentContainer.validate();
                contentContainer.repaint();
            }
        }
    }

    private class TabContentCloseTabListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            TabContent content = (TabContent) evt.getSource();
            Tab tab = findTab(content);
            disposeTab(tab);
        }

        private Tab findTab(TabContent content) {
            for (Tab tab : getTabs()) {
                if (tab.getContent().equals(content)) {
                    return tab;
                }
            }
            return null;
        }
    }
}
