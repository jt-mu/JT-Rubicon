package com.cantalejo.draw.command;

import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.command.Command;
import java.awt.Color;

public class ChangeColorCommand implements Command {
    private final Shape shape;
    private final Color newColor;
    private Color oldColor;

    public ChangeColorCommand(Shape shape, Color newColor) {
        this.shape = shape;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        oldColor = shape.getColor();
        shape.setColor(newColor);
    }

    @Override
    public void undo() {
        shape.setColor(oldColor);
    }

    @Override
    public void redo() {
        execute();
    }
}
