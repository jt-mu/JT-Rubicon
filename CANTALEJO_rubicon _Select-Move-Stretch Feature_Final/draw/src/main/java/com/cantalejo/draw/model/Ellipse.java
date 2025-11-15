package com.cantalejo.draw.model;

import com.cantalejo.drawfx.model.Shape;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

public class Ellipse extends Shape {
    public Ellipse(Point start) {
        super(start);
    }

    @Override
    public void draw(Graphics2D g) {
        normalize(); // allow flipping

        int x = getLocation().x;
        int y = getLocation().y;
        int w = getEnd().x - getLocation().x;
        int h = getEnd().y - getLocation().y;

        if (getFill() != null) {
            g.setColor(getFill());
            g.fillOval(x, y, w, h);
        }

        g.setColor(getColor() != null ? getColor() : Color.BLACK);
        g.drawOval(x, y, w, h);

        if (isSelected()) {
            g.setColor(Color.BLUE);
            for (Point handle : getHandles()) {
                g.fillRect(handle.x - HANDLE_SIZE/2, handle.y - HANDLE_SIZE/2, HANDLE_SIZE, HANDLE_SIZE);
            }
        }
    }


    @Override
    public boolean contains(Point p) {
        int x = Math.min(getLocation().x, getEnd().x);
        int y = Math.min(getLocation().y, getEnd().y);
        int w = Math.abs(getEnd().x - getLocation().x);
        int h = Math.abs(getEnd().y - getLocation().y);
        return new Ellipse2D.Double(x, y, w, h).contains(p);
    }

    @Override
    public List<Point> getHandles() {
        List<Point> handles = new ArrayList<>();
        int x1 = Math.min(getLocation().x, getEnd().x);
        int y1 = Math.min(getLocation().y, getEnd().y);
        int x2 = Math.max(getLocation().x, getEnd().x);
        int y2 = Math.max(getLocation().y, getEnd().y);

        handles.add(new Point(x1, y1)); // top-left
        handles.add(new Point(x2, y1)); // top-right
        handles.add(new Point(x2, y2)); // bottom-right
        handles.add(new Point(x1, y2)); // bottom-left
        return handles;
    }
}
