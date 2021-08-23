package model;

import dao.EmployeePOJO;
import dao.PerformancePOJO;
import dao.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// TODO: Implement initialize() method

/**
 * This class represents a payroll on a given payday
 * consisting of payroll entries. This class is responsible
 * for adding entries and making method calls that perform
 * different calculations.
 */
public class Payroll {
    private ArrayList<PayrollEntry> crayolaEntries, ixxiEntries;
    private Date dateStart, dateEnd;

    public Payroll() {
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PayrollEntry> getCrayolaEntries() {
        return crayolaEntries;
    }

    public ArrayList<PayrollEntry> getIxxiEntries() {
        return ixxiEntries;
    }

    public String getDateStart() {
        return new SimpleDateFormat("MM/dd/yyyy").format(dateStart);
    }

    public String getDateEnd() {
        return new SimpleDateFormat("MM/dd/yyyy").format(dateEnd);
    }

    /**
     * Initializes the entries of the payroll using employee and
     * performance information retrieved from the database.
     */
    private void initialize() {

        // TODO: Change everything later
        crayolaEntries = new ArrayList<>();
        ixxiEntries = new ArrayList<>();

        ArrayList<EmployeePOJO> employees = Repository.getInstance().getAllEmployees();
        ArrayList<PerformancePOJO> performances = Repository.getInstance().getAllPerformance();

        dateStart = performances.get(0).getDateStart();
        dateEnd = performances.get(0).getDatePaid();

        int employeeCnt = employees.size();
        System.out.println(employeeCnt);

        ArrayList<Integer> employeeIndex = new ArrayList<>();
        ArrayList<Integer> performanceIndex = new ArrayList<>();
        for (int i = 0; i < employeeCnt; i++) {
            employeeIndex.add(0);
            performanceIndex.add(0);
        }
        for (int i = 0; i < employeeCnt; i++) {
            int index = employees.get(i).getEmployeeID();
            employeeIndex.set(index, i);
        }
        for (int i = 0; i < employeeCnt; i++) {
            int index = performances.get(i).getEmployeeID();
            performanceIndex.set(index, i);
        }

        for (int i = 0; i < employeeCnt; i++) {
            EmployeePOJO employee = employees.get(employeeIndex.get(i));
            PerformancePOJO performance = performances.get(performanceIndex.get(i));

            String employeeName = employee.getCompleteName();
            String mode = employee.getMode();
            double absent = performance.getDaysAbsent();
            double workdays = performance.getDaysPresent();
            double totalDays = absent + workdays;

            double rate = employee.getWage();
            if (mode.equals("MONTHLY")) {
                rate /= totalDays;
            }

            double salary = Calculator.getInstance().computeSalary(rate, workdays);
            int time = performance.getMinsOvertime();
            double amount = Calculator.getInstance().computeOvertime(rate, time);
            double cola = 0;
            double total = salary + amount;

            double sss, philhealth, pagibig;
            double monthlyWage;
            if (mode.equals("MONTHLY")) {
                monthlyWage = employee.getWage();
                sss = Calculator.getInstance().computeSSSFee(monthlyWage);
                philhealth = Calculator.getInstance().computePhilHealthFee(monthlyWage);
                pagibig = Calculator.getInstance().computePagIbigFee(monthlyWage);
            } else {
                monthlyWage = rate * 26;
                sss = Calculator.getInstance().computeSSSFee(monthlyWage);
                philhealth = Calculator.getInstance().computePhilHealthFee(monthlyWage);
                pagibig = Calculator.getInstance().computePagIbigFee(monthlyWage);
            }

            double late = Calculator.getInstance().computeLateFee(rate, performance.getMinsLate());
            double net = total - sss - philhealth - pagibig - late;

            PayrollEntry payrollEntry = new PayrollEntry(employeeName, mode, absent, workdays, rate, salary, time,
                    amount, cola, total, sss, philhealth, pagibig, late, net, monthlyWage);
            if (employee.getCompany().equals("CRAYOLA")) {
                crayolaEntries.add(payrollEntry);
            } else {
                ixxiEntries.add(payrollEntry);
            }
        }
    }

    /**
     * Updates the Pag-Ibig, PhilHealth, and SSS Fees of an employee.
     */
    public void update() {
        for (PayrollEntry entry: crayolaEntries) {
            double sss, philhealth, pagibig;
            double monthlyWage = entry.getMonthlyWage();
            sss = Calculator.getInstance().computeSSSFee(monthlyWage);
            philhealth = Calculator.getInstance().computePhilHealthFee(monthlyWage);
            pagibig = Calculator.getInstance().computePagIbigFee(monthlyWage);

            entry.setPagibig(pagibig);
            entry.setPhilhealth(philhealth);
            entry.setSss(sss);
        }
        for (PayrollEntry entry: ixxiEntries) {
            double sss, philhealth, pagibig;
            double monthlyWage = entry.getMonthlyWage();
            sss = Calculator.getInstance().computeSSSFee(monthlyWage);
            philhealth = Calculator.getInstance().computePhilHealthFee(monthlyWage);
            pagibig = Calculator.getInstance().computePagIbigFee(monthlyWage);

            entry.setPagibig(pagibig);
            entry.setPhilhealth(philhealth);
            entry.setSss(sss);
        }
    }
}
