/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

package com.teamdev.jxbrowser.chromium.demo;

import com.teamdev.jxbrowser.chromium.EditorCommand;

import javax.swing.*;

/**
 * @author Artem Trofimov
 */
public class CommandMenuItem extends JMenuItem {

    private final EditorCommand command;

    public CommandMenuItem(String commandName, EditorCommand command) {
        super(commandName);
        this.command = command;
    }

    public EditorCommand getCommand() {
        return command;
    }
}
