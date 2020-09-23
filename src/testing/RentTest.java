package testing;

import config.CustomerType;
import config.EmployeeType;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RentTest {

    static Rent r,r2;
    static Inspection i2;
    static Property rentalProperty;
    static Customer c1;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
       // System.out.println("before");
        r = new Rent("1","1",1,"1","1",200.00,500.00,"");
        rentalProperty = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 260,"Preston", 2,3,2,500.00, PropertyCategory.Flat, EmployeeType.BranchAdmin);
        c1 = new Customer("itseasy21@gmail.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                "Indian",45000, CustomerType.LANDLORD);
        //System.out.print(r.showDetails());
    }

    @Test
    public void testinitialStatus(){
        assertEquals(r.getStatus(),"Available");
    }

    @Test
    public void addrent() throws PropertyException {
        r.ApplyRent(rentalProperty,c1);
        System.out.print(r.showDetails());
    }

    @Test//(expected = PropertyException.class)
    public void setPricenegative() throws Exception{
        Assertions.assertThrows(PropertyException.class, () -> r.setPrice(200.0));
    }

    /*
    @Test
    public void testchangeStatuspositive() throws Exception{
        r.RentProperty(rentalProperty,c1);
        assertEquals(r.getStatus(),"Available");
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

     */
}

