package project3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Car extends Auto {

    private String trim;
    private boolean turbo;


    /**************************************************************
     * Car constructor
     **************************************************************/
    public Car() {
        super();
    }

    /**************************************************************
     * Sold price setter
     * @return 0
     **************************************************************/
    public double getCost() {
        return 0;
    }

    /**************************************************************
     * Car constructor
     * @param boughtCost
     * @param name
     * @param nameOfBuyer
     * @param boughtOn
     * @param trim
     * @param turbo
     **************************************************************/
    public Car(GregorianCalendar boughtOn, String name, double boughtCost,
               String nameOfBuyer, String trim, boolean turbo) {
        super(boughtOn, name, boughtCost, nameOfBuyer);
        this.trim = trim;
        this.turbo = turbo;
    }

    /**************************************************************
     * Trim getter
     * @return trim
     **************************************************************/
    public String getTrim() {
        return trim;
    }

    /**************************************************************
     * Trim setter
     * @param trim
     **************************************************************/
    public void setTrim(String trim) {
        this.trim = trim;
    }

    /**************************************************************
     * The difference between the sold cost and the bought cost
     * method
     *
     * @param SoldCost
     * @param SoldDate
     * @return SoldCost - this.boughtCost
     **************************************************************/
    @Override
    public double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost) {
        return SoldCost - this.boughtCost;
    }

    /**************************************************************
     * Turbo getter
     * @return turbo
     **************************************************************/
    public boolean isTurbo() {
        return turbo;
    }

    /**************************************************************
     * Turbo setter
     * @param turbo
     **************************************************************/
    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    /**************************************************************
     * Converts a string
     * @return String
     **************************************************************/
    @Override
    public String toString() {
        DateFormat dfo = new SimpleDateFormat("MM/dd/yyyy");
        return "Car, " + trim + ", " + turbo + ", " + autoName + ", " + boughtCost + ", " + dfo.format(this.boughtOn.getTime()) + ", " + nameOfBuyer;
    }
}
