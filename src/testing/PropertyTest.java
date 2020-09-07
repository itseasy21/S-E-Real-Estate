package testing;


import model.PropertyCategory;
import model.PropertyException;
import model.PropertyType;
import model.mainModel;
import model.Property;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.*;


public class PropertyTest {
    static Property rentalProperty;
    static Property saleProperty,salePropertyOne,salePropertyTwo;
    static mainModel realEstate;

    @BeforeClass
    public static void setUp() throws Exception{
        System.out.println("Before Class");
        rentalProperty = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Flat);
        saleProperty = new Property( "Green Brigade", PropertyType.Sale,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Townhouse);
        salePropertyOne = new Property( "Green Brigade", PropertyType.Sale,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Townhouse);
        salePropertyTwo = new Property( "Green Brigade", PropertyType.Sale,"1216 coorkston road", 26000,"Preston", 2,3,2,234_000.00, PropertyCategory.Townhouse);
        realEstate = new mainModel();

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
    // Add Property Test case
    @Test
    public void testAddProperty() throws  Exception{
        assertTrue(realEstate.isPropertyDBEmpty());
        realEstate.addProperty(saleProperty);
        assertFalse(realEstate.isPropertyDBEmpty());
    }
    // List Property test case
    @Test
    public void testListProperty() throws Exception{
        assertTrue(realEstate.isPropertyDBEmpty());
        realEstate.addProperty(saleProperty);
        assertEquals(1,realEstate.getPropertyDBSize());

        realEstate.addProperty(salePropertyOne);
        salePropertyOne.setEmployeeId("E2");
        assertEquals(2,realEstate.getPropertyDBSize());
        realEstate.addProperty(rentalProperty);
        rentalProperty.setEmployeeId("E3");
        assertEquals(saleProperty,realEstate.listProperty("P1"));

        salePropertyTwo.setEmployeeId("E1");
        realEstate.addProperty(salePropertyTwo);
        realEstate.listProperties();
    }

    // List Property test case
    @Test(expected = PropertyException.class)
    public void testListPropertyNegative() throws Exception{
        //check DB is empty
        assertTrue(realEstate.isPropertyDBEmpty());
        realEstate.addProperty(saleProperty);
        assertEquals(1,realEstate.getPropertyDBSize());
        realEstate.addProperty(rentalProperty);
        assertEquals(2,realEstate.getPropertyDBSize());
        // trying to list a property not found in database
        assertEquals(saleProperty,realEstate.listProperty("P3"));
    }

    @AfterClass
    public static void tearDown(){
        System.out.println("Test case executed !");
    }





}
