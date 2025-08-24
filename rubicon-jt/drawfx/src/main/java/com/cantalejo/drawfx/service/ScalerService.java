package com.cantalejo.drawfx.service;

import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public final class  ScalerService {
    void scale(Shape shape, Point newEnd){
        shape.setEnd(newEnd);
    }
}
