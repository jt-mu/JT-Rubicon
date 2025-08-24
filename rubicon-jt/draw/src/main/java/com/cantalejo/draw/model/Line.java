package com.cantalejo.draw.model;


import com.cantalejo.draw.service.LineRendererService;
import lombok.Data;
import com.cantalejo.drawfx.model.Shape;
import java.awt.*;


@Data
public class Line extends Shape {

    public Line(Point start, Point end){
        super(start);
        this.setEnd(end);
        this.setColor(Color.RED);
        this.setRendererService(new LineRendererService());
    }
}
