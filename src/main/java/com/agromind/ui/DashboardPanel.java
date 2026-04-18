package com.agromind.ui;

import com.agromind.util.AnimationUtils;
import com.agromind.util.UIUtils;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class DashboardPanel extends JPanel {
    private JPanel welcomePanel;
    private JPanel metricsPanel;
    private JPanel gridPanel;
    private JPanel fCards;
    private float entranceAlpha = 1f;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(0, 0, 0, 0));

        initUI();
    }

    private void initUI() {
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(UIUtils.BACKGROUND_COLOR);
        scrollContent.setBorder(new EmptyBorder(40, 40, 40, 40));

        // --- 1. Welcome Section ---
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setOpaque(false);
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Icon above title
        JPanel logoPnl = UIUtils.createRoundedPanel(25, new Color(220, 252, 231));
        logoPnl.setPreferredSize(new Dimension(80, 80));
        logoPnl.setMaximumSize(new Dimension(80, 80));
        logoPnl.setLayout(new GridBagLayout());
        JLabel logoIcn = new JLabel("🌱");
        logoIcn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        logoIcn.setForeground(new Color(22, 163, 74));
        logoPnl.add(logoIcn);
        logoPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(logoPnl);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel titleLabel = new JLabel("Welcome to AgroMind AI");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(15, 23, 42)); // Slate 900
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel subTitleLabel = new JLabel("Smart Agriculture Decision Support System");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subTitleLabel.setForeground(new Color(71, 85, 105)); // Slate 600
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(subTitleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        JLabel descLabel = new JLabel("Empowering farmers with AI-driven insights for better crop decisions");
        descLabel.setFont(UIUtils.NORMAL_FONT);
        descLabel.setForeground(UIUtils.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(descLabel);
        
        scrollContent.add(welcomePanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 40)));

        // --- 2. 4 Metric Cards ---
        metricsPanel = new JPanel();
        metricsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        metricsPanel.setOpaque(false);
        metricsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        metricsPanel.add(createMetricCard("Total Crops", "10", new Color(34, 197, 94), "🛢")); // Green
        metricsPanel.add(createMetricCard("AI Accuracy", "96%", new Color(59, 130, 246), "🏅")); // Blue
        metricsPanel.add(createMetricCard("Recommendations", "1.2K+", new Color(168, 85, 247), "📈")); // Purple
        metricsPanel.add(createMetricCard("System Status", "Active", new Color(249, 115, 22), "🌿")); // Orange
        
        scrollContent.add(metricsPanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 50)));

        // --- 3. Crop Gallery Header ---
        JPanel galleryHeader = new JPanel();
        galleryHeader.setLayout(new BoxLayout(galleryHeader, BoxLayout.Y_AXIS));
        galleryHeader.setOpaque(false);
        galleryHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel glTitle = new JLabel("Crop Gallery");
        glTitle.setFont(UIUtils.HEADER_FONT);
        glTitle.setForeground(new Color(15, 23, 42));
        glTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        galleryHeader.add(glTitle);
        
        JLabel glSub = new JLabel("Browse through our comprehensive database of crops supported by AgroMind AI");
        glSub.setFont(UIUtils.NORMAL_FONT);
        glSub.setForeground(UIUtils.TEXT_SECONDARY);
        glSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        glSub.setBorder(new EmptyBorder(10, 0, 20, 0));
        galleryHeader.add(glSub);
        
        // Wrap in a left-aligned container to restrict width to the grid
        JPanel leftAlignWrapper = new JPanel(new BorderLayout());
        leftAlignWrapper.setOpaque(false);
        leftAlignWrapper.add(galleryHeader, BorderLayout.WEST);
        leftAlignWrapper.setMaximumSize(new Dimension(1100, 80));
        
        scrollContent.add(leftAlignWrapper);

        // --- 4. Crop Gallery Grid ---
        gridPanel = new JPanel(new GridLayout(0, 5, 20, 20)); // 5 columns
        gridPanel.setOpaque(false);
        gridPanel.setMaximumSize(new Dimension(1100, 1000)); // Increased height

        String[] crops = {"Rice", "Wheat", "Cotton", "Sugarcane", "Maize", "Pulses", "Groundnut", "Soybean", "Potato", "Tomato"};
        Color[] cropColors = {
            new Color(134, 239, 172), new Color(253, 230, 138), new Color(224, 242, 254), new Color(167, 243, 208),
            new Color(254, 240, 138), new Color(251, 207, 232), new Color(254, 215, 170), new Color(231, 229, 228),
            new Color(253, 186, 116), new Color(254, 202, 202)
        };

        for (int i = 0; i < crops.length; i++) {
            gridPanel.add(createGalleryCard(crops[i], cropColors[i % cropColors.length]));
        }

        scrollContent.add(gridPanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 50)));
        
        // --- 5. Key Features ---
        JPanel featHeader = new JPanel();
        featHeader.setLayout(new BoxLayout(featHeader, BoxLayout.Y_AXIS));
        featHeader.setOpaque(false);
        JLabel fTitle = new JLabel("Key Features");
        fTitle.setFont(UIUtils.HEADER_FONT);
        fTitle.setForeground(new Color(15, 23, 42));
        fTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        featHeader.add(fTitle);
        
        JPanel featWrapper = new JPanel(new BorderLayout());
        featWrapper.setOpaque(false);
        featWrapper.add(featHeader, BorderLayout.WEST);
        featWrapper.setMaximumSize(new Dimension(1100, 50));
        scrollContent.add(featWrapper);
        
        // Add the three feature cards
        fCards = new JPanel(new GridLayout(1, 3, 25, 0));
        fCards.setOpaque(false);
        fCards.setMaximumSize(new Dimension(1100, 400)); // Increased height
        
        fCards.add(createFeatureCard("🌱", "AI Crop\nRecommendation", "Get top 3 crop suggestions based on soil type, season, and rainfall with confidence scores.", new Color(22, 163, 74), new Color(220, 252, 231)));
        fCards.add(createFeatureCard("📊", "Analytics Dashboard", "Visualize crop distribution, production trends, and state-wise agriculture data.", new Color(37, 99, 235), new Color(219, 234, 254)));
        fCards.add(createFeatureCard("💡", "Smart Advice", "Receive intelligent farming suggestions based on production trend analysis.", new Color(147, 51, 234), new Color(243, 232, 255)));
        
        scrollContent.add(fCards);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 50)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(24);

        add(scrollPane, BorderLayout.CENTER);
        
        // Initial state for animation
        entranceAlpha = 0f;
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        if (entranceAlpha < 1.0f) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, entranceAlpha));
            super.paintChildren(g2);
            g2.dispose();
        } else {
            super.paintChildren(g);
        }
    }
    
    private void setAllVisible(boolean visible) {
        entranceAlpha = visible ? 1f : 0f;
        repaint();
    }

    public void animateEntrance() {
        entranceAlpha = 0f;
        
        // Staggered fade in
        AnimationUtils.fadeIn(600, alpha -> {
            entranceAlpha = alpha;
            repaint();
        }, null);
    }
    
    private JPanel createFeatureCard(String ecoIcon, String title, String desc, Color iconColor, Color iconBg) {
        JPanel p = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(30, 30, 30, 30));
        p.setPreferredSize(new Dimension(350, 280));
        
        JPanel iconWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconWrapper.setOpaque(false);
        JPanel iconPnl = UIUtils.createRoundedPanel(12, iconBg);
        iconPnl.setPreferredSize(new Dimension(50, 50));
        iconPnl.setLayout(new GridBagLayout());
        JLabel i = new JLabel(ecoIcon);
        i.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        i.setForeground(iconColor);
        iconPnl.add(i);
        iconWrapper.add(iconPnl);
        
        p.add(iconWrapper);
        p.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Multi-line title support (using html)
        String htmlTitle = "<html><b>" + title.replace("\n", "<br>") + "</b></html>";
        JLabel titleLabel = new JLabel(htmlTitle);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(15, 23, 42)); // Slate 900
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(titleLabel);
        
        p.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JLabel descLabel = new JLabel("<html><body style='width: 200px; color:#64748b; line-height: 1.5;'>" + desc + "</body></html>");
        descLabel.setFont(UIUtils.NORMAL_FONT);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        p.add(descLabel);
        p.add(Box.createVerticalGlue()); // Push everything up smoothly
        
        return p;
    }

    private JPanel createMetricCard(String title, String mainValue, Color bgColor, String rightIcon) {
        JPanel card = UIUtils.createRoundedPanel(20, bgColor);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(250, 140));

        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setOpaque(false);
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIUtils.NORMAL_FONT);
        lblTitle.setForeground(new Color(255, 255, 255, 230));

        JLabel lblValue = new JLabel(mainValue);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(Color.WHITE);

        leftCol.add(lblTitle);
        leftCol.add(Box.createRigidArea(new Dimension(0, 10)));
        leftCol.add(lblValue);
        
        card.add(leftCol, BorderLayout.WEST);
        
        JLabel iconL = new JLabel(rightIcon);
        iconL.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        iconL.setForeground(new Color(255, 255, 255, 180));
        iconL.setVerticalAlignment(SwingConstants.CENTER);
        card.add(iconL, BorderLayout.EAST);

        return card;
    }

    private JPanel createGalleryCard(String cropName, Color placeholderColor) {
        BufferedImage img = null;
        try {
            File file = new File("src/main/resources/images/" + cropName + ".png");
            if(file.exists()) {
                img = ImageIO.read(file);
            }
        } catch (Exception e) {}
        
        final BufferedImage finalImg = img;

        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                
                // Draw rounded white background
                g2.setColor(UIUtils.CARD_BG);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                
                if (finalImg != null) {
                    // Draw image at top with rounding
                    g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 60, 15, 15));
                    g2.drawImage(finalImg, 0, 0, getWidth(), getHeight() - 60, null);
                    g2.setClip(null);
                } else {
                    // Fallback to placeholder color 
                    g2.setColor(placeholderColor);
                    g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 60, 15, 15));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 60, 15, 15));
                    g2.setClip(null);
                }
                
                // Subtle border
                g2.setColor(new Color(226, 232, 240));
                g2.setStroke(new BasicStroke(1.2f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(180, 260));

        JLabel nameLabel = new JLabel(cropName, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(new Color(15, 23, 42));
        nameLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        card.add(nameLabel, BorderLayout.SOUTH);
        return card;
    }
}
