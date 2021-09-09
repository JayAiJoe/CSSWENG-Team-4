package model;

import dao.ColaPOJO;
import dao.EmployeePOJO;
import dao.Repository;
import wrapper.EmployeeWrapper;

import java.text.SimpleDateFormat;
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

    public void updateCola(ArrayList<ColaPOJO> colas, Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String today = format.format(date);
        ArrayList<ColaPOJO> check = Repository.getInstance().getCola(
                new Date(date.getTime() - 86400000L), new Date(date.getTime() + 86400000L));

        for (ColaPOJO cola: colas) {
            boolean found = false;
            for (ColaPOJO checkCola: check) {
                // check if cola is already in database
                if (today.equals(format.format(checkCola.getDate())) &&
                        cola.getEmployeeID() == checkCola.getEmployeeID()) {
                    found = true;
                    checkCola.setCola(cola.getCola());
                    Repository.getInstance().updateCola(checkCola);
                    break;
                }
            }
            // cola is not yet in database
            if (!found) {
                Repository.getInstance().addCola(cola);
            }
        }
    }
}