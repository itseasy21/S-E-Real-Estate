package controller;

public class registerController extends baseController{

    public boolean registerhandler(String firstName, String lastName) {

        //all checks here and pass to model for add

        if(model.isValidUser(check_user, check_pass)){
            return true;
        }else{
            return false;
        }

    }

}