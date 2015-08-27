/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import javax.swing.*;

/**
 * @author TeamDev Ltd.
 */
public class TabButton extends JButton {

    public TabButton(Icon icon, String toolTipText) {
        setIcon(icon);
        setToolTipText(toolTipText);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setContentAreaFilled(false);
        setFocusable(false);
    }

}
