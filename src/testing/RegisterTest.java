package testing;

import config.*;
import model.*;
import controller.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class RegisterTest {

    String name, email, password, phoneNo, address, gender, dob, nationality, income;
    CustomerType type;
    registerController registerHandler;
    mainModel model;

    @Before
    public void setUp() throws Exception{
        model = new mainModel(); //Initialize Model
        model.syncDB(); //Synchronize the data into memory from DB
        registerHandler = new registerController();
        registerHandler.initializeModel("", model);

        //Setting up the registeration variables
         name = "Amellia";
         email = "amellia@gmail.com";
         password = "12345678";
         phoneNo = "0409898453";
         address = "Rowville";
         gender = "female";
         dob = "05/01/1999";
         nationality = "Indian";
         income = "100000";
         type = CustomerType.VENDOR;
    }

    @Test //Positive
    public void test1() throws UserException, ParseException {
        assertNull(model.getUserByUsername("amellia@gmail.com"));
        Boolean registered = registerHandler.registerhandler(name, email, password, phoneNo, address, gender, dob, nationality, income,type);
        assertEquals("12345678", (model.getUserByUsername("amellia@gmail.com")).getPassword());
    }

    @Test(expected = UserException.class) //Negative, throws invalid email/email is taken
    public void test2() throws UserException, ParseException {
        assertEquals("12345678", (model.getUserByUsername("amellia@gmail.com")).getPassword()); //making sure user exists to throw the error
        Boolean registered = registerHandler.registerhandler(name, email, password, phoneNo, address, gender, dob, nationality, income,type);
        assertEquals("12345678", (model.getUserByUsername("amellia@gmail.com")).getPassword());
    }

    @Test(expected = UserException.class) //Negative, throws invalid date exception
    public void test3() throws UserException, ParseException {
        email = "amellia2@gmail.com";
        dob = "111111";
        assertNull((model.getUserByUsername("amellia2@gmail.com"))); //making sure user exists to throw the error
        Boolean registered = registerHandler.registerhandler(name, email, password, phoneNo, address, gender, dob, nationality, income,type);
        assertEquals("12345678", (model.getUserByUsername("amellia2@gmail.com")).getPassword());
    }

    @After
    public void endTest() throws SQLException {
        Customer someCustomer = (Customer) model.getUserByUsername("amellia@gmail.com");
        System.out.println(someCustomer.showDetails());
//        model.savetoDB();
    }

}
