package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;
import java.util.Collection;

public class AnjoComboBox extends AnjoComponent {
    private JComboBox<String> comboBox;

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

    public JComboBox<String> getComboBox() {
        return comboBox;
    }
}