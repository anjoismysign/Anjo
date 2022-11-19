package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.entities.*;
import me.anjoismysign.anjo.swing.components.AnjoTextField;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnjoTextValidateListener implements KeyListener {
    private AnjoTextField component;
    private Runnable runnable;
    private TextInputType type;

    public static AnjoTextValidateListener build(AnjoTextField component, TextInputType type,
                                                 Runnable runnable) {
        return new AnjoTextValidateListener().component(component)
                .runnable(runnable).type(type);
    }

    public AnjoTextField getComponent() {
        return component;
    }

    public void setComponent(AnjoTextField component) {
        this.component = component;
    }

    public AnjoTextValidateListener component(AnjoTextField component) {
        setComponent(component);
        return this;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public AnjoTextValidateListener runnable(Runnable runnable) {
        setRunnable(runnable);
        return this;
    }

    public TextInputType getType() {
        return type;
    }

    public void setType(TextInputType type) {
        this.type = type;
    }

    public AnjoTextValidateListener type(TextInputType type) {
        setType(type);
        return this;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getID() != KeyEvent.KEY_TYPED)
            return;
        run();
    }

    public void run() {
        switch (type) {
            case BYTE -> {
                ByteResult result = getComponent().getByte();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case SHORT -> {
                ShortResult result = component.getShort();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case INTEGER -> {
                IntegerResult result = component.getInteger();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case LONG -> {
                LongResult result = component.getLong();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case FLOAT -> {
                FloatResult result = component.getFloat();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case DOUBLE -> {
                DoubleResult result = component.getDouble();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
            case CHARACTER -> {
                CharacterResult result = component.getCharacter();
                if (result.isValid()) {
                    return;
                }
                runnable.run();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
