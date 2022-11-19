package me.anjoismysign.anjo.swing.components;

import me.anjoismysign.anjo.swing.AnjoComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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

    public JTable getTable() {
        return table;
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
        getTable().setModel(model);
        return this;
    }
}
