package controller;

import config.CustomerType;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registerController extends baseController{

    public boolean registerhandler(String name, String email, String password, String phoneNo, String address, String gender, String dob, String nationality, String income, CustomerType type) {

        int error = 0;
        String errorMsg = "";

        //all checks here and pass to model for add
        //Name check
        if(name.length() < 3){
            error = 1;
            errorMsg += "Name is Invalid, Please Enter Full Name\n";
        }
        //Email check
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            error = 1;
            errorMsg += "Email ID is invalid\n";
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

        }

        //gender, String dob, String nationality, String income, CustomerType type

        if(model.isValidUser(check_user, check_pass)){
            return true;
        }else{
            return false;
        }

    }

}