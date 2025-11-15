package com.cantalejo.draw.model;

import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public class ShapeFactory {
    public static Shape createShape(ShapeMode mode, Point start, Color color, Color fill) {
        switch (mode) {
            case Line: {
                Line line = new Line(start);
                line.setColor(color);
                return line;
            }
            case Rectangle: {
                Rectangle rect = new Rectangle(start);
                rect.setColor(color);
                rect.setFill(fill);
                return rect;
            }
            case Ellipse: {
                Ellipse ellipse = new Ellipse(start);
                ellipse.setColor(color);
                ellipse.setFill(fill);
                return ellipse;
            }
            default:
                return null;
        }
    }
}
