package model;

import java.util.Date;

public class DebtPOJO {

    private int employeeID;
    private String completeName;
    private Date dateBorrowed;
    private Date datePaid;
    private double amountBorrowed;
    private String reason;
    private double amountPaid;
    private String wageFrequency;
    private boolean paidFull;

    public DebtPOJO(int employeeID, String completeName, Date dateBorrowed, Date datePaid, double amountBorrowed, String reason, double amountPaid, String wageFrequency, boolean paidFull) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.dateBorrowed = dateBorrowed;
        this.datePaid = datePaid;
        this.amountBorrowed = amountBorrowed;
        this.reason = reason;
        this.amountPaid = amountPaid;
        this.wageFrequency = wageFrequency;
        this.paidFull = paidFull;
    }

    public DebtPOJO() {}

    public int getEmployeeID() {
        return employeeID;
    }

    public String getCompleteName() {
        return completeName;
    }

    public Date getDateBorrowed() {
        return dateBorrowed;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public double getAmountBorrowed() {
        return amountBorrowed;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getWageFrequency() {
        return wageFrequency;
    }

    public String getReason() {
        return reason;
    }

    public boolean getPaidFull() {
        return paidFull;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setDateBorrowed(Date dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setWageFrequency(String wageFrequency) {
        this.wageFrequency = wageFrequency;
    }

    public void setAmountBorrowed(double amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setPaidFull(boolean paidFull) {
        this.paidFull = paidFull;
    }

}