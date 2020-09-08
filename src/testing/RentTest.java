package testing;

import static org.junit.jupiter.api.Assertions.*;
import model.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RentTest {

    static Rent r,r2;
    static Inspection i2;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("before");
       // r = new Rent(1,12,200.00,500.00);
        System.out.print(r.showDetails());
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.print(r.showDetails());
    }

    @Test
    public void testinitialStatus(){
        assertEquals(r.getStatus(),"Available");
    }

    @Test
    public void testchangeStatuspositive() throws Exception{
        r.changeStatus();
        assertEquals(r.getStatus(),"Rented");
    }

    @Test
    public void testcancelledStatusnegative() throws Exception{
        r.changeStatus();
        assertEquals(r.getStatus(),"scheduled");
    }

    @Test
    public void setPricepositive() throws Exception{
        r.setPrice(700.0);
    }

    @Test//(expected = PropertyException.class)
    public void setPricenegative() throws Exception{
        r.setPrice(200.0);
    }
}

