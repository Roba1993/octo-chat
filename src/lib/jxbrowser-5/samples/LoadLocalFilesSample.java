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
 * The sample demonstrates how to enable access to local files from a web page.
 */
public class LoadLocalFilesSample {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches("--disable-web-security", "--allow-file-access-from-files");

        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadHTML("<html><body>Image:<img src='file:///C:\\image.jpg'></body></html>");
    }
}
