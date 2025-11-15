package com.cantalejo.draw.command;

import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.model.Drawing;
import com.cantalejo.drawfx.command.Command;

public class CreateShapeCommand implements Command {
    private final Drawing drawing;
    private final Shape shape;

    public CreateShapeCommand(Drawing drawing, Shape shape) {
        this.drawing = drawing;
        this.shape = shape;
    }

    @Override
    public void execute() {
        drawing.getShapes().add(shape);
    }

    @Override
    public void undo() {
        drawing.getShapes().remove(shape);
    }

    @Override
    public void redo() {
        execute();
    }
}
