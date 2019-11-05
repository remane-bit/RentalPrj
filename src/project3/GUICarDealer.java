package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

/*****************************************************************
 *
 * Maintains the GUI1024 for the red box rental store
 *
 *****************************************************************/
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
    private JPanel panel;

    /** Holds JListArea */
    private JTable jListArea;

    /** Scroll pane */
    //private JScrollPane scrollList;

    /** Test button **/
    private JButton button;


    /*****************************************************************
     *
     * A constructor that starts a new GUI1024 for the rental store
     *
     *****************************************************************/
    public GUICarDealer(){
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

        boughtScreen();
    }

    /*****************************************************************
     *
     * This method handles event-handling code for the GUI1024
     *
     * @param e - Holds the action event parameter
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object comp = e.getSource();

        if (saveSerItem == comp || saveTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();
                if (saveSerItem == e.getSource())
                    DList.saveDatabase(filename);
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
        }

        if (comp == soldItem) {
            //The selected index does work, prints out the respective row as selected.
            int index = jListArea.getSelectedRow();

            Object check = "";

            //But this never gets removed!!
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

                System.out.println(unitCar.getAutoName());
                System.out.println(unitCar.getBoughtCost());

                //unit = unitTruck;
            }

            //Original line of code
            Auto unit = DList.remove(index);

            if(check == jListArea.getValueAt(index, 4) ) unit = unitCar;
            else unit = unitTruck;



            //Gets to here before error
            SoldOnDialog dialog = new SoldOnDialog(this, unit);

            //Then here when it gets cancelled
            JOptionPane.showMessageDialog(null, " Cost:" + unit.getCost());
        }

        if(comp == openTextItem) {
            // do something
        }

        if(comp == saveTextItem) {
            // do something
        }

        if(comp == boughtScreenItem) {
            dispose();
            boughtScreen();


            ;
        }

        if(comp == soldScreenItem) {
            //Do something
            dispose();
            soldScreen();
            ;
        }

        if(comp == daysOverDueItem) {
            //Do something
            dispose();
            daysOverDueScreen();
            ;
        }

    }

    public void boughtScreen() {

        menuBar();

        panel = new JPanel();
        DList = new ListEngine();
        jListArea = new JTable(DList);
        JScrollPane scrollList = new JScrollPane(jListArea);
        scrollList.setPreferredSize(new Dimension(800,300));
        panel.add(scrollList);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
        setSize(950,450);
    }

    public void soldScreen() {
        menuBar();

        panel = new JPanel();
        //Add in list engine and other things
       // add(button);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
        setSize(950,450);
    }

    public void daysOverDueScreen() {
        menuBar();

        panel = new JPanel();
        //Add in list engine and other things
        //add(button);
        add(panel, BorderLayout.CENTER);

        setVisible(true);
        setSize(950,450);
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




    public static void main(String[] args) {
        new GUICarDealer();
    }
}

