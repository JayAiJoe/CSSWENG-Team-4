package model;

import java.util.Date;

public class LogbookPOJO {

    private int employeeID;
    private String completeName;
    private Date date;
    private float temp;
    private int timeIn1;
    private int timeOut1;
    private int timeIn2;
    private int timeOut2;


    public LogbookPOJO(int employeeID, String completeName, Date date, float temp, int timeIn1, int timeOut1, int timeIn2, int timeOut2) {
        this.employeeID = employeeID;
        this.completeName = completeName;
        this.date = date;
        this.temp = temp;
        this.timeIn1 = timeIn1;
        this.timeOut1 = timeOut1;
        this.timeIn2 = timeIn2;
        this.timeOut2 = timeOut2;
    }

    public LogbookPOJO(LogbookPOJO log)
    {
        this.employeeID = log.getEmployeeID();
        this.completeName = log.getCompleteName();
        this.date = log.getDate();
        this.temp = log.getTemp();
        this.timeIn1 = log.getTimeIn1();
        this.timeIn2 = log.getTimeIn2();
        this.timeOut1 = log.getTimeOut1();
        this.timeOut2 = log.getTimeOut2();
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

    public float getTemp() {
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

}