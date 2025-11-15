package com.cantalejo.draw.command;

import com.cantalejo.drawfx.DrawMode;
import com.cantalejo.drawfx.command.Command;
import com.cantalejo.drawfx.service.AppService;

public class SetDrawModeCommand implements Command {
    DrawMode drawMode;
    DrawMode prevDrawMode;
    AppService appService;
    public SetDrawModeCommand(AppService appService, DrawMode drawMode){
        this.appService = appService;
        this.drawMode = drawMode;
    }

    @Override
    public void execute() {
        prevDrawMode = appService.getDrawMode();
        appService.setDrawMode(drawMode);
    }

    @Override
    public void undo() {
        appService.setDrawMode(prevDrawMode);
    }

    @Override
    public void redo() {
        appService.setDrawMode(drawMode);
    }
}
