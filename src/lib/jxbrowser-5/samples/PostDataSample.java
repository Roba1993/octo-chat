/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.DefaultNetworkDelegate;

/**
 * This sample demonstrates how to read and modify POST parameters using NetworkDelegate.
 */
public class PostDataSample {
    public static void main(String[] args) {
        Browser browser = new Browser();
        BrowserContext browserContext = browser.getContext();
        NetworkService networkService = browserContext.getNetworkService();
        networkService.setNetworkDelegate(new DefaultNetworkDelegate() {
            @Override
            public void onBeforeURLRequest(BeforeURLRequestParams params) {
                if ("POST".equals(params.getMethod())) {
                    PostData post = params.getPostData();
                    PostDataContentType contentType = post.getContentType();
                    if (contentType == PostDataContentType.FORM_URL_ENCODED) {
                        FormData postData = (FormData) post;
                        postData.setPair("key1", "value1", "value2");
                        postData.setPair("key2", "value2");
                    } else if (contentType == PostDataContentType.MULTIPART_FORM_DATA) {
                        MultipartFormData postData = (MultipartFormData) post;
                        postData.setPair("key1", "value1", "value2");
                        postData.setPair("key2", "value2");
                        postData.setFilePair("file3", "C:\\Test.zip");
                    } else if (contentType == PostDataContentType.PLAIN_TEXT) {
                        RawData postData = (RawData) post;
                        postData.setData("raw data");
                    }
                    params.setPostData(post);
                }
            }
        });
        browser.loadURL(new LoadURLParams("http://localhost/", "key=value"));
    }
}
