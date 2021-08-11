package model;

/**
 * This class represents a single entry in the payroll that
 * contains information related to an employee in the payroll
 * such as employee name, payment mode, salary, etc.
 */
public class PayrollEntry {
    private String employeeName;
    private String mode;
    private int workdays;
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

    public PayrollEntry(String employeeName, String mode, int workdays,
                        double rate, double salary, int time, double amount,
                        double cola, double total, double sss,
                        double philhealth, double pagibig, double late) {
        this.employeeName = employeeName;
        this.mode = mode;
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
    }

    // getters
    public String getEmployeeName() {
        return employeeName;
    }

    public double getAmount() {
        return amount;
    }

    public double getCola() {
        return cola;
    }

    public double getLate() {
        return late;
    }

    public double getPagibig() {
        return pagibig;
    }

    public double getPhilhealth() {
        return philhealth;
    }

    public double getRate() {
        return rate;
    }

    public double getSalary() {
        return salary;
    }

    public double getSss() {
        return sss;
    }

    public double getTotal() {
        return total;
    }

    public int getTime() {
        return time;
    }

    public int getWorkdays() {
        return workdays;
    }

    public String getMode() {
        return mode;
    }
}
