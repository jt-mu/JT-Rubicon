package com.cantalejo.draw.service;

import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.RendererService;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseRendererService implements RendererService {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Graphics2D g2d = (Graphics2D) g;

        int x = shape.getLocation().x;
        int y = shape.getLocation().y;
        int w = shape.getEnd().x - x;
        int h = shape.getEnd().y - y;

        if (xor) {
            g2d.setXORMode(shape.getColor());
        } else {
            g2d.setColor(shape.getColor());
        }

        g2d.draw(new Ellipse2D.Double(x, y, w, h));
    }
}
