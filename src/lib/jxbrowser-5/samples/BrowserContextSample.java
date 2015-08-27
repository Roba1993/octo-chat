/*
 * Copyright (c) 2000-2014 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;

import java.io.File;

/**
 * By default all Browser instances with same BrowserContext share
 * cookies and cache data. This sample demonstrates how to create two isolated
 * Browser instances that don't share cookies and cache data.
 */
public class BrowserContextSample {
    public static void main(String[] args) {
        // This Browser instance will store cookies and user data files in "user-data-dir-one" dir.
        String browserOneUserDataDir = new File("user-data-dir-one").getAbsolutePath();
        Browser browserOne = new Browser(new BrowserContext(browserOneUserDataDir));

        // This Browser instance will store cookies and user data files in "user-data-dir-two" dir.
        String browserTwoUserDataDir = new File("user-data-dir-two").getAbsolutePath();
        Browser browserTwo = new Browser(new BrowserContext(browserTwoUserDataDir));

        // The browserOne and browserTwo will not see the cookies and cache data files of each other.
    }
}
