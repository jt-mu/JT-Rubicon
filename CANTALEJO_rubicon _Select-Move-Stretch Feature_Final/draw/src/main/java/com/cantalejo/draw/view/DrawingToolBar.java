package com.cantalejo.draw.view;

import com.cantalejo.draw.controller.ActionController;
import com.cantalejo.drawfx.ActionCommand;

import javax.swing.*;
import java.awt.*;

public class DrawingToolBar extends JToolBar {
    public DrawingToolBar(ActionController actionController) {
        setFloatable(false);
        setBorderPainted(false);

        // Shape toggle buttons (grouped)
        ButtonGroup group = new ButtonGroup();

        JToggleButton lineBtn = createToggle("/icons/line.png", "Line", ActionCommand.LINE.name(), actionController);
        JToggleButton rectBtn = createToggle("/icons/rectangle.png", "Rectangle", ActionCommand.RECTANGLE.name(), actionController);
        JToggleButton ellipseBtn = createToggle("/icons/ellipse.png", "Ellipse", ActionCommand.ELLIPSE.name(), actionController);

        group.add(lineBtn); add(lineBtn);
        group.add(rectBtn); add(rectBtn);
        group.add(ellipseBtn); add(ellipseBtn);

        addSeparator(new Dimension(10, 0));

        // Undo / Redo
        JButton undoBtn = IconButtonFactory.createIconButton("/icons/undo.png", "Undo", () -> actionController.undo());
        undoBtn.setActionCommand(ActionCommand.UNDO.name());
        add(undoBtn);

        JButton redoBtn = IconButtonFactory.createIconButton("/icons/redo.png", "Redo", () -> actionController.redo());
        redoBtn.setActionCommand(ActionCommand.REDO.name());
        add(redoBtn);

        addSeparator(new Dimension(10, 0));



        addSeparator(new Dimension(10, 0));

        // Color & Fill
        JButton colorBtn = IconButtonFactory.createIconButton("/icons/color.png", "Stroke Color", () -> actionController.chooseStrokeColor());
        colorBtn.setActionCommand(ActionCommand.COLOR.name());
        add(colorBtn);

        JButton fillBtn = IconButtonFactory.createIconButton("/icons/fill.png", "Fill Color", () -> actionController.chooseFillColor());
        fillBtn.setActionCommand(ActionCommand.FILL.name());
        add(fillBtn);

        // Register buttons into ActionController
        actionController.lineButton = lineBtn;
        actionController.rectButton = rectBtn;
        actionController.ellipseButton = ellipseBtn;
        actionController.undoButton = undoBtn;
        actionController.redoButton = redoBtn;
        actionController.colorButton = colorBtn;
        actionController.fillButton = fillBtn;
    }

    private JToggleButton createToggle(String iconPath, String tooltip, String actionCommand, ActionController actionController) {
        Icon icon = null;
        java.net.URL url = getClass().getResource(iconPath);
        if (url != null) {
            ImageIcon i = new ImageIcon(url);
            Image scaled = i.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
        }
        JToggleButton t = new JToggleButton(icon);
        t.setToolTipText(tooltip);
        t.setActionCommand(actionCommand);
        t.addActionListener(actionController);
        t.setFocusable(false);
        return t;
    }
}
