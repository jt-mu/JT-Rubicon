package com.cantalejo.draw.model;

import com.cantalejo.draw.service.EllipseRendererService;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public class Ellipse extends Shape {
    public Ellipse(Point start, Point end) {
        super(start);
        this.setEnd(end);
        this.setColor(Color.MAGENTA); // default color
        this.setRendererService(new EllipseRendererService());
    }
}
