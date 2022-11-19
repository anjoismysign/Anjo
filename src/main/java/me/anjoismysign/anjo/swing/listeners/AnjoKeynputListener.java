package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class AnjoKeynputListener extends AnjoConsumerListener implements KeyListener {
    private char[] inputs;

    public static AnjoKeynputListener build(AnjoComponent component, Consumer<AnjoComponent> consumer, char[] inputs) {
        return new AnjoKeynputListener().anjoComponent(component).consumer(consumer).inputs(inputs);
    }

    @Override
    public AnjoKeynputListener anjoComponent(AnjoComponent component) {
        super.anjoComponent(component);
        return this;
    }

    public AnjoKeynputListener inputs(char[] inputs) {
        this.inputs = inputs;
        return this;
    }

    public char[] getInputs() {
        return inputs;
    }

    public AnjoKeynputListener consumer(Consumer<AnjoComponent> consumer) {
        super.consumer(consumer);
        return this;
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
            getConsumer().accept(getAnjoComponent());
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
