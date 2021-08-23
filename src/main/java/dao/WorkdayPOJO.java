package dao;

import java.util.Date;

public class WorkdayPOJO {
    private Date date;
    private int timeIn1;
    private int timeOut1;
    private int timeIn2;
    private int timeOut2;
    private int overtimeIn;
    private int overtimeOut;

    public WorkdayPOJO(Date date, int timeIn1, int timeOut1, int timeIn2, int timeOut2,
                       int overtimeIn, int overtimeOut) {
        this.date = date;
        this.timeIn1 = timeIn1;
        this.timeOut1 = timeOut1;
        this.timeIn2 = timeIn2;
        this.timeOut2 = timeOut2;
        this.overtimeIn = overtimeIn;
        this.overtimeOut = overtimeOut;
    }

    public WorkdayPOJO() {
    }

    public Date getDate() {
        return date;
    }

    public int getTimeIn1() {
        return timeIn1;
    }

    public int getTimeOut1() {
        return timeOut1;
    }

    public int getTimeIn2() {
        return timeIn2;
    }

    public int getTimeOut2() {
        return timeOut2;
    }

    public int getOvertimeIn() {
        return overtimeIn;
    }

    public int getOvertimeOut() {
        return overtimeOut;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimeIn1(int timeIn1) {
        this.timeIn1 = timeIn1;
    }

    public void setTimeOut1(int timeOut1) {
        this.timeOut1 = timeOut1;
    }

    public void setTimeIn2(int timeIn2) {
        this.timeIn2 = timeIn2;
    }

    public void setTimeOut2(int timeOut2) {
        this.timeOut2 = timeOut2;
    }

    public void setOvertimeIn(int overtimeIn) {
        this.overtimeIn = overtimeIn;
    }

    public void setOvertimeOut(int overtimeOut) {
        this.overtimeOut = overtimeOut;
    }
}
