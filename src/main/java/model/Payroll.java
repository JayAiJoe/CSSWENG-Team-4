package model;

import dao.*;

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

    public Payroll(Date startDate, Date endDate, String frequency) {
        // change time to midnight in PST
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate = new Date(startDate.getTime() + 8 * 3600000L);
        endDate.setHours(23);
        endDate.setMinutes(59);
        endDate = new Date(endDate.getTime() + 8 * 3600000L);
        try {
            initialize(startDate, endDate, frequency);
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

    /**
     * Initializes the entries of the payroll using employee and
     * logbook information retrieved from the database.
     */
    private void initialize(Date startDate, Date endDate, String frequency) {

        // TODO: Change everything later
        crayolaEntries = new ArrayList<>();
        ixxiEntries = new ArrayList<>();

        ArrayList<EmployeePOJO> employees = Repository.getInstance().getAllEmployees();
        for (EmployeePOJO employee: employees) {
            if (!employee.getWageFrequency().equals(frequency)) {
                continue;
            }
            PerformancePOJO performance = Repository.getInstance()
                    .findPerformanceOne(employee.getEmployeeID(), startDate);
            System.out.println(startDate);

            if (performance == null) {
                ArrayList<LogbookPOJO> logbooks = Repository.getInstance()
                        .getEmployeeAttendance(employee.getEmployeeID(), startDate, endDate);
                ArrayList<ColaPOJO> colas = Repository.getInstance()
                        .getEmployeeCola(employee.getEmployeeID(), startDate, endDate);
                if (logbooks.size() == 0) {
                    continue;
                }

                double daysPresent = 0, daysAbsent = 0;
                int minsOvertime = 0, minsLate = 0, cola = 0;

                for (LogbookPOJO logbook : logbooks) {
                    // check present or absent
                    if (logbook.getTimeIn1() == 0 && logbook.getTimeOut1() == 0) {
                        daysAbsent += 0.5;
                    } else {
                        daysPresent += 0.5;
                    }
                    if (logbook.getTimeIn2() == 0 && logbook.getTimeOut2() == 0) {
                        daysAbsent += 0.5;
                    } else {
                        daysPresent += 0.5;
                    }
                    // add overtime and late
                    minsOvertime += logbook.getApprovedOT();
                    minsLate += logbook.getMinsLate();
                }
                for (ColaPOJO colaPOJO : colas) {
                    cola += colaPOJO.getCola();
                }

                performance = new PerformancePOJO(employee.getEmployeeID(),
                        employee.getCompleteName(), startDate, endDate, daysPresent, daysAbsent,
                        minsOvertime, minsLate, cola);
                Repository.getInstance().addPerformance(performance);
            }

            String employeeName = employee.getCompleteName();
            String mode = employee.getMode();
            double absent = performance.getDaysAbsent();
            double workdays = performance.getDaysPresent();
            double totalDays = absent + workdays;

            double rate = employee.getWage();
            if (mode.equals("MONTHLY")) {
                rate /= 2 * totalDays;
            }

            double salary = Calculator.getInstance().computeSalary(rate, workdays);
            int time = performance.getMinsOvertime();
            double amount = Calculator.getInstance().computeOvertime(rate, time);
            double total = salary + amount;
            double cola = performance.getCola();

            double sss, philhealth, pagibig;
            double monthlyWage;
            if (mode.equals("MONTHLY")) {
                monthlyWage = employee.getWage();
            } else {
                monthlyWage = rate * 26;
            }
            sss = Calculator.getInstance().computeSSSFee(monthlyWage);
            philhealth = Calculator.getInstance().computePhilHealthFee(monthlyWage);
            pagibig = Calculator.getInstance().computePagIbigFee(monthlyWage);

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
