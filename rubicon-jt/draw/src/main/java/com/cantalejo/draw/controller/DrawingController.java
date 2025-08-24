package com.cantalejo.draw.controller;

import com.cantalejo.draw.model.Ellipse;
import com.cantalejo.draw.model.Rectangle;
import com.cantalejo.draw.model.Line;
import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.draw.view.DrawingView;
import com.cantalejo.drawfx.service.AppService;
import com.cantalejo.drawfx.model.Shape;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingController  implements MouseListener, MouseMotionListener {
    private Point end;
    final private DrawingView drawingView;

    Shape currentShape;
    AppService appService;
     public DrawingController(AppService appService, DrawingView drawingView){
       this.appService = appService;
         this.drawingView = drawingView;
         drawingView.addMouseListener(this);
         drawingView.addMouseMotionListener(this);
         appService.setDrawMode(DrawMode.Idle);
         appService.setShapeMode(ShapeMode.Ellipse);
     }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point start = e.getPoint();

        if (appService.getDrawMode() == DrawMode.Idle) {
            switch (appService.getShapeMode()) {
                case Line -> currentShape = new Line(start, start);
                case Rectangle -> currentShape = new Rectangle(start, start);
                case Ellipse -> currentShape = new Ellipse(start, start);
            }

            if (currentShape != null) {
                currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, false);
                appService.setDrawMode(DrawMode.MousePressed);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed && currentShape != null) {
            appService.create(currentShape);
            appService.setDrawMode(DrawMode.Idle);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (appService.getDrawMode() == DrawMode.MousePressed && currentShape != null) {
            Point end = e.getPoint();

            // erase previous preview
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);

            // update shape endpoint
            appService.scale(currentShape, end);

            // redraw preview
            currentShape.getRendererService().render(drawingView.getGraphics(), currentShape, true);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
