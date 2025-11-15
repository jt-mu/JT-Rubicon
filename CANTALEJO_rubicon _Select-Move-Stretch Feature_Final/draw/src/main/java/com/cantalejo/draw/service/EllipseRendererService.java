package com.cantalejo.draw.service;

import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.RendererService;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseRendererService implements RendererService {
    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Graphics2D g2d = (Graphics2D) g;

        int x1 = shape.getLocation().x;
        int y1 = shape.getLocation().y;
        int x2 = shape.getEnd().x;
        int y2 = shape.getEnd().y;

        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);

        Ellipse2D ellipse = new Ellipse2D.Double(x, y, w, h);

        if (xor) {
            g2d.setXORMode(shape.getColor());
            g2d.draw(ellipse);
            g.setPaintMode();
        } else {
            if (shape.getFill() != null) {
                g2d.setColor(shape.getFill());
                g2d.fill(ellipse);
            }
            g2d.setColor(shape.getColor());
            g2d.draw(ellipse);
        }
    }
}
