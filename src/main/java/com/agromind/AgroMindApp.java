package com.agromind;

import com.agromind.ui.MainFrame;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Font;
import java.util.Enumeration;

public class AgroMindApp {
    public static void main(String[] args) {
        // Set up Look and Feel
        try {
            FlatLightLaf.setup();
            
            // Global Smooth Scrolling for FlatLaf
            UIManager.put("ScrollPane.smoothScrolling", true);
            UIManager.put("ScrollBar.showButtons", false);
            UIManager.put("ScrollBar.width", 12);
            UIManager.put("ScrollBar.thumbArc", 999);
            
            // Set global font family to Segoe UI
            setUIFont(new javax.swing.plaf.FontUIResource("Segoe UI", Font.PLAIN, 14));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
    
    private static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
}
