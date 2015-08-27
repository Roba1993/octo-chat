/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;

/**
 * This sample demonstrates how to clear the Browser's cache. If you create
 * several browser instances by calling new Browser() or
 * new Browser(BrowserContext context) method using the same
 * BrowserContext instance, calling CacheStorage.clearCache()
 * will remove cache for all these browser instances.
 */
public class ClearCacheSample {
    public static void main(String[] args) {
        BrowserContext context1 = new BrowserContext("C:\\my-data1");
        Browser browser1 = new Browser(context1);
        Browser browser2 = new Browser(context1);

        BrowserContext context2 = new BrowserContext("C:\\my-data2");
        Browser browser3 = new Browser(context2);

        // Clears cache of browser1 and browser2 instances because they use the same
        // user data and cache "C:\\my-data1" directory. It doesn't clear cache
        // of browser3, because browser3 uses a different directory for storing
        // cache data - "C:\\my-data2".
        browser1.getCacheStorage().clearCache();
    }
}
