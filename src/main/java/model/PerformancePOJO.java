package model;

import java.util.Date;

public class PerformancePOJO {

    private int employeeID;
    private String completeName;
    private Date dateStart;
    private Date datePaid;
    private float daysPresent;
    private float daysAbsent;
    private int minsOvertime;
    private int minsLate;

    public PerformancePOJO(int employeeID, String completeName, Date dateStart, Date datePaid, float daysPresent, float daysAbsent, int minsOvertime, int minsLate) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.dateStart = dateStart;
        this.datePaid = datePaid;
        this.daysPresent = daysPresent;
        this.daysAbsent = daysAbsent;
        this.minsOvertime = minsOvertime;
        this.minsLate = minsLate;
    }

    public PerformancePOJO(){}

    public int getEmployeeId() {
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

    public float getDaysPresent() {
        return daysPresent;
    }

    public float getDaysAbsent() {
        return daysAbsent;
    }

    public int getMinsOvertime() {
        return minsOvertime;
    }

    public int getMinsLate() {
        return minsLate;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public void setDaysAbsent(float daysAbsent) {
        this.daysAbsent = daysAbsent;
    }

    public void setDaysPresent(float daysPresent) {
        this.daysPresent = daysPresent;
    }

    public void setMinsLate(int minsLate) {
        this.minsLate = minsLate;
    }

    public void setMinsOvertime(int minsOvertime) {
        this.minsOvertime = minsOvertime;
    }


}
