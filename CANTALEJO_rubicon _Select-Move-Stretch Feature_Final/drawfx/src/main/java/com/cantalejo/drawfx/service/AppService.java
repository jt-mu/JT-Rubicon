package com.cantalejo.drawfx.service;

import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;
import java.util.List;

public interface AppService {
    // shape mode
    ShapeMode getShapeMode();
    void setShapeMode(ShapeMode shapeMode);

    // draw mode
    DrawMode getDrawMode();
    void setDrawMode(DrawMode drawMode);

    // colors
    Color getColor();
    void setColor(Color color);
    Color getFill();
    void setFill(Color color);

    // shape actions
    void move(Shape shape, Point newLoc);
    void scale(Shape shape, Point newEnd);
    void create(Shape shape);
    void close(Shape shape);

    // NEW: modification tracking
    void modify(Shape target, Shape before, Shape after);


    // NEW: command execution wrapper
    void executeCommand(Command cmd);

    // model
    Object getModel();
    List<Shape> getShapes();

    // undo/redo
    void undo();
    void redo();
    boolean canUndo();
    boolean canRedo();


}
