package controller;

import config.CustomerType;
import model.UserException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerController extends baseController{

    public boolean registerhandler(String name, String email, String password, String phoneNo, String address, String gender, String dob, String nationality, String income, CustomerType type) throws ParseException, UserException {

        int error = 0;
        String errorMsg = "";

        //all checks here and pass to model for add
        //Name check
        if(name.length() < 3){
            error = 1;
            errorMsg += "Name is Invalid, Please Enter Full Name\n";
        }
        //Email check
        if(!email.isEmpty()) {
            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
//            if (!matcher.matches()) {
            if (!matcher.matches() || !model.isEmailAvailable(email)) {
                error = 1;
                errorMsg += "Email ID is invalid or already in use, please try another one!\n";
            }
        }

        //Password Validate
        if(password.length() < 8){
            error = 1;
            errorMsg += "Password Needs to Be at-least 8 characters or more\n";
        }

        //Phone Validate
        if(phoneNo.length() < 10){
            error = 1;
            errorMsg += "Phone Number Should be Starting from 0 and of 10 Digits\n";
        }

        //Password Validate
        if(address.length() < 8){
            error = 1;
            errorMsg += "Please Enter Complete Address\n";
        }

        if(gender.length() < 4){
            error = 1;
            errorMsg += "Please Enter Gender as Male/Female/TransGender\n";
        }

        if(dob.length() < 11 && !validateDOB(dob)){
            error = 1;
            errorMsg += "A Valid Date is of dd/MM/yyyy format.\n Example: 21/04/2020\n";
        }

        if(nationality.length() < 4){
            error = 1;
            errorMsg += "Please enter Complete Country Name\n";
        }


        try{
            if(Double.parseDouble(income) < 0){
                error = 1;
                errorMsg += "Income should be 0 or more then that!\n";
            }
        }catch(NumberFormatException exp){
            error = 1;
            errorMsg += "Income should be a number and more then 0!\n";
        }

        if(String.valueOf(type).isEmpty()){
            error = 1;
            errorMsg += "An Error Occured in the System!\n";
        }

        if(error == 0) {
            String custID = model.registerCustomer(name, email, password, phoneNo, address, gender, dob, nationality, income, type);

            if (!custID.isEmpty() || custID != null) {
                System.out.println("\u001B[32m" + "Registration Successful with Customer ID: CUST-" + custID + "\nYou could proceed to login now!\u001B[0m");
                return true;
            }
        }else{
            throw new UserException("The following error(s) occurred:\n" +errorMsg);
//            System.out.println("\u001B[31m" + "The following error(s) occurred:\n" +errorMsg + "\u001B[0m");
//            return false;
        }

        return false;
    }

    /**
     * Additional Validation Methods
     * @source https://beginnersbook.com/2013/05/java-date-format-validation/
     * @return true/false
     */
    public static boolean validateDOB(String strDate)
    {
        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
        sdfrmt.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try
        {
            Date javaDate = sdfrmt.parse(strDate);
//		        System.out.println(strDate+" is valid date format");
        }
        /* Date format is invalid */
        catch (ParseException e)
        {
//            System.out.println(strDate+" is Invalid Date format\nA Valid Date is of dd/MM/yyyy format.\n Example: 21/04/2020");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }

    public static boolean validateJavaTime(String strDate)
    {
        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        //SimpleDateFormat sdfrmt = new SimpleDateFormat("hh:mm");
        //sdfrmt.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try
        {
            LocalTime t = LocalTime.parse(strDate);
//		        System.out.println(strDate+" is valid date format");
        }
        /* Date format is invalid */
        catch (DateTimeParseException e)
        {
//            System.out.println(strDate+" is Invalid Date format\nA Valid Date is of dd/MM/yyyy format.\n Example: 21/04/2020");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }

}