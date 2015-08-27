/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * This sample demonstrates how to handle all resources such as
 * HTML, PNG, JavaScript, CSS files and decide whether web browser
 * engine should load them from web server or not. For example, in
 * this sample we cancel loading of all Images.
 */
public class ResourceHandlerSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        BrowserContext context = browser.getContext();
        NetworkService networkService = context.getNetworkService();
        networkService.setResourceHandler(new ResourceHandler() {
            @Override
            public boolean canLoadResource(ResourceParams params) {
                System.out.println("URL: " + params.getURL());
                System.out.println("Type: " + params.getResourceType());
                // Cancel loading of all images
                return params.getResourceType() != ResourceType.IMAGE;
            }
        });

        browser.loadURL("http://www.google.com");
    }
}
