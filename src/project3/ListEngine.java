package project3;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;

public class ListEngine extends AbstractTableModel {

    /** List of vehicles in the bought screen **/
    private ArrayList<Auto> listAutos;

    /** Column names **/
    private String[] columnNamesBought = {"Auto Name", "Bought Cost",
            "Bought Date", "Trim Package ", "Four by Four", "Turbo"};

    /**************************************************************
     * Column name getter
     *
     * @param col
     * @return columnNamesOverdue[col]
     **************************************************************/
    @Override
    public String getColumnName(int col) {
        return columnNamesBought[col];
    }

    /**************************************************************
     * Constructor for the engine, creates the list of vehicles
     **************************************************************/
    public ListEngine() {
        super();
        listAutos = new ArrayList<Auto>();
        createList();
    }

    /**************************************************************
     * Removes the vehicle in the ArrayList at that index
     *
     * @param i
     **************************************************************/
    public void remove(int i) {
        listAutos.remove(i);
        fireTableDataChanged();
    }

    /**************************************************************
     * Adds a vehicle in to the ArrayList
     *
     * @param a
     **************************************************************/
    public void add(Auto a) {
        listAutos.add(a);
        sortDate();
        fireTableDataChanged();
    }

    /**************************************************************
     * Lambda sorting function
     **************************************************************/
    public void sortDate() {
        listAutos.sort((auto1, auto2) -> (auto1.getBoughtOn().getTime())
                .compareTo(auto2.getBoughtOn().getTime()));
    }

    /**************************************************************
     * This function acts a getter for the vehicle stored in the
     * ArrayList at index i
     *
     * @param i
     * @return Auto
     **************************************************************/
    public Auto get(int i) {
        return listAutos.get(i);
    }

    /**************************************************************
     * This gets the size of the ArrayList
     * @return int
     **************************************************************/
    public int getSize() {
        return listAutos.size();
    }

    /**************************************************************
     * This method counts the amount of rows in the created table
     *
     * @return int
     **************************************************************/
    @Override
    public int getRowCount() {
        return listAutos.size();
    }

    /**************************************************************
     * This method counts the amount of columns in the created table
     *
     * @return int
     **************************************************************/
    @Override
    public int getColumnCount() {
        return columnNamesBought.length;
    }

    /**************************************************************
     * This method will return the value stored at (row, col) in the
     * created table. The row basically acts as the index of the
     * ArrayList, while the col acts as the certain object the user
     * is trying to acquire
     *
     * @param col
     * @param row
     * @return Object
     **************************************************************/
    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                /** Name of the vehicle **/
                return (listAutos.get(row).getAutoName());

            case 1:
                /** The amount the vehicle was bought for **/
                return (listAutos.get(row).getBoughtCost());

            case 2:
                /** The day the vehicle was bought **/
                return (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listAutos.get(row).getBoughtOn().getTime()));

            case 3:
                /** The trim type for the vehicle **/
                return (listAutos.get(row).getTrim());

            case 4:
                /** If the vehicle is a truck, return T/F for 4 X 4. If car, return blank **/
                if (listAutos.get(row) instanceof Truck)
                    return (((Truck) listAutos.get(row)).isFourByFour());
                else
                    return "";

            case 5:
                /** If the vehicle is a car, return T/F for turbo. If truck, return blank **/
                if (listAutos.get(row) instanceof Car)
                    return (((Car) listAutos.get(row)).isTurbo());
                else
                    return "";

            default:
                /** Throws default error **/
                throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
        }
    }

    /**************************************************************
     * This method saves the data a file on the users desktop
     *
     * @param filename
     **************************************************************/
    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listAutos);
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving db");

        }
    }

    /**************************************************************
     * This method load the data from a file on the users desktop
     *
     * @param filename
     **************************************************************/
    public void loadDatabase(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listAutos = (ArrayList<Auto>) is.readObject();
            fireTableDataChanged();
            is.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in loading db");
        }
    }

    /*****************************************************************
     * Saves the data as a text file
     *
     * @param filename Name of the file where the data is being loaded from
     *******************************************************************/
    public void saveAsText(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fw);

            for (int i = 0; i < listAutos.size(); i++){
                out.write(listAutos.get(i).toString());
                out.newLine();
            }
            out.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File was not created.");
        }
    }

    /*****************************************************************
     * Loads the data from a text file
     *
     * @param filename Name of the file where the data is being stored in
     ********************************************************************/
    public void loadFromText(String filename) throws FileNotFoundException, ParseException {
        listAutos.clear();

        Scanner scanner = new Scanner(new FileReader(filename));

        while (scanner.hasNextLine()) {
            String[] data = scanner.nextLine().split(",");

            if (data[0].equals("Car")) {

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar temp1 = new GregorianCalendar();
                Date d1 = df.parse(data[5]);

                temp1.setTime(d1);
                double price = parseDouble(data[4]);
                String nameCar = data[3];
                String trim = data[1];
                String nameBuyer = data[6];
                boolean turbo = parseBoolean(data[2]);

                Car newCar = new Car(temp1, nameCar, price, nameBuyer, trim, turbo) {
                    @Override
                    public double getCost() {
                        return 0;
                    }

                    @Override
                    public double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost) {
                        return 0;
                    }
                };

                listAutos.add(newCar);
            }

            if (data[0].equals("Truck")) {

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                GregorianCalendar temp1 = new GregorianCalendar();
                Date d1 = df.parse(data[5]);

                temp1.setTime(d1);
                double price = parseDouble(data[4]);
                String nameCar = data[3];
                String trim = data[1];
                String nameBuyer = data[6];
                boolean fourByFour = parseBoolean(data[2]);

                Truck newTruck = new Truck(temp1, nameCar, price, nameBuyer, trim, fourByFour) {
                    @Override
                    public double getCost() {
                        return 0;
                    }

                    @Override
                    public double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost) {
                        return 0;
                    }
                };

                listAutos.add(newTruck);
            }
        }

        fireTableDataChanged();
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