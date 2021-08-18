package model;

import java.text.DecimalFormat;

/**
 * This class represents a single entry in the payroll that
 * contains information related to an employee in the payroll
 * such as employee name, payment mode, salary, etc.
 */
public class PayrollEntry {
    private String employeeName;
    private String mode;
    private double absent;
    private double workdays;
    private double rate;
    private double salary;
    private int time;
    private double amount;
    private double cola;
    private double total;
    private double sss;
    private double philhealth;
    private double pagibig;
    private double late;
    private double net;

    private static DecimalFormat df = new DecimalFormat("0.00");

    public PayrollEntry(String employeeName, String mode, double absent, double workdays,
                        double rate, double salary, int time, double amount,
                        double cola, double total, double sss,
                        double philhealth, double pagibig, double late, double net) {
        this.employeeName = employeeName;
        this.mode = mode;
        this.absent = absent;
        this.workdays = workdays;
        this.rate = rate;
        this.salary = salary;
        this.time = time;
        this.amount = amount;
        this.cola = cola;
        this.total = total;
        this.sss = sss;
        this.philhealth = philhealth;
        this.pagibig = pagibig;
        this.late = late;
        this.net = net;
    }

    // getters
    public String getEmployeeName() {
        return employeeName;
    }

    public String getAbsent() {
        return df.format(absent);
    }

    public String getAmount() {
        return df.format(amount);
    }

    public String getCola() {
        return df.format(cola);
    }

    public String getLate() {
        return df.format(late);
    }

    public String getNet() {
        return df.format(net);
    }

    public String getPagibig() {
        return df.format(pagibig);
    }

    public String getPhilhealth() {
        return df.format(philhealth);
    }

    public String getRate() {
        return df.format(rate);
    }

    public String getSalary() {
        return df.format(salary);
    }

    public String getSss() {
        return df.format(sss);
    }

    public String getTotal() {
        return df.format(total);
    }

    public int getTime() {
        return time;
    }

    public String getWorkdays() {
        return df.format(workdays);
    }

    public String getMode() {
        return mode;
    }
}
