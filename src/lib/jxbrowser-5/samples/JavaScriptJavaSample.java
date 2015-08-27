/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFunction;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

/**
 * The sample demonstrates how to register a new JavaScript function and
 * map it to a Java method that will be invoked every time when the JavaScript
 * function is invoked.
 */
public class JavaScriptJavaSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        browser.registerFunction("MyFunction", new BrowserFunction() {
            public JSValue invoke(JSValue... args) {
                for (JSValue arg : args) {
                    System.out.println("arg = " + arg);
                }
                return JSValue.create("Hello!");
            }
        });
        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    Browser browser = event.getBrowser();
                    JSValue returnValue = browser.executeJavaScriptAndReturnValue(
                            "MyFunction('Hello JxBrowser!', 1, 2, 3, true);");
                    System.out.println("return value = " + returnValue);
                }
            }
        });
        browser.loadURL("about:blank");
    }
}
