package project3;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Auto implements Serializable {

    private static final long serialVersionUID = 1L;
    protected GregorianCalendar boughtOn;
    protected GregorianCalendar soldOn;
    protected String autoName;
    protected String nameOfBuyer;
    protected double boughtCost;
    protected double soldPrice;
    protected String trim;

    public Auto() {
    }

    public abstract double getCost();

    public Auto(GregorianCalendar boughtOn, String name, String nameOfBuyer) {
        this.boughtOn = boughtOn;
        this.autoName = name;
        this.nameOfBuyer = nameOfBuyer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public GregorianCalendar getBoughtOn() {
        return boughtOn;
    }

    public void setBoughtOn(GregorianCalendar boughtOn) {
        this.boughtOn = boughtOn;
    }

    public GregorianCalendar getSoldOn() {
        return soldOn;
    }

    public void setSoldOn(GregorianCalendar soldOn) {
        this.soldOn = soldOn;
    }

    public double getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getAutoName() {
        return autoName;
    }

    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }

    public String getNameOfBuyer() {
        return nameOfBuyer;
    }

    public void setNameOfBuyer(String nameOfBuyer) {
        this.nameOfBuyer = nameOfBuyer;
    }

    public double getBoughtCost() {
        return boughtCost;
    }

    public void setBoughtCost(double boughtCost) {
        this.boughtCost = boughtCost;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }
}
