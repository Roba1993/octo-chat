/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to handle navigation on Backspace and
 * Shift+Backspace.
 */
public class DisableBackspaceNavigationSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.setLoadHandler(new DefaultLoadHandler() {
            @Override
            public boolean canNavigateOnBackspace() {
                return false;
            }
        });

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.google.com");
    }
}
