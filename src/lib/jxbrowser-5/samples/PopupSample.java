/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PopupContainer;
import com.teamdev.jxbrowser.chromium.PopupHandler;
import com.teamdev.jxbrowser.chromium.PopupParams;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The sample demonstrates how to register popup handler and
 * implement functionality that displays popup windows.
 */
public class PopupSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        final BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setPopupHandler(new PopupHandler() {
            public PopupContainer handlePopup(PopupParams params) {
                return new PopupContainer() {
                    public void insertBrowser(final Browser browser, Rectangle initialBounds) {
                        BrowserView browserView = new BrowserView(browser);
                        browserView.setPreferredSize(initialBounds.getSize());

                        final JFrame frame = new JFrame("Popup");
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        frame.add(browserView, BorderLayout.CENTER);
                        frame.pack();
                        frame.setLocation(initialBounds.getLocation());
                        frame.setVisible(true);
                        frame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                browser.dispose();
                            }
                        });

                        browser.addDisposeListener(new DisposeListener<Browser>() {
                            public void onDisposed(DisposeEvent<Browser> event) {
                                frame.setVisible(false);
                            }
                        });
                    }
                };
            }
        });

        browser.loadURL("http://www.popuptest.com");
    }
}
