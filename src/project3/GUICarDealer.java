package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/*****************************************************************
 *
 * Maintains the GUI1024 for the red box rental store
 *
 *****************************************************************/
public class GUICarDealer extends JFrame implements ActionListener{
    /** Holds menu bar */
    private JMenuBar menus;
    //

    /** menus in the menu bar */
    private JMenu fileMenu;
    private JMenu actionMenu;

    /** menu items in each of the menus */
    private JMenuItem openSerItem;
    private JMenuItem exitItem;
    private JMenuItem saveSerItem;
    private JMenuItem openTextItem;
    private JMenuItem saveTextItem;
    private JMenuItem boughtCarItem;
    private JMenuItem boughtTruckItem;
    private JMenuItem boughtScreenItem;
    private JMenuItem soldScreenItem;
    private JMenuItem daysOverDueItem;
    private JMenuItem soldItem;

    /** Holds the list engine */
    private ListEngine DList;
    private ListEngineSold DListSold;
    private ListEngineOverDue DListOverDue;
    private JPanel panel;

    /** Variable to hold current view */
    private int currentView;

    /** Holds JListArea */
    private JTable jListArea;
    private JTable jListArea2;
    private JTable jListArea3;

    /** Scroll pane */
    //private JScrollPane scrollList;

    /** Test button **/
    private JButton button;


    /*****************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     *****************************************************************/
    public GUICarDealer() throws ParseException {
        //adding menu bar and menu items
        menus = new JMenuBar();
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");
        openSerItem = new JMenuItem("Open File");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save File");
        openTextItem = new JMenuItem("Open Text");
        saveTextItem = new JMenuItem("Save Text");
        boughtCarItem = new JMenuItem("Bought Car");
        boughtTruckItem = new JMenuItem("Bought Truck");
        boughtScreenItem = new JMenuItem("Bought Screen");
        soldScreenItem = new JMenuItem("Sold Screen");
        daysOverDueItem = new JMenuItem("30 Days overDue Screen");
        soldItem = new JMenuItem("Sold Car or Truck");

       // button = new JButton("test");

        panel = new JPanel();
        DList = new ListEngine();
        DListSold = new ListEngineSold();
        DListOverDue = new ListEngineOverDue();

        menuBar();
        boughtScreen();

        //Todays date
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");

        int sizeofList = DList.getSize();
        int i = 0;
        String test;
        int difference = 0;
        Date d1;

        Auto testTruck = new Truck();
        Auto testCar = new Car();

        Object test1 = "";

        double paidPrice1 = 0.0;
        String paidprice1 = "";


        while(i < sizeofList) {
//            System.out.println(DList.getValueAt(i, 2));

            test = DList.getValueAt(i, 2).toString();
            d1 = dateF.parse(test);
            difference = daysBetween(date, d1);
//            System.out.println("Days between " + difference);
            //DList.setValueAt();

            //Not sure about this. This sets the days between in that DlistOverDue,
            if(difference >= 90){

                System.out.println(DList.getValueAt(i, 2));
                System.out.println("Days between " + difference);

                if(test1 == jListArea.getValueAt(i, 4) ) {
                    testCar.setAutoName((jListArea.getValueAt(i, 0).toString()));
                    paidprice1 = ((jListArea.getValueAt(i, 1).toString()));
                    paidPrice1 = Double.parseDouble(paidprice1);
                    testCar.setBoughtCost(paidPrice1);
                    testCar.setDaysBetween(difference);

                    DListOverDue.add(testCar);

                }
                else {
                    testTruck.setAutoName((jListArea.getValueAt(i, 0).toString()));
                    paidprice1 = ((jListArea.getValueAt(i, 1).toString()));
                    paidPrice1 = Double.parseDouble(paidprice1);
                    testTruck.setBoughtCost(paidPrice1);
                    testTruck.setDaysBetween(difference);

                    DListOverDue.add(testTruck);
                }



            }

            i++;
        }

    }

    public int daysBetween(Date d1, Date d2) {
        long diffInMillies = 0;
        long diff = 0;

        //test = DList.getValueAt(i, 2).toString();
        diffInMillies = Math.abs(d2.getTime() - d1.getTime());
        diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        diff -= 730486;
        int x = Math.toIntExact(diff);

        return x;
    }

    public void menuItemViewChecker () {
        if (currentView != 0) {
            boughtCarItem.setEnabled(false);
            boughtTruckItem.setEnabled(false);
            soldItem.setEnabled(false);
        } else {
            boughtCarItem.setEnabled(true);
            boughtTruckItem.setEnabled(true);
            soldItem.setEnabled(true);
        }
    }

    /*****************************************************************
     *
     * This method handles event-handling code for the GUI1024
     *
     * @param e - Holds the action event parameter
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    DList.saveDatabase(filename);
            }
        }

        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == e.getSource())
                    DList.loadDatabase(filename);
            }
        }

        //MenuBar options
        if(comp == boughtCarItem){
            Auto auto = new Car();
            BoughtOnDialogCar dialog = new BoughtOnDialogCar(this, auto);
            if(dialog.getCloseStatus() == BoughtOnDialogCar.OK){
                DList.add(auto);
            }
        }

        if(comp == boughtTruckItem){
            Auto auto = new Truck();
            BoughtOnDialogTruck dialog = new BoughtOnDialogTruck(this, auto);
            if(dialog.getCloseStatus() == BoughtOnDialogTruck.OK){
                DList.add(auto);
            }

            //Add a method to check how many days between now and the day it was purchased?

        }

        if (comp == soldItem) {
            //The selected index does work, prints out the respective row as selected.
            int index = jListArea.getSelectedRow();

            Object check = "";

            //Create the unit to have all the details of that selected unit then remove it?
            Auto unitTruck = new Truck();
            Auto unitCar = new Car();
            double paidPrice = 0.0;
            String paidprice = "";

                        if(check == jListArea.getValueAt(index, 4) ) {

                            System.out.println("I'm a car");

                            unitCar.setAutoName((jListArea.getValueAt(index, 0).toString()));
                            paidprice = ((jListArea.getValueAt(index, 1).toString()));
                            paidPrice = Double.parseDouble(paidprice);
                            unitCar.setBoughtCost(paidPrice);

                            System.out.println(unitCar.getAutoName());
                            System.out.println(unitCar.getBoughtCost());
                        }

                        else /** If its a truck **/ {

                            System.out.println("I'm a truck");

                            unitTruck.setAutoName((jListArea.getValueAt(index, 0).toString()));
                            paidprice = ((jListArea.getValueAt(index, 1).toString()));
                            paidPrice = Double.parseDouble(paidprice);
                            unitTruck.setBoughtCost(paidPrice);

                            System.out.println(unitTruck.getAutoName());
                            System.out.println(unitTruck.getBoughtCost());

                        }

            //Original line of code
            Auto unit;
            if(check == jListArea.getValueAt(index, 4) ) unit = unitCar;
            else unit = unitTruck;
//            DList.remove(index);
           // SoldOnDialog dialog;
            new SoldOnDialog(this, unit);

            //Adds the sold vehicle to the sold list
            if(check == jListArea.getValueAt(index, 4) ) {
                DListSold.add(unitCar);
                System.out.println("Car Added To Sold Window");
            } else {
                DListSold.add(unitTruck);
                System.out.println("Truck Added To Sold Window");
            }

            DList.remove(index);

        }

        if(comp == openTextItem) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openTextItem == e.getSource()) {
                    try {
                        DList.loadFromText(filename);
                    } catch (FileNotFoundException | ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        if(comp == saveTextItem) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveTextItem == e.getSource()) {
                        DList.saveAsText(filename);
                }
            }
        }

        if(comp == boughtScreenItem) {
            panel.removeAll();
            System.out.println("Entering the bought screen");
            boughtScreen();
        }

        if(comp == soldScreenItem) {
            panel.removeAll();
            System.out.println("Entering the sold screen");
            soldScreen();
        }

        if(comp == daysOverDueItem) {
            panel.removeAll();
            System.out.println("Entering the overdue screen");
            daysOverDueScreen();
        }

    }

    public void boughtScreen() {
       currentView = 0;
       menuItemViewChecker();

        /** This is where list is being made **/
        jListArea = new JTable(DList);
        JScrollPane scrollList = new JScrollPane(jListArea);
        scrollList.setPreferredSize(new Dimension(800,300));
        panel.add(scrollList);

        add(panel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();

        setSize(950,450);
        setVisible(true);
    }

    public void soldScreen() {
        currentView = 1;
        menuItemViewChecker();


        jListArea2 = new JTable(DListSold);
        JScrollPane scrollList2 = new JScrollPane(jListArea2);
        scrollList2.setPreferredSize(new Dimension(800,300));
        panel.add(scrollList2);

        add(panel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();

        setSize(950,450);
        setVisible(true);

        System.out.println("Sold Screen created!");
    }

    public void daysOverDueScreen() {
        currentView = 2;
        menuItemViewChecker();

        jListArea3 = new JTable(DListOverDue);
        JScrollPane scrollList3 = new JScrollPane(jListArea3);
        scrollList3.setPreferredSize(new Dimension(800,300));
        panel.add(scrollList3);

        add(panel, BorderLayout.CENTER);

        panel.revalidate();
        panel.repaint();

        setSize(950,450);
        setVisible(true);

        System.out.println("Overdue Screen created!");
    }


    public void menuBar() {

        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.add(openTextItem);
        fileMenu.add(saveTextItem);
        fileMenu.add(exitItem);
        fileMenu.add(boughtScreenItem);
        fileMenu.add(soldScreenItem);
        fileMenu.add(daysOverDueItem);

        actionMenu.add(boughtCarItem);
        actionMenu.add(boughtTruckItem);
        actionMenu.add(soldItem);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        openTextItem.addActionListener(this);
        saveTextItem.addActionListener(this);
        exitItem.addActionListener(this);
        boughtScreenItem.addActionListener(this);
        soldScreenItem.addActionListener(this);
        daysOverDueItem.addActionListener(this);

        boughtCarItem.addActionListener(this);
        boughtTruckItem.addActionListener(this);
        soldItem.addActionListener(this);

        setJMenuBar(menus);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }




    public static void main(String[] args) throws ParseException {
        new GUICarDealer();

    }
}

