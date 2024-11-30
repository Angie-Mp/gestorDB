package vistas;

import javax.swing.JComponent;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

public class UIUtils {

    // Clase interna para el borde redondeado
    public static class RoundedBorder implements Border {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(color);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    // Método para aplicar un borde redondeado a cualquier componente
    public static void applyRoundedBorder(JComponent component, int radius, Color borderColor, Color backgroundColor) {
        component.setOpaque(false);
        component.setBackground(backgroundColor);
        component.setBorder(new RoundedBorder(radius, borderColor));
        component.setForeground(Color.BLACK); // Cambia el color del texto si es necesario
    }
}
