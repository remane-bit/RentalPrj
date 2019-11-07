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

public class BoughtOnDialogTruck extends JDialog implements ActionListener {

    private JTextField txtTruckName;
    private JTextField txtDate;
    private JTextField txtTrimPackage;
    private JTextField txtFourbyFour;
    private JTextField txtCost;
    private JButton okButton;
    private JButton cancelButton;
    private int closeStatus;
    private Auto auto;
    static final int OK = 0;
    static final int CANCEL = 1;
    JPanel truckPanel = new JPanel();
    private boolean failureFlag;

    /*********************************************************
     Instantiate a Custom Dialog as 'modal' and wait for the
     user to provide data and click on a button.

     @param parent reference to the JFrame application
     @param auto an instantiated object to be filled with data
     *********************************************************/

    public BoughtOnDialogTruck(JFrame parent, Auto auto) {
        /** call parent and create a 'modal' dialog **/
        super(parent, true);

        /** Sets auto, title, and size **/
        this.auto = auto;
        setTitle("Bought Truck");
        closeStatus = CANCEL;
        setSize(400,200);

        /** Prevent user from closing window **/
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        /** Instantiate and display all of the text fields **/
        txtTruckName = new JTextField("F150",30);
        txtDate = new JTextField(15);
        txtFourbyFour = new JTextField("True",15);
        txtTrimPackage = new JTextField("LT",15);
        txtCost = new JTextField("10100.00", 15);

        /** Make new greg calendar with today's date to use to set txtDate **/
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
        String todayDate = dateF.format(date);
        txtDate.setText(todayDate);

        /** Sets layout of panel and where the panel sits **/
        truckPanel.setLayout(new GridLayout(7,2));
        getContentPane().add(truckPanel, BorderLayout.CENTER);

        /** Instantiate and display two buttons **/
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        /** Attach buttons to action listener **/
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);

        /** Create and add Jlabels to the panel **/
        truckPanel.add(new JLabel("Name of Truck: "));
        truckPanel.add(txtTruckName);
        truckPanel.add(new JLabel("bought on Date: "));
        truckPanel.add(txtDate);
        truckPanel.add(new JLabel("Trim Package"));
        truckPanel.add(txtTrimPackage);
        truckPanel.add(new JLabel("Four by Four"));
        truckPanel.add(txtFourbyFour);
        truckPanel.add(new JLabel("Amount Paid for"));
        truckPanel.add(txtCost);

        /** Make GUI visible **/
        setVisible (true);
    }

    /** Checks input parameters based on what we are looking for and updates the failure flag if its incorrect **/
    public void inputParameters() throws ParseException {

        /** New date and greg variables to use to compare to and run user inputs **/
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        GregorianCalendar newDate = new GregorianCalendar();
        newDate.setTime(df.parse(txtDate.getText()));
        GregorianCalendar date = new GregorianCalendar();
        date.getInstance().getTime();

        /** Checks to make sure date doesn't equal null, that the length equals 10 **/
        if (txtDate.getText() == null || txtDate.getText().length() != 10) {
            failureFlag = true;
            return;
        }
    }

    /**************************************************************
     Respond to either button clicks
     @param e the action event that was just fired
     **************************************************************/
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        /** if OK clicked the fill the object **/
        if (source == okButton) {
            /** save the information in the object **/
            closeStatus = OK;
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            GregorianCalendar temp = new GregorianCalendar();

            /** Resets failure flag each time before checking inputs **/
            failureFlag = false;

            /** Runs user inputs through the parameters method **/
            try {
                inputParameters();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            /** if any of the parameters of user input tripped the flag don't update values and try again **/
            if (failureFlag) {
                JOptionPane.showMessageDialog(this, "Data was invalid, try again.");
                return;
            }

            Date d = null;

                try {
                    d = df.parse(txtDate.getText());
                    temp.setTime(d);

                } catch (ParseException e1) {
                }

            /** Updates values of auto based on user input **/
                auto.setBoughtOn(temp);
                auto.setAutoName(txtTruckName.getText());
                auto.setBoughtCost(Double.parseDouble(txtCost.getText()));
                auto.setTrim(txtTrimPackage.getText());

            /** Updates values of auto based on user input **/
            if (txtFourbyFour.getText().equalsIgnoreCase("true"))
                    ((Truck) auto).setFourByFour(true);
                else
                    ((Truck) auto).setFourByFour(false);

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
