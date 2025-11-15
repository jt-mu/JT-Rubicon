package com.cantalejo.draw.model;

import com.cantalejo.drawfx.model.Shape;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Shape {
    public Rectangle(Point start) {
        super(start);
    }

    @Override
    public void draw(Graphics2D g) {
        normalize(); // ensure flipping is allowed

        int x = getLocation().x;
        int y = getLocation().y;
        int w = getEnd().x - getLocation().x;
        int h = getEnd().y - getLocation().y;

        if (getFill() != null) {
            g.setColor(getFill());
            g.fillRect(x, y, w, h);
        }

        g.setColor(getColor() != null ? getColor() : Color.BLACK);
        g.drawRect(x, y, w, h);

        // optional: draw resize handles if selected
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
        return new java.awt.Rectangle(x, y, w, h).contains(p);
    }

    @Override
    public List<Point> getHandles() {
        List<Point> handles = new ArrayList<>();
        int x1 = Math.min(getLocation().x, getEnd().x);
        int y1 = Math.min(getLocation().y, getEnd().y);
        int x2 = Math.max(getLocation().x, getEnd().x);
        int y2 = Math.max(getLocation().y, getEnd().y);

        // order: top-left, top-right, bottom-right, bottom-left
        handles.add(new Point(x1, y1));
        handles.add(new Point(x2, y1));
        handles.add(new Point(x2, y2));
        handles.add(new Point(x1, y2));
        return handles;
    }
}
