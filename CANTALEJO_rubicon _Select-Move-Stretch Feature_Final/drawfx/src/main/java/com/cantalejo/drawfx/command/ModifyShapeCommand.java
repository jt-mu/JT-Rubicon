package com.cantalejo.drawfx.command;

import com.cantalejo.drawfx.model.Shape;

public class ModifyShapeCommand implements Command {
    private final Shape target;
    private final Shape before;
    private final Shape after;

    public ModifyShapeCommand(Shape target, Shape before, Shape after) {
        this.target = target;
        this.before = before;
        this.after = after;
    }

    @Override
    public void execute() {
        target.copyFrom(after);
    }

    @Override
    public void undo() {
        target.copyFrom(before);
    }

    @Override
    public void redo() {
        execute();
    }
}
