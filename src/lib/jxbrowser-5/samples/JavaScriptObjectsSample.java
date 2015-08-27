/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.JSFunction;
import com.teamdev.jxbrowser.chromium.JSObject;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to work with JavaScript objects from Java code.
 */
public class JavaScriptObjectsSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JSValue value = browser.executeJavaScriptAndReturnValue("document");
        if (value.isObject() && value instanceof JSObject) {
            JSObject document = (JSObject) value;
            // Call document.title = "New Document Title";
            boolean success = document.set("title", JSValue.create("New Document Title"));
            System.out.println("Title has been updated successfully: " + success);

            // Call document.write("Hello World!");
            JSValue method = document.get("write");
            if (method.isFunction() && method instanceof JSFunction) {
                JSFunction documentWrite = (JSFunction) method;
                documentWrite.invoke(document, JSValue.create("Hello World!"));
            }
        }
    }
}
