package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnjoKeynputListener implements KeyListener {
    private AnjoComponent component;
    private char[] inputs;
    private Runnable runnable;

    public static AnjoKeynputListener build(AnjoComponent component, Runnable runnable, char[] inputs) {
        return new AnjoKeynputListener().component(component).runnable(runnable).inputs(inputs);
    }

    public AnjoKeynputListener component(AnjoComponent component) {
        this.component = component;
        return this;
    }

    public JComponent getComponent() {
        return component.getComponent();
    }

    public AnjoKeynputListener inputs(char[] inputs) {
        this.inputs = inputs;
        return this;
    }

    public char[] getInputs() {
        return inputs;
    }

    public AnjoKeynputListener runnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        Component component = e.getComponent();
        if (!component.equals(getComponent()))
            return;
        char input = e.getKeyChar();
        for (char character : inputs) {
            if (input != character) {
                continue;
            }
            runnable.run();
            break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
