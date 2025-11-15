package com.cantalejo.draw.command;
import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.model.Shape;
import java.awt.Point;

public class MoveShapeCommand implements Command {
    private final Shape shape;
    private final Point oldLoc, newLoc;


    public MoveShapeCommand(Shape shape, Point oldLoc, Point newLoc) {
        this.shape = shape;
        this.oldLoc = oldLoc;
        this.newLoc = newLoc;
    }

    @Override public void execute() { shape.moveBy(newLoc.x - oldLoc.x, newLoc.y - oldLoc.y); }
    @Override public void undo() { shape.moveBy(oldLoc.x - newLoc.x, oldLoc.y - newLoc.y); }
    @Override public void redo() { execute(); }
}

