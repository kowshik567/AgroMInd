package com.agromind.ui;

import com.agromind.util.AnimationUtils;
import com.agromind.util.UIUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class NavigationPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel buttonsPanel;
    private NavButton currentlySelectedButton = null;

    public NavigationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(UIUtils.SIDEBAR_BG);
        setPreferredSize(new Dimension(280, 0));

        // Logo / Title area
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(UIUtils.SIDEBAR_BG);
        logoPanel.setBorder(new EmptyBorder(30, 20, 40, 20));

        JPanel titleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titleRow.setOpaque(false);
        
        JLabel iconLabel = new JLabel(loadIcon("crop", 28)); // Logo leaf
        iconLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("AgroMind AI");
        titleLabel.setFont(UIUtils.HEADER_FONT);
        titleLabel.setForeground(Color.WHITE);
        
        titleRow.add(iconLabel);
        titleRow.add(titleLabel);
        
        JLabel subTitleLabel = new JLabel("Smart Agriculture");
        subTitleLabel.setFont(UIUtils.SMALL_FONT);
        subTitleLabel.setForeground(new Color(180, 230, 200));
        subTitleLabel.setBorder(new EmptyBorder(0, 45, 0, 0)); // Align with title
        
        logoPanel.add(titleRow);
        logoPanel.add(subTitleLabel);

        add(logoPanel, BorderLayout.NORTH);

        // Navigation Buttons
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(UIUtils.SIDEBAR_BG);
        buttonsPanel.setBorder(new EmptyBorder(0, 0, 0, 20)); // Padding for rounded right edge

        NavButton btnDashboard = new NavButton("Home", "Dashboard", "home");
        NavButton btnRecommendations = new NavButton("Crop Recommendation", "Recommendations", "crop");
        NavButton btnAnalytics = new NavButton("Analytics Dashboard", "Analytics", "analytics");
        NavButton btnAdvice = new NavButton("Farmer Advice", "Advice", "advice");
        NavButton btnWeather = new NavButton("Weather Simulation", "Weather", "weather");

        buttonsPanel.add(btnDashboard);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(btnRecommendations);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(btnAnalytics);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(btnAdvice);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonsPanel.add(btnWeather);

        add(buttonsPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        footerPanel.setBackground(UIUtils.SIDEBAR_BG);
        footerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel statusLabel = new JLabel("System Status: Active");
        statusLabel.setFont(UIUtils.SMALL_FONT);
        statusLabel.setForeground(new Color(180, 230, 200));
        
        JLabel engineLabel = new JLabel("AI Engine Running");
        engineLabel.setIcon(createDotIcon(new Color(60, 230, 100), 8));
        engineLabel.setFont(UIUtils.SMALL_FONT);
        engineLabel.setForeground(Color.WHITE);
        
        footerPanel.add(statusLabel);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        footerPanel.add(engineLabel);
        
        add(footerPanel, BorderLayout.SOUTH);

        // Initial selection setup deferred so views exist first
        SwingUtilities.invokeLater(() -> selectButton(btnDashboard));
    }

    private ImageIcon loadIcon(String name, int size) {
        try {
            File f = new File("src/main/resources/icons/" + name + ".png");
            if (f.exists()) {
                Image img = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {}
        return new ImageIcon();
    }
    
    private Icon createDotIcon(Color color, int size) {
        return new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(x, y + 2, size, size);
            }
            @Override public int getIconWidth() { return size + 5; }
            @Override public int getIconHeight() { return size; }
        };
    }

    private class NavButton extends JButton {
        private String viewName;
        private boolean isSelected = false;
        private float hoverAlpha = 0.0f; // 0 to 1

        public NavButton(String text, String viewName, String iconName) {
            super("  " + text);
            this.viewName = viewName;
            setFont(UIUtils.NORMAL_FONT);
            setForeground(Color.WHITE);
            setIcon(loadIcon(iconName, 18));
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setHorizontalAlignment(SwingConstants.LEFT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addActionListener((ActionEvent e) -> {
                mainFrame.navigateTo(this.viewName);
                selectButton(this);
            });

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    AnimationUtils.animate(hoverAlpha, 1.0f, 150, alpha -> {
                        hoverAlpha = alpha;
                        repaint();
                    }, null);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    AnimationUtils.animate(hoverAlpha, 0.0f, 150, alpha -> {
                        hoverAlpha = alpha;
                        repaint();
                    }, null);
                }
            });
        }
        
        public void setCustomSelected(boolean selected) {
            this.isSelected = selected;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (isSelected) {
                g2.setColor(UIUtils.SIDEBAR_SELECTED);
                g2.fillRoundRect(-10, 0, getWidth() + 10, getHeight(), 20, 20);
            } else if (hoverAlpha > 0) {
                // Smooth hover effect
                Color baseColor = UIUtils.SIDEBAR_SELECTED;
                Color hoverColor = new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), (int)(hoverAlpha * 100)); // Lower alpha for hover
                g2.setColor(hoverColor);
                g2.fillRoundRect(-10, 0, getWidth() + 10, getHeight(), 20, 20);
            }
            
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private void selectButton(NavButton button) {
        if (currentlySelectedButton != null) {
            currentlySelectedButton.setCustomSelected(false);
        }
        currentlySelectedButton = button;
        currentlySelectedButton.setCustomSelected(true);
    }
}
