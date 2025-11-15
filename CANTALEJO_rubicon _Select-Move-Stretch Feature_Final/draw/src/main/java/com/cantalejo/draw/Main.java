package com.cantalejo.draw;

import com.cantalejo.draw.controller.ActionController;
import com.cantalejo.draw.controller.DrawingController;
import com.cantalejo.draw.service.DrawingAppService;
import com.cantalejo.draw.service.DrawingCommandAppService;
import com.cantalejo.draw.view.*;
import com.cantalejo.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        AppService base = new DrawingAppService();
        AppService appService = new DrawingCommandAppService(base);

        // Controller
        ActionController actionController = new ActionController(appService);

        // Frame
        DrawingFrame drawingFrame = new DrawingFrame(appService);
        drawingFrame.getContentPane().setLayout(new BorderLayout());

        // Views
        DrawingView drawingView = new DrawingView(appService);
        DrawingToolBar toolBar = new DrawingToolBar(actionController);
        DrawingMenuBar menuBar = new DrawingMenuBar(actionController);
        StatusBar statusBar = new StatusBar();

        // Wire controller to view
        actionController.setDrawingView(drawingView);
        actionController.setStatusLabel(statusBar.getLabel());

        DrawingController drawingController = new DrawingController(appService, drawingView, actionController);
        drawingView.addMouseListener(drawingController);
        drawingView.addMouseMotionListener(drawingController);

        // Add components
        drawingFrame.setJMenuBar(menuBar);
        drawingFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
        drawingFrame.getContentPane().add(drawingView, BorderLayout.CENTER);
        drawingFrame.getContentPane().add(statusBar, BorderLayout.SOUTH);

        // Initial UI sync
        actionController.updateShapeSelection(appService.getShapeMode());
        actionController.updateUndoRedoButtons();
        actionController.updateStatus();

        // Show frame
        drawingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawingFrame.setSize(800, 600);
        drawingFrame.setVisible(true);
    }
}


