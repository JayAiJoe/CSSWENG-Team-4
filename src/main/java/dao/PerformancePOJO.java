package dao;

import java.util.Date;

public class PerformancePOJO {

    private int employeeID;
    private String completeName;
    private Date dateStart;
    private Date datePaid;
    private double daysPresent;
    private double daysAbsent;
    private int minsOvertime;
    private int minsLate;
    private int cola;

    public PerformancePOJO(int employeeID, String completeName, Date dateStart, Date datePaid,
                           double daysPresent, double daysAbsent, int minsOvertime, int minsLate, int cola) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.dateStart = dateStart;
        this.datePaid = datePaid;
        this.daysPresent = daysPresent;
        this.daysAbsent = daysAbsent;
        this.minsOvertime = minsOvertime;
        this.minsLate = minsLate;
        this.cola = cola;
    }

    public PerformancePOJO() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getCompleteName() {
        return completeName;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDatePaid() {
        return datePaid;
    }

    public double getDaysPresent() {
        return daysPresent;
    }

    public double getDaysAbsent() {
        return daysAbsent;
    }

    public int getMinsOvertime() {
        return minsOvertime;
    }

    public int getMinsLate() {
        return minsLate;
    }

    public int getCola() {
        return cola;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setDaysAbsent(double daysAbsent) {
        this.daysAbsent = daysAbsent;
    }

    public void setDaysPresent(double daysPresent) {
        this.daysPresent = daysPresent;
    }

    public void setMinsLate(int minsLate) {
        this.minsLate = minsLate;
    }

    public void setMinsOvertime(int minsOvertime) {
        this.minsOvertime = minsOvertime;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }
}