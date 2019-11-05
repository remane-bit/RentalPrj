package project3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Truck extends Auto {

    private boolean FourByFour;

    public Truck() {
        super();
    }

    public double getCost() {
        return 42;
    }

    @Override
    public double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost) {
        return SoldCost - this.boughtCost;
    }

    public Truck(GregorianCalendar boughtOn, String name, double boughtCost,
                 String nameOfBuyer, String trimPackage, boolean fourByFour) {
        super(boughtOn, name, boughtCost, nameOfBuyer);
        trim = trimPackage;
        FourByFour = fourByFour;
    }

    public boolean isFourByFour() {
        return FourByFour;
    }

    public void setFourByFour(boolean fourByFour) {
        FourByFour = fourByFour;
    }

    @Override
    public String toString() {
        DateFormat dfo = new SimpleDateFormat("MM/dd/yyyy");
        return "Truck, " + trim + ", " + FourByFour + ", " + autoName + ", " + boughtCost + ", " + dfo.format(this.boughtOn.getTime()) + ", " + nameOfBuyer;
    }
}