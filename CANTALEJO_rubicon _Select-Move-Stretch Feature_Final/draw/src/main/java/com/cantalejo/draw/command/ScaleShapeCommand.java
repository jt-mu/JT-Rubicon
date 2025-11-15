package com.cantalejo.draw.command;

import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.model.Shape;

import java.awt.*;

public class ScaleShapeCommand implements Command {
    private final Shape shape;
    private final Point oldEnd, newEnd;

    public ScaleShapeCommand(Shape shape, Point oldEnd, Point newEnd) {
        this.shape = shape;
        this.oldEnd = oldEnd;
        this.newEnd = newEnd;
    }

    @Override public void execute() { shape.setEnd(newEnd); }
    @Override public void undo() { shape.setEnd(oldEnd); }
    @Override public void redo() { execute(); }
}


