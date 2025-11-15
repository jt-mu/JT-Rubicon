package com.cantalejo.draw.view;

import com.cantalejo.draw.controller.ActionController;
import com.cantalejo.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DrawingMenuBar extends JMenuBar {

    public DrawingMenuBar(ActionController actionController) {
        // Draw Menu
        JMenu drawMenu = new JMenu("Draw");
        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem lineItem = new JRadioButtonMenuItem("Line");
        lineItem.setActionCommand(ActionCommand.LINE.name());
        lineItem.addActionListener(actionController);
        drawMenu.add(lineItem);
        group.add(lineItem);
        actionController.lineMenuItem = lineItem;

        JRadioButtonMenuItem rectItem = new JRadioButtonMenuItem("Rectangle");
        rectItem.setActionCommand(ActionCommand.RECTANGLE.name());
        rectItem.addActionListener(actionController);
        drawMenu.add(rectItem);
        group.add(rectItem);
        actionController.rectMenuItem = rectItem;

        JRadioButtonMenuItem ellipseItem = new JRadioButtonMenuItem("Ellipse");
        ellipseItem.setActionCommand(ActionCommand.ELLIPSE.name());
        ellipseItem.addActionListener(actionController);
        drawMenu.add(ellipseItem);
        group.add(ellipseItem);
        actionController.ellipseMenuItem = ellipseItem;

        this.add(drawMenu);

        // Edit menu: undo/redo
        JMenu editMenu = new JMenu("Edit");

        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        undoItem.setActionCommand(ActionCommand.UNDO.name());
        undoItem.addActionListener(actionController);
        undoItem.setEnabled(false);
        editMenu.add(undoItem);
        actionController.undoMenuItem = undoItem;

        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        redoItem.setActionCommand(ActionCommand.REDO.name());
        redoItem.addActionListener(actionController);
        redoItem.setEnabled(false);
        editMenu.add(redoItem);
        actionController.redoMenuItem = redoItem;

        this.add(editMenu);

        // Properties
        JMenu propMenu = new JMenu("Properties");

        JMenuItem colorItem = new JMenuItem("Color");
        colorItem.setActionCommand(ActionCommand.COLOR.name());
        colorItem.addActionListener(actionController);
        propMenu.add(colorItem);
        actionController.colorMenuItem = colorItem;

        JMenuItem fillItem = new JMenuItem("Fill");
        fillItem.setActionCommand(ActionCommand.FILL.name());
        fillItem.addActionListener(actionController);
        propMenu.add(fillItem);
        actionController.fillMenuItem = fillItem;

        this.add(propMenu);
    }
}
