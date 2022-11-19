package me.anjoismysign.anjo.swing;

import me.anjoismysign.anjo.swing.listeners.AnjoClickListener;
import me.anjoismysign.anjo.swing.listeners.AnjoKeynputListener;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

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
     * empty constructor. you need to call {@link #setLabel(String)} and {@link #component(JComponent)}}
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
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = new JLabel(label);
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public void setLabel(JLabel label) {
        this.label = label;
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoComponent label(JLabel label) {
        setLabel(label);
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoComponent label(String label) {
        setLabel(label);
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
    public void setComponent(JComponent component) {
        this.component = component;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoComponent component(JComponent component) {
        setComponent(component);
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

    /**
     * Will add an AnjoClickListener to the component
     *
     * @param consumer the consumer to accept when the component is clicked
     */
    public AnjoComponent addAnjoClickListener(Consumer<AnjoComponent> consumer) {
        addMouseListener(AnjoClickListener.build(this, consumer));
        return this;
    }

    /**
     * Will add an AnjoKeynputListener to the component
     *
     * @param consumer the consumer to accept when receiving a key input from the component
     * @param inputs   the inputs to listen for
     */
    public AnjoComponent addAnjoKeynputListener(Consumer<AnjoComponent> consumer, char[] inputs) {
        addKeyListener(AnjoKeynputListener.build(this, consumer, inputs));
        return this;
    }
}