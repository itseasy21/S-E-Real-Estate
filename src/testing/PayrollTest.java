package testing;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PayrollTest {
    Payroll p1,p2;
   // Employee c1,c2;
    @Before
    public void setUp() throws Exception, MyException {
        System.out.println("before");
        p1 = new Payroll("EMP1",40,10,45000);
        p2 = new Payroll("EMP2",20,8,20000);

        /*c1 = new Employee("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg",EmployeeType.FullTIme,45000,0);
        c2 = new Employee("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg",EmployeeType.PartTime,22000,10);*/



    }
    @Test
    public void testCase1()throws MyException {
        p1.setSalary(20000);
        assertEquals(20000.0, p1.getSalary());


    }
    @Test(expected = MyException.class)
    public void testCase2() throws MyException {
        p1.setSalary(-1000);
        assertEquals(0, p1.getSalary(),0);

    }
    @Test
    public void testCase3()throws MyException {
        p1.setSalary(10000);
        p1.getHours();
        assertEquals(40.0, p1.getHours());
    }
    @Test
    public void testCase4() throws MyException {
        p1.setSalary(5000);
        p1.getRate();
        assertEquals(10.0, p1.getRate());
    }
    @Test
    public void testCase5()throws MyException{
        p1.setBonus(1000);
        assertEquals(46000.0,p1.getSalary());

    }
    @Test(expected = MyException.class)
    public void testCase6()throws MyException
    {
        p1.reducedHours(41);
        assertEquals(0,p1.getHours(),0);
    }
    @After
    public void outputCase1(){
        System.out.println(p1.showDetails());
        //System.out.println(p2.showDetails());
    }

}
