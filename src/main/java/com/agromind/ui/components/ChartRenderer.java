package com.agromind.ui.components;

import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

public class ChartRenderer extends JPanel {
    
    public enum ChartType { PIE, BAR, LINE }
    
    private ChartType type;
    private Map<String, Double> data;
    private String title;
    
    // Animation fields
    private double animationProgress = 1.0; 
    private Timer animTimer;
    
    // Hover fields for tooltips
    private Point mousePoint = null;
    private String hoveredKey = null;
    private Double hoveredVal = null;

    public ChartRenderer(ChartType type, Map<String, Double> data, String title) {
        this.type = type;
        this.data = data;
        this.title = title;
        setOpaque(false);
        
        addAncestorListener(new AncestorListener() {
            @Override public void ancestorAdded(AncestorEvent event) { startAnimation(); }
            @Override public void ancestorRemoved(AncestorEvent event) {}
            @Override public void ancestorMoved(AncestorEvent event) {}
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mousePoint = e.getPoint();
                repaint();
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                mousePoint = null;
                repaint();
            }
        });
    }
    
    public void startAnimation() {
        if (type != ChartType.PIE) return; // Currently only animating pie
        
        animationProgress = 0.0;
        if (animTimer != null && animTimer.isRunning()) animTimer.stop();
        
        animTimer = new Timer(16, e -> {
            animationProgress += 0.02;
            if (animationProgress >= 1.0) {
                animationProgress = 1.0;
                animTimer.stop();
            }
            repaint();
        });
        animTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (title != null && !title.isEmpty()) {
            g2.setFont(UIUtils.SUBHEADER_FONT);
            g2.setColor(UIUtils.TEXT_PRIMARY);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 25);
        }

        if (type == ChartType.PIE) {
            drawPieChart(g2);
        } else if (type == ChartType.BAR) {
            drawBarChart(g2);
        } else if (type == ChartType.LINE) {
            drawLineChart(g2);
        }

        if (hoveredKey != null && mousePoint != null) {
            drawTooltip(g2);
        }

        g2.dispose();
    }
    
    private void drawTooltip(Graphics2D g2) {
        String line1 = "Year: " + hoveredKey;
        String line2 = "Production: " + String.format("%.1f", hoveredVal) + " MT";
        
        g2.setFont(UIUtils.NORMAL_FONT);
        FontMetrics fm = g2.getFontMetrics();
        int tw = Math.max(fm.stringWidth(line1), fm.stringWidth(line2)) + 30;
        int th = 65;
        
        int tx = mousePoint.x + 15;
        int ty = mousePoint.y - th - 5;
        
        // Keep in bounds
        if (tx + tw > getWidth()) tx = mousePoint.x - tw - 15;
        if (ty < 0) ty = mousePoint.y + 15;

        // Shadow
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fill(new RoundRectangle2D.Double(tx + 2, ty + 2, tw, th, 12, 12));
        
        // Card
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Double(tx, ty, tw, th, 12, 12));
        g2.setColor(new Color(16, 185, 129)); // Border match
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(tx, ty, tw, th, 12, 12));
        
        // Text
        g2.setColor(UIUtils.TEXT_PRIMARY);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.drawString(line1, tx + 15, ty + 25);
        g2.setColor(UIUtils.TEXT_SECONDARY);
        g2.setFont(UIUtils.NORMAL_FONT);
        g2.drawString(line2, tx + 15, ty + 48);
    }

    private void drawPieChart(Graphics2D g2) {
        double total = data.values().stream().mapToDouble(Double::doubleValue).sum();
        double currentAngle = 90; // Start at top
        
        int size = Math.max(10, Math.min(getWidth() - 100, getHeight() - 120));
        int x = Math.max(0, (getWidth() - size) / 2);
        int y = 30;

        // Custom Pie Colors
        Color[] colors = {
            new Color(16, 185, 129), // Emerald
            new Color(245, 158, 11), // Amber
            new Color(99, 102, 241), // Indigo
            new Color(236, 72, 153), // Pink
            new Color(168, 85, 247), // Purple
            new Color(148, 163, 184) // Slate
        };
        
        int colorIdx = 0;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            double sliceAngle = (entry.getValue() / total) * -360;
            // Apply animation progress
            double drawAngle = sliceAngle * animationProgress;
            
            g2.setColor(colors[colorIdx % colors.length]);
            Arc2D.Double arc = new Arc2D.Double(x, y, size, size, currentAngle, drawAngle, Arc2D.PIE);
            g2.fill(arc);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2f));
            g2.draw(arc);
            
            // Draw labels near the slice (Image 4 style)
            if (animationProgress > 0.5) {
                double midAngle = Math.toRadians(-(currentAngle + (sliceAngle / 2)));
                int labelR = (size / 2) + 40;
                int lx = (int) (x + (size / 2) + Math.cos(midAngle) * labelR);
                int ly = (int) (y + (size / 2) + Math.sin(midAngle) * labelR);
                
                g2.setFont(UIUtils.SMALL_FONT);
                g2.setColor(colors[colorIdx % colors.length]);
                String label = String.format("%s: %.0f%%", entry.getKey(), (entry.getValue() / total) * 100);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(label, lx - (fm.stringWidth(label) / 2), ly);
            }

            currentAngle += drawAngle; 
            colorIdx++;
        }

        // Legend Horizontally at bottom (Image 4 has it at bottom)
        int legendY = y + size + 70;
        int totalLegendWidth = 0;
        g2.setFont(UIUtils.SMALL_FONT);
        FontMetrics fm = g2.getFontMetrics();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            totalLegendWidth += 25 + fm.stringWidth(entry.getKey()) + 20;
        }
        
        int startX = (getWidth() - totalLegendWidth) / 2;
        colorIdx = 0;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            g2.setColor(colors[colorIdx % colors.length]);
            g2.fillRect(startX, legendY - 10, 15, 15);
            g2.setColor(UIUtils.TEXT_SECONDARY);
            g2.drawString(entry.getKey(), startX + 25, legendY + 2);
            startX += 25 + fm.stringWidth(entry.getKey()) + 20;
            colorIdx++;
        }
    }

    private void drawBarChart(Graphics2D g2) {
        // Redundant but keeping it incase
        double maxVal = data.values().stream().mapToDouble(Double::doubleValue).max().orElse(1);
        int marginX = 50, marginY = 50;
        int width = getWidth() - (marginX * 2);
        int height = Math.max(50, getHeight() - (marginY * 2));
        int barWidth = Math.min((width / data.size()) - 10, 60);

        g2.setColor(new Color(226, 232, 240));
        g2.drawLine(marginX, marginY, marginX, marginY + height); 
        g2.drawLine(marginX, marginY + height, marginX + width, marginY + height); 
        
        int startX = marginX + 20;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int barHeight = (int) ((entry.getValue() / maxVal) * (height - 20));
            int yPos = marginY + height - barHeight;
            g2.setColor(UIUtils.CARD_BLUE);
            g2.fill(new RoundRectangle2D.Double(startX, yPos, barWidth, barHeight, 5, 5));
            g2.setColor(UIUtils.TEXT_SECONDARY);
            g2.setFont(UIUtils.SMALL_FONT);
            String k = entry.getKey();
            g2.drawString(k, startX + (barWidth / 2) - (g2.getFontMetrics().stringWidth(k) / 2), marginY + height + 15);
            startX += barWidth + 10;
        }
    }

    private void drawLineChart(Graphics2D g2) {
        int marginX = 60;
        int marginY = 40;
        int width = getWidth() - (marginX * 2) - 40;
        int height = Math.max(50, getHeight() - marginY - 90); 

        double maxVal = 400.0;
        int yLines = 4;
        
        // Draw Grid Lines (Dashed) & Y Labels
        g2.setFont(UIUtils.SMALL_FONT);
        FontMetrics fm = g2.getFontMetrics();
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0);
        Stroke solid = new BasicStroke(1f);
        
        for (int i = 0; i <= yLines; i++) {
            int y = marginY + height - (i * (height / yLines));
            int val = i * 100;
            
            // Text
            g2.setColor(UIUtils.TEXT_SECONDARY);
            String valStr = String.valueOf(val);
            g2.drawString(valStr, marginX - fm.stringWidth(valStr) - 10, y + 5);
            
            // Grid line
            g2.setColor(new Color(226, 232, 240));
            if (i == 0) {
                g2.setStroke(solid);
            } else {
                g2.setStroke(dashed);
            }
            g2.drawLine(marginX, y, marginX + width, y);
        }
        
        // X-axis & Y-axis Solid solid
        g2.setStroke(solid);
        g2.drawLine(marginX, marginY, marginX, marginY + height);

        // Draw Line
        int[] xPoints = new int[data.size()];
        int[] yPoints = new int[data.size()];
        String[] keys = new String[data.size()];
        Double[] vals = new Double[data.size()];
        
        int xStep = width / Math.max(1, data.size() - 1);
        int startX = marginX;
        
        int idx = 0;
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            int x = startX + (idx * xStep);
            int y = marginY + height - (int) ((entry.getValue() / maxVal) * height);
            
            xPoints[idx] = x;
            yPoints[idx] = y;
            keys[idx] = entry.getKey();
            vals[idx] = entry.getValue();
            
            // Draw X Label
            g2.setColor(UIUtils.TEXT_SECONDARY);
            g2.drawString(entry.getKey(), x - (fm.stringWidth(entry.getKey()) / 2), marginY + height + 25);
            
            idx++;
        }
        
        // Anti-aliased Green Line
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(16, 185, 129)); // #10B981
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        for (int i = 0; i < xPoints.length - 1; i++) {
            g2.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
        }
        
        // Draw Dots/Waypoints & Check Hover
        hoveredKey = null;
        hoveredVal = null;
        int dotSize = 10;
        for (int i = 0; i < xPoints.length; i++) {
            boolean isHovered = false;
            if (mousePoint != null) {
                double d = Math.sqrt(Math.pow(mousePoint.x - xPoints[i], 2) + Math.pow(mousePoint.y - yPoints[i], 2));
                if (d < 15) {
                    isHovered = true;
                    hoveredKey = keys[i];
                    hoveredVal = vals[i];
                }
            }

            Ellipse2D.Double dot = new Ellipse2D.Double(xPoints[i] - dotSize/2.0, yPoints[i] - dotSize/2.0, dotSize, dotSize);
            g2.setColor(isHovered ? new Color(16, 185, 129) : Color.WHITE);
            g2.fill(dot);
            g2.setColor(new Color(16, 185, 129));
            g2.setStroke(new BasicStroke(isHovered ? 4.0f : 2.5f));
            g2.draw(dot);
        }
        
        // Production Legend
        g2.setColor(new Color(16, 185, 129)); // #10B981
        String legText = "Production (MT)";
        int lx = marginX + (width / 2) - (fm.stringWidth(legText) / 2);
        int ly = marginY + height + 55;
        g2.drawString(legText, lx + 15, ly + 5);
        
        // draw tiny dot link
        Ellipse2D.Double tinyDot = new Ellipse2D.Double(lx - 5, ly -1, 6, 6);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(tinyDot);
        g2.drawLine(lx - 12, ly+2, lx + 1, ly+2);
    }
}
