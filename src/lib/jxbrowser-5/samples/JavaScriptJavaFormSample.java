/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to send form data from JavaScript to Java.
 */
public class JavaScriptJavaFormSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);
        browser.registerFunction("save", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                // FirstName is supposed to be first parameter
                JSValue firstNameValue = args[0];
                System.out.println("firstName: " + firstNameValue.getString());

                // FirstName is supposed to be second parameter
                JSValue lastNameValue = args[1];
                System.out.println("lastName: " + lastNameValue.getString());

                return JSValue.createNull();
            }
        });

        JFrame frame = new JFrame();
        frame.getContentPane().add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.loadURL("D:\\form.html");
    }
}
