package com.agromind.ui;

import com.agromind.ui.components.ChartRenderer;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnalyticsPanel extends JPanel {

    public AnalyticsPanel() {
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
        
        JLabel titleLabel = new JLabel("Analytics Dashboard");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        
        JLabel subTitleLabel = new JLabel("Comprehensive agriculture data visualization and insights");
        subTitleLabel.setFont(UIUtils.NORMAL_FONT);
        subTitleLabel.setForeground(UIUtils.TEXT_SECONDARY);
        subTitleLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        
        headerPanel.add(titleLabel);
        headerPanel.add(subTitleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Main Content Area (Scrollable)
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setOpaque(false);

        // --- Section 1: Crop Distribution (Pie Chart) ---
        JPanel card1 = createCardContainer("Crop Distribution", "Current crop cultivation distribution across regions", "pie", new Color(168, 85, 247), new Color(250, 245, 255));
        Map<String, Double> pieData = new LinkedHashMap<>();
        pieData.put("Rice", 28.0);
        pieData.put("Wheat", 24.0);
        pieData.put("Cotton", 15.0);
        pieData.put("Sugarcane", 12.0);
        pieData.put("Maize", 10.0);
        pieData.put("Others", 11.0);
        ChartRenderer pieChart = new ChartRenderer(ChartRenderer.ChartType.PIE, pieData, "");
        pieChart.setBorder(new EmptyBorder(20, 0, 0, 0));
        card1.add(pieChart, BorderLayout.CENTER);
        
        scrollContent.add(card1);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Section 2: Year-wise Production Trend (Line Chart) ---
        JPanel card2 = createCardContainer("Year-wise Production Trend", "Agriculture production growth over the years (Million Tonnes)", "line", new Color(16, 185, 129), new Color(220, 252, 231));
        Map<String, Double> lineData = new LinkedHashMap<>();
        lineData.put("2018", 290.0); lineData.put("2019", 295.0); lineData.put("2020", 310.0);
        lineData.put("2021", 305.0); lineData.put("2022", 330.0); lineData.put("2023", 345.0);
        lineData.put("2024", 360.0); lineData.put("2025", 380.0); lineData.put("2026", 400.0);
        ChartRenderer lineChart = new ChartRenderer(ChartRenderer.ChartType.LINE, lineData, "");
        
        // Add Trend Analysis box under line chart
        JPanel lineChartWrapper = new JPanel(new BorderLayout());
        lineChartWrapper.setOpaque(false);
        lineChartWrapper.add(lineChart, BorderLayout.CENTER);
        
        JPanel trendBox = UIUtils.createRoundedPanel(10, new Color(241, 245, 249)); // Slate 100 fallback
        // we'll try to use the greenish background from the image
        trendBox.setBackground(new Color(240, 253, 244));
        trendBox.setBorder(new EmptyBorder(15, 20, 15, 20));
        trendBox.setLayout(new BoxLayout(trendBox, BoxLayout.Y_AXIS));
        JLabel trendTitle = new JLabel("<html>📊 <b>Trend Analysis:</b></html>");
        trendTitle.setForeground(new Color(22, 163, 74));
        JLabel trendText = new JLabel("<html>Production has shown consistent growth with a 36.8% increase from 2018 to 2026. The trend indicates effective<br>implementation of modern farming techniques and better resource management.</html>");
        trendText.setFont(UIUtils.NORMAL_FONT);
        trendText.setForeground(UIUtils.TEXT_PRIMARY);
        trendText.setBorder(new EmptyBorder(5, 0, 0, 0));
        trendBox.add(trendTitle);
        trendBox.add(trendText);
        
        lineChartWrapper.add(trendBox, BorderLayout.SOUTH);
        card2.add(lineChartWrapper, BorderLayout.CENTER);
        
        scrollContent.add(card2);
        scrollContent.add(Box.createRigidArea(new Dimension(0, 30)));

        // --- Section 3: State-wise Agriculture Data ---
        JPanel card3 = createCardContainer("State-wise Agriculture Data", "Regional analysis of cultivation area and production", "table", new Color(59, 130, 246), new Color(239, 246, 255));
        card3.setPreferredSize(new Dimension(1000, 600)); 
        card3.setMaximumSize(new Dimension(1000, 600));
        card3.setMinimumSize(new Dimension(800, 600));
        
        // The table
        String[] columns = {"State", "Area (1000 Ha)", "Production (1000 Tonnes)", "Yield (T/Ha)"};
        Object[][] rowData = {
            {"Andhra Pradesh", "7,200", "19,500", "2.71"},
            {"Telangana", "6,800", "17,200", "2.53"},
            {"Punjab", "4,050", "20,500", "5.06"},
            {"Haryana", "2,510", "13,200", "5.26"},
            {"Uttar Pradesh", "9,850", "32,400", "3.29"},
            {"Maharashtra", "6,200", "18,600", "3.00"},
            {"Madhya Pradesh", "7,800", "23,100", "2.96"},
            {"Karnataka", "5,600", "15,800", "2.82"},
            {"Gujarat", "4,900", "14,200", "2.90"},
            {"Tamil Nadu", "5,100", "15,400", "3.02"}
        };
        DefaultTableModel model = new DefaultTableModel(rowData, columns) {
            @Override public boolean isCellEditable(int root, int col) { return false; }
        };
        JTable table = new JTable(model);
        table.setOpaque(false);
        table.setRowHeight(40);
        table.setFont(UIUtils.NORMAL_FONT);
        table.setForeground(UIUtils.TEXT_PRIMARY);
        table.setShowGrid(false);
        table.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        
        JTableHeader th = table.getTableHeader();
        th.setFont(UIUtils.SUBHEADER_FONT);
        th.setForeground(UIUtils.TEXT_PRIMARY);
        th.setOpaque(false);
        th.setBackground(Color.WHITE);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(226, 232, 240)));
        ((DefaultTableCellRenderer)th.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        centerRender.setHorizontalAlignment(JLabel.CENTER);
        for(int i=1; i<4; i++) table.getColumnModel().getColumn(i).setCellRenderer(centerRender);

        // Remove selection bg
        table.setSelectionBackground(Color.WHITE);
        table.setSelectionForeground(UIUtils.TEXT_PRIMARY);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setOpaque(false);
        tableScroll.getViewport().setOpaque(false);
        tableScroll.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        tableScroll.setWheelScrollingEnabled(false); // Propagate scroll to parent
        
        // Bottom summary statistics panels
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setOpaque(false);
        statsPanel.add(createSummaryPanel("Total Area", "44,710 Ha", new Color(239, 246, 255), new Color(30, 64, 175)));
        statsPanel.add(createSummaryPanel("Total Production", "148,200 T", new Color(240, 253, 244), new Color(22, 163, 74)));
        statsPanel.add(createSummaryPanel("Average Yield", "3.50 T/Ha", new Color(250, 245, 255), new Color(107, 33, 168)));
        
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setOpaque(false);
        tableWrapper.add(tableScroll, BorderLayout.CENTER);
        tableWrapper.add(statsPanel, BorderLayout.SOUTH);
        
        card3.add(tableWrapper, BorderLayout.CENTER);
        scrollContent.add(card3);
        
        // Wrap everything
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(24);

        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JPanel createSummaryPanel(String title, String val, Color bg, Color textCol) {
        JPanel p = UIUtils.createRoundedPanel(15, bg);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel tl = new JLabel(title);
        tl.setFont(UIUtils.NORMAL_FONT);
        tl.setForeground(textCol);
        JLabel vl = new JLabel(val);
        vl.setFont(UIUtils.HEADER_FONT);
        vl.setForeground(textCol);
        p.add(tl);
        p.add(Box.createRigidArea(new Dimension(0, 5)));
        p.add(vl);
        return p;
    }

    private JPanel createCardContainer(String titleText, String subTitle, String iconName, Color iconColor, Color iconBg) {
        JPanel card = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(30, 30, 30, 30));
        card.setMaximumSize(new Dimension(1000, 500));
        card.setPreferredSize(new Dimension(1000, 500));
        card.setMinimumSize(new Dimension(800, 500));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel cardHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        cardHeader.setOpaque(false);
        
        JPanel iconPnl = UIUtils.createRoundedPanel(10, iconBg);
        iconPnl.setPreferredSize(new Dimension(50, 50));
        iconPnl.setLayout(new GridBagLayout());
        
        JLabel icn = new JLabel();
        if ("pie".equals(iconName)) {
            icn.setText("◔"); // Pie Chart look
        } else if ("table".equals(iconName)) {
            icn.setText("▦"); // Table/Grid look
        } else if ("line".equals(iconName)) {
            icn.setText("📈");
        }
        icn.setForeground(iconColor);
        icn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconPnl.add(icn);
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        JLabel cTitle = new JLabel(titleText);
        cTitle.setFont(UIUtils.HEADER_FONT);
        cTitle.setForeground(UIUtils.TEXT_PRIMARY);
        JLabel cSub = new JLabel(subTitle);
        cSub.setFont(UIUtils.NORMAL_FONT);
        cSub.setForeground(UIUtils.TEXT_SECONDARY);
        titleBox.add(cTitle);
        titleBox.add(cSub);
        
        cardHeader.add(iconPnl);
        cardHeader.add(titleBox);
        card.add(cardHeader, BorderLayout.NORTH);
        
        return card;
    }
}
