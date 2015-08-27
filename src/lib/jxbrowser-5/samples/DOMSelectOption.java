/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMOptionElement;
import com.teamdev.jxbrowser.chromium.dom.DOMSelectElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The sample demonstrates how to programatically select an option item in SELECT tag.
 */
public class DOMSelectOption {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    DOMDocument document = event.getBrowser().getDocument();
                    DOMSelectElement select = (DOMSelectElement) document.findElement(By.id("select-tag"));
                    selectOptionByIndex(select, 2);
                }
            }
        });
        browser.loadHTML("<html><body><select id='select-tag'>\n" +
                "  <option value=\"volvo\">Volvo</option>\n" +
                "  <option value=\"saab\">Saab</option>\n" +
                "  <option value=\"opel\">Opel</option>\n" +
                "  <option value=\"audi\">Audi</option>\n" +
                "</select></body></html>");
    }

    private static void selectOptionByIndex(DOMSelectElement selectElement, int index) {
        List<DOMOptionElement> options = selectElement.getOptions();
        options.get(index).setSelected(true);
    }
}
