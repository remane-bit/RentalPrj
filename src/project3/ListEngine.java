package project3;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;

public class ListEngine extends AbstractTableModel {

    private ArrayList<Auto> listAutos;

    private String[] columnNamesBought = {"Auto Name", "Bought Cost",
            "Bought Date", "Trim Package ", "Four by Four", "Turbo"};

    @Override
    public String getColumnName(int col) {
        //This method is being used somewhere, but I'm not sure where
        System.out.println("Test! ListEngine has been accessed!");
        return columnNamesBought[col];
    }

    public ListEngine() {
        super();
        listAutos = new ArrayList<Auto>();
        createList();
    }

    public void remove(int i) {
        listAutos.remove(i);
        fireTableDataChanged();
    }

    public void add(Auto a) {
        listAutos.add(a);
        sortDate();
        fireTableDataChanged();
    }

    /** THIS IS OUR LAMBDA SORTING FUNCTION */
    public void sortDate() {
        listAutos.sort((auto1, auto2) -> (auto1.getBoughtOn().getTime())
                .compareTo(auto2.getBoughtOn().getTime()));
    }

    public Auto get(int i) {
        return listAutos.get(i);
    }

    public int getSize() {
        return listAutos.size();
    }

    @Override
    public int getRowCount() {
        return listAutos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNamesBought.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        switch (col) {
            case 0:
                return (listAutos.get(row).getAutoName());

            case 1:
                return (listAutos.get(row).getBoughtCost());

            case 2:

                return (DateFormat.getDateInstance(DateFormat.SHORT)
                        .format(listAutos.get(row).getBoughtOn().getTime()));

            case 3:
                return (listAutos.get(row).getTrim());

            case 4:
                if (listAutos.get(row) instanceof Truck)
                    return (((Truck) listAutos.get(row)).isFourByFour());
                else
                    return "";

            case 5:
                if (listAutos.get(row) instanceof Car)
                    return (((Car) listAutos.get(row)).isTurbo());
                else
                    return "";


            default:
                throw new RuntimeException("JTable row,col out of range: " + row + " " + col);
        }
    }

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
     * The following code is half baked code. It should help you
     * understand how to save to a text file.
     *
     * @param filename Name of the file where the data is being loaded from
     */
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
     * The following code is half baked code. It should help you
     * understand how to load to a text file.  THis code does NOT
     * function correctly but, should give you a great start to
     * your code.
     *
     * @param filename Name of the file where the data is being stored in
     */
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

//    public int calculateDays() {
//
//
//        return daysBetween(bought date, todays date);
//    }
//
//    public int daysBetween(Date d1, Date d2) {
//        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
//    }


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

/*

   Here is the instructor's test data.  This will be the starting point for project
   demonstration day.


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


            Car Car1 = new Car (temp1, "Outback", 18000,"LX", false);
            Car Car2 = new Car (temp2, "Chevy", 11000,"EX", false);
            Car Car3 = new Car (temp3, "Focus", 19000,"EX", true);
            Truck Truck1 = new Truck(temp4,"F150",12000,"Tow",false);
            Truck Truck2 = new Truck(temp5,"F250",42000,"NA",false);
            Truck Truck3 = new Truck(temp1,"F350",2000,"Turbo",true);

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

 */
}