/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * This sample demonstrates how to scroll document programmatically.
 */
public class ScrollDocumentSample {
    public static void main(String[] args) throws InterruptedException {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("http://www.teamdev.com");
        while (browser.isLoading()) {
            TimeUnit.MILLISECONDS.sleep(50);
        }

        browser.executeJavaScript("window.scrollTo(document.body.scrollWidth, " +
                "document.body.scrollHeight);");
    }
}
