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

    public PerformancePOJO(PerformancePOJO performance)
    {
        this.employeeID = performance.getEmployeeId();
        this.completeName = performance.getCompleteName();
        this.dateStart = performance.getDateStart();
        this.datePaid = performance.getDatePaid();
        this.daysPresent = performance.getDaysPresent();
        this.daysAbsent = performance.getDaysAbsent();
        this.minsLate = performance.getMinsLate();
        this.minsOvertime = performance.getMinsOvertime();
    }

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
