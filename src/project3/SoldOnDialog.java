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

    private boolean failureFlag;

    /*********************************************************
     Instantiate a Custom Dialog as 'modal' and wait for the
     user to provide data and click on a button.

     @param parent reference to the JFrame application
     @param auto an instantiated object to be filled with data
     *********************************************************/

    public SoldOnDialog(JFrame parent, Auto auto) {
        /** call parent and create a 'modal' dialog **/
        super(parent, true);

        /** Sets auto, title, and size **/
        this.auto = auto;
        setTitle("Sold Car or Truck");
        closeStatus = CANCEL;
        setSize(400,200);

        /** Prevent user from closing window **/
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /** Instantiate and display all of the text fields **/
        txtName = new JTextField("Joe",30);
        txtDate = new JTextField("10/17/2018",15);
        txtCost = new JTextField("14000.00",15);
        txtVehicleSold = new JTextField("F150", 30);

        /** Create and add Jlabels to the panel **/
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

        /** Instantiate and display two buttons **/
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        txtVehicleSold.setText(auto.getAutoName());

        /** Make new greg calendar with today's date to use to set txtDate **/
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
        String todayDate = dateF.format(date);
        txtDate.setText(todayDate);

        /** Make GUI visible **/
        setVisible(true);

    }

    /** Checks input parameters based on what we are looking for and updates the failure flag if its incorrect **/
    public void inputParameters() throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar newDate = new GregorianCalendar();
        newDate.setTime(df.parse(txtDate.getText()));


        GregorianCalendar date = new GregorianCalendar();
        date.getInstance().getTime();

        /** Checks to make sure date of sale isn't before the bought date **/
        if (auto.getBoughtOn().getTime().after(newDate.getTime())) {
            failureFlag = true;
        }

        /** Checks to make sure date of sale isn't in the future **/
        if (newDate.getTime().after(date.getTime())) {
            failureFlag = true;
        }
    }

    /**************************************************************
     Respond to either button clicks
     @param e the action event that was just fired
     **************************************************************/
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton) e.getSource();

        /** Resets failure flag each time before checking inputs **/
        failureFlag = false;

        /** if OK clicked the fill the object **/
        if (button == okButton) {
            /** save the information in the object **/
            closeStatus = OK;
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            GregorianCalendar temp = new GregorianCalendar();

            try {
                inputParameters();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            /** if any of the parameters of user input tripped the flag don't update values and try again **/
            if (failureFlag) {
                JOptionPane.showMessageDialog(this, "Date was invalid, try again.");
                return;
            }

            Date d = null;
            try {
                d = df.parse(txtDate.getText());
                System.out.println("d is equal to:" + d);
                temp.setTime(d);

            } catch (ParseException e1) {
            }

            /** Updates values of auto based on user input **/
            auto.setNameOfBuyer(txtName.getText());

            /** The day the auto is being sold on **/
            auto.setSoldOn(temp);
            auto.setSoldPrice(Double.parseDouble(txtCost.getText()));
            auto.setAutoName(txtVehicleSold.getText());

            /** Variable that holds the sale difference for the show message dialog**/
            double finalSoldDifference = auto.getSoldBoughtCost(temp, auto.getSoldPrice());

            /** Dialog that pops up when something is sold to give the user information about it**/
            JOptionPane.showMessageDialog(this, "For the salesman: Be sure to thank " + txtName.getText()
                    + " for the " + txtVehicleSold.getText() + ", the price difference was " + finalSoldDifference + ".");
        }

        /** make the dialog disappear **/
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
