package project3;

import javax.swing.table.AbstractTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ListEngineSold extends AbstractTableModel {

    /** List of vehicles in the sold screen **/
    private ArrayList<Auto> listSoldAutos;

    /** Column names **/
    private String[] columnNamesSold = {"Auto Name", "Bought Cost", "Bought Date",
            "Buyer's Name" , "Sold For", "Sold On"};

    /**************************************************************
     * Column name getter
     * @param col
     * @return columnNamesOverdue[col]
     **************************************************************/
    @Override
    public String getColumnName(int col) {
        return columnNamesSold[col];
    }

    /**************************************************************
     * Constructor for the engine, creates the list of vehicles sold
     **************************************************************/
    public ListEngineSold() {
        super();
        listSoldAutos = new ArrayList<Auto>();
        createList();
    }


    /**************************************************************
     * Adds a vehicle in to the ArrayList
     * @param a
     **************************************************************/
    public void add(Auto a) {
        listSoldAutos.add(a);
        sortByNames();
        fireTableRowsInserted(0, 9);
    }

    /**************************************************************
     * Generic sorting function, sorts by buyer's names
     **************************************************************/
    public void sortByNames() {
        listSoldAutos.sort(Comparator.comparing(Auto::getNameOfBuyer));
    }

    /**************************************************************
     * This method counts the amount of rows in the created table
     *
     * @return int
     **************************************************************/
    @Override
    public int getRowCount() {
        return listSoldAutos.size();
    }

    /**************************************************************
     * This method counts the amount of columns in the created table
     * @return int
     **************************************************************/
    @Override
    public int getColumnCount() {
        return columnNamesSold.length;
    }

    /**************************************************************
     * This method will return the value stored at (row, col) in the
     * created table. The row basically acts as the index of the
     * ArrayList, while the col acts as the certain object the user
     * is trying to acquire
     *
     * @param columnIndex
     * @param row
     * @return Object
     **************************************************************/
    @Override
    public Object getValueAt(int row, int columnIndex) {

        switch (columnIndex) {
            case 0:
                /** Name of the vehicle **/
                return (listSoldAutos.get(row).getAutoName());

            case 1:
                /** How much the dealer paid for the vehicle **/
                return (listSoldAutos.get(row).getBoughtCost());

            case 2:
                /** The day the dealer bought the vehicle **/
                return ((DateFormat.getDateInstance(DateFormat.SHORT).format(listSoldAutos.get(row).getBoughtOn().getTime())));

            case 3:
                /** The name of the person who bought the vehicle **/
                return (listSoldAutos.get(row).getNameOfBuyer());

            case 4:
                /** The amount the dealer sold the vehicle for **/
                return (listSoldAutos.get(row).getSoldPrice());

            case 5:
                /** The day the dealer sold the vehicle **/
                return ((DateFormat.getDateInstance(DateFormat.SHORT).format(listSoldAutos.get(row).getSoldOn().getTime())));

            default:
                /** Throws default error **/
                throw new RuntimeException("JTable row,col out of range: " + row + " " + columnIndex);
        }
    }

    /**************************************************************
     * This method creates many auto objects for data to test and
     * work with
     **************************************************************/
    public void createList() {

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar temp1 = new GregorianCalendar();
        GregorianCalendar temp2 = new GregorianCalendar();
        GregorianCalendar temp3 = new GregorianCalendar();
        GregorianCalendar temp4 = new GregorianCalendar();
        GregorianCalendar temp5 = new GregorianCalendar();
        GregorianCalendar temp6 = new GregorianCalendar();

        try {
            Date d1 = df.parse("3/20/2019");
            temp1.setTime(d1);
            Date d2 = df.parse("9/20/2019");
            temp2.setTime(d2);
            Date d3 = df.parse("12/20/2018");
            temp3.setTime(d3);
            Date d4 = df.parse("9/20/2019");
            temp4.setTime(d4);
            Date d5 = df.parse("1/20/2010");
            temp5.setTime(d5);
            Date d6 = df.parse("10/20/2019");
            temp6.setTime(d6);


            Car Car1 = new Car(temp3, "Outback",14000, "Buyer1", "LX", false);
            Car Car2 = new Car(temp2, "Chevy", 14000, "Buyer2", "EX", false);
            Car Car3 = new Car(temp6, "Focus", 14000, "Buyer3", "EX", true);
            Truck Truck1 = new Truck(temp4, "F150", 14000, "BuyerA", "LX", false);
            Truck Truck2 = new Truck(temp1, "F250", 14000, "BuyerB", "LX", false);
            Truck Truck3 = new Truck(temp5, "F350", 14000, "BuyerC", "EX", true);

            Car1.setSoldOn(temp6);
            Car2.setSoldOn(temp6);
            Car3.setSoldOn(temp6);
            Truck1.setSoldOn(temp6);
            Truck2.setSoldOn(temp6);
            Truck3.setSoldOn(temp6);

            Car1.setSoldPrice(20000.0);
            Car2.setSoldPrice(20000.0);
            Car3.setSoldPrice(20000.0);
            Truck1.setSoldPrice(20000.0);
            Truck2.setSoldPrice(20000.0);
            Truck3.setSoldPrice(20000.0);

            add(Car1);
            add(Car2);
            add(Car3);
            add(Truck1);
            add(Truck2);
            add(Truck3);
        } catch (ParseException e) {
            throw new RuntimeException("Error in testing, creation of list");
        }

    }

}
