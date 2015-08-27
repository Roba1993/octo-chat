/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Cookie;
import com.teamdev.jxbrowser.chromium.CookieStorage;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The sample demonstrates how to access CookieStorage instance to
 * read all the available cookies.
 */
public class CookieSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        CookieStorage cookieStorage = browser.getCookieStorage();

        // Create and add new cookie
        int oneHourInMilliseconds = 36000000;
        int microsecondsOffset = 1000;
        // Cookie will be alive during one hour starting from now
        long expirationTimeInMicroseconds = (System.currentTimeMillis() +
                oneHourInMilliseconds) * microsecondsOffset;
        cookieStorage.setCookie("http://www.google.com", "mycookie", "myvalue",
                ".google.com", "/", expirationTimeInMicroseconds, false, false);

        List<Cookie> cookies = cookieStorage.getAllCookies();
        for (Cookie cookie : cookies) {
            System.out.println("cookie = " + cookie);
        }
    }
}
