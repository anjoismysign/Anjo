package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.entities.Result;
import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.components.AnjoTextField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class AnjoTextValidateListener extends AnjoConsumerListener implements KeyListener {
    private TextInputType type;
    private boolean valid;

    /**
     * @param component The component to validate
     * @param type      The type of input
     * @param consumer  The consumer to accept when the input is not valid
     * @return The listener
     */
    public static AnjoTextValidateListener build(AnjoTextField component, TextInputType type,
                                                 Consumer<AnjoComponent> consumer, boolean valid) {
        return new AnjoTextValidateListener().anjoComponent(component)
                .type(type).valid(valid).consumer(consumer);
    }

    public static AnjoTextValidateListener colorText(AnjoTextField component, TextInputType type,
                                                     Color color, boolean valid) {
        return build(component, type, c -> {
            AnjoTextField textField = (AnjoTextField) c;
            textField.getComponent().setForeground(color);
        }, valid);
    }

    @Override
    public AnjoTextField getAnjoComponent() {
        return (AnjoTextField) super.getAnjoComponent();
    }

    @Override
    public AnjoTextValidateListener anjoComponent(AnjoComponent component) {
        if (component instanceof AnjoTextField) {
            super.anjoComponent(component);
            return this;
        }
        throw new IllegalArgumentException("Component must be an instance of AnjoTextField");
    }

    @Override
    public AnjoTextValidateListener consumer(Consumer<AnjoComponent> consumer) {
        super.consumer(consumer);
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

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public AnjoTextValidateListener valid(boolean valid) {
        setValid(valid);
        return this;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean validResult = true;
        switch (type) {
            case BYTE -> {
                Result<Byte> result = getAnjoComponent().getByte();
                validResult = result.isValid();
            }
            case SHORT -> {
                Result<Short> result = getAnjoComponent().getShort();
                validResult = result.isValid();
            }
            case INTEGER -> {
                Result<Integer> result = getAnjoComponent().getInteger();
                validResult = result.isValid();
            }
            case LONG -> {
                Result<Long> result = getAnjoComponent().getLong();
                validResult = result.isValid();
            }
            case FLOAT -> {
                Result<Float> result = getAnjoComponent().getFloat();
                validResult = result.isValid();
            }
            case DOUBLE -> {
                Result<Double> result = getAnjoComponent().getDouble();
                validResult = result.isValid();
            }
            case CHARACTER -> {
                Result<Character> result = getAnjoComponent().getCharacter();
                validResult = result.isValid();
            }
        }
        if (validResult == true && valid == true || validResult == false && valid == false) {
            getConsumer().accept(getAnjoComponent());
        }
    }
}
