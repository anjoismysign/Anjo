package me.anjoismysign.anjo.swing;

import me.anjoismysign.anjo.entities.*;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * @author anjoismysign
 */
public class AnjoPane {
    private JPanel panel;
    private int result;

    /**
     * default constructor
     *
     * @param panel the panel
     */
    public AnjoPane(JPanel panel) {
        this.panel = panel;
    }

    /**
     * empty constructor. you need to call {@link #setPanel(JPanel)} to set the panel
     */
    public AnjoPane() {
    }

    /**
     * @param components The form components.
     * @param title      The form title.
     * @param optionType Types of buttons to be shown.
     * @param image      Image to be used as icon through the form
     * @return The form
     * @see AnjoComponent
     */
    public static AnjoPane build(Collection<AnjoComponent> components,
                                 String title, int optionType,
                                 Image image) {
        AnjoPane pane = new AnjoPane(new JPanel(new GridLayout(components.size(), 2)));
        JPanel panel = pane.getPanel();
        components.forEach(component -> {
            panel.add(component.getLabel());
            panel.add(component.getComponent());
        });
        if (image != null) {
            JPanel master = new JPanel(new BorderLayout());
            JPanel imagePanel = new JPanel(new FlowLayout());
            JLabel label = new JLabel(new ImageIcon(image));
            imagePanel.add(label);
            master.add(imagePanel, BorderLayout.CENTER);
            master.add(panel, BorderLayout.PAGE_END);
            pane.setResult(new AnjoConfirmDialog(master, title, optionType, image).getResult());
            return pane;
        }
        pane.setResult(new AnjoConfirmDialog(panel, title, optionType, null).getResult());
        return pane;
    }

    /**
     * builds a form without an image
     *
     * @param components The form components.
     * @param title      The form title.
     * @param optionType Types of buttons to be shown.
     * @return The form
     * @see AnjoComponent
     */
    public static AnjoPane build(Collection<AnjoComponent> components,
                                 String title, int optionType) {
        return build(components, title, optionType, null);
    }

    /**
     * sets the panel
     *
     * @param panel the panel
     */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * @return the panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * sets the result
     *
     * @param result the result
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * @return 0 in case of "YES/OK", 1 in case of "NO", 2 in case of "CANCEL", -1 in case of
     * closing the window
     */
    public int getResult() {
        return result;
    }

    /**
     * Gets the component of a row.
     *
     * @param index index of the row
     * @return The component in case of being found, null otherwise.
     */
    public Component getComponent(int index) {
        Component component;
        try {
            component = getPanel().getComponent(index + index + 1);
            return component;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Index doesn't exist \n" +
                    "Total form indexes: " + panel.getComponentCount() / 2);
            return null;
        }
    }


    /**
     * Casts AnjoPane#getComponent to a JTextField and returns it.
     *
     * @param index Index of the row
     * @return The component in case of being found, null otherwise.
     */
    public JTextField getJTextField(int index) {
        Component component = getComponent(index);
        if (component instanceof JTextField)
            return (JTextField) component;
        return null;
    }

    /**
     * Casts AnjoPane#getComponent to a JComboBox and returns it.
     *
     * @param index Index of the row
     * @return The component in case of being found, null otherwise.
     */
    public JComboBox getJComboBox(int index) {
        Component component = getComponent(index);
        if (component instanceof JComboBox)
            return (JComboBox) component;
        return null;
    }

    /**
     * Gets the text of a row in case of being a JTextField.
     *
     * @param index Index of the row
     * @return The text in case of being found, null otherwise.
     */
    public String getTextFieldText(int index) {
        JTextField textField = getJTextField(index);
        if (textField != null)
            return textField.getText();
        return null;
    }

    /**
     * Gets a byte of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with ByteResult#isValid
     * due that if it being "false" meaning it's not a byte.
     * An example of not being a byte is if NumberFormatException
     * is catched.
     * @see ByteResult
     */
    public ByteResult getByte(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new ByteResult((byte) 0, false);
        byte value;
        try {
            value = Byte.parseByte(textField.getText());
            return new ByteResult(value, true);
        } catch (NumberFormatException e) {
            return new ByteResult((byte) 0, false);
        }
    }

    /**
     * Gets a short of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with ShortResult#isValid
     * due that if it being "false" meaning it's not a short.
     * An example of not being a short is if NumberFormatException
     * is catched.
     * @see ShortResult
     */
    public ShortResult getShort(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new ShortResult((short) 0, false);
        short value;
        try {
            value = Short.parseShort(textField.getText());
            return new ShortResult(value, true);
        } catch (NumberFormatException e) {
            return new ShortResult((short) 0, false);
        }
    }

    /**
     * Gets an integer of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with IntegerResult#isValid
     * due that if it being "false" meaning it's not an integer.
     * An example of not being an integer is if NumberFormatException
     * is catched.
     * @see IntegerResult
     */
    public IntegerResult getInteger(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new IntegerResult(0, false);
        int value;
        try {
            value = Integer.parseInt(textField.getText());
            return new IntegerResult(value, true);
        } catch (NumberFormatException e) {
            return new IntegerResult(0, false);
        }
    }

    /**
     * Gets a long of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with LongResult#isValid
     * due that if it being "false" meaning it's not a long.
     * An example of not being a long is if NumberFormatException
     * is catched.
     * @see LongResult
     */
    public LongResult getLong(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new LongResult(0, false);
        long value;
        try {
            value = Long.parseLong(textField.getText());
            return new LongResult(value, true);
        } catch (NumberFormatException e) {
            return new LongResult(0, false);
        }
    }

    /**
     * Gets a float of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with FloatResult#isValid
     * due that if it being "false" meaning it's not a float.
     * An example of not being a float is if NumberFormatException
     * is catched.
     * @see FloatResult
     */
    public FloatResult getFloat(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new FloatResult(0, false);
        float value;
        try {
            value = Float.parseFloat(textField.getText());
            return new FloatResult(value, true);
        } catch (NumberFormatException e) {
            return new FloatResult(0, false);
        }
    }

    /**
     * Gets a double of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with DoubleResult#isValid
     * due that if it being "false" meaning it's not a double.
     * An example of not being a double is if NumberFormatException
     * is catched.
     * @see DoubleResult
     */
    public DoubleResult getDouble(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new DoubleResult(0, false);
        double value;
        try {
            value = Double.parseDouble(textField.getText());
            return new DoubleResult(value, true);
        } catch (NumberFormatException e) {
            return new DoubleResult(0, false);
        }
    }

    /**
     * Gets a character of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with CharacterResult#isValid
     * due that if it being "false" meaning it's not a char.
     * An example of not being a char is if the text is more than a single character.
     * @see CharacterResult
     */
    public CharacterResult getCharacter(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new CharacterResult('0', false);
        String text = textField.getText();
        if (text.length() == 1)
            return new CharacterResult(text.charAt(0), true);
        return new CharacterResult('0', false);
    }

    /**
     * Returns the object selected in a JComboBox.
     * You should upcast it, as a String for example.
     *
     * @param index Index of the row
     * @return The object selected in a JComboBox if found, null otherwise.
     */
    public Object getComboBoxItem(int index) {
        JComboBox comboBox = getJComboBox(index);
        if (comboBox != null)
            return comboBox.getSelectedItem();
        return null;
    }

    /**
     * Gets the text selected in a JComboBox.
     * It auto upcasts to String and returns it.
     *
     * @param index Index of the row
     * @return The selected text of a JComboBox if found, null otherwise.
     */
    public String getComboBoxText(int index) {
        JComboBox comboBox = getJComboBox(index);
        if (comboBox != null)
            return comboBox.getSelectedItem().toString();
        return null;
    }
}
