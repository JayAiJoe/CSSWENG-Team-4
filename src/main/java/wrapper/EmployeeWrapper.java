package wrapper;

import dao.EmployeePOJO;

import java.text.DecimalFormat;

public class EmployeeWrapper {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private EmployeePOJO employee;
    private int cola;

    public EmployeeWrapper(EmployeePOJO employee) {
        this.employee = employee;
        this.cola = 10;
    }

    public EmployeePOJO getEmployee() {
        return employee;
    }

    public int getEmployeeID() {
        return employee.getEmployeeID();
    }

    public String getCompany() {
        return employee.getCompany();
    }

    public String getCompleteName() {
        return employee.getCompleteName();
    }

    public double getWage() {
        return employee.getWage();
    }

    public String getMode() {
        return employee.getMode();
    }

    public String getWageFrequency() {
        return employee.getWageFrequency();
    }

    public String getWageString() {
        return df.format(getWage());
    }

    public String getCompanyFull() {
        return getCompany().equals("CRAYOLA") ? "Crayola Atbp." : "IX-XI Hardware";
    }

    public int getCola() {
        return cola;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }
}
