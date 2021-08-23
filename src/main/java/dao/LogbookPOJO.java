package dao;

import java.util.Date;

public class LogbookPOJO {
    private int employeeID;
    private String completeName;
    private Date date;
    private double temp;
    private int timeIn1;
    private int timeOut1;
    private int timeIn2;
    private int timeOut2;
    private int approvedOT;
    private int pendingOT;

    public LogbookPOJO(int employeeID, String completeName, Date date, double temp, int timeIn1, int timeOut1, int timeIn2, int timeOut2) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.date = date;
        this.temp = temp;
        this.timeIn1 = timeIn1;
        this.timeOut1 = timeOut1;
        this.timeIn2 = timeIn2;
        this.timeOut2 = timeOut2;
        this.approvedOT = 0;
        this.pendingOT = 0;
    }

    public LogbookPOJO() {
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getCompleteName() {
        return completeName;
    }

    public Date getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public int getTimeIn1() {
        return timeIn1;
    }

    public int getTimeIn2() {
        return timeIn2;
    }

    public int getTimeOut1() {
        return timeOut1;
    }

    public int getTimeOut2() {
        return timeOut2;
    }

    public int getApprovedOT() {
        return approvedOT;
    }

    public int getPendingOT() {
        return pendingOT;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTimeIn1(int timeIn1) {
        this.timeIn1 = timeIn1;
    }

    public void setTimeIn2(int timeIn2) {
        this.timeIn2 = timeIn2;
    }

    public void setTimeOut1(int timeOut1) {
        this.timeOut1 = timeOut1;
    }

    public void setTimeOut2(int timeOut2) {
        this.timeOut2 = timeOut2;
    }

    public void setApprovedOT(int approvedOT) {
        this.approvedOT = approvedOT;
    }

    public void setPendingOT(int pendingOT) {
        this.pendingOT = pendingOT;
    }
}