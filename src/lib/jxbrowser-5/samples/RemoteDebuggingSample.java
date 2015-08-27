/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates how to use Chromium remote debugging feature.
 */
public class RemoteDebuggingSample {

    public static void main(String[] args) {
        // Specifies remote debugging port for remote Chrome Developer Tools.
        BrowserPreferences.setChromiumSwitches("--remote-debugging-port=9222");

        Browser browser1 = new Browser();
        BrowserView browserView1 = new BrowserView(browser1);

        // Gets URL of the remote Developer Tools web page for browser1 instance.
        String remoteDebuggingURL = browser1.getRemoteDebuggingURL();

        JFrame frame1 = new JFrame();
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.add(browserView1, BorderLayout.CENTER);
        frame1.setSize(700, 500);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);

        browser1.loadURL("http://www.google.com");

        // Creates another Browser instance and loads the remote Developer
        // Tools URL to access HTML inspector.
        Browser browser2 = new Browser();
        BrowserView browserView2 = new BrowserView(browser2);

        JFrame frame2 = new JFrame();
        frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame2.add(browserView2, BorderLayout.CENTER);
        frame2.setSize(700, 500);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);

        browser2.loadURL(remoteDebuggingURL);
    }
}
