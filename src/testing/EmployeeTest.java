package testing;

import config.EmployeeType;
import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class EmployeeTest {

    static Employee c1, c2, c3, c4;
    Property p1;

    @Before
    public void setUp() throws Exception{
        c1 = new Employee("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                 EmployeeType.FullTIme, EmployeeType.SalesConsultant, 45000,0);
        c2 = new Employee("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                EmployeeType.PartTime,EmployeeType.PropertyManager, 22000,10);
    }

    @Test //Positive
    public void testCase1() throws UserException {
        assertEquals(45000.0, c1.getSalary());
        System.out.println("updating salary to 10000");
        c1.setSalary(10000);
        assertEquals(10000.0, c1.getSalary());
    }

    @Test(expected = UserException.class) //Negative
    public void testCase2() throws UserException {
        assertEquals(45000.0, c1.getSalary());
        System.out.println("updating salary to negative");
        c1.setSalary(-1);
        assertEquals(0, c1.getSalary());
    }

    @Test(expected = UserException.class) //Negative
    public void testCase3() throws UserException {
        System.out.println("testing updating of working hours for full time employee");
        c1.setWorkingHours(20);
    }

    @Test //Positive
    public void testCase4() throws UserException {
        System.out.println("testing updating of working hours for part time employee to 20");
        assertEquals(10.0, c2.getWorkingHours());
        System.out.println("before changing:" + c2.getWorkingHours());
        c2.setWorkingHours(20);
        System.out.println("after changing:" + c2.getWorkingHours());
        assertEquals(20.0, c2.getWorkingHours());
    }
}
