package com.cantalejo.draw.view;

import com.cantalejo.drawfx.service.AppService;
import com.cantalejo.drawfx.model.Shape;

import javax.swing.*;
import java.awt.*;

public class DrawingView extends JPanel {

    private final AppService appService;

    public DrawingView(AppService appService) {
        this.appService = appService;
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (Shape shape : appService.getShapes()) {
            shape.draw(g2);
        }
    }

}
