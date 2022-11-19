package me.anjoismysign.anjo.swing.components.progressbar;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.listeners.AnjoClickListener;
import me.anjoismysign.anjo.swing.listeners.AnjoKeynputListener;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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


    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoProgressBar label(JLabel label) {
        super.label(label);
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoProgressBar label(String label) {
        super.label(label);
        return this;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoProgressBar component(JComponent component) {
        super.component(component);
        return this;
    }

    /**
     * will add a mouse listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoProgressBar addMouseListener(MouseListener listener) {
        super.addMouseListener(listener);
        return this;
    }

    /**
     * will add a key listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoProgressBar addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        return this;
    }

    /**
     * Will add an AnjoClickListener to the component
     *
     * @param runnable the runnable to be run when the component is clicked
     */
    public AnjoProgressBar addAnjoClickListener(Runnable runnable) {
        addMouseListener(AnjoClickListener.build(this, runnable));
        return this;
    }

    /**
     * Will add an AnjoKeynputListener to the component
     *
     * @param runnable the runnable to be run when receiving a key input from the component
     * @param inputs   the inputs to listen for
     */
    public AnjoProgressBar addAnjoKeynputListener(Runnable runnable, char[] inputs) {
        addKeyListener(AnjoKeynputListener.build(this, runnable, inputs));
        return this;
    }

    @Override
    public JProgressBar getComponent() {
        return (JProgressBar) super.getComponent();
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
