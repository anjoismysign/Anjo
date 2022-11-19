package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class AnjoClickListener extends AnjoConsumerListener implements MouseListener {

    public static AnjoClickListener build(AnjoComponent component, Consumer<AnjoComponent> consumer) {
        return new AnjoClickListener().anjoComponent(component).consumer(consumer);
    }

    public AnjoClickListener anjoComponent(AnjoComponent component) {
        super.anjoComponent(component);
        return this;
    }

    @Override
    public AnjoClickListener consumer(Consumer<AnjoComponent> consumer) {
        super.consumer(consumer);
        return this;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = e.getComponent();
        if (component.equals(getComponent())) {
            return;
        }
        getConsumer().accept(getAnjoComponent());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
