/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

/**
 * This sample demonstrates how to get and override web preferences.
 */
public class BrowserPreferencesSample {
    public static void main(String[] args) {
        BrowserPreferences.setChromiumSwitches("");

        Browser browserOne = new Browser();
        // Gets the current Browser's preferences
        BrowserPreferences preferences = browserOne.getPreferences();
        preferences.setImagesEnabled(false);
        preferences.setJavaScriptEnabled(false);
        // Updates Browser's preferences
        browserOne.setPreferences(preferences);

        // Images and JavaScript will be disabled
        browserOne.loadURL("http://www.google.com/");

        // Images and JavaScript will not be disabled
        Browser browserTwo = new Browser();
        browserTwo.loadURL("http://www.teamdev.com/");
    }
}
