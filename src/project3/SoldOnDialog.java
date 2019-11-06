package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SoldOnDialog extends JDialog implements ActionListener {

    private JTextField txtName;
    private JTextField txtDate;
    private JTextField txtCost;
    private JTextField txtVehicleSold;

    private JButton okButton;
    private JButton cancelButton;
    private int closeStatus;
    private Auto auto;
    static final int OK = 0;
    static final int CANCEL = 1;

    /*********************************************************
     Instantiate a Custom Dialog as 'modal' and wait for the
     user to provide data and click on a button.

     @param parent reference to the JFrame application
     @param auto an instantiated object to be filled with data
     *********************************************************/

    public SoldOnDialog(JFrame parent, Auto auto) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        this.auto = auto;
        setTitle("Sold Car or Truck");
        closeStatus = CANCEL;
        setSize(400,200);

        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display two text fields
        txtName = new JTextField("Joe",30);
        txtDate = new JTextField("10/17/2018",15);
        txtCost = new JTextField("14000.00",15);
        txtVehicleSold = new JTextField("F150", 30);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(4,2));
        textPanel.add(new JLabel("Name of Buyer: "));
        textPanel.add(txtName);
        textPanel.add(new JLabel("Sold on Date: "));
        textPanel.add(txtDate);
        textPanel.add(new JLabel("Sold for ($): "));
        textPanel.add(txtCost);
        textPanel.add(new JLabel("Model: "));
        textPanel.add(txtVehicleSold);
        getContentPane().add(textPanel, BorderLayout.CENTER);

        // Instantiate and display two buttons
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        txtVehicleSold.setText(auto.getAutoName());

        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
        String todayDate = dateF.format(date);

        txtDate.setText(todayDate);

        setVisible(true);

    }

    /**************************************************************
     Respond to either button clicks
     @param e the action event that was just fired
     **************************************************************/
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        // if OK clicked the fill the object
        if (button == okButton) {
            // save the information in the object
            closeStatus = OK;
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            GregorianCalendar temp = new GregorianCalendar();

            Date d = null;
            try {
                d = df.parse(txtDate.getText());
                temp.setTime(d);

            } catch (ParseException e1) {
//                  Do some thing good, what I am not sure.
            }

//            System.out.println(txtName.getText());
//            System.out.println(temp);
//            System.out.println(Double.parseDouble(txtCost.getText()));
//            System.out.println(txtVehicleSold.getText());

            auto.setNameOfBuyer(txtName.getText());
            auto.setSoldOn(temp);
            auto.setSoldPrice(Double.parseDouble(txtCost.getText()));
            auto.setAutoName(txtVehicleSold.getText());

            double finalSoldDifference = auto.getSoldBoughtCost(temp, auto.getSoldPrice());

            JOptionPane.showMessageDialog(this, "For the salesman: Be sure to thank " + txtName.getText()
                    + " for the " + txtVehicleSold.getText() + ", the price difference was " + finalSoldDifference + ".");
        }
        // make the dialog disappear
        dispose();
    }

    /**************************************************************
     Return a String to let the caller know which button
     was clicked

     @return an int representing the option OK or CANCEL
     **************************************************************/
    public int getCloseStatus(){
        return closeStatus;
    }

}
