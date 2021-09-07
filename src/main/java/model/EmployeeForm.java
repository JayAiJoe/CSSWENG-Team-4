package model;

import dao.EmployeePOJO;
import dao.Repository;
import wrapper.EmployeeWrapper;

import java.util.ArrayList;
import java.util.Date;

public class EmployeeForm {
    private ArrayList<EmployeeWrapper> employees = new ArrayList<>();
    private ArrayList<EmployeeWrapper> activeEmployees = new ArrayList<>();
    private final Date dateUniform = new Date(8100, 0, 1);

    public EmployeeForm() {
        initialize();
    }

    private void initialize() {
        ArrayList<EmployeePOJO> allEmployees = Repository.getInstance().getAllEmployees();

        for (EmployeePOJO employee: allEmployees) {
            EmployeeWrapper entry = new EmployeeWrapper(employee);
            employees.add(entry);
            if (employee.getDateLeft().equals(dateUniform)) {
                activeEmployees.add(entry);
            }
        }
    }

    public boolean addEmployee(String name, String frequency, double wage, String company, String mode) {
        String companyF = company.equals("Crayola Atbp.") ? "CRAYOLA" : "IX-XI";

        int num = employees.size();
        Date date = new Date();

        EmployeePOJO employee = new EmployeePOJO(num, name, companyF, wage, mode, frequency, 0, date, dateUniform);
        EmployeeWrapper entry = new EmployeeWrapper(employee);
        employees.add(entry);
        activeEmployees.add(entry);

        return Repository.getInstance().addEmployee(employee);
    }

    public ArrayList<EmployeeWrapper> getEmployees() {
        return activeEmployees;
    }

    public void updateEmployee(EmployeeWrapper employee) {
        Repository.getInstance().updateEmployee(employee.getEmployee());
    }
}