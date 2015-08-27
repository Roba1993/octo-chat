/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to use Browser in JInternalFrame components.
 */
public class JInternalFrameSample {
    public static void main(String[] args) {
        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.add(createInternalFrame("Browser One", "http://www.teamdev.com", 0));
        desktopPane.add(createInternalFrame("Browser Two", "http://www.google.com", 100));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(desktopPane, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JInternalFrame createInternalFrame(String title, String url, int offset) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.loadURL(url);

        JInternalFrame internalFrame = new JInternalFrame(title, true);
        internalFrame.setContentPane(browserView);
        internalFrame.setLocation(100 + offset, 100 + offset);
        internalFrame.setSize(400, 400);
        internalFrame.setVisible(true);
        return internalFrame;
    }
}