package com.cantalejo.draw.service;

import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.model.Drawing;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.AppService;
import com.cantalejo.drawfx.service.MoverService;
import com.cantalejo.drawfx.service.ScalerService;

import java.awt.*;
import java.util.List;

public class DrawingAppService implements AppService {
    final private Drawing drawing;
    private Color color;
    private Color fill;
    private Shape selectedShape;
    private ShapeMode shapeMode = ShapeMode.Line;
    private DrawMode drawMode = DrawMode.Idle;
    MoverService moverService;
    ScalerService scalerService;

    public DrawingAppService() {
        drawing = new Drawing();
        moverService = new MoverService();
        scalerService = new ScalerService();
        this.color = Color.BLACK;
        this.fill = null;
    }

    @Override
    public ShapeMode getShapeMode() {
        return shapeMode;
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        this.shapeMode = shapeMode;
    }

    @Override
    public DrawMode getDrawMode() {
        return drawMode;
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        this.drawMode = drawMode;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        if (selectedShape != null) {
            selectedShape.setColor(color); // update last shape
        }
    }


    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public void setFill(Color color) {
        this.fill = color;
        if (selectedShape != null) {
            selectedShape.setFill(color); // update last shape
        }
    }

    @Override
    public void move(Shape shape, Point newLoc) {
        moverService.move(shape, newLoc);
    }

    @Override
    public void scale(Shape shape, Point newEnd) {
        shape.setEnd(newEnd);
    }

    @Override
    public void create(Shape shape) {
        this.drawing.getShapes().add(shape);
        this.selectedShape = shape; // keep track of it
    }

    @Override
    public void close(Shape shape) {
        System.exit(0);
    }

    @Override
    public Object getModel() {
        return drawing;
    }

    @Override
    public List<Shape> getShapes() {
        return drawing.getShapes();  // ✅ FIXED
    }

    @Override
    public void executeCommand(Command cmd) {
        // base service: just execute immediately
        cmd.execute();
    }

    // No-op undo/redo
    @Override
    public void undo() {
    }

    @Override
    public void redo() {
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean canRedo() {
        return false;
    }

    @Override
    public void modify(Shape target, Shape before, Shape after) {
        // base service → apply the "after" state
        target.setLocation(after.getLocation());
        target.setEnd(after.getEnd());
        target.setColor(after.getColor());
        target.setFill(after.getFill());
    }
}