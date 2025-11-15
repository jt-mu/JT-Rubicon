package com.cantalejo.draw.view;

import com.cantalejo.draw.controller.ActionController;
import com.cantalejo.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingFrame extends JFrame {

    public DrawingFrame(AppService appService) {
        // Controllers
        ActionController actionController = new ActionController(appService);
        this.setJMenuBar(new DrawingMenuBar(actionController));

        DrawingToolBar toolBar = new DrawingToolBar(actionController);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        // Drawing View
        DrawingView drawingView = new DrawingView(appService);
        this.getContentPane().add(drawingView, BorderLayout.CENTER);

        // Window controller
        var windowController = new com.cantalejo.draw.controller.DrawingWindowController(appService);
        this.addWindowListener(windowController);
        this.addWindowFocusListener(windowController);
        this.addWindowStateListener(windowController);

        this.setSize(600, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
