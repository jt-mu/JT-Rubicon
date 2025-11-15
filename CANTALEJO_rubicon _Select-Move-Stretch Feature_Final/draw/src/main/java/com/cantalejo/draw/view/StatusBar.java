package com.cantalejo.draw.view;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private final JLabel label = new JLabel("Ready");

    public StatusBar() {
        setLayout(new BorderLayout());
        add(label, BorderLayout.WEST);
        setBorder(BorderFactory.createEtchedBorder());
    }

    public JLabel getLabel() { return label; }
}
