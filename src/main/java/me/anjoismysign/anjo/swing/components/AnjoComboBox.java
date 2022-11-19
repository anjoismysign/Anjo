package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.listeners.AnjoClickListener;
import me.anjoismysign.anjo.swing.listeners.AnjoKeynputListener;

import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.function.Consumer;

public class AnjoComboBox extends AnjoComponent {
    private final JComboBox<String> comboBox;

    public static AnjoComboBox build(String label, Collection<String> options) {
        return build(label, options.toArray(new String[0]));
    }

    public static AnjoComboBox build(JLabel label, Collection<String> options) {
        return build(label, options.toArray(new String[0]));
    }

    public static AnjoComboBox build(String label, String[] options) {
        return new AnjoComboBox(label, new JComboBox<>(options));
    }

    public static AnjoComboBox build(JLabel label, String[] options) {
        return new AnjoComboBox(label, new JComboBox<>(options));
    }

    public AnjoComboBox(String label, JComboBox<String> comboBox) {
        super(label, comboBox);
        this.comboBox = comboBox;
    }

    public AnjoComboBox(JLabel label, JComboBox<String> comboBox) {
        super(label, comboBox);
        this.comboBox = comboBox;
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoComboBox label(JLabel label) {
        super.label(label);
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoComboBox label(String label) {
        super.label(label);
        return this;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoComboBox component(JComponent component) {
        super.component(component);
        return this;
    }

    /**
     * will add a mouse listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoComboBox addMouseListener(MouseListener listener) {
        super.addMouseListener(listener);
        return this;
    }

    /**
     * will add a key listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoComboBox addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        return this;
    }

    /**
     * Will add an AnjoClickListener to the component
     *
     * @param consumer the consomuer to accept when the component is clicked
     */
    public AnjoComboBox addAnjoClickListener(Consumer<AnjoComponent> consumer) {
        addMouseListener(AnjoClickListener.build(this, consumer));
        return this;
    }

    /**
     * Will add an AnjoKeynputListener to the component
     *
     * @param consumer the consumer to accept when receiving a key input from the component
     * @param inputs   the inputs to listen for
     */
    public AnjoComboBox addAnjoKeynputListener(Consumer<AnjoComponent> consumer, char[] inputs) {
        addKeyListener(AnjoKeynputListener.build(this, consumer, inputs));
        return this;
    }

    @Override
    public JComboBox getComponent() {
        return (JComboBox) super.getComponent();
    }

    public AnjoComboBox addItemListener(ItemListener listener) {
        comboBox.addItemListener(listener);
        return this;
    }

    public AnjoComboBox addItem(String item) {
        comboBox.addItem(item);
        return this;
    }

    public AnjoComboBox removeItem(String item) {
        comboBox.removeItem(item);
        return this;
    }
}
