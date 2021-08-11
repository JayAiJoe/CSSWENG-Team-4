package model;

import java.util.Date;

public class EmployeePOJO {

    private int employeeID;
    private String completeName;
    private double wage;
    private String wageFrequency;
    private double debtAmount;
    private Date dateJoin;
    private Date dateLeft;

    public EmployeePOJO(int employeeID, String completeName, double wage, String wageFrequency, double debtAmount, Date dateJoin, Date dateLeft) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.wage = wage;
        this.wageFrequency = wageFrequency;
        this.debtAmount = debtAmount;
        this.dateJoin = dateJoin;
        this.dateLeft = dateLeft;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getCompleteName() {
        return completeName;
    }

    public double getWage() {
        return wage;
    }

    public String getWageFrequency() {
        return wageFrequency;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public Date getDateJoin() {
        return dateJoin;
    }

    public Date getDateLeft() {
        return dateLeft;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public void setWageFrequency(String wageFrequency) {
        this.wageFrequency = wageFrequency;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public void setDateJoin(Date dateJoin) {
        this.dateJoin = dateJoin;
    }

    public void setDateLeft(Date dateLeft) {
        this.dateLeft = dateLeft;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

}