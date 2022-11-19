package me.anjoismysign.anjo.swing.components.progressbar;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;

public class AnjoProgressBar extends AnjoComponent {
    private JProgressBar progressBar;

    public static AnjoProgressBar build(String label, int min, int max) {
        return new AnjoProgressBar(label, new JProgressBar(min, max));
    }

    public static AnjoProgressBar build(JLabel label, int min, int max) {
        return new AnjoProgressBar(label, new JProgressBar(min, max));
    }

    public static AnjoProgressBar build(String label, Orientation orientation, int min, int max) {
        int orientationInteger = orientation == Orientation.HORIZONTAL ? JProgressBar.HORIZONTAL : JProgressBar.VERTICAL;
        return new AnjoProgressBar(label, new JProgressBar(orientationInteger, min, max));
    }

    public static AnjoProgressBar build(JLabel label, Orientation orientation, int min, int max) {
        int orientationInteger = orientation == Orientation.HORIZONTAL ? JProgressBar.HORIZONTAL : JProgressBar.VERTICAL;
        return new AnjoProgressBar(label, new JProgressBar(orientationInteger, min, max));
    }

    public AnjoProgressBar(String label, JProgressBar progressBar) {
        super(label, progressBar);
        this.progressBar = progressBar;
    }

    public AnjoProgressBar(JLabel label, JProgressBar progressBar) {
        super(label, progressBar);
        this.progressBar = progressBar;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public AnjoProgressBar setValue(int value) {
        progressBar.setValue(value);
        return this;
    }

    public int getMin() {
        return progressBar.getMinimum();
    }

    public int getMax() {
        return progressBar.getMaximum();
    }
}
