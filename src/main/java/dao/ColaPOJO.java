package dao;

import java.util.Date;

public class ColaPOJO {
    private int employeeID;
    private Date date;
    private int cola;

    public ColaPOJO(int employeeID, Date date, int cola) {
        this.employeeID = employeeID;
        this.date = date;
        this.cola = cola;
    }

    public ColaPOJO() {}

    public int getEmployeeID() {
        return employeeID;
    }

    public Date getDate() {
        return date;
    }

    public int getCola() {
        return cola;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }
}
