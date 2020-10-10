package controller;

public class loginController extends baseController{

    public boolean loginHandler(String check_user, String check_pass) {

        if(model.isValidUser(check_user, check_pass, 0)){
            return true;
        }else{
            return false;
        }

    }

    public boolean adminloginHandler(String check_user, String check_pass) {

        if(model.isValidUser(check_user, check_pass, 1)){
            return true;
        }else{
            return false;
        }

    }

}