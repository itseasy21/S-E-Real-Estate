package testing;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class InspectionTest {
    static Inspectionmain i,in;
    static Inspection i1,i2;
    static Property p;
    static Customer c1;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("BEFORE");
        i = new Inspectionmain();
        in = new Inspectionmain();
        i1= new Inspection("1",0,0," "," " ," ");
        i2= new Inspection("2",0,0," "," " ," ");
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",new Date(),"Male",
                "abc.jpg","Indian",45000);

        p = new Property(123, 1,"Green Brigade", 2,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00);
        System.out.print(i1.showDetails());
    }

/*
    @Test
    public void createins() throws Exception{
        System.out.println("\nCREATE INSPECTION");
        i.createInspection(p,i2);
        System.out.print(i2.showDetails());
    }
*/


 // /*
    @Test
    public void bookins() throws Exception{
        System.out.println("\nBOOK INSPECTION");
        i.createInspection(p,i1);

      //  System.out.println(i1.showDetails()+"\n");
        i.bookInspection(c1,i1);
      //  System.out.print(i1.showDetails());
    }
// */
// /*

    @Test
    public void cancellins() throws Exception{
        System.out.println("\nCANCELL INSPECTION");
        i.cancellInspection(i1);
        System.out.print(i1.showDetails());
    }

    @Test//(expected = PropertyException.class)
    public void setStatusNegative() throws Exception{
        Assertions.assertThrows(PropertyException.class, () -> i1.setStatus("not created"));
    }
// */

/*



    @Test
    public void testcreatedStatus() throws PropertyException {
        System.out.println("\nTESTING CREATED STATUS");
        i2 = new Inspection(2);
        i2.createInspection(p);
        Assertions.assertEquals(i2.getStatus(),"Created");
    }







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
