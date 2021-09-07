package dao;

import java.util.Date;

public class EmployeePOJO {
    private int employeeID;
    private String completeName;
    private String company;
    private double wage;
    private String mode;
    private String wageFrequency;
    private double debtAmount;
    private Date dateJoin;
    private Date dateLeft;

    public EmployeePOJO(int employeeID, String completeName, String company, double wage, String mode,
                        String wageFrequency, double debtAmount, Date dateJoin, Date dateLeft) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.company = company;
        this.wage = wage;
        this.mode = mode;
        this.wageFrequency = wageFrequency;
        this.debtAmount = debtAmount;
        this.dateJoin = dateJoin;
        this.dateLeft = dateLeft;
    }

    public EmployeePOJO() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getCompany() {
        return company;
    }

    public String getCompleteName() {
        return completeName;
    }

    public double getWage() {
        return wage;
    }

    public String getMode() {
        return mode;
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

    public void setCompany(String company) {
        this.company = company;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public void setMode(String mode) {
        this.mode = mode;
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