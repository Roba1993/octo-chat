/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DialogParams;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import com.teamdev.jxbrowser.chromium.swing.DefaultDialogHandler;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to override default alert dialog. You
 * can register your own DialogHandler where you can implement all
 * the required JavaScript dialogs yourself.
 */
public class JavaScriptDialogsSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        final BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setDialogHandler(new DefaultDialogHandler(browserView) {
            @Override
            public void onAlert(DialogParams params) {
                String title = "My Alert Dialog";
                String message = params.getMessage();
                JOptionPane.showMessageDialog(browserView, message, title,
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        browser.executeJavaScript("alert('test');");
    }
}
