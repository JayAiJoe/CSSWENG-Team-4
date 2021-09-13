package model;

import dao.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents a payroll on a given payday
 * consisting of payroll entries. This class is responsible
 * for adding entries and making method calls that perform
 * different calculations.
 */
public class ThirteenPayroll {
    private ArrayList<PayrollEntry> crayolaEntries, ixxiEntries;

    public ThirteenPayroll(String frequency) {
        try {
            initialize(frequency);
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
    private void initialize(String frequency) {
        crayolaEntries = new ArrayList<>();
        ixxiEntries = new ArrayList<>();

        ArrayList<EmployeePOJO> employees = Repository.getInstance().getAllEmployees();
        System.out.println(frequency);
        for (EmployeePOJO employee : employees) {
            if (!employee.getWageFrequency().equals(frequency)) {
                continue;
            }

            int cola = 0;

            String employeeName = employee.getCompleteName();
            String mode = employee.getMode();
            double totalDays = 26;
            double rate = employee.getWage();
            if (mode.equals("MONTHLY")) {
                rate /= totalDays;
            }
            double salary = Calculator.getInstance().computeSalary(rate, 26);

            double monthlyWage;
            if (mode.equals("MONTHLY")) {
                monthlyWage = employee.getWage();
            } else {
                monthlyWage = rate * 26;
            }

            PayrollEntry payrollEntry = new PayrollEntry(employeeName, mode, 0, 26, rate, salary, 0,
                        0, cola, salary, 0, 0, 0, 0, salary, monthlyWage);
            if (employee.getCompany().equals("CRAYOLA")) {
                crayolaEntries.add(payrollEntry);
            } else {
                ixxiEntries.add(payrollEntry);
            }
        }
    }
}
