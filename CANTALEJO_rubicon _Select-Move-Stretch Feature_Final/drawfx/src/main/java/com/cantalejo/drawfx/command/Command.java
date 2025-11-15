package com.cantalejo.drawfx.command;

public interface Command {
    void execute();
    void undo();
    void redo();
}
