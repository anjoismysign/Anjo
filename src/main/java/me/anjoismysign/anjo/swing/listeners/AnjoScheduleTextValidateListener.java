package me.anjoismysign.anjo.swing.listeners;

import me.anjoismysign.anjo.swing.components.AnjoTextField;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnjoScheduleTextValidateListener extends AnjoTextValidateListener implements KeyListener {
    private long transcurred;
    private long maxTranscurred;

    /**
     * @param component      The component to validate
     * @param type           The type of input
     * @param runnable       The runnable to run when the input is not valid
     * @param maxTranscurred max time in milliseconds
     * @return The listener
     */
    public static AnjoScheduleTextValidateListener build(AnjoTextField component, TextInputType type,
                                                         Runnable runnable, long maxTranscurred) {
        return new AnjoScheduleTextValidateListener().component(component)
                .runnable(runnable).type(type).maxTranscurred(maxTranscurred)
                .transcurred(System.currentTimeMillis());
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
    public AnjoScheduleTextValidateListener component(AnjoTextField component) {
        super.component(component);
        return this;
    }

    @Override
    public AnjoScheduleTextValidateListener runnable(Runnable runnable) {
        super.runnable(runnable);
        return this;
    }

    @Override
    public AnjoScheduleTextValidateListener type(TextInputType type) {
        super.type(type);
        return this;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getID() != KeyEvent.KEY_TYPED)
            return;
        if (this.transcurred - System.currentTimeMillis() > this.maxTranscurred) {
            this.transcurred = System.currentTimeMillis();
            run();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
