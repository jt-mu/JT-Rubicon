package com.cantalejo.draw.service;

import com.cantalejo.draw.model.Line;
import com.cantalejo.drawfx.service.RendererService;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;


public class LineRendererService implements RendererService {

    @Override
    public void render(Graphics g, Shape shape, boolean xor) {
        Line line = (Line) shape;
      //  g.setColor(shape.getColor());
        g.setXORMode(shape.getColor());
        g.drawLine(line.getLocation().x, line.getLocation().y, line.getEnd().x, line.getEnd().y);
    }
}