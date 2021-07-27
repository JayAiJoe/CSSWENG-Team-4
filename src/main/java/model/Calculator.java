package model;

// TODO: check invalid inputs i.e. negative inputs, maybe

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
    /** The FeeTable for the Pag-Ibig Fee. */
    private FeeTable pagibigFeeTable;
    /** The FeeTable for the SSS Fee. */
    private FeeTable sssFeeTable;

    /**
     * A constructor for a Calculator.
     */
    private Calculator() {
        philhealthFeeTable = new FeeTable(FeeTable.PHILHEALTH_FILE_NAME);
        pagibigFeeTable = new FeeTable(FeeTable.PAG_IBIG_FILE_NAME);
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
     * @param rate the hourly rate of on employee
     * @param workDays the number of work days
     * @param minutesLate the number of minutes late
     * @param minutesOT the number of minutes overtime
     * @return the total salary of an employee
     */
    public double computeSalary(double rate, int workDays, int minutesLate,
                                       int minutesOT) {
        return rate * 8 * workDays + computeOvertime(rate, minutesOT) -
                computeLateFee(rate, minutesLate);
    }

    /**
     * Computes for the Overtime bonus to be added to an employee's
     * salary based on the hourly rate of an employee and the number
     * of minutes overtime.
     * @param rate the hourly rate of an employee
     * @param minutesOT the number of minutes overtime
     * @return the Overtime bonus to be added to an employee's salary
     */
    private double computeOvertime(double rate, int minutesOT) {
        return rate * minutesOT / 60 * 1.25;
    }

    /**
     * Computes for the Late Fee to be deducted from an employee's
     * salary based on the hourly rate of an employee and the number
     * of minutes late.
     * @param rate the hourly rate of an employee
     * @param minutesLate the number of minutes late
     * @return the Late Fee to be deducted from an employee's salary
     */
    private double computeLateFee(double rate, int minutesLate) {
        return rate * minutesLate / 60;
    }

    /**
     * Computes for the PhilHealth Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of an employee
     * @return the PhilHealth Fee to be paid by an employee
     * @throws Exception if the salary does not fit any range
     */
    public double computePhilHealthFee(double salary) throws Exception {
        return computeGovtFee(salary, philhealthFeeTable.getFormulas());
    }

    /**
     * Computes for the Pag-Ibig Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of on employee
     * @return the Pag-Ibig Fee to be paid by an employee
     * @throws Exception if the salary does not fit any range
     */
    public double computePagIbigFee(double salary) throws Exception {
        return computeGovtFee(salary, pagibigFeeTable.getFormulas());
    }

    /**
     * Computes for the SSS Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary the monthly basic salary of an employee
     * @return the SSS Fee to be paid by an employee
     * @throws Exception if the salary does not fit any range
     */
    public double computeSSSFee(double salary) throws Exception {
        return computeGovtFee(salary, sssFeeTable.getFormulas());
    }

    /**
     * Computes for a government fee to be paid by an employee
     * based on the employee's monthly basic salary and the
     * fee table of the government fee.
     * @param salary the monthly basic salary of an employee
     * @param formulas the fee table of the government fee
     * @return the government fee to be paid by an employee
     * @throws Exception if the salary does not fit any range
     */
    private double computeGovtFee(double salary, ArrayList<ArrayList<Double>> formulas)
            throws Exception {
        for (ArrayList<Double> row: formulas) {
            double lower_bound = row.get(0);
            double upper_bound = row.get(1);
            double rate = row.get(2);
            double constant = row.get(3);

            if (salary >= lower_bound && salary < upper_bound) {
                return rate * salary + constant;
            }
        }
        throw new Exception("Salary does not fit any range");
    }
}
