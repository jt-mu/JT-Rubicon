package com.cantalejo.draw.view;

import com.cantalejo.draw.controller.DrawingController;
import com.cantalejo.drawfx.model.Drawing;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {
    AppService appService;

    public DrawingView(AppService appService){

        this.appService = appService;
        DrawingController drawingController = new DrawingController(appService, this);
    }

    @Override
    public void paint(Graphics g) {
        Drawing drawing = (Drawing) appService.getModel();
        for(Shape shape : drawing.getShapes()){
            shape.getRendererService().render(g, shape, false);
        }
    }
}
