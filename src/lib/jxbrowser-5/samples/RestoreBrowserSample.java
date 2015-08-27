/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.RenderAdapter;
import com.teamdev.jxbrowser.chromium.events.RenderEvent;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates how to restore Browser instance after its
 * native process unexpectedly terminated. In general to rest Browser owser instance you just need to load the same or another URL.
 */
public class RestoreBrowserSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addRenderListener(new RenderAdapter() {
            @Override
            public void onRenderGone(RenderEvent event) {
                Browser browser = event.getBrowser();
                // Restore Browser instance by loading the same URL
                browser.loadURL(browser.getURL());
            }
        });

        browser.loadURL("http://www.google.com");

        System.out.println("Run 'Task Manager' app and kill the 'jxbrowser-chromium.exe' " +
                "process with the '--type=renderer' command line argument.");
    }
}
