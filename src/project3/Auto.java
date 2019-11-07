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
    protected int daysBetween;

    public Auto() {
    }

    public Auto(GregorianCalendar boughtOn, String name, double boughtCost, String nameOfBuyer) {
        this.boughtOn = boughtOn;
        this.boughtCost = boughtCost;
        this.autoName = name;
        this.nameOfBuyer = nameOfBuyer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**************************************************************
     * Bought on getter
     * @return  boughtOn
     **************************************************************/
    public GregorianCalendar getBoughtOn() {
        return boughtOn;
    }

    /**************************************************************
     * Bought on setter
     * @param  boughtOn
     **************************************************************/
    public void setBoughtOn(GregorianCalendar boughtOn) {
        this.boughtOn = boughtOn;
    }

    /**************************************************************
     * Sold on getter
     * @return  soldOn
     **************************************************************/
    public GregorianCalendar getSoldOn() {
        return soldOn;
    }

    /**************************************************************
     * Sold on setter
     * @param soldOn
     **************************************************************/
    public void setSoldOn(GregorianCalendar soldOn) {
        this.soldOn = soldOn;
    }

    /**************************************************************
     * Sold price getter
     * @return  soldPrice
     **************************************************************/
    public double getSoldPrice() {
        return soldPrice;
    }

    /**************************************************************
     * Sold price setter
     * @param soldPrice
     **************************************************************/
    public void setSoldPrice(double soldPrice) {
        this.soldPrice = soldPrice;
    }

    /**************************************************************
     * Auto name getter
     * @return  autoName
     **************************************************************/
    public String getAutoName() {
        return autoName;
    }

    /**************************************************************
     * Auto name setter
     * @param autoName
     **************************************************************/
    public void setAutoName(String autoName) {
        this.autoName = autoName;
    }

    /**************************************************************
     * Name of buyer getter
     * @return  nameOfBuyer
     **************************************************************/
    public String getNameOfBuyer() {
        return nameOfBuyer;
    }

    /**************************************************************
     * Name of buyer setter
     * @param nameOfBuyer
     **************************************************************/
    public void setNameOfBuyer(String nameOfBuyer) {
        this.nameOfBuyer = nameOfBuyer;
    }

    /**************************************************************
     * Bought cost getter
     * @return  boughtOn
     **************************************************************/
    public double getBoughtCost() {
        return boughtCost;
    }

    /**************************************************************
     * Bought cost setter
     * @param
     **************************************************************/
    public void setBoughtCost(double boughtCost) {
        this.boughtCost = boughtCost;
    }

    /**************************************************************
     * Trim getter
     * @return  boughtOn
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
     * Days between setter
     * @param daysBetween
     **************************************************************/
    public void setDaysBetween(int daysBetween) { this.daysBetween = daysBetween; }

    /**************************************************************
     * Days between getter
     * @return  boughtOn
     **************************************************************/
    public int getDaysBetween() { return daysBetween; }



    public abstract double getSoldBoughtCost(GregorianCalendar SoldDate, double SoldCost);
}
