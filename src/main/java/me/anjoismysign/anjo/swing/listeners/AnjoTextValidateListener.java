package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.entities.*;
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
        boolean validResult = true;
        System.out.println("1. validResult: " + validResult);
        switch (type) {
            case BYTE -> {
                ByteResult result = getAnjoComponent().getByte();
                validResult = result.isValid();
            }
            case SHORT -> {
                ShortResult result = getAnjoComponent().getShort();
                validResult = result.isValid();
            }
            case INTEGER -> {
                IntegerResult result = getAnjoComponent().getInteger();
                validResult = result.isValid();
                System.out.println("2. validResult: " + validResult);
            }
            case LONG -> {
                LongResult result = getAnjoComponent().getLong();
                validResult = result.isValid();
            }
            case FLOAT -> {
                FloatResult result = getAnjoComponent().getFloat();
                validResult = result.isValid();
            }
            case DOUBLE -> {
                DoubleResult result = getAnjoComponent().getDouble();
                validResult = result.isValid();
            }
            case CHARACTER -> {
                CharacterResult result = getAnjoComponent().getCharacter();
                validResult = result.isValid();
            }
        }
        if (validResult == true && valid == true || validResult == false && valid == false) {
            System.out.println("3. validResult: " + validResult + " valid: " + valid);
            getConsumer().accept(getAnjoComponent());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
