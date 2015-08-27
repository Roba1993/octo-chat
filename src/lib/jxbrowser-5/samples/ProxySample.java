/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.AuthRequiredParams;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.CustomProxyConfig;
import com.teamdev.jxbrowser.chromium.HostPortPair;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * The sample demonstrates how to configure Browser instance to use custom proxy settings.
 * By d Browser owser instance uses system proxy settings.
 */
public class ProxySample {
    public static void main(String[] args) {
        // Browser will use system proxy settings.
        // Browser browser = new Browser();

        // Browser will automatically detect proxy settings.
        // Browser browser = new Browser(new AutoDetectProxyConfig());

        // Browser will not use a proxy server.
        // Browser browser = new Browser(new DirectProxyConfig());

        // Browser will use proxy settings received from proxy auto-config (PAC) file.
        // Browser browser = new Browser(new URLProxyConfig("<pac-file-url>"));

        // Browser will use custom user's proxy settings.
        HostPortPair httpServer = new HostPortPair("http-proxy-server", 80);
        HostPortPair httpsServer = new HostPortPair("https-proxy-server", 80);
        HostPortPair ftpServer = new HostPortPair("ftp-proxy-server", 80);
        String exceptions = "<local>";  // bypass proxy server for local web pages

        Browser browser = new Browser(new CustomProxyConfig(httpServer,
                httpsServer, ftpServer, exceptions));

        // In order to handle proxy authorization you can use the following way
        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public boolean onAuthRequired(AuthRequiredParams params) {
                if (params.isProxy()) {
                    params.setUsername("proxy-username");
                    params.setPassword("proxy-password");
                    return false;
                }
                return true;
            }
        });
    }
}
