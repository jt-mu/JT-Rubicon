package com.cantalejo.draw.service;

import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.command.ModifyShapeCommand;
import com.cantalejo.drawfx.command.ResizeShapeCommand;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.model.Drawing;
import com.cantalejo.drawfx.service.AppService;
import com.cantalejo.draw.command.CreateShapeCommand;
import com.cantalejo.draw.command.ChangeColorCommand;
import com.cantalejo.draw.command.ChangeFillCommand;

import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Stack;

public class DrawingCommandAppService implements AppService {
    private final AppService delegate;
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public DrawingCommandAppService(AppService delegate) {
        this.delegate = delegate;
    }

    // shape/draw mode delegation
    @Override
    public ShapeMode getShapeMode() {
        return delegate.getShapeMode();
    }

    @Override
    public void setShapeMode(ShapeMode shapeMode) {
        delegate.setShapeMode(shapeMode);
    }

    @Override
    public DrawMode getDrawMode() {
        return delegate.getDrawMode();
    }

    @Override
    public void setDrawMode(DrawMode drawMode) {
        delegate.setDrawMode(drawMode);
    }

    // color/fill: create commands so color changes are undoable
    @Override
    public Color getColor() {
        return delegate.getColor();
    }

    @Override
    public void setColor(Color color) {
        Shape selected = getLastShape();
        if (selected != null) {
            Command c = new ChangeColorCommand(selected, color);
            c.execute();
            undoStack.push(c);
            redoStack.clear();
        }
        delegate.setColor(color);
    }

    @Override
    public Color getFill() {
        return delegate.getFill();
    }

    @Override
    public void setFill(Color color) {
        Shape selected = getLastShape();
        if (selected != null) {
            Command c = new ChangeFillCommand(selected, color);
            c.execute();
            undoStack.push(c);
            redoStack.clear();
        }
        delegate.setFill(color);
    }

    // move/scale delegate
    @Override
    public void move(Shape shape, Point newLoc) {
        delegate.move(shape, newLoc);
    }

    @Override
    public void scale(Shape shape, Point newEnd) {
        Point oldEnd = shape.getEnd();
        Command cmd = new ResizeShapeCommand(shape, oldEnd, newEnd);
        executeCommand(cmd);
        undoStack.push(cmd);
        redoStack.clear();
    }


    // create uses a CreateShapeCommand (undoable)
    @Override
    public void create(Shape shape) {
        Command c = new CreateShapeCommand((Drawing) delegate.getModel(), shape);
        c.execute();
        undoStack.push(c);
        redoStack.clear();
        // do NOT call delegate.create(shape) because the command already added it
    }


    @Override
    public void close(Shape shape) {
        delegate.close(shape);
    }

    @Override
    public Object getModel() {
        return delegate.getModel();
    }

    @Override
    public List<Shape> getShapes() {
        return delegate.getShapes();
    }

    // undo/redo
    @Override
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command c = undoStack.pop();
            c.undo();
            redoStack.push(c);
        }
    }

    @Override
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command c = redoStack.pop();
            c.redo();
            undoStack.push(c);
        }
    }

    @Override
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }

    @Override
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    // helper: last shape in drawing
    private Shape getLastShape() {
        Drawing d = (Drawing) delegate.getModel();
        List<Shape> shapes = d.getShapes();
        if (shapes.isEmpty()) return null;
        return shapes.get(shapes.size() - 1);
    }

    @Override
    public void modify(Shape target, Shape before, Shape after) {
        Command cmd = new ModifyShapeCommand(target, before, after);
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }

    @Override
    public void executeCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear();
    }


}

