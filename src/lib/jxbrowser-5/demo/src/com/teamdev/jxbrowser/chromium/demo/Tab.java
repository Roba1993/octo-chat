/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

/**
 * @author TeamDev Ltd.
 */
public class Tab {

    private final TabCaption caption;
    private final TabContent content;

    public Tab(TabCaption caption, TabContent content) {
        this.caption = caption;
        this.content = content;
    }

    public TabCaption getCaption() {
        return caption;
    }

    public TabContent getContent() {
        return content;
    }
}
