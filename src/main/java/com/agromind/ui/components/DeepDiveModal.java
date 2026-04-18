package com.agromind.ui.components;

import com.agromind.model.PredictionResult;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public class DeepDiveModal extends JDialog {
    
    private PredictionResult result;
    private String[] axes = {"Soil Match", "Weather", "Market Potential", "Yield Efficiency", "Sustainability"};
    private double[] values = new double[5];

    public DeepDiveModal(Frame parent, PredictionResult result) {
        super(parent, "AI Decision Deep-Dive", true);
        this.result = result;
        
        // Mocking radar data based on confidence score
        double base = result.getConfidenceScore();
        values[0] = Math.min(100, base + (Math.random() * 10 - 5));
        values[1] = Math.min(100, base + (Math.random() * 10 - 5));
        values[2] = 60 + (Math.random() * 40);
        values[3] = base - (Math.random() * 10);
        values[4] = 80 + (Math.random() * 20);

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0)); // Transparent for glass effect
        setSize(700, 500);
        setLocationRelativeTo(parent);

        initUI();
    }

    private void initUI() {
        JPanel content = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Glass background
                g2.setColor(new Color(255, 255, 255, 240));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
                
                // Border
                g2.setStroke(new BasicStroke(2));
                g2.setColor(new Color(226, 232, 240));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 30, 30));
                
                g2.dispose();
            }
        };
        content.setLayout(new BorderLayout());
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel title = new JLabel("<html>AI Decision Analysis: <font color='#065F46'>" + result.getCrop().getName() + "</font></html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(title, BorderLayout.WEST);
        
        JButton closeBtn = new JButton("✕");
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        header.add(closeBtn, BorderLayout.EAST);
        
        content.add(header, BorderLayout.NORTH);

        // Center: Radar Chart
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawRadar(g);
            }
        };
        chartPanel.setOpaque(false);
        content.add(chartPanel, BorderLayout.CENTER);

        // Footer: Summary text
        JLabel summary = new JLabel("<html><div style='text-align: center; color: #64748b;'>" + 
            "The AI evaluated multiple layers of data including soil chemistry, moisture levels, and market trends to generate these scores." + 
            "</div></html>");
        summary.setFont(UIUtils.NORMAL_FONT);
        summary.setHorizontalAlignment(SwingConstants.CENTER);
        summary.setBorder(new EmptyBorder(20, 0, 0, 0));
        content.add(summary, BorderLayout.SOUTH);

        add(content);
    }

    private void drawRadar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2 - 40; // Adjust for content padding
        int cy = getHeight() / 2 - 20;
        int radius = 120;
        int numAxes = axes.length;

        // 1. Draw concentric pentagons (grids)
        g2.setStroke(new BasicStroke(1));
        for (int i = 1; i <= 5; i++) {
            float r = radius * (i / 5.0f);
            Path2D grid = new Path2D.Double();
            for (int j = 0; j < numAxes; j++) {
                double angle = Math.toRadians(j * 360.0 / numAxes - 90);
                double x = cx + r * Math.cos(angle);
                double y = cy + r * Math.sin(angle);
                if (j == 0) grid.moveTo(x, y);
                else grid.lineTo(x, y);
            }
            grid.closePath();
            g2.setColor(new Color(226, 232, 240));
            g2.draw(grid);
        }

        // 2. Draw axes lines and labels
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        for (int i = 0; i < numAxes; i++) {
            double angle = Math.toRadians(i * 360.0 / numAxes - 90);
            double x = cx + radius * Math.cos(angle);
            double y = cy + radius * Math.sin(angle);
            
            g2.setColor(new Color(203, 213, 225));
            g2.drawLine(cx, cy, (int)x, (int)y);
            
            // Labels
            double lx = cx + (radius + 40) * Math.cos(angle);
            double ly = cy + (radius + 20) * Math.sin(angle);
            g2.setColor(UIUtils.TEXT_PRIMARY);
            String label = axes[i];
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(label);
            g2.drawString(label, (int)(lx - tw / 2), (int)ly);
        }

        // 3. Draw Data Polygon
        Path2D dataPath = new Path2D.Double();
        for (int i = 0; i < numAxes; i++) {
            double angle = Math.toRadians(i * 360.0 / numAxes - 90);
            double r = radius * (values[i] / 100.0);
            double x = cx + r * Math.cos(angle);
            double y = cy + r * Math.sin(angle);
            if (i == 0) dataPath.moveTo(x, y);
            else dataPath.lineTo(x, y);
            
            // Draw points
            g2.setColor(UIUtils.SIDEBAR_BG);
            g2.fillOval((int)x - 4, (int)y - 4, 8, 8);
        }
        dataPath.closePath();
        
        // Fill
        g2.setColor(new Color(16, 185, 129, 60)); // Transparent Emerald
        g2.fill(dataPath);
        
        // Border
        g2.setStroke(new BasicStroke(3));
        g2.setColor(new Color(5, 150, 105)); // Emerald 600
        g2.draw(dataPath);

        g2.dispose();
    }
}
