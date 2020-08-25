import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class CustomerTest {

    Customer c1, c2, c3, c4;
    Property p1;

    @Before
    public void setUp() throws Exception{
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe",0401717860,new Date(),"Male",
                "abc.jpg","Indian",45000);
        c2 = new Customer("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe",0401717860,new Date(),"Male",
                "abc.jpg","Indian",45000);

    }

    @Test
    public void testCase1(){
        c1.showDetails();
        c1.updateUser("itseasy19@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe",0401717860,new Date(),"Male",
                "abc.jpg","Indian",45000);
    }

    @After
    public void outputCase1(){
        c1.showDetails();
    }

}
