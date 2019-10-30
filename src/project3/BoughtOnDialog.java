package project3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class BoughtOnDialog extends JDialog implements ActionListener {

    private JTextField txtTruckName;
    private JTextField txtCarName;
    private JTextField txtDate;
    private JTextField txtTurbo;
    private JTextField txtTrimPackage;
    private JTextField txtFourbyFour;
    private JTextField txtCost;
    private JButton okButton;
    private JButton cancelButton;
    private JComboBox<String> combobox;
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

    public BoughtOnDialog(JFrame parent, Auto auto) {
        // call parent and create a 'modal' dialog
        super(parent, true);

        this.auto = auto;
        setTitle("Bought Truck or Car");
        closeStatus = CANCEL;
        setSize(400,200);

        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display two text fields
        txtTruckName = new JTextField("F150",30);
        txtCarName = new JTextField("Honda Civic",30);
        txtDate = new JTextField(15);
        txtFourbyFour = new JTextField("True",15);
        txtTrimPackage = new JTextField("LT",15);
        txtCost = new JTextField("10100.00", 15);
        txtTurbo = new JTextField("Turbo", 15);


        String[] autoStrings = { "Car", "Truck" };

        combobox = new JComboBox<>(autoStrings);
        txtDate.setText("10/17/2018");
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7,2));

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

        if (combobox.getSelectedIndex() == 0){
            textPanel.removeAll();
            textPanel.updateUI();

            textPanel.add(new JLabel(""));
            textPanel.add(combobox);
            textPanel.add(new JLabel(""));
            textPanel.add(new JLabel(""));

            textPanel.add(new JLabel("Name of Car: "));
            textPanel.add(txtCarName);
            textPanel.add(new JLabel("bought on Date: "));
            textPanel.add(txtDate);
            textPanel.add(new JLabel("Trim Package"));
            textPanel.add(txtTrimPackage);
            textPanel.add(new JLabel("Turbo"));
            textPanel.add(txtTurbo);
            textPanel.add(new JLabel("Amount Paid for"));
            textPanel.add(txtCost);
        }

        if (combobox.getSelectedIndex() == 1){
            textPanel.removeAll();
            textPanel.updateUI();

            textPanel.add(new JLabel(""));
            textPanel.add(combobox);
            textPanel.add(new JLabel(""));
            textPanel.add(new JLabel(""));

            textPanel.add(new JLabel("Name of Truck: "));
            textPanel.add(txtTruckName);
            textPanel.add(new JLabel("bought on Date: "));
            textPanel.add(txtDate);
            textPanel.add(new JLabel("Trim Package"));
            textPanel.add(txtTrimPackage);
            textPanel.add(new JLabel("Four by Four"));
            textPanel.add(txtFourbyFour);
            textPanel.add(new JLabel("Amount Paid for"));
            textPanel.add(txtCost);
        }

        setVisible (true);
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

            if (combobox.getSelectedIndex() == 1) {
                Date d = null;
                try {
                    d = df.parse(txtDate.getText());
                    temp.setTime(d);

                } catch (ParseException e1) {
//                  Do some thing good, what I am not sure.
                }
                auto.setBoughtOn(temp);
                auto.setAutoName(txtCarName.getText());
                ((Car) auto).setTrim(txtTrimPackage.getText());
                auto.setBoughtCost(Double.parseDouble(txtCost.getText()));

            }

            else {
                Date d = null;
                try {
                    d = df.parse(txtDate.getText());
                    temp.setTime(d);

                } catch (ParseException e1) {
//                  Do some thing good, what I am not sure.
                }

                auto.setBoughtOn(temp);
                auto.setAutoName(txtTruckName.getText());
                auto.setBoughtCost(Double.parseDouble(txtCost.getText()));
                ((Truck) auto).setTrim(txtTrimPackage.getText());

                if (txtFourbyFour.getText().equalsIgnoreCase("true"))
                    ((Truck) auto).setFourByFour(true);
                else
                    ((Truck) auto).setFourByFour(false);
            }
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
