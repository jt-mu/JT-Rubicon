package com.cantalejo.drawfx.service;

import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public final class MoverService {
    public void  move(Shape shape, Point newLoc){
        shape.setLocation( newLoc);
     }
}
