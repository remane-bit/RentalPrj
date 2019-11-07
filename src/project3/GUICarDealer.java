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

/************************************************
 * Description
 *
 * @author Max Ziegler
 * @author Remy Merriman
 * @version Fall 2019
 ***************************************************/

public class GUICarDealer extends JFrame implements ActionListener{

    /** Holds menu bar */
    private JMenuBar menus;

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

    /** Holds JListAreas */
    private JTable jListArea;
    private JTable jListArea2;
    private JTable jListArea3;

    Date date = GregorianCalendar.getInstance().getTime();
    SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");

    /*****************************************************************
     * A constructor that starts a new dealership program for the user
     *****************************************************************/
    public GUICarDealer() throws ParseException {
        /** adding menu bar and menu items **/
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
        daysOverDueItem = new JMenuItem("90 Days overDue Screen");
        soldItem = new JMenuItem("Sold Car or Truck");

        /** Creates the panel that displays the data **/
        panel = new JPanel();

        /** Creates the three lists for each  **/
        DList = new ListEngine();
        DListSold = new ListEngineSold();
        DListOverDue = new ListEngineOverDue();

        /** This sets up the bought screen to be the initial window **/
        menuBar();
        boughtScreen();

        /** Variables created for the while loop that determines the days between **/
        something();

    }

    /**********************************************************************
     * This method takes in two dates and determines how many days are
     * between them
     * @param d1
     * @param d2
     * @return x
     ********************************************************************/
    public int daysBetween(Date d1, Date d2) {
        long diffInMillies = 0;
        long diff = 0;
        diffInMillies = Math.abs(d2.getTime() - d1.getTime());
        diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        diff -= 730486;
        int x = Math.toIntExact(diff);
        return x;
    }


    /**************************************************************
     * This method adds vehicles to the 90 days overdue Arraylist
     * if the difference between the date it was bought and today's
     * date exceeds 90 days.
     **************************************************************/
    public void something() throws ParseException {
        Date d1;
        int sizeofList = DList.getSize();
        int sizeofOverList = DListOverDue.getSize();
        int i = 0;
        String test;
        int difference = 0;
        Auto testTruck;
        Auto testCar;
        Object test1 = "";

        /** If there is stuff already in the OverDue ArrayList, clear it **/
        if(sizeofOverList > 0 ) {
            int j = sizeofOverList - 1;
            while(j >= 0) {
                DListOverDue.remove(j);
                j--;
            }
        }

        /******************************************************************************************
         * Loop that determines how many days are between the day bought and todays date
         * This loop copies vehicles in the bought vehicles ArrayList to the OverDue ArrayList
         * if they've been on the lot for more than 90 days
         ********************************************************************************************/
        while(i < sizeofList) {
            test = DList.getValueAt(i, 2).toString();
            d1 = dateF.parse(test);
            difference = daysBetween(date, d1);
            if(difference >= 90){

                if(test1 == jListArea.getValueAt(i, 4) ) {

                    testCar = DList.get(i);
                    testCar.setDaysBetween(difference);
                    DListOverDue.add(testCar);
                }

                else {
                    testTruck = DList.get(i);
                    testTruck.setDaysBetween(difference);
                    DListOverDue.add(testTruck);
                }
            }
            i++;
        }
    }

    /**********************************************************************
     * This method will make the bought car & truck and the sold buttons
     * not function if not in the bought screen
     ********************************************************************/
    public void menuItemViewChecker () {
        if (currentView != 0) {
            boughtCarItem.setEnabled(false);
            boughtTruckItem.setEnabled(false);
            soldItem.setEnabled(false);
            openSerItem.setEnabled(false);
            saveSerItem.setEnabled(false);
            openTextItem.setEnabled(false);
            saveTextItem.setEnabled(false);
        } else {
            boughtCarItem.setEnabled(true);
            boughtTruckItem.setEnabled(true);
            soldItem.setEnabled(true);
            openSerItem.setEnabled(true);
            saveSerItem.setEnabled(true);
            openTextItem.setEnabled(true);
            saveTextItem.setEnabled(true);
        }
    }

    /*****************************************************************
     * This method handles event-handling code for the car dealer
     *
     * @param e - Holds the action event parameter
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        /** If the save file button is pressed, save the file **/
        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    DList.saveDatabase(filename);
            }
        }

        /** If the exit button is pressed, close the program **/
        if (exitItem == comp) {
            System.exit(1);
        }

        /** If the open file button is pressed, open a selected file  **/
        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (openSerItem == e.getSource())
                    DList.loadDatabase(filename);
            }
        }

        /** If the bought car button is pressed, go through the process of buying a car **/
        if(comp == boughtCarItem){
            Auto auto = new Car();
            BoughtOnDialogCar dialog = new BoughtOnDialogCar(this, auto);
            if(dialog.getCloseStatus() == BoughtOnDialogCar.OK){
                DList.add(auto);

                try {
                    something();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }


            }
        }

        /** If the bought truck button is pressed, go through the process of buying a truck **/
        if(comp == boughtTruckItem){
            Auto auto = new Truck();
            BoughtOnDialogTruck dialog = new BoughtOnDialogTruck(this, auto);
            if(dialog.getCloseStatus() == BoughtOnDialogTruck.OK){
                DList.add(auto);

                try {
                    something();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

            }

        }

        /** If the sold vehicle button is pressed, go through the process of selling a vehicle **/
        if (comp == soldItem) {
            int index = jListArea.getSelectedRow();
            Object check = "";
            Auto unitTruck = new Truck();
            Auto unitCar = new Car();

                /** If its a car **/
                if (check == jListArea.getValueAt(index, 4)) {
                    unitCar = DList.get(index);
                } else /** If its a truck **/ {
                    unitTruck = DList.get(index);
                }

            Auto unit;
            if(check == jListArea.getValueAt(index, 4) ) unit = unitCar;
            else unit = unitTruck;
            new SoldOnDialog(this, unit);

            /** Adds the sold vehicle to the sold list **/
            if(check == jListArea.getValueAt(index, 4) ) {
                DListSold.add(unitCar);
            } else {
                DListSold.add(unitTruck);
            }

            DList.remove(index);

        }

        /** If the open text file button is selected, open a text file of data **/
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

        /** If the save text file button is selected, save the data to a text file **/
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

        /** If the bought screen button is pressed, clear the current screen and go to the bought one **/
        if(comp == boughtScreenItem) {
            panel.removeAll();
            boughtScreen();
        }

        /** If the sold screen button is pressed, clear the current screen and go to the sold one **/
        if(comp == soldScreenItem) {
            panel.removeAll();
            soldScreen();
        }

        /** If the Over Due screen button is pressed, clear the current screen and go to the Over Due one **/
        if(comp == daysOverDueItem) {
            panel.removeAll();
            daysOverDueScreen();
        }

    }

    /**********************************************************************
     * This method displays the bought screen
     ********************************************************************/
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

    /**********************************************************************
     * This method displays the sold screen
     ********************************************************************/
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

    }

    /**********************************************************************
     * This method displays the days over due screen
     ********************************************************************/
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

    }

    /**********************************************************************
     * This method creates the menu bar that appears at the top of the GUI.
     * This was made to make things more concise.
     ********************************************************************/
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

