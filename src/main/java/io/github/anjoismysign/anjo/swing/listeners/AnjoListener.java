package io.github.anjoismysign.anjo.swing.listeners;

import io.github.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.JComponent;

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
