/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This sample demonstrates how to use querySelector DOM API.
 */
public class DOMQuerySelectorSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.getContentPane().add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                DOMDocument document = event.getBrowser().getDocument();
                // Get the div with id = "root".
                DOMNode divRoot = document.findElement(By.cssSelector("#root"));
                // Get all paragraphs.
                List<DOMElement> paragraphs = divRoot.findElements(By.cssSelector("p"));
                for (DOMElement paragraph : paragraphs) {
                    System.out.println("paragraph.getNodeValue() = " +
                            paragraph.getNodeValue());
                }
            }
        });
        browser.loadHTML(
                "<html><body><div id='root'>" +
                        "<p>paragraph1</p>" +
                        "<p>paragraph2</p>" +
                        "<p>paragraph3</p>" +
                        "</div></body></html>");
    }
}
