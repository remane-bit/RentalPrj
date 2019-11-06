package project3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Car extends Auto {

    private String trim;
    private boolean turbo;

    public Car() {
    }

    public double getCost() {
        //FIXME
        return 42;
    }

    public Car(GregorianCalendar boughtOn, String name, double boughtCost,
               String nameOfBuyer, String trim, boolean turbo) {
        super(boughtOn, name, boughtCost, nameOfBuyer);
        this.trim = trim;
        this.turbo = turbo;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    @Override
    public double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost) {
        return SoldCost - this.boughtCost;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    @Override
    public String toString() {
        DateFormat dfo = new SimpleDateFormat("MM/dd/yyyy");
        return "Car, " + trim + ", " + turbo + ", " + autoName + ", " + boughtCost + ", " + dfo.format(this.boughtOn.getTime()) + ", " + nameOfBuyer;
    }
}
