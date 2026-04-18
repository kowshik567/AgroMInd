package com.agromind.util;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class UIUtils {
    // Theme Colors
    public static final Color SIDEBAR_BG = new Color(5, 122, 56); // Deep Green
    public static final Color SIDEBAR_SELECTED = new Color(11, 166, 77); // Lighter Green
    public static final Color BACKGROUND_COLOR = new Color(248, 250, 252); // Off white
    public static final Color CARD_BG = Color.WHITE;
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(30, 41, 59); // Slate 800
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139); // Slate 500
    
    // Semantic/Card Colors
    public static final Color CARD_GREEN = new Color(16, 185, 129); // Emerald
    public static final Color CARD_BLUE = new Color(59, 130, 246); // Blue
    public static final Color CARD_PURPLE = new Color(168, 85, 247); // Purple
    public static final Color CARD_ORANGE = new Color(249, 115, 22); // Orange
    public static final Color CARD_TEAL = new Color(6, 182, 212); // Teal
    
    // Status Colors
    public static final Color STATUS_SUCCESS = new Color(220, 252, 231); // Light Green BG
    public static final Color STATUS_SUCCESS_TEXT = new Color(22, 101, 52);
    public static final Color STATUS_MODERATE = new Color(254, 252, 232); // Light Yellow BG
    public static final Color STATUS_MODERATE_TEXT = new Color(133, 77, 14);
    public static final Color STATUS_FAIL = new Color(254, 242, 242); // Light Red BG
    public static final Color STATUS_FAIL_TEXT = new Color(153, 27, 27);

    // Backwards compatibility colors
    public static final Color PRIMARY_COLOR = CARD_GREEN;
    public static final Color SECONDARY_COLOR = CARD_BLUE;
    public static final Color ACCENT_COLOR = CARD_ORANGE;
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font SUBHEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Creates a rounded panel with a specific background color and corner radius.
     */
    public static JPanel createRoundedPanel(int radius, Color bgColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }

    /**
     * Creates a rounded panel with a border.
     */
    public static JPanel createBorderedRoundedPanel(int radius, Color bgColor, Color borderColor) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                g2.setColor(borderColor);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        return panel;
    }
}
