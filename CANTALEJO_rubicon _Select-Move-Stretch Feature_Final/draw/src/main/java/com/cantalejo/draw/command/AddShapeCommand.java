package com.cantalejo.draw.command;

import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.AppService;

public class AddShapeCommand implements Command{
    Shape shape;
    AppService appService;

    public AddShapeCommand(AppService appService, Shape shape){
        this.shape = shape;
        this.appService = appService;
    }
    @Override
    public void execute() {
        appService.create(shape);
    }

    @Override
    public void undo() {
        appService.close(shape);
    }

    @Override
    public void redo() {
        appService.create(shape);
    }
}