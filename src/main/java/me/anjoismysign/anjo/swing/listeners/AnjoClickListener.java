package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AnjoClickListener implements MouseListener {
    private AnjoComponent component;
    private Runnable runnable;

    public static AnjoClickListener build(AnjoComponent component, Runnable runnable) {
        return new AnjoClickListener().component(component).runnable(runnable);
    }

    public JComponent getComponent() {
        return component.getComponent();
    }

    public void setComponent(AnjoComponent component) {
        this.component = component;
    }

    public AnjoClickListener component(AnjoComponent component) {
        setComponent(component);
        return this;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public AnjoClickListener runnable(Runnable runnable) {
        setRunnable(runnable);
        return this;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = e.getComponent();
        if (component.equals(getComponent())) {
            return;
        }
        runnable.run();
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
