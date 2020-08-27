package testing;

import static junit.framework.TestCase.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.*;

import java.util.Date;

public class CustomerTest {

    Customer c1, c2, c3, c4;
    Property p1;

    @Before
    public void setUp() throws Exception{
        System.out.println("before");
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","Indian",45000);
        c2 = new Customer("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","Indian",45000);

    }

    @Test
    public void testCase1() throws UserException {
        System.out.println(c1.showDetails());
        c1.updateUser("itseasy19@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","Indian",45000);
    }

    @Test
    public void testCase2() throws UserException {
        System.out.println("lol");
        System.out.println(c1.showDetails());
        c1.updateUser("itseasy19@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","",45000);
    }

    @Test
    public void testCase3() throws UserException {
        System.out.println("lol");
        System.out.println(c1.showDetails());
        c1.updateUser("itseasy19@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","Australian",25000);
        assertEquals(25000.0, c1.getIncome());
    }

    @After
    public void outputCase1(){
        System.out.println(c1.showDetails());
    }

}
