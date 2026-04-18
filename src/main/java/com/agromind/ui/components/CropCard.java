package com.agromind.ui.components;

import com.agromind.model.Crop;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CropCard extends JPanel {
    
    private Crop crop;
    private double score;
    private String explanation;

    // Constructor for Dashboard (Gallery view, no score)
    public CropCard(Crop crop) {
        this(crop, -1, null);
    }

    // Constructor for Recommendation View (With score and AI reasoning)
    public CropCard(Crop crop, double score, String explanation) {
        this.crop = crop;
        this.score = score;
        this.explanation = explanation;
        
        setLayout(new BorderLayout());
        setBackground(UIUtils.CARD_BG);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Add content
        initUI();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // shadow or rounded corner could go here
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
        
        // outline
        g2.setColor(new Color(220, 224, 222));
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
        
        g2.dispose();
    }

    private void initUI() {
        setOpaque(false); // Make sure background drawing is visible
        
        // Top area: Image placeholder and Title
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);
        
        // Placeholder for image - drawing a colored rect or actual image
        JPanel imagePlaceholder = new JPanel() {
            BufferedImage img = null;
            {
                try {
                    java.io.File file = new java.io.File("src/main/resources/images/" + crop.getName() + ".png");
                    if(file.exists()) {
                        img = javax.imageio.ImageIO.read(file);
                    }
                } catch (Exception e) {}
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                
                if (img != null) {
                    g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(UIUtils.SECONDARY_COLOR);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
                    FontMetrics fm = g2.getFontMetrics();
                    String text = crop.getName().substring(0, 1);
                    int x = (getWidth() - fm.stringWidth(text)) / 2;
                    int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                    g2.drawString(text, x, y);
                }
                g2.dispose();
            }
        };
        imagePlaceholder.setPreferredSize(new Dimension(80, 80));
        imagePlaceholder.setOpaque(false);
        
        JLabel nameLabel = new JLabel(crop.getName());
        nameLabel.setFont(UIUtils.HEADER_FONT);
        nameLabel.setForeground(UIUtils.TEXT_PRIMARY);
        
        topPanel.add(imagePlaceholder, BorderLayout.WEST);
        topPanel.add(nameLabel, BorderLayout.CENTER);
        
        if (score >= 0) {
            JLabel scoreLabel = new JLabel(String.format("Score: %.1f%%", score));
            scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            scoreLabel.setForeground(UIUtils.PRIMARY_COLOR);
            topPanel.add(scoreLabel, BorderLayout.EAST);
        }
        
        add(topPanel, BorderLayout.NORTH);
        
        // Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        contentPanel.add(createInfoLabel("Soil Match:", crop.getIdealSoilType()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createInfoLabel("Season:", crop.getSeason()));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(createInfoLabel("Water Need:", crop.getWaterRequirement()));
        
        if (explanation != null && !explanation.isEmpty()) {
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            JLabel aiLabel = new JLabel("<html><b>AI Insight:</b> " + explanation + "</html>");
            aiLabel.setFont(UIUtils.SMALL_FONT);
            aiLabel.setForeground(UIUtils.TEXT_SECONDARY);
            contentPanel.add(aiLabel);
        }
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JLabel createInfoLabel(String key, String value) {
        JLabel label = new JLabel("<html><b>" + key + "</b> " + value + "</html>");
        label.setFont(UIUtils.NORMAL_FONT);
        label.setForeground(UIUtils.TEXT_PRIMARY);
        return label;
    }
}
