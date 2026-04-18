package com.agromind.ui.components;

import com.agromind.model.Crop;
import com.agromind.model.PredictionResult;
import com.agromind.util.UIUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;

public class RecommendationCard extends JPanel {
    
    private Crop crop;
    private int rank;
    private double score;
    private String explanation;
    private PredictionResult fullResult; // Store for modal

    public RecommendationCard(Crop crop, int rank, double score, String explanation) {
        this.crop = crop;
        this.rank = rank;
        this.score = score;
        this.explanation = explanation;
        this.fullResult = new PredictionResult(crop, score, explanation);
        
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(UIUtils.CARD_BG);
        setBorder(new EmptyBorder(10, 0, 10, 0));
        
        initUI();
        initInteractions();
    }

    private void initInteractions() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                setBackground(new Color(240, 253, 244)); // Light Emerald tint
                repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                setBackground(UIUtils.CARD_BG);
                repaint();
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Frame parent = (Frame) SwingUtilities.getWindowAncestor(RecommendationCard.this);
                DeepDiveModal modal = new DeepDiveModal(parent, fullResult);
                modal.setVisible(true);
            }
        });
    }

    private void initUI() {
        JPanel cardMain = UIUtils.createBorderedRoundedPanel(20, UIUtils.CARD_BG, new Color(226, 232, 240));
        cardMain.setLayout(new BorderLayout(25, 0));
        cardMain.setBorder(new EmptyBorder(25, 25, 25, 25));

        // 1. Left Image
        JPanel imgContainer = new JPanel() {
            private Image img;
            {
                try {
                    File f = new File("src/main/resources/images/" + crop.getName() + ".png");
                    if (f.exists()) img = new ImageIcon(f.getAbsolutePath()).getImage();
                } catch(Exception e) {}
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                if (img != null) {
                    g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(new Color(241, 245, 249));
                    g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                }
                g2.dispose();
            }
        };
        imgContainer.setPreferredSize(new Dimension(180, 180));
        imgContainer.setMinimumSize(new Dimension(180, 180));
        cardMain.add(imgContainer, BorderLayout.WEST);

        // 2. Center Content
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Top Row: Rank Badge + Name + Confidence %
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        
        JPanel leftTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        leftTop.setOpaque(false);
        
        // Rank Badge
        JPanel badge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Dynamic Badge Color
                if (rank == 1) g2.setColor(new Color(245, 158, 11)); // Amber/Gold 500
                else if (rank == 2) g2.setColor(new Color(148, 163, 184)); // Slate/Silver 400
                else if (rank == 3) g2.setColor(new Color(217, 119, 6)); // Bronze 600
                else g2.setColor(new Color(203, 213, 225)); // Light Gray
                
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        badge.setPreferredSize(new Dimension(45, 45));
        badge.setLayout(new GridBagLayout());
        JLabel rankLbl = new JLabel("#" + rank);
        rankLbl.setForeground(Color.WHITE);
        rankLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        badge.add(rankLbl);
        leftTop.add(badge);

        // Name and Score Box
        JPanel nameScoreBox = new JPanel();
        nameScoreBox.setLayout(new BoxLayout(nameScoreBox, BoxLayout.Y_AXIS));
        nameScoreBox.setOpaque(false);
        
        JLabel nameLbl = new JLabel(crop.getName());
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        nameLbl.setForeground(UIUtils.TEXT_PRIMARY);
        nameScoreBox.add(nameLbl);
        
        JLabel scoreLbl = new JLabel("Score: " + (int)score + "/100");
        scoreLbl.setFont(UIUtils.NORMAL_FONT);
        scoreLbl.setForeground(UIUtils.TEXT_SECONDARY);
        nameScoreBox.add(scoreLbl);
        
        leftTop.add(nameScoreBox);
        
        topRow.add(leftTop, BorderLayout.WEST);

        // Confidence Area (Top Right)
        JPanel confArea = new JPanel();
        confArea.setLayout(new BoxLayout(confArea, BoxLayout.Y_AXIS));
        confArea.setOpaque(false);
        JLabel confPer = new JLabel("↗ " + (int)score + "%");
        confPer.setFont(new Font("Segoe UI", Font.BOLD, 22));
        confPer.setForeground(new Color(16, 185, 129)); // Emerald 500
        confPer.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JLabel confTxt = new JLabel("Confidence");
        confTxt.setFont(UIUtils.SMALL_FONT);
        confTxt.setForeground(UIUtils.TEXT_SECONDARY);
        confTxt.setAlignmentX(Component.RIGHT_ALIGNMENT);
        confArea.add(confPer);
        confArea.add(confTxt);
        topRow.add(confArea, BorderLayout.EAST);

        centerPanel.add(topRow);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Progress Bar
        JProgressBar progress = new JProgressBar(0, 100);
        progress.setValue((int)score);
        progress.setPreferredSize(new Dimension(Integer.MAX_VALUE, 8));
        progress.setMaximumSize(new Dimension(Integer.MAX_VALUE, 8));
        progress.setBackground(new Color(226, 232, 240));
        progress.setForeground(new Color(15, 23, 42)); // Black bar track style
        progress.setUI(new javax.swing.plaf.basic.BasicProgressBarUI() {
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = c.getWidth();
                int h = c.getHeight();
                g2.setColor(c.getBackground());
                g2.fillRoundRect(0, 0, w, h, h, h);
                g2.setColor(c.getForeground());
                int valW = (int) (w * ((double)progress.getValue() / 100.0));
                g2.fillRoundRect(0, 0, valW, h, h, h);
                g2.dispose();
            }
        });
        centerPanel.add(progress);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // AI Explanation Section
        JPanel aiBox = UIUtils.createRoundedPanel(12, new Color(248, 250, 252));
        aiBox.setLayout(new BoxLayout(aiBox, BoxLayout.Y_AXIS));
        aiBox.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel aiTitle = new JLabel("AI Explanation:");
        aiTitle.setFont(UIUtils.SUBHEADER_FONT);
        aiTitle.setForeground(UIUtils.TEXT_PRIMARY);
        aiBox.add(aiTitle);
        aiBox.add(Box.createRigidArea(new Dimension(0, 8)));
        
        // Split explanation into lines with checkmarks
        String[] rules = explanation.split("\\. ");
        for (String rule : rules) {
            String symbol = rule.toLowerCase().contains("not") ? "✕" : "✓";
            Color col = symbol.equals("✕") ? UIUtils.STATUS_FAIL_TEXT : UIUtils.STATUS_SUCCESS_TEXT;
            JLabel ruleLbl = new JLabel("<html><font color='" + String.format("#%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue()) + "'>" + symbol + "</font> &nbsp;" + rule + "</html>");
            ruleLbl.setFont(UIUtils.NORMAL_FONT);
            ruleLbl.setForeground(UIUtils.TEXT_PRIMARY);
            aiBox.add(ruleLbl);
            aiBox.add(Box.createRigidArea(new Dimension(0, 4)));
        }
        centerPanel.add(aiBox);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Meta Stats Row
        JPanel metaRow = new JPanel(new GridLayout(1, 2));
        metaRow.setOpaque(false);
        
        JLabel tempLbl = new JLabel("<html><font color='#94a3b8'>Optimal Temperature:</font><br><b>15°C - 28°C</b></html>");
        tempLbl.setFont(UIUtils.NORMAL_FONT);
        
        JLabel rainLbl = new JLabel("<html><font color='#94a3b8'>Rainfall Range:</font><br><b>40mm - 80mm</b></html>");
        rainLbl.setFont(UIUtils.NORMAL_FONT);
        
        metaRow.add(tempLbl);
        metaRow.add(rainLbl);
        centerPanel.add(metaRow);

        cardMain.add(centerPanel, BorderLayout.CENTER);
        add(cardMain, BorderLayout.CENTER);
    }
}
