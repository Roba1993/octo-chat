/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.ProductInfo;
import com.teamdev.jxbrowser.chromium.demo.resources.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

/**
 * @author TeamDev Ltd.
 */
public class AboutDialog extends JDialog {
    public AboutDialog(Frame owner) {
        super(owner, "About JxBrowser Demo", true);
        initContent();
        initKeyStroke();
        setResizable(false);
        pack();
        setSize(250, getHeight());
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void initContent() {
        JLabel icon = new JLabel();
        icon.setIcon(Resources.getIcon("jxbrowser32x32.png"));
        JLabel appName = new JLabel("JxBrowser Demo");
        JLabel version = new JLabel("Version: " + ProductInfo.getVersion());
        JLabel company = new JLabel("\u00A9 "
                + Calendar.getInstance().get(Calendar.YEAR) + " TeamDev Ltd.");
        JLabel rights = new JLabel("All rights reserved.");

        icon.setAlignmentX(CENTER_ALIGNMENT);
        appName.setAlignmentX(CENTER_ALIGNMENT);
        appName.setFont(appName.getFont().deriveFont(Font.BOLD));
        version.setAlignmentX(CENTER_ALIGNMENT);
        company.setAlignmentX(CENTER_ALIGNMENT);
        rights.setAlignmentX(CENTER_ALIGNMENT);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(icon);
        contentPane.add(Box.createVerticalStrut(16));
        contentPane.add(appName);
        contentPane.add(Box.createVerticalStrut(8));
        contentPane.add(version);
        contentPane.add(Box.createVerticalStrut(8));
        contentPane.add(company);
        contentPane.add(Box.createVerticalStrut(4));
        contentPane.add(rights);
        setContentPane(contentPane);
    }

    private void initKeyStroke() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    dispose();
                }
            }
        });
        JRootPane rootPane = getRootPane();
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
