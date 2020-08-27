package model;

import java.util.ArrayList;

public class Payroll
{
    private static double totalSalary ;
    private int id ;
    private double hours ;
    private double rate ;
    private double salary ;
    public Payroll(int id, double hours, double rate)throws PayrollException
    {
        this.id = id ;
        if(hours<=0)
        {
            throw new PayrollException("hours Cannot be 0 or less!");}
        else{
        this.hours = hours ;}
        if(rate<=0)
        {throw new PayrollException("rates Cannot be 0 or less!");}
        this.rate = rate ;
        double salaryCalc = hours * rate ;
        totalSalary = totalSalary + salaryCalc ;
    }

    public void calculateSalary()
    {
        salary = hours * rate ;
    }

    public double getSalary()
    {
        return salary ;
    }

    public int getEmployeeID()
    {
        return id ;
    }

    public double getHours()
    {
        return hours ;
    }

    public double getRate()
    {
        return rate ;
    }
    public void setSalary(double salary) throws PayrollException
    {
        if(salary<=0)
            throw new PayrollException("Salary Cannot be 0 or less!");
        else
        this.salary=salary;

    }


    public void increaseHours(double hourIncrease)
    {
        hours = hours + hourIncrease ;
    }

    public static double getTotalPayout()
    {
        return totalSalary ;
    }
    public String showDetails(){
        String printDetails="";
        printDetails += "\nSalary:\t"+this.salary;
        printDetails += "\nWorking Hours    :\t"+this.hours;
        printDetails +="\n rate:\t"+this.getRate();

        return printDetails;
    }
}