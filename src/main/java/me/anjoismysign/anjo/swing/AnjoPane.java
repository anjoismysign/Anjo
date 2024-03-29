package me.anjoismysign.anjo.swing;

import me.anjoismysign.anjo.entities.Result;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.List;

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
    public static AnjoPane build(String title, OptionType optionType, Image image, AnjoComponent... components) {
        int optionTypeInt = switch (optionType) {
            case OK -> -1;
            case YES_NO -> 0;
            case YES_NO_CANCEL -> 1;
            case OK_CANCEL -> 2;
        };
        return build(List.of(components), title, optionTypeInt, image);
    }

    /**
     * @param components The form components.
     * @param title      The form title.
     * @param optionType Types of buttons to be shown.
     * @return The form
     * @see AnjoComponent
     */
    public static AnjoPane build(String title, OptionType optionType, AnjoComponent... components) {
        return build(title, optionType, null, components);
    }

    /**
     * @param components The form components.
     * @param title      The form title.
     * @param optionType Types of buttons to be shown.
     *                   -1: OK button
     *                   0:  YES/NO buttons
     *                   1:  YES/NO/CANCEL buttons
     *                   2:  OK/CANCEL buttons
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
     * @return true if the user clicked "CANCEL" or closed the window
     */
    public boolean didCancel() {
        return result == 2 || result == -1;
    }

    /**
     * @return true if the user clicked "NO"
     */
    public boolean saidNo() {
        return result == 1;
    }

    /**
     * @return true if the user clicked "YES/OK"
     */
    public boolean saidYes() {
        return result == 0;
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
     * @return The result. Always check with Result<Byte>#isValid
     * due that if it being "false" meaning it's not a byte.
     * An example of not being a byte is if NumberFormatException
     * is catched.
     * @see Result<Byte>
     */
    public Result<Byte> getByte(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        byte value;
        try {
            value = Byte.parseByte(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a short of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Short>#isValid
     * due that if it being "false" meaning it's not a short.
     * An example of not being a short is if NumberFormatException
     * is catched.
     * @see Result<Short>
     */
    public Result<Short> getShort(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        short value;
        try {
            value = Short.parseShort(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets an integer of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Integer>#isValid
     * due that if it being "false" meaning it's not an integer.
     * An example of not being an integer is if NumberFormatException
     * is catched.
     * @see Result<Integer>
     */
    public Result<Integer> getInteger(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        int value;
        try {
            value = Integer.parseInt(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a long of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Long>#isValid
     * due that if it being "false" meaning it's not a long.
     * An example of not being a long is if NumberFormatException
     * is catched.
     * @see Result<Long>
     */
    public Result<Long> getLong(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        long value;
        try {
            value = Long.parseLong(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a float of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Float>#isValid
     * due that if it being "false" meaning it's not a float.
     * An example of not being a float is if NumberFormatException
     * is catched.
     * @see Result<Float>
     */
    public Result<Float> getFloat(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        float value;
        try {
            value = Float.parseFloat(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a double of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Double>#isValid
     * due that if it being "false" meaning it's not a double.
     * An example of not being a double is if NumberFormatException
     * is catched.
     * @see Result<Double>
     */
    public Result<Double> getDouble(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        double value;
        try {
            value = Double.parseDouble(textField.getText());
            return new Result<>(value, true);
        } catch (NumberFormatException e) {
            return new Result<>(null, false);
        }
    }

    /**
     * Gets a character of an index.
     * A useful method to validate!
     *
     * @param index The index of the row
     * @return The result. Always check with Result<Character>#isValid
     * due that if it being "false" meaning it's not a char.
     * An example of not being a char is if the text is more than a single character.
     * @see Result<Character>
     */
    public Result<Character> getCharacter(int index) {
        JTextField textField = getJTextField(index);
        if (textField == null)
            return new Result<>(null, false);
        String text = textField.getText();
        if (text.length() == 1)
            return new Result<>(text.charAt(0), true);
        return new Result<>(null, false);
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
