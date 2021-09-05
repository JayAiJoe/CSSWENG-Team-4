package model;

import dao.EmployeePOJO;
import dao.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class EmployeeForm {
    ArrayList<EmployeePOJO> employees = new ArrayList<>();
    ArrayList<EmployeePOJO> activeEmployees = new ArrayList<>();
    Date dateUniform = new Date(8100, 0, 1);

    public EmployeeForm() {
        initialize();
    }

    private void initialize() {
        employees = Repository.getInstance().getAllEmployees();

        for (EmployeePOJO entry: employees) {
            if (entry.getDateLeft().equals(dateUniform)) {
                activeEmployees.add(entry);
            }
        }
        Collections.sort(activeEmployees);
    }

    public boolean addEmployee(String name, String frequency, double wage, String company, String mode) {
        String companyF = company.equals("Crayola Atbp.") ? "CRAYOLA" : "IX-XI";

        int num = employees.size();
        Date date = new Date();

        EmployeePOJO employee = new EmployeePOJO(num, name, companyF, wage, mode, frequency, 0, date, dateUniform);
        employees.add(employee);
        activeEmployees.add(employee);
        for (int i = activeEmployees.size() - 1; i > 0; i--) {
            if (activeEmployees.get(i).compareTo(activeEmployees.get(i - 1)) < 0) {
                EmployeePOJO temp = activeEmployees.get(i);
                activeEmployees.set(i, activeEmployees.get(i - 1));
                activeEmployees.set(i - 1, temp);
            } else {
                break;
            }
        }

        return Repository.getInstance().addEmployee(employee);
    }

    public ArrayList<EmployeePOJO> getEmployees() {
        return activeEmployees;
    }
}