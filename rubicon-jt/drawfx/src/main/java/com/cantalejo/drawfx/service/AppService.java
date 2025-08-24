package com.cantalejo.drawfx.service;

import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public interface AppService {
    ShapeMode getShapeMode();
    void setShapeMode(ShapeMode shapeMode);

    DrawMode getDrawMode();
    void setDrawMode(DrawMode drawMode);

    Color getColor();
    void setColor(Color color);

    Color getFill();
    void setFill(Color color);

    void move(Shape shape, Point newLoc);
    void scale(Shape shape, Point newEnd);

    void create(Shape shape);

    void close();

    Object getModel();
}
