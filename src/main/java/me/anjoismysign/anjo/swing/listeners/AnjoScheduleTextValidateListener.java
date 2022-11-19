package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.components.AnjoTextField;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class AnjoScheduleTextValidateListener extends AnjoTextValidateListener implements KeyListener {
    private long transcurred;
    private long maxTranscurred;

    /**
     * @param component      The component to validate
     * @param type           The type of input
     * @param consumer       The consumer to accept when the input is not valid
     * @param maxTranscurred max time in milliseconds
     * @return The listener
     */
    public static AnjoScheduleTextValidateListener build(AnjoTextField component, TextInputType type,
                                                         Consumer<AnjoComponent> consumer,
                                                         long maxTranscurred, boolean valid) {
        return new AnjoScheduleTextValidateListener().anjoComponent(component)
                .type(type).maxTranscurred(maxTranscurred)
                .transcurred(System.currentTimeMillis())
                .valid(valid).consumer(consumer);
    }

    public static AnjoTextValidateListener colorText(AnjoTextField component, TextInputType type,
                                                     Color color, long maxTranscurred,
                                                     boolean valid) {
        return build(component, type, c -> {
            AnjoTextField textField = (AnjoTextField) c;
            textField.getComponent().setForeground(color);
        }, maxTranscurred, valid);
    }

    @Override
    public AnjoScheduleTextValidateListener valid(boolean valid) {
        super.valid(valid);
        return this;
    }

    public long getTranscurred() {
        return transcurred;
    }

    public void setTranscurred(long transcurred) {
        this.transcurred = transcurred;
    }

    public AnjoScheduleTextValidateListener transcurred(long transcurred) {
        this.transcurred = transcurred;
        return this;
    }

    public long getMaxTranscurred() {
        return maxTranscurred;
    }

    public AnjoScheduleTextValidateListener maxTranscurred(long maxTranscurred) {
        this.maxTranscurred = maxTranscurred;
        return this;
    }

    @Override
    public AnjoScheduleTextValidateListener anjoComponent(AnjoComponent component) {
        super.anjoComponent(component);
        return this;
    }

    @Override
    public AnjoScheduleTextValidateListener consumer(Consumer<AnjoComponent> consumer) {
        super.consumer(consumer);
        return this;
    }

    @Override
    public AnjoScheduleTextValidateListener type(TextInputType type) {
        super.type(type);
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
        if (this.transcurred - System.currentTimeMillis() > this.maxTranscurred) {
            this.transcurred = System.currentTimeMillis();
            super.keyReleased(e);
        }
    }
}
