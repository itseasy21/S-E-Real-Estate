package main;

import config.*;
import controller.auctionController;
import controller.loginController;
import controller.registerController;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class mainLauncher {

    private static mainModel model;

    public static void main(String[] args) throws IOException, SERException, ParseException, SQLException, PropertyException, UserException, MyException, ApplicationException {
        model = new mainModel(); //Initialize Model
        model.syncDB(); //Synchronize the data into memory from DB
        renderMainMenu(model); //Render Main Menu
    }

    public static void quitApp(mainModel model) throws SQLException {
        model.savetoDB();
        System.exit(0);
    }

    private static void renderMainMenu(mainModel model) throws IOException, SERException, ParseException, SQLException, PropertyException, UserException, MyException, ApplicationException {
        int choiceMainMenu = 0;
        Scanner scanChoice = new Scanner(System.in);

        do {
            System.out.println("Welcome to S&E Real Estate!");
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
                case 1 -> login(scanChoice, 0, model);
                case 2 -> register(scanChoice, model);
                case 3 -> login(scanChoice, 1, model);

            }

        } while (choiceMainMenu < 1 || choiceMainMenu > menuOptions.values().length);

        scanChoice.close();
    }

    private static void register(Scanner scanChoice,mainModel model) throws ParseException, IOException, SERException, SQLException, PropertyException, UserException, MyException, ApplicationException {
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

        Boolean registered = false;
        try {
            registered = registerNew.registerhandler(details.get(0), details.get(1), details.get(2), details.get(3), details.get(4), details.get(5), details.get(6), details.get(7), details.get(8), type);
        }
        catch (UserException e){
            System.out.println(e.toString());
        }

        if(!registered)
            register(scanChoice, model);
        else
            renderMainMenu(model);

    }

    private static void login(Scanner scanChoice,int loginType, mainModel model) throws IOException, SERException, ParseException, SQLException, PropertyException, UserException, MyException, ApplicationException {
        System.out.println("Please Enter Your Registered Email ID and Password to Login! ");
        System.out.print("Email: ");
        String email = scanChoice.nextLine();
        System.out.print("Password: ");
        String password = scanChoice.nextLine();
        if(!email.isEmpty() || !password.isEmpty()){
            loginController lg = new loginController();
            lg.initializeModel("",model);
            boolean loggedin;
            if(loginType == 0)
                loggedin = lg.loginHandler(email,password);
            else
                loggedin = lg.adminloginHandler(email,password);

            if(!loggedin){
//                throw new SERException("Username You Entered is not Valid");
                System.out.println("\u001B[31m" + "Invalid Credentials! Please Retry" + "\u001B[0m");
                login(scanChoice, loginType, model);
            }else{
                if(loginType == 0)
                    renderLoggedInMenu(email,scanChoice,model);
                else
                    renderAdminLoggedInMenu(email,scanChoice,model);

            }
        }else{
            System.out.println("You Left ID Pass Blank, Redirecting Back to Menu");
            renderMainMenu(model);
        }
    }

    private static void renderAdminLoggedInMenu(String email, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {
        Employee currentEmp = (Employee) model.getUserByUsername(email);

        System.out.println("Welcome "+ currentEmp.getName() +" to S&E Real Estate" + currentEmp.getEmpRole());

        int choiceLoggedInMenu = 0;
        //All LoggedInMenus
        String[] salesPropertyManager = {"LIST PROPERTIES", "VIEW APPLICATIONS","CREATE INSPECTION TIMES","LIST INSPECTION","CANCELL INSPECTION","UPDATE COMPLETED INSPECTION", "LOGOUT"}; //Sales & Property Menu
        String[] branchAdmin = {"LIST PROPERTIES", "ADD EMPLOYEE TO PROPERTY","RUN PAYROLL","LIST INSPECTIONS" ,"LOGOUT"}; //Branch Admin Menu
        String[] menu = new String[6];

        if(currentEmp.getEmpRole().equals(EmployeeType.PropertyManager) || currentEmp.getEmpRole().equals(EmployeeType.SalesConsultant)){
            menu = salesPropertyManager;
        }else if(currentEmp.getEmpRole().equals(EmployeeType.BranchAdmin)){
            menu = branchAdmin;
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
            if(currentEmp.getEmpRole().equals(EmployeeType.PropertyManager) || currentEmp.getEmpRole().equals(EmployeeType.SalesConsultant)) {
                switch (choiceLoggedInMenu) {
                    case 1 -> listProperty(currentEmp, scanChoice, model);
                    case 2 -> viewApplications(currentEmp, scanChoice, model); //Create Inspection Time
                    case 3 -> createInspection(currentEmp, scanChoice, model); //Create Inspection Time
                    case 4 -> listInspection(currentEmp, scanChoice, model);//List inspections
                    case 5 -> cancellInspection(currentEmp, scanChoice, model);//cancel inspection
                    case 6 -> completeInspection(currentEmp, scanChoice, model);//complete inspection
                    case 7 -> renderMainMenu(model); //Logout
                }
            }else{
                switch (choiceLoggedInMenu) {
                    case 1 -> listProperty(currentEmp, scanChoice, model);
                    case 2 -> addEmpToProperty(email, scanChoice, model);
                    case 3 -> handlePayroll(email,scanChoice,model);
                    case 4 -> listInspectionAdmin(currentEmp, scanChoice, model);
                    case 5 -> renderMainMenu(model); //Logout
                }
            }

        } while (choiceLoggedInMenu < 1 || choiceLoggedInMenu > menu.length);

    }

    private static void handlePayroll(String email, Scanner scanChoice, mainModel model) throws MyException, SERException, SQLException, ParseException, IOException, UserException, PropertyException, ApplicationException {
        System.out.println("Enter the Employee id ");
        System.out.println(model.getEmpKeySets());
        String empid = scanChoice.nextLine();
        if (!model.checkEmployeeExists(empid)) {
            System.out.println("Invaild Employee id");
            renderAdminLoggedInMenu(email, scanChoice, model);
        } else {

            Payroll payroll = new Payroll(empid, model.getEmployeeHour(empid), 40, model.EmployeeSalary(empid));
            System.out.println("Select the operation");
            int choice = 0;
            while (choice != 5) {
                System.out.println("1.Update salary \n 2.Update Hour\n 3.Current salary\n4.Adding Bonus to Salary\n5.Quit");
                Scanner sc = new Scanner(System.in);
                choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new value for salary");
                        double salary = sc.nextDouble();
                        model.updateSalary(empid, salary, payroll);
                        //renderAdminLoggedInMenu(email, scanChoice, model);
                        break;
                    case 2:
                        System.out.println("Enter the new value for hour");
                        double hour = sc.nextDouble();
                        model.updateHour(empid, hour, payroll);
                       // renderAdminLoggedInMenu(email, scanChoice, model);
                        break;
                    case 3:
                        double salary1=model.getSalary(empid, payroll);
                        System.out.println(salary1);

                       // renderAdminLoggedInMenu(email, scanChoice, model);
                        break;
                    case 4:
                        double bonus = 100;
                        model.calBonus(empid,payroll,bonus);


                }

            }

            renderAdminLoggedInMenu(email, scanChoice, model);
        }
    }

    private static void createInspection(Employee currentEmp, Scanner scanChoice, mainModel model) throws PropertyException, SERException, SQLException, ParseException, IOException, UserException, MyException, ApplicationException {

        System.out.println("CREATE INSPECTION");
        System.out.println("-----------------");
        System.out.println("available properties:\n" + model.getPropertyDB());
        model.listassignedProperties(currentEmp);
        int pID;
        Property property = null;
        boolean exit = false;
        boolean loop = false;
        // do{
        System.out.println("Please Enter the property details!");
        System.out.println("Property ID:");
        pID = scanChoice.nextInt();
            /*
            try {
                property = model.listProperty(pID);
            }
            catch (PropertyException e) {
                //Invalid ID
            }

            if(property == null || !property.isEmployeeAssigned()) {
                exit = true;
                loop = false;
                System.out.println("The Property Is Either Not Yet Available or Invalid, Please try Again Later");
            }*/
        //}while (loop==false);
        //if (exit = false) {
        int count = 5;
        String dateSlot = "";
        String date;
        String date4 = "", date3 = "", date2, date1;
        loop = false;

        //while (exit = false) {
        System.out.println("Enter the Dates to conduct the inspection on:");

        do {
            do {
                System.out.println("Enter " + count + " more dates");
                date = scanChoice.nextLine();
                while (true) {
                    if (date.length() < 11 && !controller.registerController.validateDate(date)) {
                        String errorMsg = "A Valid Date is of dd/MM/yyyy format.\n Example: 21/04/2020\n";
                        System.out.println("Please enter a valid Date" + errorMsg);
                        date = scanChoice.nextLine();
                    } else {
                        if (count == 5) {
                            dateSlot = date;
                            loop = false;
                        }

                        if (count < 5) {
                            String[] split = dateSlot.split(";");
                            for (int i = 0; i < split.length; i++) {
                                if (date.equals(split[i])) {
                                    System.out.println("Date already entered!!..Add a new date..");
                                    loop = true;
                                } else {
                                    loop = false;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } while (loop == true);
            if (count < 5) {
                dateSlot = dateSlot + ";" + date;
            }
            count--;
        }
        while (count >= 1);

        count = 5;
        String timeSlot = "";
        String time;
        System.out.println("Enter the timeslots for the inspection");
        loop = false;
        do {
            do {
                System.out.println("Enter " + count + " more time slot:");
                time = scanChoice.nextLine();
                while (true) {
                    if (time.length() < 11 && !controller.registerController.validateJavaTime(time)) {
                        String errorMsg = "A Valid Time is of hh:mm format.\n Example: 13:30\n";
                        System.out.println("Please enter a valid Time" + errorMsg);
                        time = scanChoice.nextLine();
                    } else {
                        if (count == 5) {
                            timeSlot = time;
                            loop = false;
                        }

                        if (count < 5) {
                            String[] split = timeSlot.split(";");
                            for (int i = 0; i < split.length; i++) {
                                if (time.equals(split[i])) {
                                    System.out.println("Time already entered!!..Add a new time..");
                                    loop = true;
                                } else {
                                    loop = false;
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            } while (loop == true);
            if (count < 5) {
                timeSlot = timeSlot + ";" + time;
            }
            count--;
        } while (count >= 1);

        model.createInspection(pID, currentEmp, dateSlot, timeSlot, "Created");
    //}
            renderAdminLoggedInMenu(currentEmp.getEmail(), scanChoice, model);
        /*}
        else{
            renderAdminLoggedInMenu(currentEmp.getEmail(), scanChoice, model);
        }*/
    }



    private static void cancellInspection(Employee currentEmp, Scanner scanChoice, mainModel model) throws PropertyException, MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException {

        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("CANCEL INSPECTION");
        System.out.println("-----------------");
        model.listInspectionID(currentEmp);
            System.out.println("Enter the inspection ID to cancel.");
            String id=scanChoice.nextLine();

        model.cancellInspection(id,currentEmp);
        renderAdminLoggedInMenu(currentEmp.getEmail(), scanChoice, model);

    }

    private static void cancellInspection(Customer currentUser, Scanner scanChoice, mainModel model) throws PropertyException, MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException {
        System.out.println("CANCEL INSPECTION");
        System.out.println("-----------------");
        model.listInspectionIDCustomer(currentUser);
        System.out.println("Enter the inspection ID to cancel.");
        String id=scanChoice.nextLine();

        model.cancellInspectionCustomer(id,currentUser);
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);

    }

    private static void bookInspection(Customer currentUser,Scanner scanChoice,mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, PropertyException, ApplicationException {
        System.out.println("BOOK INSPECTION");
        System.out.println("-----------------");
        String id="";
        do {
            model.listInspectionBook();
            System.out.println("Enter the inspection ID you want to book. (Example: INS1)");
            id = scanChoice.nextLine();
            model.validateInspection(id);
        }while(model.validateInspection(id)==true);

        //setting date
        String date,time = null;
        boolean flag=true;
        int option = 0;
        do {
            model.availableDates(id);
            option = scanChoice.nextInt();
           if(option==1||option==2||option==3||option==4||option==5) {
               flag=false;
           }
           else{
               System.out.println("select one of the options(1 to 5)");
               flag=false;
           }
        }while(flag=false);
        date=model.validateDate(option,id);

        //setting time
        do {
            model.availableTimes(id);
            option = scanChoice.nextInt();
            if(option==1||option==2||option==3||option==4||option==5) {
                flag=false;
            }
            else{
                System.out.println("select one of the options(1 to 5)");
                flag=false;
            }
        }while(flag=false);
        time=model.validateTime(option,id);

        model.bookInspection(currentUser,id,date,time,"Scheduled");
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }

    private static void listInspection(User currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, PropertyException, ApplicationException {
       if(currentUser instanceof Customer){
           model.listInspectionCustomer(currentUser);
           renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
       }
       else if(currentUser instanceof Employee){
           model.listInspectionEmployee(currentUser);
           renderAdminLoggedInMenu(currentUser.getEmail(), scanChoice, model);
       }
    }

    private static void listInspectionAdmin(User currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, PropertyException, ApplicationException {
         if(currentUser instanceof Employee){
            model.listInspection();
            renderAdminLoggedInMenu(currentUser.getEmail(), scanChoice, model);
        }
    }

    private static void completeInspection(User currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, PropertyException, ApplicationException {
        if(currentUser instanceof Customer){
            model.listInspectionCustomer(currentUser);
        }
        else if(currentUser instanceof Employee){
            model.listInspectionEmployee(currentUser);
        }
        System.out.println("enter inspection id to update to completed");
        String id=scanChoice.nextLine();
        if(currentUser instanceof Customer){
            model.completeInspection(currentUser,id);
            renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
        }
        else if(currentUser instanceof Employee){
            model.completeInspection(currentUser,id);
            renderAdminLoggedInMenu(currentUser.getEmail(), scanChoice, model);
        }
    }

    private static void renderLoggedInMenu(String username, Scanner scanChoice,mainModel model) throws ParseException, IOException, SERException, SQLException, PropertyException, UserException, MyException, ApplicationException {

        Customer currentUser = (Customer) model.getUserByUsername(username);

        System.out.println("Welcome "+ currentUser.getName() +" to S&E Real Estate");
        int choiceLoggedInMenu = 0;

        //All LoggedInMenus
        String[] vendorMenu = {"ADD PROPERTY", "LIST PROPERTIES","VIEW PROPERTY DETAILS","AUCTION", "LOGOUT"};//Vendor Menu
        String[] landLordMenu = {"ADD PROPERTY", "LIST PROPERTIES","VIEW PROPERTY DETAILS", "LOGOUT"};//Landlord Menu
        String[] RenterMenu = {"SEARCH PROPERTY", "APPLY FOR PROPERTY", "VIEW APPLICATIONS" ,"BOOK INSPECTION", "LIST BOOKED INSPECTION", "LIST PREFERENCES","UPDATE SUBURB PREFERENCE", "CANCEL INSPECTION", "LOGOUT"}; //Buyer & Renter Menu
        String[] buyerMenu = {"SEARCH PROPERTY", "LIST AUCTIONS", "SUBMIT BID","VIEW APPLICATIONS" ,"BOOK INSPECTION", "LIST BOOKED INSPECTION", "LIST PREFERENCES","UPDATE SUBURB PREFERENCE", "CANCEL INSPECTION", "LOGOUT"}; //Buyer & Renter Menu
        String[] menu = new String[4];
        if(currentUser.getType().equals(CustomerType.VENDOR)){
            menu = vendorMenu;
        }else if(currentUser.getType().equals(CustomerType.LANDLORD)){
            menu  = landLordMenu;
        }
        else if(currentUser.getType().equals(CustomerType.RENTER)){
            menu = RenterMenu;
        }else if(currentUser.getType().equals(CustomerType.BUYER)){
            menu = buyerMenu;
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
            if(currentUser.getType().equals(CustomerType.LANDLORD)) {
                switch (choiceLoggedInMenu) {
                    case 1 -> addProperty(currentUser,scanChoice, model);
                    case 2 -> listProperty(currentUser, scanChoice, model);
                    case 3 -> viewPropertyDetails(currentUser.getEmail(), scanChoice, model);
                    case 4 -> renderMainMenu(model); //Logout
                }
            }else if(currentUser.getType().equals(CustomerType.VENDOR)){
                switch (choiceLoggedInMenu) {
                    case 1 -> addProperty(currentUser,scanChoice, model);
                    case 2 -> listProperty(currentUser, scanChoice, model);
                    case 3 -> viewPropertyDetails(currentUser.getEmail(), scanChoice, model);
                    case 4 -> createAuction(currentUser, scanChoice, model);
                    case 5 -> renderMainMenu(model); //Logout
                }
            }else if(currentUser.getType().equals(CustomerType.RENTER)){
                switch (choiceLoggedInMenu) {
                    case 1 -> searchProperty(currentUser, scanChoice, model);//Search Property
                    case 2 -> rentBuyProperty(currentUser, scanChoice, model);//Rent or Buy Property
                    case 3 -> viewApplications(currentUser, scanChoice, model);//Rent or Buy Property
                    case 4 -> bookInspection(currentUser, scanChoice, model);//book inspection
                    case 5 -> listInspection(currentUser, scanChoice, model); //List inspection
                    case 6 -> listSuburb(currentUser, scanChoice, model);//List Suburb Pref
                    case 7 -> updateSuburb(currentUser, scanChoice, model);//Update Suburb Pref
                    case 8 -> cancellInspection(currentUser, scanChoice, model);
                    case 9 -> renderMainMenu(model); //Logout
                }
            }else if(currentUser.getType().equals(CustomerType.BUYER)){
                switch (choiceLoggedInMenu) {
                    case 1 -> searchProperty(currentUser, scanChoice, model);//Search Property
                    case 2 -> listAuction(currentUser, scanChoice, model);//Rent or Buy Property
                    case 3 -> submitBid(currentUser, scanChoice, model);//Rent or Buy Property
                    case 4 -> viewApplications(currentUser, scanChoice, model);//Rent or Buy Property
                    case 5 -> bookInspection(currentUser, scanChoice, model);//book inspection
                    case 6 -> listInspection(currentUser, scanChoice, model); //List inspection
                    case 7 -> listSuburb(currentUser, scanChoice, model);//List Suburb Pref
                    case 8 -> updateSuburb(currentUser, scanChoice, model);//Update Suburb Pref
                    case 9 -> cancellInspection(currentUser, scanChoice, model);
                    case 10 -> renderMainMenu(model); //Logout
                }
            } else{
                System.out.println("ERROR");
            }

        } while (choiceLoggedInMenu < 1 || choiceLoggedInMenu > menu.length);

    }

    private static void searchProperty(Customer currentUser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {
        System.out.println("Search Property by");
        System.out.println("1. Suburb");
        System.out.println("2. Price");
        System.out.println("3. Property Category: (Flat/House/Studio/Unit/TownHouse)");
        System.out.println("4. Property Name");
        System.out.println("5. Property Type: (Sale/Rent) ");
        int choice = scanChoice.nextInt();
        if (choice < 0 || choice > 5){
            System.out.println("Invalid option!");
            return;
        }else {
            switch (choice) {
                case 1:
                    System.out.println("Enter Suburb Name");
                    scanChoice.nextLine();
                    String suburb = scanChoice.nextLine();
                    model.searchPropertyByName("Name", suburb);
                    break;
                case 2:
                    System.out.println("Enter Min Price ");
                    double minPrice = scanChoice.nextDouble();
                    System.out.println("Enter Max Price");
                    double maxPrice = scanChoice.nextDouble();
                    model.searchPropertyByPrice(minPrice,maxPrice);
                    break;
                case 3:
                    System.out.println("Select Property Category");
                    System.out.println("1. Unit");
                    System.out.println("2. House");
                    System.out.println("3. Flat");
                    System.out.println("4. Studio");
                    System.out.println("5. Townhouse");
                    int category = scanChoice.nextInt();
                    model.searchPropertyByCategory(PropertyCategory.values()[category-1]);
                    break;
                case 4:
                    System.out.println("Enter Property Name");
                    scanChoice.nextLine();
                    String name = scanChoice.nextLine();
                    model.searchPropertyByName("Name", name);
                    break;
                case 5:
                    System.out.println("Select Property Type");
                    System.out.println("1. Sale");
                    System.out.println("2. Rent");
                    int type = scanChoice.nextInt();
                    if(type == 1){
                        model.listPropertiesForSale();
                    }else if(type == 2){
                        model.listPropertiesForRent();
                    }else{
                        System.out.println("Invalid Option");
                    }
                    break;

            }
        }
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }
    private static void createAuction(Customer currentUser,Scanner scanChoice,mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException, PropertyException {
        auctionController auctionValidator = new auctionController();
        auctionValidator.initializeModel("",model);

        System.out.println("Enter date for auction: eg. dd/MM/yyyy");
        String auctionDate = scanChoice.nextLine();
        while (true){
            if(auctionDate.length()<10 && !auctionValidator.validateJavaDate(auctionDate)){
                System.out.println("Please enter a valid Date");
                auctionDate = scanChoice.nextLine();
            }else{
                break;
            }
        }

        System.out.println("Enter the property ID for Auction.");
        String propID = scanChoice.nextLine();
        while (true){
            if(propID.length()>2){
                System.out.println("Please enter a valid Property ID");
                propID = scanChoice.nextLine();
            }else{
                break;
            }
        }

        Property property = null;

        try {
            property = model.listProperty(Integer.parseInt(propID));
        }
        catch (PropertyException e) {
            //Invalid ID
        }

        if(property == null || !property.isEmployeeAssigned() || !property.isPropertyTypeSale() || !property.getAvailability().equals(PropertyState.AVAILABLE)){
            System.out.println("The Property Is Either Not Yet Available or Invalid, Please try Again Later");
        }else{
            model.createAuction(auctionDate, property);
        }

        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);

    }

    private static void listSuburb(Customer currentUser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {
        ArrayList<String> suburbs = currentUser.getInterestedSuburbs();
        int loopCounter = 1;
        for (String suburb : suburbs){
            System.out.println(loopCounter + ". " + suburb);
            loopCounter++;
        }
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }

    private static void updateSuburb(Customer currentUser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {

        do {
            System.out.print("Suburb Name to Add (press q to go Back): ");
            String input = scanChoice.nextLine();
            if(input.equals("q"))
                renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
            else {
                model.addSuburb(currentUser, input);
            }
        }while(true);
    }

    private static void addProperty(Customer currentUser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {

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

        PropertyType pType = PropertyType.values()[choice-1];

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
        PropertyCategory pCategory = PropertyCategory.values()[choice-1];
        Property property = new Property(pName,pType,pAddress,minPrice,suburb,Integer.parseInt(count.split("/")[0]),Integer.parseInt(count.split("/")[1]),Integer.parseInt(count.split("/")[2]),pricing,pCategory);
        property.setCustomerId(currentUser.getId());
        model.addProperty(property);
        System.out.println("Property has been successfully added!");
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }

    private static void listProperty(User someuser, Scanner scanChoice, mainModel model) throws SERException, SQLException, ParseException, IOException, PropertyException, UserException, MyException, ApplicationException {

        if(someuser instanceof Customer) {
            Customer currentUser = (Customer) someuser;
            if (currentUser.getType().equals(CustomerType.VENDOR)) {
                model.listPropertiesForSale();
            }
            if (currentUser.getType().equals(CustomerType.LANDLORD)) {
                System.out.println("Select type of property");
                for (int i = 0; i < PropertyType.values().length; i++) {
                    System.out.println((i + 1) + "." + PropertyType.values()[i]);
                }
                int choice = scanChoice.nextInt();
                while (true) {
                    if (choice > PropertyType.values().length) {
                        System.out.println("Invalid option ! Please try again");
                        choice = scanChoice.nextInt();
                    } else {

                        break;
                    }

                }
                switch (choice) {
                    case 1 -> model.listPropertiesForSale();
                    case 2 -> model.listPropertiesForRent();
                }
                renderLoggedInMenu(someuser.getEmail(), scanChoice, model);
            }

        }else if(someuser instanceof Employee) {
            Employee currentUser = (Employee) someuser;
            if (currentUser.getEmpType().equals(EmployeeType.BranchAdmin)) {
                model.listAvailableProperties();
            }else if((currentUser.getEmpType().equals(EmployeeType.SalesConsultant))){
                model.listPropertiesForSale();
            }else{
                model.listProperties();
            }
            renderAdminLoggedInMenu(someuser.getEmail(), scanChoice, model);
        }

    }

    private static void listProperties(String email, Scanner scanChoice, mainModel model) throws PropertyException, SERException, SQLException, ParseException, IOException, UserException, MyException, ApplicationException {
        model.listAvailableProperties();
        renderAdminLoggedInMenu(email,scanChoice,model);

    }

    private static void viewPropertyDetails(String email, Scanner scanChoice, mainModel model) throws PropertyException, SERException, SQLException, ParseException, IOException, UserException, MyException, ApplicationException {

            System.out.println("Select the Property id");
            int choice = scanChoice.nextInt();
            Property property = model.listProperty(choice);
            System.out.println(property.toString());
            renderLoggedInMenu(email, scanChoice, model);

        }

    private static void addEmpToProperty(String email, Scanner scanChoice, mainModel model) throws PropertyException, SERException, SQLException, ParseException, IOException, UserException, MyException, ApplicationException {
            while (true) {
                System.out.println("Select the Property id");
                System.out.println(model.getPropertyDB());
                if(model.getPropertyDB().size() == 0){
                    break;
                }
                int choice = scanChoice.nextInt();
                scanChoice.nextLine();
                Property property = model.listProperty(choice);
                Set<String> employees = new HashSet<>();
                if(property.isPropertyTypeSale()) {
                    for(String employee : model.getEmpKeySets()){
                        EmployeeType empRole = model.getEmployeeRole(employee);
                        if(empRole.equals(EmployeeType.SalesConsultant)){
                            employees.add(employee);
                        }

                    }
                }else{
                    for(String employee : model.getEmpKeySets()){
                        EmployeeType empRole = model.getEmployeeRole(employee);
                        if(empRole.equals(EmployeeType.PropertyManager)){
                            employees.add(employee);
                        }
                    }
                }
                if (property.isEmployeeAssigned()) {
                    System.out.println("Employee has been assigned for the selected property! would you like to re-assign ? (y/n) ");
                    String response = scanChoice.nextLine();
                    if (response.equalsIgnoreCase("Y")) {
                        System.out.println("Enter Employee Id:");
                        System.out.println(employees);
                        String empId = scanChoice.nextLine();
                        if (model.checkEmployeeExists(empId) && employees.contains(empId)) {
                            EmployeeType empRole = model.getEmployeeRole(empId);
                            System.out.println("Emp Role is "+ empRole);
                            if (empRole != null) {
                                property.setEmployeeId(empId);
                                property.setEmpRole(empRole);
                                break;
                            }
                        } else {
                            System.out.println("Invalid Employee ID");
                        }
                }else{
                        continue;
                    }

                } else if(!property.isEmployeeAssigned()) {
                    System.out.println("Enter Employee Id:");
                    System.out.println(employees);
                    String empId = scanChoice.nextLine();
                    if (model.checkEmployeeExists(empId) && employees.contains(empId)) {
                        EmployeeType empRole = model.getEmployeeRole(empId);
                        System.out.println("Emp Role is "+ empRole);
                        if (empRole != null) {
                            property.setEmployeeId(empId);
                            property.setEmpRole(empRole);
                            break;
                        }
                    } else {
                        System.out.println("Invalid Employee id");
                    }

                }
            }
            System.out.println("Employee Assigned to the Property !");
            renderAdminLoggedInMenu(email, scanChoice, model);
        }

    private static void rentBuyProperty(Customer currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, PropertyException, ApplicationException {

        System.out.println("Enter a Property ID you wish to apply for:");
        String propID = scanChoice.nextLine();
        while (true){
            if(propID.length()>2){
                System.out.println("Please enter a valid Property ID");
                propID = scanChoice.nextLine();
            }else{
                break;
            }
        }

        Property property = null;

        try {
            property = model.listProperty(Integer.parseInt(propID));
        }
        catch (PropertyException e) {
            //Invalid ID
        }

        if(property == null || !property.isEmployeeAssigned()){
            System.out.println("The Property Is Either Not Yet Available or Invalid, Please try Again Later");
        }else {

            //Print Property Details
            System.out.println(property.toString());

            if (currentUser.getType().equals(CustomerType.RENTER) && property.isPropertyTypeRental()) {
                //If the current user is a renter

                //Enter the Rent
                System.out.print("Enter the Weekly Rent:");
                String tmpWeeklyRent = scanChoice.nextLine();
                int weeklyRent;
                while (true){
                    try{
                        weeklyRent = Integer.parseInt(tmpWeeklyRent);
                        if(weeklyRent < 0){
                            System.out.println("Rent should be greater then 0");
                            System.out.print("Enter the Weekly Rent:");
                            tmpWeeklyRent = scanChoice.nextLine();
                        }else{
                            break;
                        }
                    }catch(Exception e){
                        System.out.println("Rent should be a number");
                        System.out.print("Enter the Weekly Rent:");
                        tmpWeeklyRent = scanChoice.nextLine();
                    }
                }
                System.out.println("Applying with the weekly rent " + weeklyRent);

                try{
                    model.rentApplication(property, currentUser, weeklyRent);
                }
                catch (ApplicationException e){
                    System.out.println(e.toString());
                }

            } else if (currentUser.getType().equals(CustomerType.BUYER) && property.isPropertyTypeSale()) {

            } else {
                System.out.println("An Error Occured!");
            }
        }
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }


    private static void viewApplications(User currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException, PropertyException {

        model.viewApplicationsByUser(currentUser);

        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);

    }

    private static void submitBid(Customer currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException, PropertyException {

        System.out.println("Enter the Auction ID");
        String auctID = scanChoice.nextLine();
        while (true){
            if(auctID.length()<2 && model.isValidAuction(auctID)){
                System.out.println("Please enter a valid Auction ID");
                auctID = scanChoice.nextLine();
            }else{
                break;
            }
        }

        System.out.println("Auction Details:\n" + model.getAuctionDetailsByID(auctID));

        System.out.println("Enter the bid");
        double bid = scanChoice.nextDouble();
        while (true){
            if(bid < model.getNextValidBid(auctID)){
                System.out.println("Please enter a valid Bid Amount");
                bid = scanChoice.nextDouble();
            }else{
                break;
            }
        }

        model.addBid(auctID, bid, currentUser);

        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }

    private static void listAuction(Customer currentUser, Scanner scanChoice, mainModel model) throws MyException, ParseException, IOException, SERException, SQLException, UserException, ApplicationException, PropertyException {
        model.listAuctions();
        renderLoggedInMenu(currentUser.getEmail(), scanChoice, model);
    }
}
