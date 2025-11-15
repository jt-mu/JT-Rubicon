package com.cantalejo.draw.controller;

import com.cantalejo.drawfx.service.AppService;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class DrawingWindowController implements WindowListener,
        WindowFocusListener,
        WindowStateListener {

    private final AppService appService;

    public DrawingWindowController(AppService appService) {
        this.appService = appService;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        // not used
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        // not used
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // not used
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // You can prompt a save or confirm exit here if needed
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // not used
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // not used
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // not used
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // not used
    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        // not used
    }
}
