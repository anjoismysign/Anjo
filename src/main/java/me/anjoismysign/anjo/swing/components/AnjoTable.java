package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.swing.AnjoComponent;
import me.anjoismysign.anjo.swing.listeners.AnjoClickListener;
import me.anjoismysign.anjo.swing.listeners.AnjoKeynputListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class AnjoTable extends AnjoComponent {
    private JTable table;

    /**
     * @param columnNames Column names.
     * @param data        First array is the row, second array is the column.
     *                    Remember that first column is 0, not 1!
     *                    An example of this is:
     *                    <pre>String[][] data = {{"1", "2", "3"},{"4", "5", "6"},{"7", "8", "9"}};</pre>
     *                    This will assign 1,2 and 3 to row '0', 4,5 and 6 to row '1' and 7,8 and 9 to row 2.
     * @return the AnjoTable component.
     */
    public static AnjoTable build(String[] columnNames, Object[][] data) {
        JTable table = new JTable(data, columnNames);
        return new AnjoTable(table);
    }

    public AnjoTable(JTable jTable) {
        new AnjoTable("", jTable);
    }

    public AnjoTable(String label, JTable jTable) {
        super(label, jTable);
        this.table = jTable;
    }

    /**
     * sets the label
     *
     * @param label the label to set
     */
    public AnjoTable label(JLabel label) {
        super.label(label);
        return this;
    }

    /**
     * sets the label. will be converted to a JLabel
     *
     * @param label the label to set
     */
    public AnjoTable label(String label) {
        super.label(label);
        return this;
    }

    /**
     * will set the component
     *
     * @param component the component to set
     */
    public AnjoTable component(JComponent component) {
        super.component(component);
        return this;
    }

    /**
     * will add a mouse listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoTable addMouseListener(MouseListener listener) {
        super.addMouseListener(listener);
        return this;
    }

    /**
     * will add a key listener to the component
     *
     * @param listener the listener to add
     */
    public AnjoTable addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        return this;
    }

    /**
     * Will add an AnjoClickListener to the component
     *
     * @param runnable the runnable to be run when the component is clicked
     */
    public AnjoTable addAnjoClickListener(Runnable runnable) {
        addMouseListener(AnjoClickListener.build(this, runnable));
        return this;
    }

    /**
     * Will add an AnjoKeynputListener to the component
     *
     * @param runnable the runnable to be run when receiving a key input from the component
     * @param inputs   the inputs to listen for
     */
    public AnjoTable addAnjoKeynputListener(Runnable runnable, char[] inputs) {
        addKeyListener(AnjoKeynputListener.build(this, runnable, inputs));
        return this;
    }

    @Override
    public JTable getComponent() {
        return (JTable) super.getComponent();
    }

    /**
     * Will update the TableModel of the component.
     *
     * @param columnNames Column names.
     * @param data        First array is the row, second array is the column.
     *                    Remember that first column is 0, not 1!
     *                    An example of this is:
     *                    <pre>String[][] data = {{"1", "2", "3"},{"4", "5", "6"},{"7", "8", "9"}};</pre>
     *                    This will assign 1,2 and 3 to row '0', 4,5 and 6 to row '1' and 7,8 and 9 to row 2.
     */
    public AnjoTable updateModel(String[] columnNames, Object[][] data) {
        updateModel(new DefaultTableModel(data, columnNames));
        return this;
    }

    /**
     * Will update the TableModel of the component.
     *
     * @param model the model to set
     */
    public AnjoTable updateModel(TableModel model) {
        table.setModel(model);
        return this;
    }
}
