package com.agromind.ui;

import com.agromind.util.AnimationUtils;
import com.agromind.util.UIUtils;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private Map<String, JPanel> panels = new HashMap<>();
    private String currentView = "Dashboard";
    private float globalAlpha = 1.0f;

    public MainFrame() {
        setTitle("AgroMind AI - Decision Support System");
        setSize(1300, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout) {
            @Override
            protected void paintChildren(Graphics g) {
                if (globalAlpha < 1.0f) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalAlpha));
                    super.paintChildren(g2);
                    g2.dispose();
                } else {
                    super.paintChildren(g);
                }
            }
        };
        mainContentPanel.setBackground(UIUtils.BACKGROUND_COLOR);

        // Initialize panels matching the redesigned navigation
        DashboardPanel dashboardPanel = new DashboardPanel();
        CropRecommendationPanel recommendationPanel = new CropRecommendationPanel();
        AnalyticsPanel analyticsPanel = new AnalyticsPanel();
        FarmerAdvicePanel advicePanel = new FarmerAdvicePanel();
        WeatherPanel weatherPanel = new WeatherPanel();

        // Add them to the map and card layout
        registerPanel("Dashboard", dashboardPanel);
        registerPanel("Recommendations", recommendationPanel);
        registerPanel("Analytics", analyticsPanel);
        registerPanel("Advice", advicePanel);
        registerPanel("Weather", weatherPanel);

        NavigationPanel navPanel = new NavigationPanel(this);

        add(navPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);
        
        // Initial animation for Dashboard
        SwingUtilities.invokeLater(() -> dashboardPanel.animateEntrance());
    }

    private void registerPanel(String name, JPanel panel) {
        panels.put(name, panel);
        mainContentPanel.add(panel, name);
    }

    public void navigateTo(String viewName) {
        if (viewName.equals(currentView)) return;

        JPanel nextPanel = panels.get(viewName);
        if (nextPanel == null) return;

        // Simple and smooth cross-fade animation
        // 1. Show the next panel instantly
        cardLayout.show(mainContentPanel, viewName);
        currentView = viewName;
        
        // 2. Animate the 'entry' effect
        AnimationUtils.animate(0f, 1f, 300, 
            alpha -> {
                globalAlpha = alpha;
                mainContentPanel.repaint();
            },
            () -> {
                // If dashboard, trigger inner animations
                if (nextPanel instanceof DashboardPanel) {
                    ((DashboardPanel) nextPanel).animateEntrance();
                }
            }
        );
    }
}
