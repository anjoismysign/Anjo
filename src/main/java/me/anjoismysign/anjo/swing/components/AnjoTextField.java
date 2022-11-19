package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.entities.*;
import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;

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
     * @return The result. Always check with ByteResult#isValid
     * due that if it being "false" meaning it's not a byte.
     * An example of not being a byte is if NumberFormatException
     * is catched.
     * @see ByteResult
     */
    public ByteResult getByte() {
        JTextField component = getComponent();
        if (component == null)
            return new ByteResult((byte) 0, false);
        byte value;
        try {
            value = Byte.parseByte(component.getText());
            return new ByteResult(value, true);
        } catch (NumberFormatException e) {
            return new ByteResult((byte) 0, false);
        }
    }

    /**
     * Gets a short from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with ShortResult#isValid
     * due that if it being "false" meaning it's not a short.
     * An example of not being a short is if NumberFormatException
     * is catched.
     * @see ShortResult
     */
    public ShortResult getShort() {
        JTextField component = getComponent();
        if (component == null)
            return new ShortResult((short) 0, false);
        short value;
        try {
            value = Short.parseShort(component.getText());
            return new ShortResult(value, true);
        } catch (NumberFormatException e) {
            return new ShortResult((short) 0, false);
        }
    }

    /**
     * Gets an integer from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with IntegerResult#isValid
     * due that if it being "false" meaning it's not an integer.
     * An example of not being an integer is if NumberFormatException
     * is catched.
     * @see IntegerResult
     */
    public IntegerResult getInteger() {
        JTextField component = getComponent();
        if (component == null)
            return new IntegerResult(0, false);
        int value;
        try {
            value = Integer.parseInt(component.getText());
            return new IntegerResult(value, true);
        } catch (NumberFormatException e) {
            return new IntegerResult(0, false);
        }
    }

    /**
     * Gets a long from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with LongResult#isValid
     * due that if it being "false" meaning it's not a long.
     * An example of not being a long is if NumberFormatException
     * is catched.
     * @see LongResult
     */
    public LongResult getLong() {
        JTextField component = getComponent();
        if (component == null)
            return new LongResult(0, false);
        long value;
        try {
            value = Long.parseLong(component.getText());
            return new LongResult(value, true);
        } catch (NumberFormatException e) {
            return new LongResult(0, false);
        }
    }

    /**
     * Gets a float from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with FloatResult#isValid
     * due that if it being "false" meaning it's not a float.
     * An example of not being a float is if NumberFormatException
     * is catched.
     * @see FloatResult
     */
    public FloatResult getFloat() {
        JTextField component = getComponent();
        if (component == null)
            return new FloatResult(0, false);
        float value;
        try {
            value = Float.parseFloat(component.getText());
            return new FloatResult(value, true);
        } catch (NumberFormatException e) {
            return new FloatResult(0, false);
        }
    }

    /**
     * Gets a double from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with DoubleResult#isValid
     * due that if it being "false" meaning it's not a double.
     * An example of not being a double is if NumberFormatException
     * is catched.
     * @see DoubleResult
     */
    public DoubleResult getDouble() {
        JTextField component = getComponent();
        if (component == null)
            return new DoubleResult(0, false);
        double value;
        try {
            value = Double.parseDouble(component.getText());
            return new DoubleResult(value, true);
        } catch (NumberFormatException e) {
            return new DoubleResult(0, false);
        }
    }

    /**
     * Gets a character from the text field.
     * A useful method to validate!
     *
     * @return The result. Always check with CharacterResult#isValid
     * due that if it being "false" meaning it's not a char.
     * An example of not being a char is if the text is more than a single character.
     * @see CharacterResult
     */
    public CharacterResult getCharacter() {
        JTextField component = getComponent();
        if (component == null)
            return new CharacterResult('0', false);
        String text = component.getText();
        if (text.length() == 1)
            return new CharacterResult(text.charAt(0), true);
        return new CharacterResult('0', false);
    }
}
