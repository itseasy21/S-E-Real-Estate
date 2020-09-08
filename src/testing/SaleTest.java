package testing;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SaleTest {
    Sales s1,s2;

    @Before
    public void setUp() throws Exception, MyException {
        System.out.println("before");
        s1 = new Sales(101,1,202,500,100000,Sale.OPEN);
        s2 = new Sales(102,2,203,600,180000,Sale.CLOSED);


    }
    @Test
    public void testCase1()throws MyException {
       s1.BuyProperty(100000);
        assertEquals(100000.0, s1.getOfferprice());


    }
    @Test
    public void testCase2() throws MyException {
      s1.changePrice(160000);
      assertEquals(160000.0, s1.getListingPrice());

    }

    @Test(expected = MyException.class)
    public void testCase3()throws MyException {
        //assertEquals(100000.0, s1.getOfferprice());
       s1.BuyProperty(400);
        assertEquals(0, s1.getOfferprice(),0);
    }
    @After
    public void outputCase1(){
        System.out.println(s1.showDetails());
        System.out.println(s2.showDetails());

    }

    @Test(expected = MyException.class)
    public void testCase4() throws MyException {
        s2.BuyProperty(180000);
        assertEquals(0, s2.getListingPrice(),0);
        
    }





}
