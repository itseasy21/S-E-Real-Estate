package model;

public class Payroll
{
    private static double totalSalary ;
    private String id ;
    private double hours ;
    private double rate ;
    private double salary ;
    public Payroll(String empid, double hours, double rate,double salary)throws MyException
    {
        this.id = empid;
        this.hours = hours ;
        if(rate<=0)
        {throw new MyException("rates Cannot be 0 or less!");}
        this.rate = rate ;
       // double salaryCalc = hours * rate ;
       // totalSalary = totalSalary + salaryCalc ;
        this.salary=salary;
    }

    public void calculateSalary()
    {
        this.salary = hours * rate ;
    }

    public double getSalary()
    {
        return salary ;
    }

    public String getEmployeeID()
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
        this.hours = this.hours + hourIncrease ;
    }
    public void reducedHours(double hourDecrease)throws MyException
    {
        if(hourDecrease<=0)
            throw new MyException("Hours Cannot be 0 or less!");
        else if(this.hours-hourDecrease<=0)
            throw new MyException("Cannot decrease hours");
        else
            this.hours=hours-hourDecrease;
    }
    public void setHours(double hours) throws MyException {
        if(hours<=0)
            throw new MyException("Hours Cannot be 0 or less!");
        else
            this.hours = hours;
    }
    public void setBonus(double bonus)
    {
        this.salary = this.salary+bonus;
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