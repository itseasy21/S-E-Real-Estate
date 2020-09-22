package model;

public class Payroll
{
    private static double totalSalary ;
    private int id ;
    private double hours ;
    private double rate ;
    private double salary ;
    public Payroll(int id, double hours, double rate)throws MyException
    {
        this.id = id ;
        if(hours<=0)
        {
            throw new MyException("hours Cannot be 0 or less!");}
        else{
        this.hours = hours ;}
        if(rate<=0)
        {throw new MyException("rates Cannot be 0 or less!");}
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
    public void setSalary(double salary) throws MyException
    {
        if(salary<=0)
            throw new MyException("Salary Cannot be 0 or less!");
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