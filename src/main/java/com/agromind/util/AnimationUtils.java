package com.agromind.util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * Utility class for simple Swing animations using Timers.
 */
public class AnimationUtils {

    /**
     * Smoothly interpolates a value from start to end over a given duration.
     * 
     * @param from Starting value
     * @param to Ending value
     * @param duration Milliseconds for the total animation
     * @param onUpdate Callback with the current interpolated value
     * @param onComplete Callback when animation finishes
     */
    public static void animate(float from, float to, int duration, Consumer<Float> onUpdate, Runnable onComplete) {
        int delay = 16; // ~60 FPS
        int steps = Math.max(1, duration / delay);
        float delta = (to - from) / steps;

        Timer timer = new Timer(delay, null);
        final int[] currentStep = {0};

        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentStep[0]++;
                float currentVal = from + (delta * currentStep[0]);
                
                // Ensure we don't overshoot due to float precision
                if (currentStep[0] >= steps) {
                    onUpdate.accept(to);
                    timer.stop();
                    if (onComplete != null) onComplete.run();
                } else {
                    onUpdate.accept(currentVal);
                }
            }
        });
        timer.start();
    }

    /**
     * Simple fade-in effect (0 to 1 opacity)
     */
    public static void fadeIn(int duration, Consumer<Float> onUpdate, Runnable onComplete) {
        animate(0f, 1f, duration, onUpdate, onComplete);
    }
}
