package testing;

import static org.junit.Assert.*;

import config.CustomerType;
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
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                "Indian",45000, CustomerType.VENDOR);
        c2 = new Customer("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                "Indian",45000, CustomerType.BUYER);

    }

    @Test
    public void testCase1(){
        System.out.println("lol");
        System.out.println(c1.showDetails());
        c1.updateUser("itseasy19@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male", "Indian",45000, CustomerType.VENDOR);
    }

    @After
    public void outputCase1(){
        System.out.println(c1.showDetails());
    }

}
