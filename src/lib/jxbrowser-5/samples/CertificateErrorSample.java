/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Certificate;
import com.teamdev.jxbrowser.chromium.CertificateErrorParams;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.events.NetError;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * Demonstrates how to handle SSL certificate errors.
 */
public class CertificateErrorSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setLoadHandler(new DefaultLoadHandler() {
            @Override
            public boolean onCertificateError(CertificateErrorParams params) {
                Certificate certificate = params.getCertificate();
                System.out.println("certificate.getSubjectName() = " + certificate.getSubjectName());
                System.out.println("certificate.getIssuerName() = " + certificate.getIssuerName());
                System.out.println("certificate.hasExpired() = " + certificate.hasExpired());
                NetError errorCode = params.getCertificateError();
                System.out.println("errorCode = " + errorCode);
                // Return false to ignore certificate error.
                return false;
            }
        });
        browser.loadURL("<https-url-with-invalid-ssl-certificate>");
    }
}
