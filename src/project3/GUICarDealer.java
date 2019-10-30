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
    private JMenuItem saveTextItem;
    private JMenuItem boughtCarItem;
    private JMenuItem boughtTruckCarItem;

    private JMenuItem soldItem;

    /** Holds the list engine */
    private ListEngine DList;
    private JPanel panel;

    /** Holds JListArea */
    private JTable jListArea;

    /** Scroll pane */
    //private JScrollPane scrollList;

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
        boughtTruckCarItem = new JMenuItem("Bought Truck or Car");

        soldItem = new JMenuItem("Sold Car or Truck");

        //adding items to bar
        fileMenu.add(openSerItem);
        fileMenu.add(saveSerItem);
        fileMenu.add(exitItem);
        actionMenu.add(boughtTruckCarItem);

        actionMenu.add(soldItem);

        menus.add(fileMenu);
        menus.add(actionMenu);

        //adding actionListener
        openSerItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        exitItem.addActionListener(this);
        boughtTruckCarItem.addActionListener(this);
        soldItem.addActionListener(this);

        setJMenuBar(menus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        if(e.getSource() == boughtTruckCarItem){
            Auto auto = new Truck();
            BoughtOnDialog dialog = new BoughtOnDialog(this, auto);
            if(dialog.getCloseStatus() == BoughtOnDialog.OK){
                DList.add(auto);
            }
        }

        if (soldItem == e.getSource()) {
            int index = jListArea.getSelectedRow();
            Auto unit = DList.remove(index);
            SoldOnDialog dialog = new SoldOnDialog(this, unit);
            JOptionPane.showMessageDialog(null, " Cost:" + unit.getCost());
        }
    }


    public static void main(String[] args) {
        new GUICarDealer();
    }
}

