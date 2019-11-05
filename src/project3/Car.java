package project3;

import java.util.GregorianCalendar;

public class Car extends Auto {

    private String trim;
    private boolean turbo;

    public Car() {
    }

    public double getCost() {
        return 42;
    }

    public Car(GregorianCalendar boughtOn, String name,
               String nameOfBuyer, String trim, boolean turbo) {
        super(boughtOn, name, nameOfBuyer);
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
        return this.boughtCost - SoldCost;
    }

    public boolean isTurbo() {
        return turbo;
    }

    public void setTurbo(boolean turbo) {
        this.turbo = turbo;
    }

    @Override
    public String toString() {
        return "Car    " +
                "trim='" + trim + '\'' +  "    " +
                ", turbo=" + turbo + "    " +
                ", autoName='" + autoName + '\'' + "    " +
                ' ';
    }
}
