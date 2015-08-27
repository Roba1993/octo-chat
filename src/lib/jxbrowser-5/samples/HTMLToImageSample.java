/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.Callback;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The sample demonstrates how to get screen shot of the web page
 * and save it as PNG image file.
 */
public class HTMLToImageSample {
    public static void main(String[] args) throws Exception {
        // #1 Create Browser instance
        Browser browser = new Browser();

        // #2 Set the required view size
        int viewWidth = 1280;
        int viewHeight = 1024;
        BrowserView view = new BrowserView(browser);
        view.setBounds(new Rectangle(viewWidth, viewHeight));

        // #3 Load web page and wait until web page is loaded completely
        Browser.invokeAndWaitFinishLoadingMainFrame(browser, new Callback<Browser>() {
            @Override
            public void invoke(Browser browser) {
                browser.loadURL("http://www.teamdev.com");
            }
        });

        // #4 Get java.awt.Image of the loaded web page.
        Image image = view.getImage();

        // Save image as teamdev.com.png file
        BufferedImage bufferedImage = new BufferedImage(viewWidth, viewHeight,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        // Scale image according to the current device scale factor.
        // This code must be used to support Retina displays.
        Double scale = 1.0 / view.getDeviceScaleFactor();
        graphics.scale(scale, scale);
        graphics.drawImage(image, 0, 0, null);

        ImageIO.write(bufferedImage, "PNG", new File("teamdev.com.png"));

        // Dispose Browser instance
        browser.dispose();
    }
}
