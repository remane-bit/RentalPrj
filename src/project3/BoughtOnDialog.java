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

    private JTextField txtName;
    private JTextField txtDate;
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
        setTitle("Sold to dialog box");
        closeStatus = CANCEL;
        setSize(400,200);

        // prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // instantiate and display two text fields
        txtName = new JTextField("F150",30);
        txtDate = new JTextField(15);
        txtFourbyFour = new JTextField("True",15);
        txtTrimPackage = new JTextField("LT",15);
        txtCost = new JTextField("10100.00", 15);

        String[] autoStrings = { "Truck", "Car" };

        combobox = new JComboBox<>(autoStrings);
        txtDate.setText("10/17/2018");
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7,2));

        textPanel.add(new JLabel(""));
        textPanel.add(combobox);
        textPanel.add(new JLabel(""));
        textPanel.add(new JLabel(""));

        textPanel.add(new JLabel("Name of Car: "));
        textPanel.add(txtName);
        textPanel.add(new JLabel("bought on Date: "));
        textPanel.add(txtDate);
        textPanel.add(new JLabel("Trim Package"));
        textPanel.add(txtTrimPackage);
        textPanel.add(new JLabel("Four by Four"));
        textPanel.add(txtFourbyFour);
        textPanel.add(new JLabel("Amount Paid for"));
        textPanel.add(txtCost);

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
                auto.setAutoName(txtName.getText());
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
                auto.setAutoName(txtName.getText());
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
