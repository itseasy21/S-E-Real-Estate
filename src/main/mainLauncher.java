package main;

import controller.*;
import config.*;
import model.Customer;
import model.SERException;
import model.mainModel;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainLauncher {

    private static mainModel model;

    public static void main(String[] args) throws IOException, SERException, ParseException, SQLException {
        model = new mainModel(); //Initialize Model
        model.syncDB(); //Synchronize the data into memory from DB
        renderMainMenu(model); //Render Main Menu
    }

    public static void quitApp(mainModel model) throws SQLException {
        model.savetoDB();
        System.exit(0);
    }

    private static void renderMainMenu(mainModel model) throws IOException, SERException, ParseException, SQLException {
        int choiceMainMenu = 0;
        Scanner scanChoice = new Scanner(System.in);

        do {
            System.out.println("Pick an option.");
            for(int i = 0; i < menuOptions.values().length; i++){
                System.out.println(i+1 + ". " + menuOptions.values()[i]);
            }
            System.out.println("Please press q to quit.");

            String input = scanChoice.nextLine();
            if(input.equals("q") || input.isEmpty())
                quitApp(model);
            else {
                try {
                    choiceMainMenu = Integer.parseInt(input.trim());
                }catch(NumberFormatException exp){
                    System.out.println("Invalid Input! Please retry.");
                }
            }

            //Handling Choice
            switch (choiceMainMenu) {
                case 1 -> login(scanChoice, model);
                case 2 -> register(scanChoice, model);
            }

        } while (choiceMainMenu < 1 || choiceMainMenu > menuOptions.values().length);

        scanChoice.close();
    }

    private static void register(Scanner scanChoice,mainModel model) throws ParseException, IOException, SERException, SQLException {
        System.out.println("Glad you decided to register with Us!\nPlease Select Your Account Type:");
        String input;
        int registerChoice = 0;
        do {
            for(int i = 0; i < CustomerType.values().length; i++){
                System.out.println(i+1 + ". " + CustomerType.values()[i]);
            }
            System.out.println("Please press q to quit.");
            input = scanChoice.nextLine();
            if(input.equals("q") || input.trim().isEmpty())
                renderMainMenu(model);
            else {
                try {
                    registerChoice = Integer.parseInt(input.trim());
                }catch(Exception exp){
                    System.out.println("Invalid Input! Please retry.");
                }
            }

        }while(registerChoice < 1 || registerChoice > CustomerType.values().length);
        CustomerType type = CustomerType.values()[registerChoice - 1];
        System.out.println("Registering as "+type+"\nPlease Enter Details Below");

        //Start taking all the values to register in the portal
        String[] requiredDetails = {"Name", "Email", "Password", "Phone No.", "Address", "Gender (Male/Female/TransGender)", "DOB (eg: 21/11/1991)", "Nationality", "Income"};
        ArrayList<String> details = new ArrayList<String>();
        for(String ask : requiredDetails){
            System.out.print(ask + ": ");
            String userInput = "";
            do {
                userInput = scanChoice.nextLine();
            }while(userInput.isEmpty());
            details.add(userInput);
        }

        registerController registerNew = new registerController();
        registerNew.initializeModel("", model);
        Boolean registered = registerNew.registerhandler(details.get(0),details.get(1),details.get(2),details.get(3),details.get(4),details.get(5),details.get(6),details.get(7),details.get(8),type);
        if(!registered)
            register(scanChoice, model);
        else
            renderMainMenu(model);

    }

    private static void login(Scanner scanChoice,mainModel model) throws IOException, SERException, ParseException, SQLException {
        System.out.println("Please Enter Your Registered Email ID and Password to Login! ");
        System.out.print("Email: ");
        String email = scanChoice.nextLine();
        System.out.print("Password: ");
        String password = scanChoice.nextLine();
        if(!email.isEmpty() || !password.isEmpty()){
            loginController lg = new loginController();
            lg.initializeModel("",model);
            boolean loggedin = lg.loginHandler(email,password);
            if(!loggedin){
//                throw new SERException("Username You Entered is not Valid");
                System.out.println("\u001B[31m" + "Invalid Credentials! Please Retry" + "\u001B[0m");
                login(scanChoice, model);
            }else{
                renderLoggedInMenu(email,scanChoice,model);
            }
        }else{
            System.out.println("You Left ID Pass Blank, Redirecting Back to Menu");
            renderMainMenu(model);
        }
    }

    private static void renderLoggedInMenu(String username, Scanner scanChoice,mainModel model) throws ParseException, IOException, SERException, SQLException {

        Customer currentUser = (Customer) model.getUserByUsername(username);

        System.out.println("Welcome "+ currentUser.getName() +" to S&E Real Estate");
        int choiceLoggedInMenu = 0;

        //All LoggedInMenus
        //    VENDOR,
        //    LANDLORD,
        //    BUYER,
        //    RENTER
        String[] vendorLandlordMenu = {"ADD PROPERTY", "LIST PROPERTIES", "LOGOUT"};
        String[] buyerRenterMenu = {"SEARCH PROPERTY", "UPDATE SUBURB PREFERENCE" ,"LOGOUT"};
        String[] menu = new String[3];
        if(currentUser.getType().equals(CustomerType.LANDLORD) || currentUser.getType().equals(CustomerType.VENDOR)){
            menu = vendorLandlordMenu;
        }else if(currentUser.getType().equals(CustomerType.BUYER) || currentUser.getType().equals(CustomerType.RENTER)){
            menu = buyerRenterMenu;
        }

        do {
            System.out.println("Pick an option.");
            for(int i = 0; i < menu.length; i++){
                System.out.println(i+1 + ". " + menu[i]);
            }
            System.out.println("Please press q to quit.");

            String input = scanChoice.nextLine();
            if(input.equals("q") || input.isEmpty())
                renderMainMenu(model);
            else {
                try {
                    choiceLoggedInMenu = Integer.parseInt(input.trim());
                }catch(NumberFormatException exp){
                    System.out.println("Invalid Input! Please retry.");
                }
            }

            //Handling Choice
            if(currentUser.getType().equals(CustomerType.LANDLORD) || currentUser.getType().equals(CustomerType.VENDOR)) {
                switch (choiceLoggedInMenu) {
                    case 1 -> System.out.println("test");//Add Property TODO
                    case 2 -> System.out.println("test");//List Property TODO
                    case 3 -> renderMainMenu(model); //Logout
                }
            }else{
                switch (choiceLoggedInMenu) {
                    case 1 -> System.out.println("test1");//Search Property TODO
                    case 2 -> System.out.println("test2");//Update Suburb Pref TODO
                    case 3 -> renderMainMenu(model); //Logout
                }
            }

        } while (choiceLoggedInMenu < 1 || choiceLoggedInMenu > menu.length);

    }

}
