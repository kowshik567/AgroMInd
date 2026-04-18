package com.agromind.ui;

import com.agromind.ui.components.ChartRenderer;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FarmerAdvicePanel extends JPanel {

    public FarmerAdvicePanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UIUtils.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        initUI();
    }

    private void initUI() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Smart Farmer Advice System");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        
        JLabel subTitleLabel = new JLabel("AI-powered insights and recommendations based on production trends");
        subTitleLabel.setFont(UIUtils.NORMAL_FONT);
        subTitleLabel.setForeground(UIUtils.TEXT_SECONDARY);
        subTitleLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        
        headerPanel.add(titleLabel);
        headerPanel.add(subTitleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Scroll Content
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setOpaque(false);

        // --- 1. Production Trend Analysis (Chart) ---
        JPanel chartSection = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        chartSection.setLayout(new BorderLayout());
        chartSection.setBorder(new EmptyBorder(25, 30, 25, 30));
        chartSection.setMaximumSize(new Dimension(1000, 600)); // Increased
        chartSection.setPreferredSize(new Dimension(1000, 450));
        chartSection.setMinimumSize(new Dimension(800, 400));
        chartSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel chartHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        chartHeader.setOpaque(false);
        JPanel chartIconPnl = UIUtils.createRoundedPanel(10, new Color(220, 252, 231)); // green-100 fallback is blue? In mockup Image 4 it's blue background for icon
        chartIconPnl.setBackground(new Color(219, 234, 254)); // Blue 100
        chartIconPnl.setPreferredSize(new Dimension(45, 45));
        chartIconPnl.setLayout(new GridBagLayout());
        JLabel chartIcon = new JLabel("📈"); // Fallback
        chartIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        chartIconPnl.add(chartIcon);
        
        JPanel chartTitleBox = new JPanel();
        chartTitleBox.setLayout(new BoxLayout(chartTitleBox, BoxLayout.Y_AXIS));
        chartTitleBox.setOpaque(false);
        JLabel lblChartTitle = new JLabel("Production Trend Analysis");
        lblChartTitle.setFont(UIUtils.HEADER_FONT);
        lblChartTitle.setForeground(UIUtils.TEXT_PRIMARY);
        JLabel lblChartSub = new JLabel("Historical production data for intelligent forecasting");
        lblChartSub.setFont(UIUtils.NORMAL_FONT);
        lblChartSub.setForeground(UIUtils.TEXT_SECONDARY);
        chartTitleBox.add(lblChartTitle);
        chartTitleBox.add(lblChartSub);
        
        chartHeader.add(chartIconPnl);
        chartHeader.add(chartTitleBox);
        chartSection.add(chartHeader, BorderLayout.NORTH);

        Map<String, Double> data = new LinkedHashMap<>();
        data.put("2018", 290.0); data.put("2019", 295.0); data.put("2020", 310.0);
        data.put("2021", 305.0); data.put("2022", 330.0); data.put("2023", 345.0);
        data.put("2024", 360.0); data.put("2025", 380.0); data.put("2026", 400.0);

        ChartRenderer chart = new ChartRenderer(ChartRenderer.ChartType.LINE, data, "");
        chartSection.add(chart, BorderLayout.CENTER);
        scrollContent.add(chartSection);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 2. AI-Generated Recommendations ---
        JPanel recSection = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        recSection.setLayout(new BorderLayout(0, 20));
        recSection.setBorder(new EmptyBorder(25, 30, 25, 30));
        recSection.setMaximumSize(new Dimension(1000, 800)); // Increased for multi-row
        recSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel recHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        recHeader.setOpaque(false);
        JPanel recIconPnl = UIUtils.createRoundedPanel(10, new Color(254, 243, 199)); // Amber 100
        recIconPnl.setPreferredSize(new Dimension(45, 45));
        recIconPnl.setLayout(new GridBagLayout());
        JLabel recIcon = new JLabel("💡"); 
        recIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        recIconPnl.add(recIcon);
        
        JPanel recTitleBox = new JPanel();
        recTitleBox.setLayout(new BoxLayout(recTitleBox, BoxLayout.Y_AXIS));
        recTitleBox.setOpaque(false);
        JLabel lblRecTitle = new JLabel("AI-Generated Recommendations");
        lblRecTitle.setFont(UIUtils.HEADER_FONT);
        lblRecTitle.setForeground(UIUtils.TEXT_PRIMARY);
        JLabel lblRecSub = new JLabel("Smart suggestions to improve your farming practices");
        lblRecSub.setFont(UIUtils.NORMAL_FONT);
        lblRecSub.setForeground(UIUtils.TEXT_SECONDARY);
        recTitleBox.add(lblRecTitle);
        recTitleBox.add(lblRecSub);
        
        recHeader.add(recIconPnl);
        recHeader.add(recTitleBox);
        recSection.add(recHeader, BorderLayout.NORTH);

        JPanel recListContainer = new JPanel();
        recListContainer.setLayout(new BoxLayout(recListContainer, BoxLayout.Y_AXIS));
        recListContainer.setOpaque(false);

        recListContainer.add(createRecommendationCard("📊", "Steady Growth", "Production is improving consistently. Maintain current practices while exploring optimization.", new Color(240, 253, 244), new Color(22, 163, 74)));
        recListContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        recListContainer.add(createRecommendationCard("🌱", "Focus on Quality", "Consider implementing quality improvement measures to fetch better prices.", new Color(240, 249, 255), new Color(14, 165, 233)));
        recListContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        recListContainer.add(createRecommendationCard("💧", "Water Management", "Implement drip irrigation or sprinkler systems to optimize water usage and improve yield.", new Color(250, 245, 255), new Color(168, 85, 247)));
        recListContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        recListContainer.add(createRecommendationCard("🌿", "Crop Rotation", "Practice crop rotation to maintain soil fertility and reduce pest buildup.", new Color(255, 251, 235), new Color(245, 158, 11)));
        recListContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        recListContainer.add(createRecommendationCard("📱", "Technology Adoption", "Use precision agriculture tools and mobile apps for better farm management.", new Color(241, 245, 249), new Color(71, 85, 105)));
        
        recSection.add(recListContainer, BorderLayout.CENTER);
        scrollContent.add(recSection);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 3. Key Metrics (3 Color Cards) ---
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        metricsPanel.setOpaque(false);
        metricsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        metricsPanel.add(createMetricCard("Growth Rate (2024-2026)", "+8.3%", "Strong upward momentum", new Color(16, 185, 129))); // Green
        metricsPanel.add(createMetricCard("Average Production", "337 MT", "Last 5 years average", new Color(59, 130, 246))); // Blue
        metricsPanel.add(createMetricCard("Projected 2027", "405 MT", "Based on current trend", new Color(168, 85, 247))); // Purple
        
        scrollContent.add(metricsPanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- 4. Best Practices Grid ---
        JPanel bpSection = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        bpSection.setLayout(new BorderLayout());
        bpSection.setBorder(new EmptyBorder(25, 30, 25, 30));
        bpSection.setMaximumSize(new Dimension(1000, 600)); // Increased
        bpSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblBPTitle = new JLabel("Best Practices for Sustainable Farming");
        lblBPTitle.setFont(UIUtils.HEADER_FONT);
        lblBPTitle.setForeground(UIUtils.TEXT_PRIMARY);
        lblBPTitle.setBorder(new EmptyBorder(0, 0, 20, 0));
        bpSection.add(lblBPTitle, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 40, 20));
        gridPanel.setOpaque(false);

        gridPanel.add(createListSection("Soil Health Management", new String[]{"Regular soil testing every 6 months", "Use organic fertilizers and compost", "Practice green manuring", "Maintain proper pH levels"}));
        gridPanel.add(createListSection("Water Conservation", new String[]{"Install drip irrigation systems", "Harvest rainwater effectively", "Use mulching to retain moisture", "Schedule irrigation during cooler hours"}));
        gridPanel.add(createListSection("Pest Management", new String[]{"Implement integrated pest management", "Use biological pest control methods", "Regular field monitoring", "Plant pest-resistant crop varieties"}));
        gridPanel.add(createListSection("Technology Adoption", new String[]{"Use weather forecasting apps", "Adopt precision farming techniques", "Implement farm management software", "Join farmer networks and communities"}));

        bpSection.add(gridPanel, BorderLayout.CENTER);

        scrollContent.add(bpSection);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createRecommendationCard(String emoji, String title, String desc, Color bgColor, Color iconColor) {
        JPanel card = UIUtils.createRoundedPanel(15, new Color(248, 250, 252));
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(new EmptyBorder(15, 20, 15, 20));
        card.setMaximumSize(new Dimension(950, 70));

        // Let's use the green checkmark layout from Image 5
        JPanel leftIconPanel = new JPanel(new GridBagLayout());
        leftIconPanel.setOpaque(false);
        JLabel checkIcon = new JLabel("<html><font color='#22c55e' size='6'>☑</font></html>");
        leftIconPanel.add(checkIcon);

        JPanel textContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        textContainer.setOpaque(false);
        
        JLabel mainText = new JLabel("<html>" + emoji + " <b>" + title + ":</b> " + desc + "</html>");
        mainText.setFont(UIUtils.NORMAL_FONT);
        mainText.setForeground(UIUtils.TEXT_PRIMARY);

        textContainer.add(mainText);

        card.add(leftIconPanel, BorderLayout.WEST);
        card.add(textContainer, BorderLayout.CENTER);

        // Optional thin border
        JPanel borderWrapper = new JPanel(new BorderLayout());
        borderWrapper.setOpaque(false);
        borderWrapper.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        // Note: Java line borders don't round natively well without custom UI, so skipping wrapper.
        // Actually FlatLaf rounded panel looks fine.
        
        return card;
    }

    private JPanel createMetricCard(String title, String mainValue, String subText, Color bgColor) {
        JPanel card = UIUtils.createRoundedPanel(15, bgColor);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setPreferredSize(new Dimension(280, 140));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(UIUtils.NORMAL_FONT);
        lblTitle.setForeground(new Color(255, 255, 255, 220));

        JLabel lblValue = new JLabel(mainValue);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel(subText);
        lblSub.setFont(UIUtils.SMALL_FONT);
        lblSub.setForeground(new Color(255, 255, 255, 200));

        card.add(lblTitle);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(lblValue);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(lblSub);

        return card;
    }

    private JPanel createListSection(String title, String[] items) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        section.add(titleLabel);

        for (String item : items) {
            JLabel itemLabel = new JLabel("• " + item);
            itemLabel.setFont(UIUtils.NORMAL_FONT);
            itemLabel.setForeground(UIUtils.TEXT_SECONDARY);
            itemLabel.setBorder(new EmptyBorder(4, 0, 4, 0));
            section.add(itemLabel);
        }

        return section;
    }
}
