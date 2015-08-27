/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.ProductInfo;

/**
 * This sample demonstrates how to get JxBrowser version and build.
 */
public class GetProductVersionSample {
    public static void main(String[] args) {
        System.out.println("JxBrowser version: " + ProductInfo.getVersion());
    }
}
