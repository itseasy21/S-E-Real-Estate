package main;

import config.CustomerType;
import config.menuOptions;
import controller.loginController;
import controller.registerController;
import model.*;

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

        System.out.println("Welcome "+ currentUser.getName() +" to S&E Real Estate " + currentUser.getType());
        int choiceLoggedInMenu = 0;

        //All LoggedInMenus
        //    VENDOR,
        //    LANDLORD,
        //    BUYER,
        //    RENTER
        String[] vendorLandlordMenu = {"ADD PROPERTY", "LIST PROPERTIES", "LOGOUT"};
        String[] buyerRenterMenu = {"SEARCH PROPERTY", "LIST PREFERENCES","UPDATE SUBURB PREFERENCE" ,"LOGOUT"};
        String[] menu = new String[4];
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
                    case 1 -> addProperty(currentUser,scanChoice, model);//Add Property TODO
                    case 2 -> System.out.println("LIST PROPERTY");//List Property TODO
                    case 3 -> renderMainMenu(model); //Logout
                }
            }else{
                switch (choiceLoggedInMenu) {
                    case 1 -> System.out.println("test1");//Search Property TODO
                    case 2 -> listSuburb(currentUser, scanChoice, model);//Update Suburb Pref TODO
                    case 3 -> updateSuburb(currentUser, scanChoice, model);//Update Suburb Pref TODO
                    case 4 -> renderMainMenu(model); //Logout
                }
            }

        } while (choiceLoggedInMenu < 1 || choiceLoggedInMenu > menu.length);

    }

    private static void listSuburb(Customer currentUser, Scanner scanChoice, mainModel model) {
        ArrayList<String> suburbs = currentUser.getInterestedSuburbs();
        int loopCounter = 1;
        for (String suburb : suburbs){
            System.out.println(loopCounter + ". " + suburb);
            loopCounter++;
        }
    }

    private static void updateSuburb(Customer currentUser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException {

        do {
            System.out.print("Suburb Name to Add (press q to go Back): ");
            String input = scanChoice.nextLine();
            if(input.equals("q") || input.isEmpty())
                renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
            else {
                currentUser.addSuburb(input);
            }
        }while(true);

    }

    public static void addProperty(Customer currentUser, Scanner scanChoice, mainModel model) {

        System.out.println("Please Enter the property details !");

        System.out.println("Property Name:");
        String pName = scanChoice.nextLine();
        while (true){
            if(pName.length()<2){
                System.out.println("Please enter a valid Property Name");
                pName = scanChoice.nextLine();
            }else{
                break;
            }
        }

        System.out.println("Select a Property Type");
        for (int i = 0; i < PropertyType.values().length; i++) {
            System.out.println((i + 1) + "." + PropertyType.values()[i]);
        }

        int choice = scanChoice.nextInt();

        while(true){
            if(choice > PropertyType.values().length){
                System.out.println("Invalid Property Type! Try again");
                choice = scanChoice.nextInt();
            }else{
                break;
            }
        }

        PropertyType pType = PropertyType.values()[choice];

        System.out.println("Address:");
        String pAddress = scanChoice.nextLine();
        while (true){
            if(pAddress.length()<4){
                System.out.println("Please enter a complete Property Address");
                pAddress = scanChoice.nextLine();
            }else{
                break;
            }
        }


        System.out.println("Minimum Price");
        double minPrice = scanChoice.nextDouble();
        while (true){
          if(minPrice<1000){
              System.out.println("Minimum pricing too low for the property ! Try again");
              minPrice = scanChoice.nextDouble();
          }else{
              break;
          }
        }
        System.out.println("Suburb:");
        String suburb = scanChoice.nextLine();
        while (true){
            if(suburb.length()<4){
                System.out.println("Please enter the complete suburb Name");
                suburb = scanChoice.nextLine();
            }else{
                break;
            }
        }
        System.out.println("Bedroom/Bathroom/Parking count : eg.(3/2/1) ");
        String count = scanChoice.nextLine();
        while(true){
         String[] values = count.split("/");
         try {
             for (String value : values) {

                 Integer.parseInt(value);
             }
             break;
         }catch (NumberFormatException e){
             System.out.println(" Invalid values please try again");
             count = scanChoice.nextLine();
         }
        }

        System.out.println("Listed Pricing");
        double pricing  = scanChoice.nextDouble();
        while (true){
            if(pricing<1000){
                System.out.println("Listed pricing too low for the property ! Try again");
                pricing = scanChoice.nextDouble();
            }else{
                break;
            }
        }

        System.out.println("Select Property Category");
        for(int i = 0; i < PropertyCategory.values().length; i++){
            System.out.println((i+1)+ "." + PropertyCategory.values()[i]);
        }
        choice = scanChoice.nextInt();
        while(true){
            if(choice > PropertyCategory.values().length){
                System.out.println("Invalid Property Type! Try again");
                choice = scanChoice.nextInt();
            }else{
                break;
            }
        }
        PropertyCategory pCategory = PropertyCategory.values()[choice];
        model.addProperty(new Property(pName,pType,pAddress,minPrice,suburb,Integer.parseInt(count.split("/")[0]),Integer.parseInt(count.split("/")[1]),Integer.parseInt(count.split("/")[2]),pricing,pCategory));



    }

}
