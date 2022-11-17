package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;

public class AnjoTextField extends AnjoComponent {

    public AnjoTextField(String label) {
        super(label, new JTextField());
    }

    /**
     * @param jLabel the label.
     */
    public AnjoTextField(JLabel jLabel) {
        super(jLabel, new JTextField());
    }

}
