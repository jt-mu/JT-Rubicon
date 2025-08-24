package com.cantalejo.draw.model;

import com.cantalejo.draw.service.RectangleRendererService;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public class Rectangle extends Shape {
    public Rectangle(Point start, Point end) {
        super(start);
        this.setEnd(end);
        this.setColor(Color.BLUE); // default color
        this.setRendererService(new RectangleRendererService());
    }
}
