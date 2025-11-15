package com.cantalejo.draw.controller;

import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.AppService;
import com.cantalejo.draw.view.DrawingView;
import com.cantalejo.draw.model.ShapeFactory;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class DrawingController implements MouseListener, MouseMotionListener {
    private final AppService appService;
    private final DrawingView drawingView;
    private final ActionController actionController;

    private Shape currentShape;
    private Shape selectedShape;
    private Shape beforeModification;   // snapshot for undo/redo
    private Point dragStart;
    private boolean resizing = false;
    private int activeHandle = -1;

    public DrawingController(AppService appService, DrawingView drawingView, ActionController actionController) {
        this.appService = appService;
        this.drawingView = drawingView;
        this.actionController = actionController;
    }

    private void clearSelection() {
        List<Shape> shapes = appService.getShapes();
        for (Shape s : shapes) s.setSelected(false);
        selectedShape = null;
        activeHandle = -1;
        actionController.setSelectedShape(null); // 🔹 notify controller
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point start = e.getPoint();
        List<Shape> shapes = appService.getShapes();

        // top-to-bottom hit test
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            int hit = s.getHandleIndex(start);
            if (hit >= 0) {
                clearSelection();
                selectedShape = s;
                s.setSelected(true);
                actionController.setSelectedShape(s); // 🔹 notify
                resizing = true;
                activeHandle = hit;
                dragStart = start;
                beforeModification = s.clone();
                drawingView.repaint();
                return;
            }
            if (s.contains(start)) {
                clearSelection();
                selectedShape = s;
                s.setSelected(true);
                actionController.setSelectedShape(s); // 🔹 notify
                resizing = false;
                activeHandle = -1;
                dragStart = start;
                beforeModification = s.clone();
                drawingView.repaint();
                return;
            }
        }

        // nothing selected → start creating a new shape using current tool
        clearSelection();
        currentShape = ShapeFactory.createShape(
                appService.getShapeMode(),
                start,
                appService.getColor(),
                appService.getFill()
        );
        if (currentShape != null) {
            appService.create(currentShape);
            if (actionController != null) {
                actionController.updateUndoRedoButtons();
                actionController.updateStatus();
            }
            drawingView.repaint();
        }
    }

    private void resizeShape(Shape shape, int handleIndex, Point current) {
        // Special-case line endpoints
        if (shape.getHandles().size() == 2) {
            // treat handles 0 -> start, 1 -> end
            if (handleIndex == 0) {
                shape.setLocation(current);
            } else {
                shape.setEnd(current);
            }
            return;
        }

        // For rectangle/ellipse: fix opposite corner and set variable to current
        Point a = shape.getLocation();
        Point b = shape.getEnd();
        int x1 = a.x, y1 = a.y, x2 = b.x, y2 = b.y;

        int fx, fy, vx, vy;
        switch (handleIndex) {
            case 0: // top-left clicked -> fix bottom-right (x2,y2)
                fx = x2; fy = y2;
                vx = current.x; vy = current.y;
                break;
            case 1: // top-right -> fix bottom-left (x1,y2)
                fx = x1; fy = y2;
                vx = current.x; vy = current.y;
                break;
            case 2: // bottom-right -> fix top-left (x1,y1)
                fx = x1; fy = y1;
                vx = current.x; vy = current.y;
                break;
            case 3: // bottom-left -> fix top-right (x2,y1)
                fx = x2; fy = y1;
                vx = current.x; vy = current.y;
                break;
            default:
                fx = x1; fy = y1;
                vx = current.x; vy = current.y;
                break;
        }

        // 🔹 allow inversion by directly setting location & end, then normalize
        shape.setLocation(new Point(fx, fy));
        shape.setEnd(new Point(vx, vy));
        shape.normalize();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point current = e.getPoint();

        if (selectedShape != null) {
            if (resizing && activeHandle >= 0) {
                resizeShape(selectedShape, activeHandle, current);
            } else {
                // move
                int dx = current.x - dragStart.x;
                int dy = current.y - dragStart.y;
                selectedShape.moveBy(dx, dy);
                dragStart = current;
            }
            drawingView.repaint();
            return;
        }

        if (currentShape != null) {
            currentShape.setEnd(current);
            currentShape.normalize();  // 🔹 flip while drawing
            drawingView.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // finalize modification
        if (selectedShape != null && beforeModification != null) {
            Shape after = selectedShape.clone();
            try {
                appService.getClass().getMethod("modify", Shape.class, Shape.class, Shape.class)
                        .invoke(appService, selectedShape, beforeModification, after);
            } catch (Exception ex) {
                // ignore if no modify()
            }
            beforeModification = null;
        }

        // finalize drawing
        if (currentShape != null) {
            currentShape.setEnd(e.getPoint());
            currentShape.normalize();   // 🔹 finalize normalized coords
            currentShape = null;
        }

        resizing = false;
        activeHandle = -1;

        if (actionController != null) {
            actionController.updateUndoRedoButtons();
            actionController.updateStatus();
        }
        drawingView.repaint();
    }

    // unused
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
