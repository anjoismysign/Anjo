package me.anjoismysign.anjo.swing;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * @author anjoismysign
 */
public class AnjoComponent {
    private JLabel label;
    private JComponent component;

    /**
     * @param label     the label. Will be converted to a JLabel.
     * @param component the component
     */
    public AnjoComponent(String label, JComponent component) {
        this.label = new JLabel(label);
        this.component = component;
    }

    /**
     * @param jLabel    the label.
     * @param component the component
     */
    public AnjoComponent(JLabel jLabel, JComponent component) {
        this.label = jLabel;
        this.component = component;
    }

    /**
     * empty constructor. you need to call {@link #setLabel(String)} and {@link #setComponent(JComponent)}}
     */
    public AnjoComponent() {
    }

    /**
     * @return the label
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoComponent setLabel(JLabel label) {
        this.label = label;
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoComponent setLabel(String label) {
        this.label = new JLabel(label);
        return this;
    }

    /**
     * @return the component
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoComponent setComponent(JComponent component) {
        this.component = component;
        return this;
    }

    /**
     * will add a mouse listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoComponent addMouseListener(MouseListener listener) {
        component.addMouseListener(listener);
        return this;
    }

    /**
     * will add a key listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoComponent addKeyListener(KeyListener listener) {
        component.addKeyListener(listener);
        return this;
    }
}