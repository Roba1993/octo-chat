/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.events.DisposeEvent;
import com.teamdev.jxbrowser.chromium.events.DisposeListener;

/**
 * This sample demonstrates how to dispose Browser instance.
 * Every Browser instance must be disposed before your Java
 * application exit.
 */
public class DisposeBrowserSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.addDisposeListener(new DisposeListener<Browser>() {
            public void onDisposed(DisposeEvent<Browser> event) {
                System.out.println("Browser is disposed.");
            }
        });
        browser.dispose();
    }
}
