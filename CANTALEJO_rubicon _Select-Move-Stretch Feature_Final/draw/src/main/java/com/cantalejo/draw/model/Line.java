package com.cantalejo.draw.model;

import com.cantalejo.drawfx.model.Shape;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Line extends Shape {
    public Line(Point start) {
        super(start);
    }

    @Override
    public void draw(java.awt.Graphics2D g) {
        g.setColor(getColor() != null ? getColor() : Color.BLACK);
        g.drawLine(getLocation().x, getLocation().y, getEnd().x, getEnd().y);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            int size = Shape.HANDLE_SIZE;
            for (Point h : getHandles()) {
                g.fillRect(h.x - size/2, h.y - size/2, size, size);
            }
        }
    }

    @Override
    public boolean contains(Point p) {
        Line2D line = new Line2D.Double(getLocation(), getEnd());
        return line.ptSegDist(p) < 5.0;
    }

    @Override
    public List<Point> getHandles() {
        List<Point> list = new ArrayList<>();
        list.add(new Point(getLocation()));
        list.add(new Point(getEnd()));
        return list;
    }
}
