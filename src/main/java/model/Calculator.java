package model;

import java.util.ArrayList;

/**
 * This class is responsible for all computations related
 * to the employees' wages. These include the base wages of employees
 * as well as government fees such as SSS, PhilHealth, and Pag-Ibig.
 */
public class Calculator {
    /** The instance of the Calculator class. */
    private static Calculator instance = null;
    /** The FeeTable for the PhilHealth Fee. */
    private FeeTable philhealthFeeTable;
    /** The FeeTable for the SSS Fee. */
    private FeeTable sssFeeTable;
    /** The instance of the PagIbigFee. */
    private PagIbigFee pagIbigFee;

    /**
     * A constructor for a Calculator.
     */
    private Calculator() {
        philhealthFeeTable = new FeeTable(FeeTable.PHILHEALTH_FILE_NAME);
        pagIbigFee = new PagIbigFee();
        sssFeeTable = new FeeTable(FeeTable.SSS_FILE_NAME);
    }

    /**
     * Returns the instance of the Calculator class. If no instance
     * of the Calculator has been instantiated, this method will create
     * a new Calculator instance.
     * @return the instance of the Calculator class
     */
    public static Calculator getInstance() {
        if (instance == null) {
            instance = new Calculator();
        }
        return instance;
    }

    /**
     * Computes the total salary of an employee based on the
     * hourly rate of an employee, the number of work days, the
     * number of minutes late, and the number of minutes overtime.
     * @param rate the daily rate of on employee
     * @param daysPresent the number of days the employee was present
     *                    for work
     * @return the total salary of an employee
     * @throws IllegalArgumentException when one of the inputs is negative
     */
    public double computeSalary(double rate, double daysPresent)
            throws IllegalArgumentException {
        if (rate < 0 || daysPresent < 0) {
            throw new IllegalArgumentException("Negative inputs are not allowed");
        }
        return rate * daysPresent;
    }

    /**
     * Computes for the Overtime bonus to be added to an employee's
     * salary based on the hourly rate of an employee and the number
     * of minutes overtime.
     * @param rate the daily rate of an employee
     * @param minutesOT the number of minutes overtime
     * @return the Overtime bonus to be added to an employee's salary
     * @throws IllegalArgumentException when one of the inputs is negative
     */
    public double computeOvertime(double rate, int minutesOT)
            throws IllegalArgumentException {
        if (rate < 0 || minutesOT < 0) {
            throw new IllegalArgumentException("Negative inputs are not allowed");
        }
        return rate / 8 * minutesOT / 60 * 1.25;
    }

    /**
     * Computes for the Late Fee to be deducted from an employee's
     * salary based on the hourly rate of an employee and the number
     * of minutes late.
     * @param rate the daily rate of an employee
     * @param minutesLate the number of minutes late
     * @return the Late Fee to be deducted from an employee's salary
     * @throws IllegalArgumentException when one of the inputs is negative
     */
    public double computeLateFee(double rate, int minutesLate)
            throws IllegalArgumentException {
        if (rate < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Negative inputs are not allowed");
        }
        return rate / 8 * minutesLate / 60;
    }

    /**
     * Computes for the PhilHealth Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of an employee
     * @return the PhilHealth Fee to be paid by an employee
     * @throws IllegalArgumentException when the given salary is negative
     */
    public double computePhilHealthFee(double salary)
            throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        ArrayList<ArrayList<Double>> formulas = philhealthFeeTable.getFormulas();
        int n = formulas.size();

        for (int i = 0; i < n; i++) {
            double lower_bound = formulas.get(i).get(0);
            double upper_bound = formulas.get(i).get(1);
            double value = formulas.get(i).get(2);

            if (salary >= lower_bound && salary <= upper_bound) {
                if (i == 0 || i == n - 1) {
                    return value;
                } else {
                    return value * salary;
                }
            }
        }
        return 0;
    }

    /**
     * Computes for the Pag-Ibig Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of on employee
     * @return the Pag-Ibig Fee to be paid by an employee
     * @throws IllegalArgumentException when the given salary is negative
     */
    public double computePagIbigFee(double salary)
            throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        double sssFee = computeSSSFee(salary);
        double totalRate = pagIbigFee.getTotalRate();
        double employerContrib = pagIbigFee.getEmployerContrib();
        return totalRate * sssFee - employerContrib;
    }

    /**
     * Computes for the SSS Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of an employee
     * @return the SSS Fee to be paid by an employee
     * @throws IllegalArgumentException when the given salary is negative
     */
    public double computeSSSFee(double salary)
            throws IllegalArgumentException {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        for (ArrayList<Double> range: sssFeeTable.getFormulas()) {
            double lower_bound = range.get(0);
            double upper_bound = range.get(1);
            double value = range.get(2);

            if (salary >= lower_bound && salary <= upper_bound) {
                return value;
            }
        }
        return 0;
    }

    /**
     * Closes the fee tables so that their contents are saved to
     * the appropriate binary files.
     */
    public void close() {
        philhealthFeeTable.close();
        pagIbigFee.close();
        sssFeeTable.close();
    }
}
