package com.cantalejo.draw.controller;

import com.cantalejo.drawfx.ActionCommand;
import com.cantalejo.drawfx.ShapeMode;
import com.cantalejo.drawfx.model.Shape;
import com.cantalejo.drawfx.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController implements ActionListener {
    private final AppService appService;
    private JLabel statusLabel;
    private com.cantalejo.draw.view.DrawingView drawingView;

    // toolbar/menu references
    public AbstractButton lineButton;
    public AbstractButton rectButton;
    public AbstractButton ellipseButton;
    public AbstractButton selectButton;
    public JButton undoButton;
    public JButton redoButton;
    public JButton colorButton;
    public JButton fillButton;

    // menu items
    public JMenuItem undoMenuItem;
    public JMenuItem redoMenuItem;
    public JMenuItem colorMenuItem;
    public JMenuItem fillMenuItem;
    public JRadioButtonMenuItem lineMenuItem;
    public JRadioButtonMenuItem rectMenuItem;
    public JRadioButtonMenuItem ellipseMenuItem;

    // currently selected shape (from DrawingController)
    private Shape selectedShape;

    public ActionController(AppService appService) {
        this.appService = appService;
    }

    public void setDrawingView(com.cantalejo.draw.view.DrawingView view) {
        this.drawingView = view;
    }

    public void setStatusLabel(JLabel label) {
        this.statusLabel = label;
    }

    public void setSelectedShape(Shape shape) {
        this.selectedShape = shape;
        updateStatus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionCommand cmd = ActionCommand.valueOf(e.getActionCommand());
        switch (cmd) {
            case LINE -> setShapeMode(ShapeMode.Line);
            case RECTANGLE -> setShapeMode(ShapeMode.Rectangle);
            case ELLIPSE -> setShapeMode(ShapeMode.Ellipse);
            case UNDO -> undo();
            case REDO -> redo();
            case COLOR -> chooseStrokeColor();
            case FILL -> chooseFillColor();
        }
    }

    // === SHAPE SELECTION ===
    public void setShapeMode(ShapeMode mode) {
        appService.setShapeMode(mode);
        updateShapeSelection(mode);
        updateStatus();
    }

    public void selectShapeTool() {
        setShapeMode(ShapeMode.Select);
    }

    public void updateShapeSelection(ShapeMode mode) {
        if (lineButton != null) lineButton.setSelected(mode == ShapeMode.Line);
        if (rectButton != null) rectButton.setSelected(mode == ShapeMode.Rectangle);
        if (ellipseButton != null) ellipseButton.setSelected(mode == ShapeMode.Ellipse);
        if (selectButton != null) selectButton.setSelected(mode == ShapeMode.Select);

        if (lineMenuItem != null) lineMenuItem.setSelected(mode == ShapeMode.Line);
        if (rectMenuItem != null) rectMenuItem.setSelected(mode == ShapeMode.Rectangle);
        if (ellipseMenuItem != null) ellipseMenuItem.setSelected(mode == ShapeMode.Ellipse);
    }

    // === UNDO / REDO ===
    public void undo() {
        appService.undo();
        updateUndoRedoButtons();
        if (drawingView != null) drawingView.repaint();
        updateStatus();
    }

    public void redo() {
        appService.redo();
        updateUndoRedoButtons();
        if (drawingView != null) drawingView.repaint();
        updateStatus();
    }

    public void updateUndoRedoButtons() {
        boolean canUndo = appService.canUndo();
        boolean canRedo = appService.canRedo();

        if (undoButton != null) undoButton.setEnabled(canUndo);
        if (redoButton != null) redoButton.setEnabled(canRedo);
        if (undoMenuItem != null) undoMenuItem.setEnabled(canUndo);
        if (redoMenuItem != null) redoMenuItem.setEnabled(canRedo);
    }

    // === COLORS ===
    public void chooseStrokeColor() {
        Color chosen = JColorChooser.showDialog(null, "Choose Stroke Color", appService.getColor());
        if (chosen != null) {
            setColor(chosen);
        }
    }

    public void chooseFillColor() {
        Color chosen = JColorChooser.showDialog(null, "Choose Fill Color", appService.getFill());
        if (chosen != null) {
            setFill(chosen);
        }
    }

    public void setColor(Color c) {
        if (selectedShape != null) {
            Shape before = selectedShape.clone();
            selectedShape.setColor(c);
            Shape after = selectedShape.clone();
            appService.modify(selectedShape, before, after); // tracked for undo
            drawingView.repaint();
        } else {
            appService.setColor(c); // default for new shapes
        }
        updateUndoRedoButtons();
        updateStatus();
    }

    public void setFill(Color f) {
        if (selectedShape != null) {
            Shape before = selectedShape.clone();
            selectedShape.setFill(f);
            Shape after = selectedShape.clone();
            appService.modify(selectedShape, before, after);
            drawingView.repaint();
        } else {
            appService.setFill(f);
        }
        updateUndoRedoButtons();
        updateStatus();
    }

    // === STATUS ===
    public void updateStatus() {
        if (statusLabel != null) {
            String shape = appService.getShapeMode().name();
            String color = (appService.getColor() != null ? appService.getColor().toString() : "none");
            String fill = (appService.getFill() != null ? appService.getFill().toString() : "none");
            String selected = (selectedShape != null ? "Selected" : "None");
            statusLabel.setText("Mode: " + shape + " | Stroke: " + color + " | Fill: " + fill + " | Selected: " + selected);
        }
    }

    // === REGISTER SHAPE MODIFICATIONS (for move/resize etc.) ===
    public void registerShapeModification(Shape shape, Shape before, Shape after) {
        appService.modify(shape, before, after);
        updateUndoRedoButtons();
        updateStatus();
    }
}
