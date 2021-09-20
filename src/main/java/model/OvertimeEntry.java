package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OvertimeEntry {
    private String employeeName;
    private int minutes;
    private Date date;
    private boolean status;

    public OvertimeEntry(String employeeName, int minutes, Date date) {
        this.employeeName = employeeName;
        this.minutes = minutes;
        this.date = date;
        this.status = false;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public int getMinutes() {
        return minutes;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString() {
        return new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    public boolean getStatus() {
        return status;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
