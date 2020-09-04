package testing;


import model.Property;
import model.PropertyException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.*;


public class PropertyTest {
    static Property rentalProperty;
    static Property saleProperty;

    @BeforeClass
    public static void setUp() throws Exception{
        System.out.println("Before Class");
        rentalProperty = new Property(123, "Green Brigade", 1,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00);
        saleProperty = new Property(123, "Green Brigade", 2,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00);
    }


    @Test
    public void propertyType(){
        assertTrue(rentalProperty.isPropertyTypeRental());

    }


    @Test
    public void propertyTypeNegative(){
        assertFalse(rentalProperty.isPropertyTypeSale());
    }


    @Test
    public void setSellingPrice() throws Exception{

        saleProperty.setSellingPrice(23_4_600);
        assertEquals(234_600.0, saleProperty.getSellingPrice(),0);

    }


    @Test(expected = PropertyException.class)
    public void setSellingPriceNegative() throws Exception{
        saleProperty.setRentalPrice(23_4_600);
        assertEquals(0,rentalProperty.getSellingPrice(),0);
    }


    @Test
    public void setRentalPrice() throws Exception{
        rentalProperty.setRentalPrice(23_4_450.00);
        assertEquals(23_4_450.00,rentalProperty.getRentalPrice(),0);


    }


    @Test(expected = PropertyException.class)
    public void setRentalPriceNegative() throws Exception{
        rentalProperty.setSellingPrice(234_3_345.00);
        assertEquals(0,rentalProperty.getRentalPrice(),0);

    }

    @AfterClass
    public static void tearDown(){
        System.out.println("Test case executed !");
    }





}
