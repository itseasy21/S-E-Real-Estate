package testing;

import config.CustomerType;
import config.EmployeeType;
import config.PropertyCategory;
import config.PropertyType;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class InspectionTest {
    static mainModel i,in;
    static Inspection i1,i2;
    static Property rentalProperty;
    static Customer c1,c2;
    static Employee e1;
    String date="10/10/2020;11/10/2020;12/10/2020;13/10/2020;14/10/2020";
    String time="10:00;10:30;11:00am;11:30am;12:00pm";
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("BEFORE");
        i = new mainModel();
        i1= new Inspection(1,"EMP1"," "," " ," ");
        i2= new Inspection(2,"EMP2"," "," " ," ");
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                "Indian",45000, CustomerType.BUYER);
        rentalProperty = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Flat);

        c2 = new Customer("itseasy21@gmail.com","pa33w0rd","amellia",

                "673 La Trobe","401717860",(new Date()).toString(),"Female",
                "Indian",45000, CustomerType.VENDOR);

        e1 = new Employee("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                EmployeeType.FullTIme, EmployeeType.SalesConsultant, 45000,0);

    }

    @Test
    public void createins() throws PropertyException, UserException, SQLException {
        System.out.println("\nCREATE INSPECTION");
        i.syncDB();
        i.createInspection(1,e1,date,time,"Created");
        i.listInspection();

    }
    @Test
    public void bookins() throws PropertyException, UserException, SQLException {
        System.out.println("\nBOOK INSPECTION");
       i.createInspection(rentalProperty.getPropertyId(),e1,date,time,"Created");
       // i.bookInspection(c1,i1);
    }

    @Test//(expected = UserException.class)
    public void bookinsnegative() throws PropertyException, UserException, SQLException {
        System.out.println("\nBOOK INSPECTION");
        i.createInspection(rentalProperty.getPropertyId(),e1,date,time,"Created");
        // i.bookInspection(c2,i1);
        //Assertions.assertThrows(UserException.class, () -> i.bookInspection(c2,i1));
    }
/*
    @Test//(expected = UserException.class)
    public void bookinsnegative1() throws PropertyException, UserException {
        System.out.println("\nBOOK INSPECTION");
        // i.bookInspection(c2,i1);
        Assertions.assertThrows(UserException.class, () -> i.bookInspection(c1,i1));
    }

    @Test
    public void cancellins() throws Exception, UserException {
        System.out.println("\nCANCELL INSPECTION");
        i.createInspection(rentalProperty,i1);
        // i.bookInspection(c1,i1);
        i.cancellInspection(i1);
        System.out.print(i1.showDetails());
    }

    @Test//(expected = PropertyException.class)
    public void setStatusNegative() throws Exception{
        Assertions.assertThrows(PropertyException.class, () -> i1.setStatus("not created"));
    }

    @Test
    public void testcreatedStatus() throws PropertyException, UserException {
        System.out.println("\nTESTING CREATED STATUS");
        i.createInspection(rentalProperty,i2);
        Assertions.assertEquals(i2.getStatus(),"Created");
    }


/*
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("\nAfter");
        System.out.print(i1.showDetails());
    }

    @Test
    public void testcancelledStatuspositive() throws Exception{
        i2.cancellInspection();
        assertEquals(i2.getStatus(),"cancelled");
    }

    @Test
    public void testcancelledStatusnegative() throws Exception{
        i.cancellInspection();
        assertEquals(i.getStatus(),"scheduled");
    }

  @Test//(expected = PropertyException.class)
    public void setStatusnegative() throws Exception{
        i.setStatus("not created");
    }
*/
}
