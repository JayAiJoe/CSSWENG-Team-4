package model;

import dao.EmployeePOJO;
import dao.Repository;

import java.util.ArrayList;
import java.util.Date;

public class EmployeeForm {
    private ArrayList<EmployeePOJO> employees = new ArrayList<>();
    private ArrayList<EmployeePOJO> activeEmployees = new ArrayList<>();
    private final Date dateUniform = new Date(8100, 0, 1);

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
    }

    public boolean addEmployee(String name, String frequency, double wage, String company, String mode) {
        String companyF = company.equals("Crayola Atbp.") ? "CRAYOLA" : "IX-XI";

        int num = employees.size();
        Date date = new Date();

        EmployeePOJO employee = new EmployeePOJO(num, name, companyF, wage, mode, frequency, 0, date, dateUniform);
        employees.add(employee);
        activeEmployees.add(employee);

        return Repository.getInstance().addEmployee(employee);
    }

    public ArrayList<EmployeePOJO> getEmployees() {
        return activeEmployees;
    }

    public void updateEmployee(EmployeePOJO employee) {
        Repository.getInstance().updateEmployee(employee);
    }
}