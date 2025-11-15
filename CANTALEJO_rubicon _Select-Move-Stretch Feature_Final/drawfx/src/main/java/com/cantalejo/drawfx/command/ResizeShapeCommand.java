package com.cantalejo.drawfx.command;

import com.cantalejo.drawfx.model.Shape;
import java.awt.Point;

public class ResizeShapeCommand implements Command {
    private final Shape shape;
    private final Point oldEnd;
    private final Point newEnd;

    public ResizeShapeCommand(Shape shape, Point oldEnd, Point newEnd) {
        this.shape = shape;
        this.oldEnd = new Point(oldEnd);
        this.newEnd = new Point(newEnd);
    }

    @Override
    public void execute() {
        shape.setEnd(newEnd);
    }

    @Override
    public void undo() {
        shape.setEnd(oldEnd);
    }

    @Override
    public void redo() {
        execute();
    }
}
