package com.cantalejo.draw.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class IconButtonFactory {

    private IconButtonFactory() {}

    public static JButton createIconButton(String resourcePath, String tooltipText, Runnable action) {
        URL url = IconButtonFactory.class.getResource(resourcePath);
        ImageIcon icon = null;
        if (url != null) {
            icon = new ImageIcon(url);
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        }

        JButton btn = new JButton(icon);
        btn.setToolTipText(tooltipText);
        btn.setFocusable(false);
        btn.addActionListener(e -> action.run());
        return btn;
    }
}
