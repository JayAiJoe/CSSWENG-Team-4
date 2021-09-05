package model;

import dao.EmployeePOJO;
import dao.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EmployeeForm {

    ArrayList<EmployeePOJO> employees = new ArrayList<>();
    Date dateUniform = new Date(8100,0,1);


    public EmployeeForm() {
        initialize();
    }

    public void initialize(){
        employees = Repository.getInstance().getAllEmployees();
    }

    public boolean addEmployee(String name, String frequency, double wage, String company, String mode){
        String companyF;
        String modeF;
        int num = employees.size();
        Date date = new Date();
        if(company.equals("Crayola Atbp.")){
            companyF = "CRAYOLA";
        }
        else{
            companyF = "IX-XI";
        }
        if(mode.equals("Monthly")){
            modeF = "MONTHLY";
        }
        else{
            modeF = "DAILY";
        }
        EmployeePOJO employee = new EmployeePOJO(num,name,companyF,wage,modeF, frequency, 0, date, dateUniform);
        return Repository.getInstance().addEmployee(employee);

    }

    public ArrayList<EmployeePOJO> getEmployees(){
        ArrayList<EmployeePOJO> employeesN = Repository.getInstance().getAllEmployees();
        ArrayList<EmployeePOJO> employeeActive = new ArrayList<>();
        for (EmployeePOJO entry : employeesN){
            if (entry.getDateLeft().equals(dateUniform)){
                employeeActive.add(entry);
            }
        }
        return employeeActive;
    }

}
