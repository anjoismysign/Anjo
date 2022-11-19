package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;

public abstract class AnjoListener {
    private AnjoComponent component;

    public AnjoComponent getAnjoComponent() {
        return component;
    }

    public void setComponent(AnjoComponent component) {
        this.component = component;
    }

    public AnjoListener anjoComponent(AnjoComponent anjoComponent) {
        setComponent(anjoComponent);
        return this;
    }

    public JComponent getComponent() {
        return component.getComponent();
    }
}
