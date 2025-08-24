package com.cantalejo.draw.view;

import com.cantalejo.draw.controller.DrawingWindowController;
import com.cantalejo.drawfx.service.AppService;

import javax.swing.*;

public class DrawingFrame extends JFrame {


    public DrawingFrame(AppService appService){
        DrawingWindowController drawingWindowController = new DrawingWindowController(appService);
        this.addWindowListener(drawingWindowController);
        this.addWindowFocusListener(drawingWindowController);
        this.addWindowStateListener(drawingWindowController);

        DrawingView drawingView = new DrawingView(appService);
        this.getContentPane().add(drawingView);
    }
}
