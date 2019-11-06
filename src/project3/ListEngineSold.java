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

public class ListEngineSold extends AbstractTableModel {

    private ArrayList<Auto> listSoldAutos;

    private String[] columnNamesSold = {"Auto Name", "Bought Cost", "Bought Date",
            "Buyer's Name" , "Sold For", "Sold On"};

    @Override
    public String getColumnName(int col) {
        //This method is being used somewhere, but I'm not sure where
        System.out.println("Test! ListEngineSold has been accessed!");
        return columnNamesSold[col];
    }


    public ListEngineSold() {
        super();
        listSoldAutos = new ArrayList<Auto>();
        createList();
    }

    public void remove(int i) {
        listSoldAutos.remove(i);
        fireTableDataChanged();
    }

    //public void

    public void add(Auto a) {
        listSoldAutos.add(a);
        fireTableDataChanged();
    }

    public void sortDate() {
        listSoldAutos.sort(Comparator.comparing(auto -> auto.getBoughtOn().getTime()));
    }

    public Auto get(int i) {
        return listSoldAutos.get(i);
    }

    public int getSize() {
        return listSoldAutos.size();
    }


    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return (listSoldAutos.get(rowIndex).getAutoName());

            case 1:
                return (listSoldAutos.get(rowIndex).getBoughtCost());

            case 2:
                return (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listSoldAutos.get(rowIndex).getBoughtOn().getTime()));

            case 3: //Buyers Name
                return (listSoldAutos.get(rowIndex).getNameOfBuyer());

            case 4: // Sold for
                return (listSoldAutos.get(rowIndex).getSoldPrice());

            case 5: // Sold date
                return (listSoldAutos.get(rowIndex).getSoldOn());


            default:
                throw new RuntimeException("JTable row,col out of range: " + rowIndex + " " + columnIndex);
        }
    }

    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listSoldAutos);
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving db");

        }
    }

    public void loadDatabase(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listSoldAutos = (ArrayList<Auto>) is.readObject();
            fireTableDataChanged();
            is.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in loading db");
        }
    }

    public void saveAsText(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter out = new BufferedWriter(fw);

            for (int i = 0; i < listSoldAutos.size(); i++){
                out.write(listSoldAutos.get(i).toString());
                out.newLine();
            }
            out.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File was not created.");
        }
    }

    public void loadFromText(String filename) throws FileNotFoundException, ParseException {
        listSoldAutos.clear();

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

                listSoldAutos.add(newCar);
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

                listSoldAutos.add(newTruck);
            }
        }

        fireTableDataChanged();
    }

    public void createList() {
        // This code has been provided to get you started on the project.

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

            Car1.setNameOfBuyer("Joe");
            Car2.setNameOfBuyer("Joe");
            Car3.setNameOfBuyer("Joe");
            Truck1.setNameOfBuyer("Joe");
            Truck2.setNameOfBuyer("Joe");
            Truck3.setNameOfBuyer("Joe");

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
