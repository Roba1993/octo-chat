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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The example demonstrates how to integrate UI built with HTML+CSS+JavaScript
 * into Java desktop application.
 */
public class HTMLUISample {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("HTMLUISample");
        final JButton newAccountButton = new JButton("New Account...");
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Account account = createAccount(frame);
                // Displays created account's details
                JOptionPane.showMessageDialog(frame, "Created Account: " + account);
            }
        });

        JPanel contentPane = new JPanel();
        contentPane.add(newAccountButton);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(contentPane, BorderLayout.CENTER);
        frame.setSize(300, 75);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static Account createAccount(JFrame parent) {
        final AtomicReference<Account> result = new AtomicReference<Account>();
        final JDialog dialog = new JDialog(parent, "New Account", true);

        // Create Browser instance.
        final Browser browser = new Browser();
        // Register Java callback function that will be invoked from JavaScript
        // when user clicks New Account button.
        browser.registerFunction("onCreateAccount", new BrowserFunction() {
            @Override
            public JSValue invoke(JSValue... args) {
                // Read text field values received from JavaScript.
                String firstName = args[0].getString();
                String lastName = args[1].getString();
                String phone = args[2].getString();
                String email = args[3].getString();
                // Create a new Account instance.
                result.set(new Account(firstName, lastName, phone, email));
                // Close dialog.
                dialog.setVisible(false);
                // Return undefined (void) to JavaScript.
                return JSValue.createUndefined();
            }
        });
        // Load HTML with dialog's HTML+CSS+JavaScript UI.
        browser.loadURL("file://<path_to_file>/html-ui.html");

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Dispose Browser instance because we don't need it anymore.
                browser.dispose();
                // Close New Account dialog.
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        // Embed Browser Swing component into the dialog.
        dialog.add(new BrowserView(browser), BorderLayout.CENTER);
        dialog.setSize(400, 500);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result.get();
    }

    public static class Account {
        public final String firstName;
        public final String lastName;
        public final String phone;
        public final String email;

        public Account(String firstName, String lastName, String phone, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.email = email;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
}
