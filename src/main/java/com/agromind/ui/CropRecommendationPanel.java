package com.agromind.ui;

import com.agromind.engine.RecommendationEngine;
import com.agromind.model.PredictionResult;
import com.agromind.model.WeatherData;
import com.agromind.ui.components.RecommendationCard;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CropRecommendationPanel extends JPanel {

    private JComboBox<String> soilCombo;
    private JComboBox<String> seasonCombo;
    private JTextField rainField;
    
    private JPanel rightPanel;
    private JPanel placeholderCard;
    private RecommendationEngine engine;
    
    public CropRecommendationPanel() {
        engine = new RecommendationEngine();
        
        setLayout(new BorderLayout(0, 0));
        setBackground(UIUtils.BACKGROUND_COLOR);
        setBorder(new EmptyBorder(30, 40, 30, 40));

        initUI();
    }

    private void initUI() {
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("AI Crop Recommendation System");
        titleLabel.setFont(UIUtils.TITLE_FONT);
        titleLabel.setForeground(UIUtils.TEXT_PRIMARY);
        
        JLabel subTitleLabel = new JLabel("Get intelligent crop suggestions based on your farm conditions");
        subTitleLabel.setFont(UIUtils.NORMAL_FONT);
        subTitleLabel.setForeground(UIUtils.TEXT_SECONDARY);
        subTitleLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        
        headerPanel.add(titleLabel);
        headerPanel.add(subTitleLabel);
        
        // Wrap everything in a main scroll content
        JPanel scrollContent = new JPanel(new BorderLayout());
        scrollContent.setOpaque(false);
        scrollContent.add(headerPanel, BorderLayout.NORTH);

        // Main Content Split
        JPanel contentSplit = new JPanel(new BorderLayout(30, 0));
        contentSplit.setOpaque(false);

        // Left Form Panel (The "Full Side")
        JPanel leftContainer = new JPanel();
        leftContainer.setLayout(new BoxLayout(leftContainer, BoxLayout.Y_AXIS));
        leftContainer.setOpaque(false);
        leftContainer.setPreferredSize(new Dimension(380, 0));
        
        leftContainer.add(createFormPanel());
        leftContainer.add(Box.createRigidArea(new Dimension(0, 25)));
        leftContainer.add(createAnalysisInfoBox());
        leftContainer.add(Box.createVerticalGlue()); // Push everything up
        
        contentSplit.add(leftContainer, BorderLayout.WEST);

        // Right Results Panel
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        
        // Placeholder
        placeholderCard = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        placeholderCard.setLayout(new BoxLayout(placeholderCard, BoxLayout.Y_AXIS));
        placeholderCard.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        // Circular Sparkle Icon for Placeholder
        JPanel iconPnl = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241, 245, 249)); // Soft Gray/Slate 100
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        iconPnl.setPreferredSize(new Dimension(80, 80));
        iconPnl.setMaximumSize(new Dimension(80, 80));
        iconPnl.setLayout(new GridBagLayout());
        JLabel sparkle = new JLabel("✨");
        sparkle.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 38));
        sparkle.setForeground(new Color(148, 163, 184)); // Slate 400
        iconPnl.add(sparkle);
        
        iconPnl.setAlignmentX(Component.CENTER_ALIGNMENT);
        iconPnl.setBorder(new EmptyBorder(0,0,30,0));
        
        JLabel readyLbl = new JLabel("Ready for Recommendations");
        readyLbl.setFont(UIUtils.HEADER_FONT);
        readyLbl.setForeground(UIUtils.TEXT_PRIMARY);
        readyLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel fillLbl = new JLabel("Fill in your farm details to get AI-powered crop suggestions");
        fillLbl.setFont(UIUtils.NORMAL_FONT);
        fillLbl.setForeground(UIUtils.TEXT_SECONDARY);
        fillLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        fillLbl.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        placeholderCard.add(Box.createVerticalGlue());
        placeholderCard.add(iconPnl);
        placeholderCard.add(readyLbl);
        placeholderCard.add(fillLbl);
        placeholderCard.add(Box.createVerticalGlue());
        
        rightPanel.add(placeholderCard, BorderLayout.CENTER);
        
        contentSplit.add(rightPanel, BorderLayout.CENTER);
        
        scrollContent.add(contentSplit, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(24);

        add(scrollPane, BorderLayout.CENTER);
    }


    private JPanel createFormPanel() {
        JPanel formCard = UIUtils.createBorderedRoundedPanel(15, UIUtils.CARD_BG, new Color(226, 232, 240));
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBorder(new EmptyBorder(25, 20, 25, 20));
        formCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 480));

        // Form Header
        JPanel formHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        formHeader.setOpaque(false);
        
        JPanel iconPnl = UIUtils.createRoundedPanel(10, new Color(220, 252, 231));
        iconPnl.setPreferredSize(new Dimension(50, 50));
        iconPnl.setLayout(new GridBagLayout());
        JLabel sparkIcon = new JLabel("✨");
        sparkIcon.setForeground(new Color(22, 163, 74));
        sparkIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        iconPnl.add(sparkIcon);
        
        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setOpaque(false);
        JLabel ft = new JLabel("Farm Details");
        ft.setFont(UIUtils.HEADER_FONT);
        JLabel st = new JLabel("Enter your conditions");
        st.setFont(UIUtils.SMALL_FONT);
        st.setForeground(UIUtils.TEXT_SECONDARY);
        titleBox.add(ft);
        titleBox.add(st);
        
        formHeader.add(iconPnl);
        formHeader.add(titleBox);
        formHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        formCard.add(formHeader);
        formCard.add(Box.createRigidArea(new Dimension(0, 30)));

        // Inputs
        formCard.add(createInputGroup("Soil Type", "Select soil type"));
        soilCombo = new JComboBox<>(new String[]{"Loamy", "Clay", "Sandy", "Alluvial", "Black", "Sandy Loam"});
        styleInput(soilCombo);
        formCard.add(soilCombo);
        formCard.add(Box.createRigidArea(new Dimension(0, 20)));

        formCard.add(createInputGroup("Season", "Select season"));
        seasonCombo = new JComboBox<>(new String[]{"Kharif (Monsoon)", "Rabi (Winter)", "Summer", "Winter", "Monsoon"});
        styleInput(seasonCombo);
        formCard.add(seasonCombo);
        formCard.add(Box.createRigidArea(new Dimension(0, 20)));

        formCard.add(createInputGroup("Rainfall (mm)", "Enter expected rainfall"));
        rainField = new JTextField("120");
        styleInput(rainField);
        formCard.add(rainField);
        formCard.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button
        JButton btnPredict = new JButton("Get Recommendations");
        btnPredict.setBackground(new Color(15, 23, 42)); // Black/Slate 900
        btnPredict.setForeground(Color.WHITE);
        btnPredict.setFont(UIUtils.SUBHEADER_FONT);
        btnPredict.setFocusPainted(false);
        btnPredict.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPredict.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnPredict.setPreferredSize(new Dimension(Integer.MAX_VALUE, 45));
        
        btnPredict.addActionListener(this::runPrediction);
        formCard.add(btnPredict);

        return formCard;
    }

    private JPanel createInputGroup(String label, String placeholderDesc) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);
        JLabel l = new JLabel("<html><b>" + label + "</b></html>");
        l.setFont(UIUtils.NORMAL_FONT);
        l.setForeground(new Color(15, 23, 42)); // Match "Farm Details" title color
        p.add(l);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setBorder(new EmptyBorder(0,0,8,0));
        return p;
    }

    private void styleInput(JComponent comp) {
        comp.setBackground(new Color(241, 245, 249)); // Slate 100
        comp.setForeground(UIUtils.TEXT_PRIMARY);
        comp.setFont(UIUtils.NORMAL_FONT);
        comp.setBorder(new EmptyBorder(10, 10, 10, 10));
        comp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        comp.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void runPrediction(ActionEvent e) {
        try {
            String soil = soilCombo.getSelectedItem().toString();
            String season = seasonCombo.getSelectedItem().toString();
            double rain = Double.parseDouble(rainField.getText());

            WeatherData mockWeather = new WeatherData(25.0, 65.0, rain);
            List<PredictionResult> results = engine.recommendCrops(soil, season, mockWeather);

            JPanel resultsCont = new JPanel();
            resultsCont.setLayout(new BoxLayout(resultsCont, BoxLayout.Y_AXIS));
            resultsCont.setOpaque(false);
            
            int rank = 1;
            for (PredictionResult res : results) {
                RecommendationCard card = new RecommendationCard(res.getCrop(), rank++, res.getConfidenceScore(), res.getExplanation());
                resultsCont.add(card);
            }
            
            JScrollPane scrollPane = new JScrollPane(resultsCont);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.getVerticalScrollBar().setUnitIncrement(24);

            rightPanel.removeAll();
            rightPanel.add(scrollPane, BorderLayout.CENTER);
            rightPanel.revalidate();
            rightPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for rainfall.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private JPanel createAnalysisInfoBox() {
        JPanel infoBox = UIUtils.createBorderedRoundedPanel(12, new Color(240, 249, 255), new Color(186, 230, 253));
        infoBox.setLayout(new BorderLayout(15, 0));
        infoBox.setBorder(new EmptyBorder(15, 20, 15, 20));
        infoBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        JLabel infoIcon = new JLabel("💡");
        infoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        infoIcon.setForeground(new Color(37, 99, 235));
        infoBox.add(infoIcon, BorderLayout.WEST);
        
        JLabel infoText = new JLabel("<html><b>AI Analysis Complete</b><br><font color='#475569'>Results are based on rule-based scoring algorithm considering soil compatibility, seasonal factors, and water requirements.</font></html>");
        infoText.setFont(UIUtils.NORMAL_FONT);
        infoBox.add(infoText, BorderLayout.CENTER);
        
        return infoBox;
    }
}
