package model;

import java.util.ArrayList;
import java.util.Date;

public class Employee extends User{

    // Counter to keep track of event post
    static int employeeCounter = 0;

    EmployeeType empType = EmployeeType.FullTIme;
    double salary = 0;
    double workingHours = 0;

    public Employee(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, EmployeeType empType, double salary, double workingHours) {
        super(++employeeCounter, email, password, name, address, phoneNo, dob, gender, profilePic);
        this.empType = empType;
        this.salary = salary;
        this.workingHours = workingHours;
    }

    public void updateUser(String email, String password, String name, String address, String phoneNo, Date dob, String gender, String profilePic, EmployeeType empType, double salary, double workingHours){
        updateUser(email, password, name, address, phoneNo, dob, gender, profilePic);
        this.empType = empType;
        this.salary = salary;
        this.workingHours = workingHours;
    }

    public String showDetails(){
        String printDetails = super.showDetails();
        printDetails += "\nEmployee Type:\t"+this.empType;
        printDetails += "\nSalary:\t"+this.salary;
        printDetails += "\nWorking Hours    :\t"+this.workingHours;

        return printDetails;
    }

    public double getSalary() {
        return this.salary;
    }

    public double getWorkingHours() {
        return this.workingHours;
    }

    public EmployeeType getEmpType() {
        return this.empType;
    }

    public void setEmpType(EmployeeType empType) {
        this.empType = empType;
    }

    public void setSalary(double salary) throws UserException {
        if(salary <= 0){
            throw new UserException("Salary Cannot be 0 or less!");
        }else {
            this.salary = salary;
        }
    }

    public void setWorkingHours(double workingHours) throws UserException {
        if(this.empType == EmployeeType.FullTIme){
            throw new UserException("Full Time Employee Cannot Change Working Hours!");
        }else{
            this.workingHours = workingHours;
        }
    }
}
