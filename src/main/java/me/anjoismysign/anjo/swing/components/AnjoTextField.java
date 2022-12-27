package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.entities.Result;
import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.listeners.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class AnjoTextField extends AnjoComponent {

    public AnjoTextField(String label) {
        super(label, new JTextField());
    }

    /**
     * @param jLabel the label.
     */
    public AnjoTextField(JLabel jLabel) {
        super(jLabel, new JTextField());
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoTextField label(JLabel label) {
        super.label(label);
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoTextField label(String label) {
        super.label(label);
        return this;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoTextField component(JComponent component) {
        super.component(component);
        return this;
    }

    /**
     * will add a mouse listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoTextField addMouseListener(MouseListener listener) {
        super.addMouseListener(listener);
        return this;
    }

    /**
     * will add a key listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoTextField addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        return this;
    }

    @Override
    public JTextField getComponent() {
        return (JTextField) super.getComponent();
    }

    /**
     * @return The text in case of being found, null otherwise.
     */
    public String getText() {
        JTextField component = getComponent();
        if (component != null)
            return component.getText();
        return null;
    }

    /**
     * Gets a byte from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Byte>#isValid
     * due that if it being "false" meaning it's not a byte.
     * An example of not being a byte is if NumberFormatException
     * is catched.
     * @see Result<Byte>
     */
    public Result<Byte> getByte() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>(null, false);
        byte value;
        try {
            value = Byte.parseByte(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a short from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Short>#isValid
     * due that if it being "false" meaning it's not a short.
     * An example of not being a short is if NumberFormatException
     * is catched.
     * @see Result<Short>
     */
    public Result<Short> getShort() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>(null, false);
        short value;
        try {
            value = Short.parseShort(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets an integer from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Integer>#isValid
     * due that if it being "false" meaning it's not an integer.
     * An example of not being an integer is if NumberFormatException
     * is catched.
     * @see Result<Integer>
     */
    public Result<Integer> getInteger() {
        JTextField component = getComponent();
        if (component == null) {
            return new Result<>(null, false);
        }
        int value;
        try {
            value = Integer.parseInt(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a long from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Long>#isValid
     * due that if it being "false" meaning it's not a long.
     * An example of not being a long is if NumberFormatException
     * is catched.
     * @see Result<Long>
     */
    public Result<Long> getLong() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>(null, false);
        long value;
        try {
            value = Long.parseLong(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a float from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Float>#isValid
     * due that if it being "false" meaning it's not a float.
     * An example of not being a float is if NumberFormatException
     * is catched.
     * @see Result<Float>
     */
    public Result<Float> getFloat() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>(null, false);
        float value;
        try {
            value = Float.parseFloat(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a double from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Double>#isValid
     * due that if it being "false" meaning it's not a double.
     * An example of not being a double is if NumberFormatException
     * is catched.
     * @see Result<Double>
     */
    public Result<Double> getDouble() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>(null, false);
        double value;
        try {
            value = Double.parseDouble(component.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a character from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with Result<Character>#isValid
     * due that if it being "false" meaning it's not a char.
     * An example of not being a char is if the text is more than a single character.
     * @see Result<Character>
     */
    public Result<Character> getCharacter() {
        JTextField component = getComponent();
        if (component == null)
            return new Result<>('0', false);
        String text = component.getText();
        if (text.length() == 1)
            return new Result<>(text.charAt(0), true);
        return new Result<>('0', false);
    }

    /**
     * Will add an AnjoTextValidateListener
     *
     * @param type     The desired input type variable.
     * @param consumer The consumer to accept in case the text is not valid.
     */
    public AnjoTextField addAnjoTextValidateListener(TextInputType type,
                                                     Consumer<AnjoComponent> consumer,
                                                     boolean valid) {
        addKeyListener(AnjoTextValidateListener.build(this, type, consumer, valid));
        return this;
    }

    /**
     * Will add an AnjoTextValidateListener that will color text
     * if met the input type and valid.
     *
     * @param type  The desired input type variable.
     * @param color The color to set if valid.
     * @param valid If the text is valid or not.
     * @return
     */
    public AnjoTextField addColorToText(TextInputType type, Color color, boolean valid) {
        addKeyListener(AnjoTextValidateListener.colorText(this, type, color, valid));
        return this;
    }

    /**
     * Will add an AnjoScheduleTextValidateListener
     *
     * @param type           The desired input type variable.
     * @param consumer       The consumer to accept in case the text is not valid.
     * @param maxTranscurred The max transcurred time to accept the consumer.
     */
    public AnjoTextField addAnjoScheduledTextValidateListener(TextInputType type,
                                                              Consumer<AnjoComponent> consumer,
                                                              long maxTranscurred, boolean valid) {
        addKeyListener(AnjoScheduleTextValidateListener.build(this, type,
                consumer, maxTranscurred, valid));
        return this;
    }

    /**
     * Will add an AnjoTextValidateListener that will color text
     * if met the input type and valid.
     *
     * @param type           The desired input type variable.
     * @param color          The color to set if valid.
     * @param maxTranscurred The max transcurred time to accept the consumer.
     * @param valid          If the text is valid or not.
     * @return
     */
    public AnjoTextField addScheduleColorToText(TextInputType type, Color color, long maxTranscurred, boolean valid) {
        addKeyListener(AnjoScheduleTextValidateListener.colorText(this, type,
                color, maxTranscurred, valid));
        return this;
    }

    /**
     * Will add an AnjoClickListener to the component
     *
     * @param consumer the consumer to accept when the component is clicked
     */
    public AnjoTextField addAnjoClickListener(Consumer<AnjoComponent> consumer) {
        addMouseListener(AnjoClickListener.build(this, consumer));
        return this;
    }

    /**
     * Will add an AnjoKeynputListener to the component
     *
     * @param consumer the consumer to accept when receiving a key input from the component
     * @param inputs   the inputs to listen for
     */
    public AnjoTextField addAnjoKeynputListener(Consumer<AnjoComponent> consumer, char[] inputs) {
        addKeyListener(AnjoKeynputListener.build(this, consumer, inputs));
        return this;
    }
}
