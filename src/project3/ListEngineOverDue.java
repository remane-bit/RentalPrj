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

public class ListEngineOverDue extends AbstractTableModel {

    private ArrayList<Auto> listOverDueAutos;

    private String[] columnNamesOverdue = {"Auto Name", "Bought Cost", "Bought Date",
            "Day's OverDue"};

    @Override
    public String getColumnName(int col) {
        //This method is being used somewhere, but I'm not sure where
        System.out.println("Test!");
        return columnNamesOverdue[col];
    }

    public ListEngineOverDue() {
        super();
        listOverDueAutos = new ArrayList<Auto>();
        //createList();
    }

    public void remove(int i) {
        listOverDueAutos.remove(i);
        fireTableDataChanged();
    }

    //public void

    public void add(Auto a) {
        listOverDueAutos.add(a);
        sortDate();
        fireTableDataChanged();
    }

    public void sortDate() {
        listOverDueAutos.sort(Comparator.comparing(auto -> auto.getBoughtOn().getTime()));
    }

    public Auto get(int i) {
        return listOverDueAutos.get(i);
    }

    public int getSize() {
        return listOverDueAutos.size();
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
                return (listOverDueAutos.get(rowIndex).getAutoName());

            case 1:
                return (listOverDueAutos.get(rowIndex).getBoughtCost());

            case 2:
                return (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listOverDueAutos.get(rowIndex).getBoughtOn().getTime()));

            case 3: //Days overdue; so the amount of days between the day it was bought and now
                //FIXME
                return 0;


            default:
                throw new RuntimeException("JTable row,col out of range: " + rowIndex + " " + columnIndex);
        }
    }

    public void saveDatabase(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listOverDueAutos);
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving db");

        }
    }

    public void loadDatabase(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listOverDueAutos = (ArrayList<Auto>) is.readObject();
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

            for (int i = 0; i < listOverDueAutos.size(); i++){
                out.write(listOverDueAutos.get(i).toString());
                out.newLine();
            }
            out.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File was not created.");
        }
    }


    public void loadFromText(String filename) throws FileNotFoundException, ParseException {
        listOverDueAutos.clear();

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

                listOverDueAutos.add(newCar);
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

                listOverDueAutos.add(newTruck);
            }
        }

        fireTableDataChanged();
    }

    public void createList() {;}

}
