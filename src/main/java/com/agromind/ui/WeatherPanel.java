package com.agromind.ui;

import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class WeatherPanel extends JPanel {

    private int temp = 20;
    private int humidity = 93;
    private int rain = 169;
    private int wind = 12;
    private String conditions = "Heavy Rain";

    public WeatherPanel() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);

        initUI();
    }

    private void initUI() {
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(UIUtils.BACKGROUND_COLOR);
        scrollContent.setBorder(new EmptyBorder(25, 40, 25, 40));

        // Header with button
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        
        JLabel title = new JLabel("Weather Simulation System");
        title.setFont(UIUtils.TITLE_FONT);
        title.setForeground(UIUtils.TEXT_PRIMARY);
        
        JLabel subTitle = new JLabel("Real-time weather data for smart farming decisions");
        subTitle.setFont(UIUtils.NORMAL_FONT);
        subTitle.setForeground(UIUtils.TEXT_SECONDARY);
        
        titleBox.add(title);
        titleBox.add(subTitle);
        headerPanel.add(titleBox, BorderLayout.WEST);

        JButton refreshBtn = new JButton(" Refresh Weather");
        refreshBtn.setIcon(loadIcon("refresh", 18));
        refreshBtn.setBackground(new Color(15, 23, 42)); 
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(UIUtils.SUBHEADER_FONT);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setBorder(new EmptyBorder(10, 20, 10, 20));
        refreshBtn.setOpaque(true);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            Random r = new Random();
            temp = 15 + r.nextInt(20); 
            humidity = 40 + r.nextInt(55); 
            rain = r.nextInt(200);
            wind = 5 + r.nextInt(30);
            if (rain > 100) conditions = "Heavy Rain";
            else if (rain > 20) conditions = "Light Rain";
            else conditions = (temp > 28) ? "Sunny" : "Cloudy";
            
            removeAll();
            initUI();
            revalidate();
            repaint();
        });
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(refreshBtn);
        headerPanel.add(btnPanel, BorderLayout.EAST);

        scrollContent.add(headerPanel);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Large Weather Cards
        JPanel weatherGrid = new JPanel(new BorderLayout(20, 0));
        weatherGrid.setOpaque(false);
        weatherGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        weatherGrid.setPreferredSize(new Dimension(1000, 280));

        // Left Big Blue Card (2/3 width)
        JPanel blueCard = UIUtils.createRoundedPanel(20, UIUtils.CARD_BLUE);
        blueCard.setLayout(new BorderLayout());
        blueCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        JPanel topBlue = new JPanel(new BorderLayout());
        topBlue.setOpaque(false);
        
        JPanel leftTopBlue = new JPanel();
        leftTopBlue.setLayout(new BoxLayout(leftTopBlue, BoxLayout.Y_AXIS));
        leftTopBlue.setOpaque(false);
        JLabel bcTitle = new JLabel("Current Conditions");
        bcTitle.setForeground(new Color(255,255,255,200));
        bcTitle.setFont(UIUtils.NORMAL_FONT);
        JLabel bcTemp = new JLabel(temp + "°C");
        bcTemp.setForeground(Color.WHITE);
        bcTemp.setFont(new Font("Segoe UI", Font.BOLD, 72));
        JLabel bcDesc = new JLabel(conditions);
        bcDesc.setForeground(Color.WHITE);
        bcDesc.setFont(UIUtils.HEADER_FONT);
        
        leftTopBlue.add(bcTitle);
        leftTopBlue.add(bcTemp);
        leftTopBlue.add(bcDesc);
        topBlue.add(leftTopBlue, BorderLayout.WEST);
        
        // Dynamic Weather Icon based on conditions
        String iconSymbol = "☀️";
        Color iconColor = new Color(250, 204, 21);
        if (conditions.contains("Rain")) {
            iconSymbol = "🌧️";
            iconColor = Color.WHITE;
        } else if (conditions.contains("Cloudy")) {
            iconSymbol = "☁️";
            iconColor = Color.WHITE;
        }
        
        JLabel weatherIcon = new JLabel(iconSymbol);
        weatherIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 110));
        weatherIcon.setForeground(iconColor);
        topBlue.add(weatherIcon, BorderLayout.EAST);
        
        blueCard.add(topBlue, BorderLayout.CENTER);
        
        // Bottom stats in blue card - horizontal row at bottom (Image 1 style)
        JPanel blueStats = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        blueStats.setOpaque(false);
        blueStats.add(createMiniStatCard("Humidity", humidity + "%"));
        blueStats.add(createMiniStatCard("Rainfall", rain + "mm"));
        blueStats.add(createMiniStatCard("Wind", wind + " km/h"));
        blueCard.add(blueStats, BorderLayout.SOUTH);
        
        weatherGrid.add(blueCard, BorderLayout.CENTER);

        // Right side stacked cards (1/3 width)
        JPanel rightStacked = new JPanel(new GridLayout(2, 1, 0, 20));
        rightStacked.setOpaque(false);
        rightStacked.setPreferredSize(new Dimension(350, 0));
        
        // Temperature Card with Icon
        JPanel tempCard = UIUtils.createRoundedPanel(20, UIUtils.CARD_ORANGE);
        tempCard.setLayout(new BorderLayout(15, 0));
        tempCard.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel tIcon = new JLabel("🌡️");
        tIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        tIcon.setForeground(new Color(255,255,255,180));
        tempCard.add(tIcon, BorderLayout.WEST);
        
        JPanel tTextPanel = new JPanel();
        tTextPanel.setLayout(new BoxLayout(tTextPanel, BoxLayout.Y_AXIS));
        tTextPanel.setOpaque(false);
        
        JLabel tTitle = new JLabel("Temperature");
        tTitle.setFont(UIUtils.NORMAL_FONT);
        tTitle.setForeground(new Color(255, 255, 255, 200));
        
        JLabel tValue = new JLabel(temp + "°C");
        tValue.setFont(new Font("Segoe UI", Font.BOLD, 38));
        tValue.setForeground(Color.WHITE);
        
        JLabel tRange = new JLabel("Optimal range");
        tRange.setFont(UIUtils.SMALL_FONT);
        tRange.setForeground(new Color(255, 255, 255, 180));
        
        tTextPanel.add(tTitle);
        tTextPanel.add(tValue);
        tTextPanel.add(tRange);
        tempCard.add(tTextPanel, BorderLayout.CENTER);
        
        // Humidity Card with Icon
        JPanel humCard = UIUtils.createRoundedPanel(20, UIUtils.CARD_TEAL);
        humCard.setLayout(new BorderLayout(15, 0));
        humCard.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel hIcon = new JLabel("💦");
        hIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        hIcon.setForeground(new Color(255,255,255,180));
        humCard.add(hIcon, BorderLayout.WEST);
        
        JPanel hTextPanel = new JPanel();
        hTextPanel.setLayout(new BoxLayout(hTextPanel, BoxLayout.Y_AXIS));
        hTextPanel.setOpaque(false);
        
        JLabel hTitle = new JLabel("Humidity");
        hTitle.setFont(UIUtils.NORMAL_FONT);
        hTitle.setForeground(new Color(255, 255, 255, 200));
        
        JLabel hValue = new JLabel(humidity + "%");
        hValue.setFont(new Font("Segoe UI", Font.BOLD, 38));
        hValue.setForeground(Color.WHITE);
        
        JLabel hLevel = new JLabel(humidity > 80 ? "High - Monitor pests" : "Normal level");
        hLevel.setFont(UIUtils.SMALL_FONT);
        hLevel.setForeground(new Color(255, 255, 255, 180));
        
        hTextPanel.add(hTitle);
        hTextPanel.add(hValue);
        hTextPanel.add(hLevel);
        humCard.add(hTextPanel, BorderLayout.CENTER);
        
        rightStacked.add(tempCard);
        rightStacked.add(humCard);
        
        weatherGrid.add(rightStacked, BorderLayout.EAST);

        scrollContent.add(weatherGrid);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Advice Section (from Image 2)
        JPanel adviceBox = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        adviceBox.setLayout(new BoxLayout(adviceBox, BoxLayout.Y_AXIS));
        adviceBox.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JPanel adviceTitleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        adviceTitleRow.setOpaque(false);
        adviceTitleRow.add(new JLabel(" "));
        
        JPanel adviceTitleText = new JPanel();
        adviceTitleText.setLayout(new BoxLayout(adviceTitleText, BoxLayout.Y_AXIS));
        adviceTitleText.setOpaque(false);
        JLabel adTitle = new JLabel("Weather-based Advice");
        adTitle.setFont(UIUtils.TITLE_FONT);
        adTitle.setForeground(UIUtils.TEXT_PRIMARY);
        JLabel adSub = new JLabel("Smart recommendations based on current conditions");
        adSub.setFont(UIUtils.NORMAL_FONT);
        adSub.setForeground(UIUtils.TEXT_SECONDARY);
        adviceTitleText.add(adTitle);
        adviceTitleText.add(adSub);
        
        adviceTitleRow.add(adviceTitleText);
        adviceTitleRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        adviceBox.add(adviceTitleRow);
        adviceBox.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // 1. High Temperature Alert or Status
        if (temp > 30) {
            adviceBox.add(createAdviceRow("⚠️", "High temperature alert! Ensure adequate irrigation for crops."));
        } else {
            adviceBox.add(createAdviceRow("✅", "Temperature is optimal for most crop growth."));
        }
        adviceBox.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // 2. Varietal Recommendation
        adviceBox.add(createAdviceRow("🌡️", "Consider heat-resistant crop varieties for better yield."));
        adviceBox.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // 3. Rainfall Status
        if (rain < 100) {
            adviceBox.add(createAdviceRow("🚜", "Low rainfall. Consider drought-resistant crops or install irrigation."));
        } else {
            adviceBox.add(createAdviceRow("🌧️", "Healthy rainfall levels detected for current crop cycle."));
        }
        adviceBox.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // 4. Humidity / Pest Alert
        if (humidity < 40) {
            adviceBox.add(createAdviceRow("🌵", "Low humidity. Increase watering frequency to prevent crop stress."));
        } else {
            adviceBox.add(createAdviceRow("🛡️", "Moderate humidity. Monitor for potential pest activity."));
        }

        scrollContent.add(adviceBox);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 20)));

        // Suitability Section (from Image 2)
        JPanel suitabilityBox = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        suitabilityBox.setLayout(new BorderLayout());
        suitabilityBox.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JLabel suitTitle = new JLabel("Crop Suitability for Current Weather");
        suitTitle.setFont(UIUtils.TITLE_FONT);
        suitTitle.setForeground(UIUtils.TEXT_PRIMARY);
        suitabilityBox.add(suitTitle, BorderLayout.NORTH);
        
        JPanel colsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        colsPanel.setOpaque(false);
        colsPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        
        colsPanel.add(createSuitabilityCol("Highly Suitable", UIUtils.STATUS_SUCCESS, UIUtils.STATUS_SUCCESS_TEXT, "• Rice\n• Sugarcane\n• Jute"));
        colsPanel.add(createSuitabilityCol("Moderate", UIUtils.STATUS_MODERATE, UIUtils.STATUS_MODERATE_TEXT, "• Maize\n• Soybean\n• Cotton"));
        colsPanel.add(createSuitabilityCol("Not Suitable", UIUtils.STATUS_FAIL, UIUtils.STATUS_FAIL_TEXT, "• Wheat\n• Mustard\n• Chickpea"));
        
        suitabilityBox.add(colsPanel, BorderLayout.CENTER);
        scrollContent.add(suitabilityBox);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private ImageIcon loadIcon(String name, int size) {
        try {
            File f = new File("src/main/resources/icons/" + name + ".png");
            if (f.exists()) {
                Image img = new ImageIcon(f.getAbsolutePath()).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {}
        return null;
    }

    private JPanel createMiniStatCard(String title, String value) {
        JPanel p = UIUtils.createRoundedPanel(12, new Color(255, 255, 255, 30));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(12, 20, 12, 20));
        JLabel l1 = new JLabel(title);
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l1.setForeground(new Color(255,255,255,180));
        JLabel l2 = new JLabel(value);
        l2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        l2.setForeground(Color.WHITE);
        p.add(l1);
        p.add(l2);
        return p;
    }

    private JPanel createAdviceRow(String icon, String text) {
        JPanel p = UIUtils.createBorderedRoundedPanel(12, new Color(240, 249, 255), new Color(186, 230, 253)); // Light Blue BG + Border
        p.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 12));
        
        JLabel icn = new JLabel(icon);
        icn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        p.add(icn);
        
        JLabel lbl = new JLabel(text);
        lbl.setFont(UIUtils.NORMAL_FONT);
        lbl.setForeground(UIUtils.TEXT_PRIMARY);
        p.add(lbl);
        
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        return p;
    }

    private JPanel createSuitabilityCol(String title, Color bg, Color fg, String items) {
        JPanel p = UIUtils.createBorderedRoundedPanel(15, bg, new Color(0,0,0,20));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel l1 = new JLabel(title);
        l1.setFont(UIUtils.SUBHEADER_FONT);
        l1.setForeground(fg);
        p.add(l1);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        
        for (String item : items.split("\n")) {
            JLabel it = new JLabel(item);
            it.setFont(UIUtils.NORMAL_FONT);
            it.setForeground(UIUtils.TEXT_PRIMARY);
            p.add(it);
            p.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        return p;
    }
}
