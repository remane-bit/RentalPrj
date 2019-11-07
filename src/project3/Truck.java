package project3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Truck extends Auto {

    private boolean FourByFour;

    /**************************************************************
     * Truck constructor
     **************************************************************/
    public Truck() {
        super();
    }

    /**************************************************************
     * Sold price getter
     * @return 0
     **************************************************************/
    public double getCost() {
        return 0;
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
     * Truck constructor
     * @param boughtCost
     * @param name
     * @param nameOfBuyer
     * @param boughtOn
     * @param trimPackage
     * @param fourByFour
     **************************************************************/
    public Truck(GregorianCalendar boughtOn, String name, double boughtCost,
                 String nameOfBuyer, String trimPackage, boolean fourByFour) {
        super(boughtOn, name, boughtCost, nameOfBuyer);
        trim = trimPackage;
        FourByFour = fourByFour;
    }

    /**************************************************************
     * Four by four getter
     * @return FourByFour
     **************************************************************/
    public boolean isFourByFour() {
        return FourByFour;
    }

    /**************************************************************
     * Four by four setter
     * @param fourByFour
     **************************************************************/
    public void setFourByFour(boolean fourByFour) {
        FourByFour = fourByFour;
    }

    /**************************************************************
     * Converts a string
     * @return String
     **************************************************************/
    @Override
    public String toString() {
        DateFormat dfo = new SimpleDateFormat("MM/dd/yyyy");
        return "Truck, " + trim + ", " + FourByFour + ", " + autoName + ", " + boughtCost + ", " + dfo.format(this.boughtOn.getTime()) + ", " + nameOfBuyer;
    }
}