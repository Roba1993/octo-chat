/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.PrintJob;
import com.teamdev.jxbrowser.chromium.events.PrintJobEvent;
import com.teamdev.jxbrowser.chromium.events.PrintJobListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The sample demonstrates how to print currently loaded web page with custom print settings.
 */
public class PrintSample {
    public static void main(String[] args) {
        final Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.print();
            }
        });
        frame.add(print, BorderLayout.NORTH);
        frame.add(browserView, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        browser.setPrintHandler(new PrintHandler() {
            @Override
            public PrintStatus onPrint(PrintJob printJob) {
                PrintSettings printSettings = printJob.getPrintSettings();
                printSettings.setPrinterName("Microsoft XPS Document Writer");
                printSettings.setLandscape(false);
                printSettings.setPrintBackgrounds(false);
                printSettings.setColorModel(ColorModel.COLOR);
                printSettings.setDuplexMode(DuplexMode.SIMPLEX);
                printSettings.setDisplayHeaderFooter(true);
                printSettings.setCopies(1);
                printSettings.setPaperSize(PaperSize.ISO_A4);

                List<PageRange> ranges = new ArrayList<PageRange>();
                ranges.add(new PageRange(0, 3));
                printSettings.setPageRanges(ranges);

                printJob.addPrintJobListener(new PrintJobListener() {
                    @Override
                    public void onPrintingDone(PrintJobEvent event) {
                        System.out.println("Printing is finished successfully: " + event.isSuccess());
                    }
                });
                return PrintStatus.CONTINUE;
            }
        });

        browser.loadURL("http://www.teamdev.com/services");
    }
}
