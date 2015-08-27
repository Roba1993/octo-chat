/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.NavigationEntry;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;

/**
 * The sample demonstrates how to work with Browser's navigation history.
 */
public class HistorySample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Returns the number of entries in the back/forward list.
        // int entryCount = browser.getNavigationEntryCount();

        // Returns index of the current navigation entry in the back/forward list.
        int index = browser.getCurrentNavigationEntryIndex();

        // Navigates to the entry at a specific index in the back/forward list.
        // browser.goToIndex(index);

        // Removes navigation entry from the back/forward list at a specific index.
        // boolean success = browser.removeNavigationEntryAtIndex(index);

        // Prints information about the navigation entry at specific index.
        NavigationEntry navigationEntry = browser.getNavigationEntryAtIndex(index);
        System.out.println("URL = " + navigationEntry.getURL());
        System.out.println("Original URL = " + navigationEntry.getOriginalURL());
        System.out.println("Title = " + navigationEntry.getTitle());

    }
}
