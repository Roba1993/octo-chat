/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.internal.FileUtil;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultPopupHandler;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to change navigate.language JS property value using
 * Chromium --lang switcher.
 */
public class NavigatorLanguageSample {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches("--lang=IT");

        String dataDir = FileUtil.createTempDir("tempDataDir").getAbsolutePath();
        Browser browser = new Browser(new BrowserContext(dataDir, "IT"));
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setPopupHandler(new DefaultPopupHandler());
        browser.loadURL("http://www.w3schools.com/jsref/prop_nav_language.asp");
    }
}
