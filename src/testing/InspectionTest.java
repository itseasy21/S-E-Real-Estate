package testing;

import static org.junit.jupiter.api.Assertions.*;
import model.*;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InspectionTest {

    static Inspection i;
    static Inspection i2;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("before");
        i = new Inspection(1,123,1,1,new Date());
        System.out.print(i.showDetails());
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.print(i.showDetails());
    }

    @Test
    public void testinitialStatus(){
        assertEquals(i.getStatus(),"Scheduled");
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
    public void setStatuspositive() throws Exception{
        i.setStatus("completed");
    }

    @Test//(expected = PropertyException.class)
    public void setStatusnegative() throws Exception{
        i.setStatus("not created");
    }

    @Test
    public void testaddinspection() throws Exception{
        i2 = new Inspection(1,123,1,1,new Date());
        assertEquals(i2.getStatus(),"cancelled");
    }



}
