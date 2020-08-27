package testing;

import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class EmployeeTest {

    Employee c1, c2, c3, c4;
    Property p1;

    @Before
    public void setUp() throws Exception{
        System.out.println("before");
        c1 = new Employee("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg",EmployeeType.FullTIme,45000,0);
        c2 = new Employee("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg",EmployeeType.PartTime,22000,10);

    }

    @Test
    public void testCase1() throws UserException {
        System.out.println("updating salary to 10000");
        c1.setSalary(10000);
        assertEquals(10000.0, c1.getSalary());
    }

    @Test
    public void testCase2() throws UserException {
        System.out.println("updating salary to 0");
        c1.setSalary(0);
        assertEquals(0, c1.getSalary());
    }

    @Test
    public void testCase3() throws UserException {
        System.out.println("testing updating of working hours for full time employee");
        c1.setWorkingHours(20);
        assertEquals(20, c1.getWorkingHours());
    }

    @Test
    public void testCase4() throws UserException {
        System.out.println("testing updating of working hours for part time employee");
        c2.setWorkingHours(20.5);
        assertEquals(20.5, c2.getWorkingHours());
    }

    @After
    public void outputCase1(){
        System.out.println(c1.showDetails());
    }
}
