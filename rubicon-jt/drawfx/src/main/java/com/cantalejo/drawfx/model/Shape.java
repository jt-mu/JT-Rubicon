package com.cantalejo.drawfx.model;

import com.cantalejo.drawfx.service.RendererService;
import lombok.Data;

import java.awt.*;
@Data
public abstract class Shape {
    private Point location;
    private Point end;
    private Color color;
    private RendererService rendererService;
    public Shape(Point location){
        this.setLocation(location);
        this.setEnd(location);
    }

}
