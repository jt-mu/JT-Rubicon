package com.cantalejo.draw.command;

import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.command.Command;
import java.awt.Color;

public class ChangeFillCommand implements Command {
    private final Shape shape;
    private final Color newFill;
    private Color oldFill;

    public ChangeFillCommand(Shape shape, Color newFill) {
        this.shape = shape;
        this.newFill = newFill;
    }

    @Override
    public void execute() {
        oldFill = shape.getFill();
        shape.setFill(newFill);
    }

    @Override
    public void undo() {
        shape.setFill(oldFill);
    }

    @Override
    public void redo() {
        execute();
    }
}
