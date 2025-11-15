package com.cantalejo.drawfx.model;

import java.awt.*;
import java.util.Collections;
import java.util.List;

/**
 * Base abstract Shape class for drawing.
 * Provides position (location, end), styling (stroke color, fill),
 * selection support, and utility methods for undo/redo via clone/copyFrom.
 */
public abstract class Shape implements Cloneable {
    private Point location;   // starting point (usually top-left after normalize)
    private Point end;        // ending point (usually bottom-right after normalize)
    private Color color;      // stroke color
    private Color fill;       // fill color
    private boolean selected; // selection flag

    public static final int HANDLE_SIZE = 7; // for resize handles

    // === CONSTRUCTOR ===
    public Shape(Point location) {
        this.location = (location == null) ? new Point(0, 0) : new Point(location);
        this.end = new Point(this.location);
    }

    // === GETTERS & SETTERS ===
    public Point getLocation() { return location; }
    public void setLocation(Point p) {
        this.location = (p == null) ? new Point(0, 0) : new Point(p);
    }

    public Point getEnd() { return end; }
    public void setEnd(Point p) {
        this.end = (p == null) ? new Point(location) : new Point(p);
    }

    public Color getColor() { return color; }
    public void setColor(Color c) { this.color = c; }

    public Color getFill() { return fill; }
    public void setFill(Color f) { this.fill = f; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean s) { this.selected = s; }

    // === MOVEMENT ===
    public void moveBy(int dx, int dy) {
        if (location != null) location.translate(dx, dy);
        if (end != null) end.translate(dx, dy);
    }

    // === SELECTION / HIT TEST ===
    /** Override this in subclasses to provide proper hit detection. */
    public boolean contains(Point p) { return false; }

    /** Override in subclasses to return handle positions for resizing. */
    public List<Point> getHandles() { return Collections.emptyList(); }

    public int getHandleIndex(Point p) {
        int size = HANDLE_SIZE;
        int i = 0;
        for (Point h : getHandles()) {
            Rectangle r = new Rectangle(h.x - size / 2, h.y - size / 2, size, size);
            if (r.contains(p)) return i;
            i++;
        }
        return -1;
    }

    public boolean isHandleClicked(Point p) {
        return getHandleIndex(p) >= 0;
    }

    // === DRAWING ===
    public abstract void draw(Graphics2D g);

    /**
     * Normalize coordinates so location = top-left, end = bottom-right.
     * Ensures consistency when drawing shapes like rectangle/ellipse.
     */
    public void normalize() {
        int x1 = Math.min(location.x, end.x);
        int y1 = Math.min(location.y, end.y);
        int x2 = Math.max(location.x, end.x);
        int y2 = Math.max(location.y, end.y);
        location = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    // === CLONING / COPYING ===
    @Override
    public Shape clone() {
        try {
            Shape s = (Shape) super.clone();
            s.location = this.location == null ? null : new Point(this.location);
            s.end = this.end == null ? null : new Point(this.end);
            s.color = this.color;
            s.fill = this.fill;
            s.selected = this.selected;
            return s;
        } catch (CloneNotSupportedException ex) {
            throw new AssertionError(ex);
        }
    }

    /**
     * Copy all fields from another shape into this one.
     * Used by commands (undo/redo).
     */
    public void copyFrom(Shape other) {
        if (other == null) return;
        this.location = other.location == null ? null : new Point(other.location);
        this.end = other.end == null ? null : new Point(other.end);
        this.color = other.color;
        this.fill = other.fill;
        this.selected = other.selected;
    }
}
