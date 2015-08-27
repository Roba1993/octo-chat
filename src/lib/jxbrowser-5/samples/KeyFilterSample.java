/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.KeyFilter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This sample demonstrates how to register key filter to filter
 * Ctrl+A key events to prevent text selection.
 */
public class KeyFilterSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browserView.setKeyFilter(new KeyFilter<KeyEvent>() {
            public boolean filter(KeyEvent event) {
                return event.isControlDown() && event.getKeyCode() == KeyEvent.VK_A;
            }
        });
        browser.loadURL("http://www.teamdev.com");
    }
}
