/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultDownloadHandler;
import com.teamdev.jxbrowser.chromium.swing.DefaultPopupHandler;
import com.teamdev.jxbrowser.chromium.Browser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author TeamDev Ltd.
 */
public final class TabFactory {

    public static Tab createFirstTab() {
        return createTab("http://www.teamdev.com/jxbrowser");
    }

    public static Tab createTab() {
        return createTab("about:blank");
    }

    public static Tab createTab(String url) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        TabContent tabContent = new TabContent(browserView);

        browser.setDownloadHandler(new DefaultDownloadHandler(browserView));
        browser.setDialogHandler(new DefaultDialogHandler(browserView));
        browser.setPopupHandler(new DefaultPopupHandler());

        final TabCaption tabCaption = new TabCaption();
        tabCaption.setTitle("about:blank");

        tabContent.addPropertyChangeListener("PageTitleChanged", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                tabCaption.setTitle((String) evt.getNewValue());
            }
        });

        browser.loadURL(url);
        return new Tab(tabCaption, tabContent);
    }
}
