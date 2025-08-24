package com.cantalejo.drawfx.service;

import com.cantalejo.drawfx.model.Shape;
import java.awt.*;

public interface RendererService {
    void render(Graphics g, Shape shape, boolean xor);
}
