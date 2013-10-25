package ch.hsr.slibrary.gui.validation;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class ErrorBorder extends AbstractBorder {
    private Color borderColour;
    private int thickness;
    private Icon errorIcon;

    public ErrorBorder(Color colour, int thickness, Icon errorIcon) {
        this.borderColour = colour;
        this.thickness = thickness;
        this.errorIcon = errorIcon;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        if ((this.thickness > 0) && (g instanceof Graphics2D)) {
            Graphics2D g2d = (Graphics2D) g;

            Color oldColor = g2d.getColor();
            g2d.setColor(this.borderColour);

            Shape outer;
            Shape inner;

            int offs = this.thickness;
            int size = offs + offs;

            outer = new Rectangle2D.Float(x, y, width, height);
            inner = new Rectangle2D.Float(x + offs, y + offs, width - size, height - size);
            this.errorIcon.paintIcon(c, g, 0, height - this.errorIcon.getIconHeight());

            Path2D path = new Path2D.Float(Path2D.WIND_EVEN_ODD);
            path.append(outer, false);
            path.append(inner, false);
            g2d.fill(path);
            g2d.setColor(oldColor);
        }
    }
}
