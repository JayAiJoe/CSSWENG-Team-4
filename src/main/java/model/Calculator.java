package model;

// TODO: check invalid inputs i.e. negative inputs, maybe

/**
 * This class is responsible for all computations related
 * to the employees' wages. These include the base wages of employees
 * as well as government fees such as SSS, PhilHealth, and Pag-Ibig.
 */
public class Calculator {
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
    public static double computeSalary(double rate, int workDays, int minutesLate,
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
    private static double computeOvertime(double rate, int minutesOT) {
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
    private static double computeLateFee(double rate, int minutesLate) {
        return rate * minutesLate / 60;
    }

    /**
     * Computes for the PhilHealth Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary is the monthly basic salary of an employee
     * @return the PhilHealth Fee to be paid by an employee
     */
    public static double computePhilHealthFee(double salary) {
        if (salary <= 10000) {
            return 175;
        } else if (salary < 70000) {
            return 0.035 * salary / 2;
        } else {
            return 1225;
        }
    }

    /**
     * Computes for the Pag-Ibig Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary is the monthly basic salary of on employee
     * @return the Pag-Ibig Fee to be paid by an employee
     */
    public static double computePagIbigFee(double salary) {
        if (salary <= 1500) {
            return salary * 0.01;
        } else {
            return Math.min(salary * 0.02, 100);
        }
    }

    /**
     * Computes for the SSS Fee to be paid by an employee
     * based on the employee's monthly basic salary.
     * @param salary is the monthly basic salary of an employee
     * @return the SSS Fee to be paid by an employee
     */
    public static double computeSSSFee(double salary) {
        if (salary < 3250) {
            return 135;
        }

        double check = 3500;
        while (check <= 19500) {
            if (salary >= check - 250 && salary < check + 250) {
                return check * 0.045;
            }
            check += 500;
        }
        return 900;
    }
}
