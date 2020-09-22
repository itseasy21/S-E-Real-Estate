package testing;

import config.CustomerType;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class InspectionTest {
    static Inspectionmain i,in;
    static Inspection i1,i2;
    static Property rentalProperty;
    static Customer c1;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("BEFORE");
        i = new Inspectionmain();
        i1= new Inspection("3",1,1," "," " ," ");
        i2= new Inspection("2",2,2," "," " ," ");
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
<<<<<<< HEAD
                "673 La Trobe","401717860",new Date(),"Male",
                "Indian",45000, CustomerType.CUSTOMER);
=======
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                "Indian",45000, CustomerType.VENDOR);

      //  p = new Property(123, 1,"Green Brigade", 2,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00);
        System.out.print(i1.showDetails());
>>>>>>> 9914c71c06fa772cce81b941333cbc93da0a1d85
        rentalProperty = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Flat);

    }

    @Test
    public void createins() throws Exception{
        System.out.println("\nCREATE INSPECTION");
        i.createInspection(rentalProperty,i1);
        System.out.print(i1.showDetails());
    }

    @Test
    public void bookins() throws Exception{
<<<<<<< HEAD
        System.out.println("\nBOOK INSPECTION");
        i.createInspection(rentalProperty,i1);
        i.bookInspection(c1,i1);
=======

        if(c1.getType().equals(CustomerType.BUYER) || c1.getType().equals(CustomerType.RENTER)){

            System.out.println("\nBOOK INSPECTION");
            i.createInspection(rentalProperty,i1);

            //  System.out.println(i1.showDetails()+"\n");
            i.bookInspection(c1,i1);
            //  System.out.print(i1.showDetails());
        }else{
            System.out.println("You are not allowed to book inspection!");
        }

>>>>>>> 9914c71c06fa772cce81b941333cbc93da0a1d85
    }

    @Test
    public void cancellins() throws Exception{
        System.out.println("\nCANCELL INSPECTION");
        i.createInspection(rentalProperty,i1);
        i.bookInspection(c1,i1);
        i.cancellInspection(i1);
        System.out.print(i1.showDetails());
    }

    @Test//(expected = PropertyException.class)
    public void setStatusNegative() throws Exception{
        Assertions.assertThrows(PropertyException.class, () -> i1.setStatus("not created"));
    }

    @Test
    public void testcreatedStatus() throws PropertyException {
        System.out.println("\nTESTING CREATED STATUS");
        i.createInspection(rentalProperty,i2);
        Assertions.assertEquals(i2.getStatus(),"Created");
    }


/*
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("\nafter");
        System.out.print(i.showDetails());
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
