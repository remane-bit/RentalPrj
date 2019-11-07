package project3;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.util.*;


public class ListEngineOverDue extends AbstractTableModel {

    /** List of vehicles in the 90 days overdue screen **/
    private ArrayList<Auto> listOverDueAutos;

    /** Column names **/
    private String[] columnNamesOverdue = {"Auto Name", "Bought Cost", "Bought Date",
            "Day's OverDue"};

    /**************************************************************
     * Column name getter
     * @param col
     * @return columnNamesOverdue[col]
     **************************************************************/
    @Override
    public String getColumnName(int col) {
        return columnNamesOverdue[col];
    }

    /**************************************************************
     * Constructor for the engine, creates the list of vehicles overdue
     **************************************************************/
    public ListEngineOverDue() {
        super();
        listOverDueAutos = new ArrayList<Auto>();
    }

    /**************************************************************
     * Adds a vehicle in to the ArrayList
     * @param a
     **************************************************************/
    public void add(Auto a) {
        listOverDueAutos.add(a);
        sortDaysOverDue();
        fireTableDataChanged();
    }

    /**************************************************************
     * Function that sorts the vehicles by the amount of days
     * overdue
     **************************************************************/
    public void sortDaysOverDue() {
        listOverDueAutos.sort(Comparator.comparing(Auto::getDaysBetween));
        Collections.reverse(listOverDueAutos);
    }

    /**************************************************************
     * Gets the amount of vehicles in the ArrayList
     *
     * @return int
     **************************************************************/
    public int getSize() {
        return listOverDueAutos.size();
    }

    /**************************************************************
     * Removes the vehicle in the ArrayList at that index
     *
     * @param i
     **************************************************************/
    public void remove(int i) {
        listOverDueAutos.remove(i);
        fireTableDataChanged();
    }

    /**************************************************************
     * This method counts the amount of rows in the created table
     *
     * @return int
     **************************************************************/
    @Override
    public int getRowCount() {
        return listOverDueAutos.size();
    }

    /**************************************************************
     * This method counts the amount of columns in the created table
     * @return int
     **************************************************************/
    @Override
    public int getColumnCount() {
        return columnNamesOverdue.length;
    }

    /**************************************************************
     * This method will return the value stored at (row, col) in the
     * created table. The row basically acts as the index of the
     * ArrayList, while the col acts as the certain object the user
     * is trying to acquire
     *
     * @param columnIndex
     * @param rowIndex
     * @return Object
     **************************************************************/
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                /** The name of the vehicle **/
                return (listOverDueAutos.get(rowIndex).getAutoName());

            case 1:
                /** The amount the dealer bought the vehicle for **/
                return (listOverDueAutos.get(rowIndex).getBoughtCost());

            case 2:
                /** The date the dealer bought the vehicle **/
                return (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listOverDueAutos.get(rowIndex).getBoughtOn().getTime()));

            case 3:
                /** The amount of days between the day it was bought and the current date **/
                return (listOverDueAutos.get(rowIndex).getDaysBetween());

            default:
                /** Throws default error **/
                throw new RuntimeException("JTable row,col out of range: " + rowIndex + " " + columnIndex);
        }
    }
}
