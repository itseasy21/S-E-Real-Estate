package main;

import controller.*;
import config.*;
import model.Customer;
import model.SERException;
import model.mainModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class mainLauncher {

    private static mainModel model;

    public static void main(String[] args) throws IOException, SERException {
        model = new mainModel(); //Initialize Model
        model.syncDB(); //Synchronize the data into memory from DB
        renderMainMenu(model); //Render Main Menu
    }

    private static void renderMainMenu(mainModel model) throws IOException, SERException {
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
                break;
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

    private static void register(Scanner scanChoice,mainModel model) {
        System.out.println("Glad you decided to register with Us!\nPlease Select Your Account Type:");
        String input;
        int registerChoice = 0;
        do {
            for(int i = 0; i < CustomerType.values().length; i++){
                System.out.println(i+1 + ". " + CustomerType.values()[i]);
            }
            System.out.println("Please press q to quit.");
            input = scanChoice.nextLine();
            if(input.equals("q"))
                break;
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
        String[] requiredDetails = {"First Name", "Last Name", "Email", "Password", "Phone No.", "Address", "Gender", "DOB", "Nationality", "Income", "Interested Suburbs"};
        ArrayList<String> details = new ArrayList<String>();
        for(String ask : requiredDetails){
            System.out.print(ask + ": ");
            details.add(scanChoice.nextLine());
        }
        System.out.println(details);
        reg registerNew = new C

    }

    private static void login(Scanner scanChoice,mainModel model) throws IOException, SERException {
        System.out.println("Please Enter Your Registered Email ID and Password to Login! Press");
        System.out.print("Email: ");
        String email = scanChoice.nextLine();
        System.out.print("Password: ");
        String password = scanChoice.nextLine();
        if(!email.isEmpty() || !password.isEmpty()){
            loginController lg = new loginController();
            lg.initializeModel("",model);
            boolean loggedin = lg.loginHandler(email,password);
            if(!loggedin){
                throw new SERException("Username You Entered is not Valid");
//                renderMainMenu(model);
            }else{
                renderLoggedInMenu(email,scanChoice,model);
            }
        }else{
            System.out.println("You Left ID Pass Blank, Redirecting Back to Menu");
            renderMainMenu(model);
        }
    }

    private static void renderLoggedInMenu(String username, Scanner scanChoice,mainModel model){
        System.out.println("Welcome "+username+" to S&E Real Estate");
        int choiceLoggedInMenu = 0;
        do {
            System.out.println("Pick an option. 1 2 or 3.");
            System.out.println("1. " + menuOptions.LOGIN);
            System.out.println("2. " + menuOptions.REGISTER);
            System.out.println("Please press q to quit.");

            String input = scanChoice.nextLine();
            if(input.equals("q") || input.isEmpty())
                break;
            else {
                try {
                    choiceLoggedInMenu = Integer.parseInt(input.trim());
                }catch(NumberFormatException exp){
                    System.out.println("Invalid Input! Please retry.");
                }
            }

            //Handling Choice
            switch(choiceLoggedInMenu){
//                case 1:
//                    login(scanChoice, model);
//                    break;
//                case 2:
//                    register();
//                    break;
            }

        } while (choiceLoggedInMenu < 1 || choiceLoggedInMenu > 3);

    }

}
