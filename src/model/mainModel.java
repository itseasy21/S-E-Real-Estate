package model;
import config.CustomerType;
import config.EmployeeType;
import config.PropertyCategory;
import config.PropertyType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class mainModel {

    public static ArrayList<User> userDB = new ArrayList<>();
    public static ArrayList<Inspection> inspectionDB = new ArrayList<Inspection>();
    public static ArrayList<Application> applicationDB = new ArrayList<Application>();
    public static HashMap<Integer, Property> propertyDB;
    Connection conn;
    Statement stmt;

    public mainModel() {
        dbConnect dbHandler = new dbConnect();
        this.conn = dbHandler.getConn();
        this.propertyDB = new HashMap<>();
    }

    public void syncDB() throws PropertyException {
        //synchronize data in memory with data from sqliteDB

        //Populating UserDB with Customers
        try {
            stmt = this.conn.createStatement();
            String findPosts = "select * from Customer;";
            ResultSet rsCustomer = stmt.executeQuery(findPosts);
            while(rsCustomer.next()){

                CustomerType thisCustomerType= null;
                if(rsCustomer.getString("type").equals("VENDOR")){
                    thisCustomerType = CustomerType.VENDOR;
                }else if(rsCustomer.getString("type").equals("LANDLORD")){
                    thisCustomerType = CustomerType.LANDLORD;
                }else if(rsCustomer.getString("type").equals("BUYER")){
                    thisCustomerType = CustomerType.BUYER;
                }else if(rsCustomer.getString("type").equals("RENTER")){
                    thisCustomerType = CustomerType.RENTER;
                }

                    Customer newCustomer = new Customer(
                            rsCustomer.getString("customer_id"),
                            rsCustomer.getString("email"),
                            rsCustomer.getString("password"),
                            rsCustomer.getString("name"),
                            rsCustomer.getString("address"),
                            rsCustomer.getString("phone_number"),
                            rsCustomer.getString("DOB"),
                            rsCustomer.getString("gender"),
                            rsCustomer.getString("nationality"),
                            rsCustomer.getDouble("income"),
                            thisCustomerType);
                    userDB.add(newCustomer);

                //add interested suburbs
                if(rsCustomer.getString("interested_suburb") != null) {
                    String[] suburbs = rsCustomer.getString("interested_suburb").split(",");
                    for (String oneSub : suburbs) {
                        newCustomer.addSuburb(oneSub);
                    }
                }

            }
            rsCustomer.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populating UserDB with Employees
        try {
            Statement stmt1 = this.conn.createStatement();
            String findEmp = "select * from Employee;";
            ResultSet rsEmp = stmt1.executeQuery(findEmp);
            while(rsEmp.next()) {

                EmployeeType thisType = null;
                EmployeeType thisRole = null;

                if (rsEmp.getString("employee_type").equals("FullTIme")) {
                    thisType = EmployeeType.FullTIme;
                } else if (rsEmp.getString("employee_type").equals("PartTime")) {
                    thisType = EmployeeType.PartTime;
                }

                switch (rsEmp.getString("employee_role")) {
                    case "BranchAdmin" -> thisRole = EmployeeType.BranchAdmin;
                    case "SalesConsultant" -> thisRole = EmployeeType.SalesConsultant;
                    case "PropertyManager" -> thisRole = EmployeeType.PropertyManager;
                }

                Employee newEmp = new Employee(
                        rsEmp.getString("employee_id"),
                        rsEmp.getString("email"),
                        rsEmp.getString("password"),
                        rsEmp.getString("name"),
                        rsEmp.getString("address"),
                        rsEmp.getString("phone_number"),
                        rsEmp.getString("DOB"),
                        rsEmp.getString("gender"),
                        thisType,
                        thisRole,
                        rsEmp.getDouble("salary"),
                        rsEmp.getDouble("hours")
                    );
                userDB.add(newEmp);
            }
                rsEmp.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

//        userDB.add(new Employee("admin@branch.com","pa33w0rd","Shubham",
//                "673 La Trobe","401717860",(new Date()).toString(),"Male",
//                EmployeeType.FullTIme, EmployeeType.SalesConsultant, 45000,20));
//        userDB.add(new Employee("s3801882@student.rmit.edu.au","sa52521","Shubham",
//                "673 La Trobe","401717860",(new Date()).toString(),"Male",
//                EmployeeType.PartTime,EmployeeType.BranchAdmin, 22000,10));

        //TODO: Populating PropertyDB with Properties
        Property p1 = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Thornbury", 2,3,3,234_000.00, PropertyCategory.Flat);
        p1.setEmployeeId("EMP01");
        addProperty(p1);
//        addProperty(new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Thornbury", 2,3,3,234_000.00, PropertyCategory.Flat));
        addProperty(new Property( "Jersey parade", PropertyType.Rent,"102 Plenty road", 26000,"Bundoora", 2,1,2,324_000.00, PropertyCategory.House));
        addProperty(new Property( "Residency Towers", PropertyType.Sale,"2 Moreland road", 26000,"Bundoora", 2,2,3,426_000.00, PropertyCategory.Unit));
        addProperty(new Property( "Spring Waters", PropertyType.Sale,"200 Clifton Hill ", 26000,"Preston", 4,3,2,204_000.00, PropertyCategory.Studio));
        addProperty(new Property( "Salt Waters", PropertyType.Sale,"18 ivanhoe crescent", 26000,"Mill Park", 2,3,1,403_000.00, PropertyCategory.House));
        addProperty(new Property( "Jelly Craig", PropertyType.Rent,"5 Flinders street", 26000,"Reservoir", 1,3,3,304_000.00, PropertyCategory.Townhouse));
        addProperty(new Property( "France City", PropertyType.Rent,"10 Koornang road", 26000,"Carnegie", 3,3,2,236_000.00, PropertyCategory.Flat));

    }

    public void savetoDB() throws SQLException {
        //Save userDB to Database

        stmt = this.conn.createStatement();
        stmt.executeUpdate("DELETE FROM Customer;");
        stmt.executeUpdate("DELETE FROM Employee;");

        for(User user : userDB){
            if(user instanceof Customer){ //If user is a customer
                try {
                    Customer thisCustomer = (Customer) user;
                    String intSuburb = String.join(",", thisCustomer.getInterestedSuburbs()); // a,b
                    String insertQuery = "insert into Customer(customer_id, name, email, password, phone_number, address, gender, DOB, nationality, income, type, interested_suburb)" +
                            "VALUES('" + thisCustomer.getId() + "', '" + thisCustomer.getName() + "', " +
                            "'" + thisCustomer.getEmail() + "', '" + thisCustomer.getPassword() + "', '" + thisCustomer.getPhoneNo() + "'" +
                            ", '" + thisCustomer.getAddress() + "', '" + thisCustomer.getGender() + "', '" + thisCustomer.getDob() + "'" +
                            ", '" + thisCustomer.getNationality() + "', " + thisCustomer.getIncome() + ", '" + thisCustomer.getType().toString() + "', '" + intSuburb + "')";

                    stmt.executeUpdate(insertQuery);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                //If user is a employee
                try {
                    Employee thisEmp = (Employee) user;
                    String insertQuery = "insert into Employee(employee_id, name, email, password, phone_number, address, gender, DOB, salary, employee_type, employee_role)" +
                            "VALUES('" + thisEmp.getId() + "', '" + thisEmp.getName() + "', " +
                            "'" + thisEmp.getEmail() + "', '" + thisEmp.getPassword() + "', '" + thisEmp.getPhoneNo() + "'" +
                            ", '" + thisEmp.getAddress() + "', '" + thisEmp.getGender() + "', '" + thisEmp.getDob() + "'" +
                            ", '" + thisEmp.getSalary() + "', '" + thisEmp.getEmpType().toString() + "', '" + thisEmp.getEmpRole().toString() + "')";

                    stmt.executeUpdate(insertQuery);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public boolean isEmailAvailable(String email){
        for(User user : userDB){
            if(user instanceof Customer) {
                if(user.getEmail().equals(email)){
                    return false;
                }
            }
        }
        return true;
    }

    public User getUserByUsername(String email){

        for(User user : userDB){
            if ( user.getEmail().equals(email) ) {
                return user;
            }
        }
        return null;
    }


    public boolean isValidUser(String check_user, String check_pass, int userType) {

        for(User user : userDB){
                if ( user.getEmail().equals(check_user) && user.getPassword().equals(check_pass) ) {
                    if(userType == 0 && user instanceof Customer)
                        return true;
                    else if(userType == 1 && user instanceof Employee)
                        return true;
                }
        }

//        try {
//            Statement stmt = this.conn.createStatement();
//            String loginQuery = "select count(*) from Customer where email = '"+ check_user +"' and password='"+ check_pass +"'";
//            ResultSet rsLogin = stmt.executeQuery(loginQuery);
//            rsLogin.next();
//            int count = rsLogin.getInt(1);
//            rsLogin.close();
//
//            if(count == 1){
//                return true;
//            }else{
//                return false;
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return false;
    }

    public boolean checkEmployeeExists(String id){
        for(User user : userDB){
            if(user instanceof Employee) {
                if ((user.getId()).equalsIgnoreCase(id)) {
                    return true;
                }
            }
        }
    return false;
    }
    public EmployeeType getEmployeeRole(String id){
        for(User user : userDB) {
            if (user instanceof Employee) {
                if ((user.getId()).equalsIgnoreCase(id)) {
                    return ((Employee) user).getEmpRole();
                }
            }
        }
        return null;
    }
    public double EmployeeSalary(String id) {
        for(User user : userDB){
        if(user instanceof Employee) {
            if ((user.getId()).equalsIgnoreCase(id)) {
                return ((Employee) user).getSalary();
            }
        }

    }
    return 0;

    }
    public double getEmployeeHour(String id) {
        for(User user : userDB){
        if(user instanceof Employee) {
            if ((user.getId()).equalsIgnoreCase(id)) {
                System.out.println(((Employee) user).getWorkingHours());
                return ((Employee) user).getWorkingHours();
            }
        }

    }
        return 0;
    }

//Property Class Functionalities
    public void addProperty(Property property) throws PropertyException{
        int propertyId = propertyDB.size() + 1;
        if(property.getPropertyId() == 0)
            property.setPropertyId(propertyId);
        propertyDB.put(propertyId,property);
    }

    public Property listProperty(int propertyId) throws PropertyException {
        if(propertyDB.containsKey(propertyId)){
            return propertyDB.get(propertyId);
        } else {
            throw new PropertyException("Invalid property ID");
        }

    }

    public void listAvailableProperties(){
       for(int property : propertyDB.keySet()){
           System.out.println(propertyDB.get(property).toString());
       }
    }
    public void searchPropertyByName(String name, String filter){
       switch (name) {
           case "Suburb":
                try {
                   System.out.println("Showing properties by " + name);
                    propertyDB.values()
                           .stream()
                           .filter(e -> (e.getSuburb().equalsIgnoreCase(filter) && e.isEmployeeAssigned()))
                        .forEach(p -> System.out.println(p.toString()));
                } catch (NullPointerException e) {
                   System.out.println("None");
                return;
                }
           break;
           case "Name":
               try {
                   System.out.println("Showing properties by " + name);
                   propertyDB.values()
                           .stream()
                           .filter(e -> (e.getPropertyName().equalsIgnoreCase(filter)) && e.isEmployeeAssigned())
                           .forEach(p -> System.out.println(p.toString()));
               } catch (NullPointerException e) {
                   System.out.println("None");
                   return;
               }
               break;
       }
    }
    public void searchPropertyByPrice(double minPrice, double maxPrice){
        try {
            System.out.println("Showing properties between " + minPrice +"- "+maxPrice);
            propertyDB.values()
                    .stream()
                    .filter(e -> (e.getPropertyPrice() >= minPrice) && (e.getPropertyPrice()<= maxPrice) && e.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch (NullPointerException e) {
            System.out.println("None");
            return;
        }
    }
    public void searchPropertyByCategory(PropertyCategory propertyCategory){
        try {
            System.out.println("Showing properties of the Category" + propertyCategory);
            propertyDB.values()
                    .stream()
                    .filter(e -> (e.getPropertyCategory().equals(propertyCategory)) && e.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch (NullPointerException e) {
            System.out.println("None");
            return;
        }
    }
    public void listPropertiesForSale() {
        try {
            System.out.println(" ");
            System.out.println("***********Properties For Sale*********");
            System.out.println(" ");
            //lists only properties that are assigned with single employee
            propertyDB.values()
                    .stream()
                    .filter(e -> e.isPropertyTypeSale() && e.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
           return;
        }
    }

    public void listPropertiesForRent() {
        try {
            System.out.println(" ");
            System.out.println("******Properties For Rent*******:");
            System.out.println(" ");
            propertyDB.values()
                    .stream()
                    .filter(i -> i.isPropertyTypeRental() && i.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
            return;
        }
    }
    public void listUnassignedProperties() {
        try {
            System.out.println(" ");
            System.out.println("Unassigned Properties");
            propertyDB.values()
                    .stream()
                    .filter(i -> !i.isEmployeeAssigned())
                    .forEach(p -> System.out.println(p.toString()));
        } catch(NullPointerException e){
            System.out.println("None");
            return;
        }
    }
    public void listProperties(){
        listPropertiesForSale();
        listPropertiesForRent();
        listUnassignedProperties();
    }

   public boolean isPropertyDBEmpty(){
        return propertyDB.isEmpty();
   }


   public int getPropertyDBSize(){
        return propertyDB.size();
   }
    public Set<Integer> getPropertyDB() {
        return propertyDB.keySet();
    }
    public Set<String> getEmpKeySets(){
        Set<String> empId = new HashSet<String>();

        for(User user : userDB ){
            if(user instanceof Employee){
                empId.add(user.getId());
            }
        }
        return empId;
    }

    public String registerCustomer(String name, String email, String password, String phoneNo, String address, String gender, String dob, String nationality, String income, CustomerType type) throws ParseException {
        String customerID = null;
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
        sdfrmt.setLenient(false);
        Date dobUpdated = sdfrmt.parse(dob);
        Customer newCustomer = new Customer(email, password, name, address, phoneNo, dobUpdated.toString(), gender, nationality, Double.parseDouble(income), type);
        if(!String.valueOf(newCustomer.getId()).isEmpty()){
            customerID = String.valueOf(newCustomer.getId());
            userDB.add(newCustomer);
        }
        return customerID;
    }

    public void addSuburb(Customer currentUser, String suburb){
        int error = 0;
        if(suburb.isEmpty()){
            error = 1;
        }else if(suburb.contains(",")){
            error = 1;
        }else if(suburb.length() < 4){
            error = 1;
        }

        if(error == 0)
            currentUser.addSuburb(suburb);
        else
            System.out.println("An Error Occurred While Adding, Please Retry!");
    }

    public void listInspectionCustomer(User currentCustomer){
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("LIST INSPECTION");
        System.out.println("---------------");
        if(currentCustomer instanceof Customer) {
            for (Inspection a : inspectionDB) {
                if(currentCustomer.getId().equals(a.getcId())) {
                    System.out.println(a.showDetails());
                    System.out.println("*************************************");
                }
            }
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void listInspectionIDCustomer(User currentCustomer){
        System.out.println("---------------------------------------------------------------------------------");
        if(currentCustomer instanceof Customer) {
            for (Inspection a : inspectionDB) {
                if(currentCustomer.getId().equals(a.getcId())) {
                    System.out.println(a.getId());
                }
            }
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void createInspection(int propertyID, Employee currentEmployee, String getdateslot, String timeslots1, String status) throws PropertyException, UserException {

        Property thisProp = null;

        for (Map.Entry<Integer, Property> set : propertyDB.entrySet()) {
            if(set.getValue().getPropertyId() == propertyID){
                thisProp = set.getValue();
            }
        }

        if(thisProp != null && thisProp.isEmployeeAssigned()){
            if(thisProp.getEmployeeId().equals(currentEmployee.getId())) {
                //InspectionController inspect = new InspectionController();
                Inspection tempIns = new Inspection(thisProp.getPropertyId(), thisProp.getEmployeeId(), getdateslot, timeslots1, status);
                inspectionDB.add(tempIns);
                System.out.println("Inspection has been created sucessfully!");
            }else{
                System.out.println("You are not assigned to this property!");
            }
        }
        else if(thisProp == null){
            System.out.println("Property does not exist..Enter another property!!");
        }
        else if(!thisProp.isEmployeeAssigned()){
            System.out.println("No employee is assigned to this property yet! Please contact Branch Admin.");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void bookInspection(Customer currentUser, String id, String date, String time, String status) throws PropertyException {
        if(currentUser.getType().equals(CustomerType.BUYER) || currentUser.getType().equals(CustomerType.RENTER)) {
            for(Inspection a:inspectionDB) {
                if (a.getId().equals(id)) {
                    if (a.getStatus().equalsIgnoreCase("created")) {
                        a.setCid(currentUser.getId());
                        a.setDate(date);
                        a.setTime(time);
                        a.setStatus(status);

                        System.out.println("Inspection has been created sucessfully!");
                    }
                    else{
                        System.out.println("Inspection is not available for bookings");
                    }
                }
            }
        }
        else{
            System.out.println("Inspection is not available for bookings");
        }
    }

    public void availableDates(String id){
        System.out.println("Available dates for inspections are:");
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                System.out.println( a.getId());
                String dates=a.getdatesl();
                String[] split=dates.split(";");
                String date1=split[0];
                String date2=split[1];
                String date3=split[2];
                String date4=split[3];
                String date5=split[4];
                System.out.println("1. "+date1);
                System.out.println("2. "+date2);
                System.out.println("3. "+date3);
                System.out.println("4. "+date4);
                System.out.println("5. "+date5);
                System.out.println("Select an opption");
            }
        }
    }

    public void availableTimes(String id){
        System.out.println("Available dates for inspections are:");
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                String times=a.gettimesl();
                String[] split=times.split(";");
                String date1=split[0];
                String date2=split[1];
                String date3=split[2];
                String date4=split[3];
                String date5=split[4];
                System.out.println("1. "+date1);
                System.out.println("2. "+date2);
                System.out.println("3. "+date3);
                System.out.println("4. "+date4);
                System.out.println("5. "+date5);
                System.out.println("Select an opption");
            }
        }
    }

    public String validateDate(int dateOption,String id){
        String date="";
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                String dates=a.getdatesl();
                String[] split=dates.split(";");
                if(dateOption==1){
                    date=split[0];
                }else if(dateOption==2){
                    date=split[1];
                }else if(dateOption==3){
                    date=split[2];
                }else if(dateOption==4){
                    date=split[3];
                }else if(dateOption==5){
                    date=split[4];
                }
            }
        }
        return date;
    }

    public String validateTime(int timeOption,String id) {
        String time = "";
        for (Inspection a : inspectionDB) {
            if (a.getId().equals(id)) {
                String times = a.gettimesl();
                String[] split = times.split(";");
                if (timeOption == 1) {
                    time = split[0];
                } else if (timeOption == 2) {
                    time = split[1];
                } else if (timeOption == 3) {
                    time = split[2];
                } else if (timeOption == 4) {
                    time = split[3];
                } else if (timeOption == 5) {
                    time = split[4];
                }
            }
        }
        return time;
    }

    public void listInspectionBook(){
        System.out.println("Available inspections:");
        for(Inspection a:inspectionDB){
            if(a.getStatus().equals("Created")){
                System.out.println("Inspecion ID:" +a.getId() +"\tProperty ID:" +a.getpId());
            }
            if(a.getId().equals(null)){
                System.out.println("No inpections are available");
            }
        }
    }

    public boolean validateInspection(String id){
        Boolean flag=false;
        for(Inspection a:inspectionDB){
            if(a.getStatus().equals(id)){
                flag=true;
            }
            else{
                flag=false;
            }
        }
        return flag;
    }

    public void listInspection(){
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("LIST INSPECTION");
        System.out.println("---------------");
        for(Inspection a:inspectionDB){
            System.out.println(a.showDetails());
            System.out.println("*************************************");
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void listInspectionID(Employee currentEmp){
        System.out.println("Available inspections are:");
        for(Inspection a:inspectionDB){
            if(a.geteId().equals(currentEmp.getId())) {
                System.out.println(a.getId());
            }
        }
    }

    public void cancellInspection(String id,Employee currentEmployee) throws PropertyException {
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                if(a.geteId().equals(currentEmployee.getId())) {
                    a.setStatus("Cancelled");
                    a.setDate(null);
                    a.setTime(null);
                    a.setTimeSlot(null);
                    a.setdatesSlot(null);
                    System.out.println("Inspection cancelled sucessfully");
                    System.out.println(a.showDetails());
                }
            }
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void cancellInspectionCustomer(String id,Customer currentUser) throws PropertyException {
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                if(a.getcId().equals(currentUser.getId())) {
                    a.setStatus("cancelled");
                    a.setDate(null);
                    a.setTime(null);
                    a.setTimeSlot(null);
                    a.setdatesSlot(null);
                    System.out.println("Inspection cancelled sucessfully");
                    System.out.println(a.showDetails());
                }
            }
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    public void updateSalary(String empid, double salary, Payroll payroll) throws MyException, UserException {
        payroll.setSalary(salary);
        for(User user : userDB){
            if(user instanceof Employee) {
                if ((user.getId()).equalsIgnoreCase(empid)) {
                    ((Employee) user).setSalary(salary);
                }

                }
            }

      //  System.out.println("hello"+payroll.getSalary());
    }

    public void updateHour(String empid, double hour, Payroll payroll)throws MyException ,UserException {
        payroll.setHours(hour);
       // System.out.println("hello"+payroll.getHours());
        for(User user : userDB){
            if(user instanceof Employee) {
                if ((user.getId()).equalsIgnoreCase(empid)) {
                    ((Employee) user).setWorkingHours(hour);
                }

            }
        }

    }

    public void getSalary(String empid, Payroll payroll) {
        payroll.getSalary();
        //System.out.println("hello"+payroll.getSalary());
    }

    public boolean isInspectionDone(Customer customer, Property thisProp){
        for(Inspection i : inspectionDB){
            if(i.getcId().equals(customer.getId()) && i.getpId() == thisProp.getPropertyId() && i.getStatus().equalsIgnoreCase("Completed")){
                return true;
            }
        }
        return false;
    }

    public void rentApplication(Property selectedProperty, Customer applyingUser, double weeklyRent) throws ApplicationException {

        int error = 0;
        String msg = "";

        if(!applyingUser.getType().equals(CustomerType.RENTER)){
            error = 1;
            msg += "You are not allowed to rent.\n";
        }

        if(isInspectionDone(applyingUser, selectedProperty)){
            error = 1;
            msg += "You need to inspect the property before applying.\n";
        }

        if(hasAlreadyApplied(applyingUser, selectedProperty)){
            error = 1;
            msg += "You have already applied for this property.\n";
        }

        if(weeklyRent < selectedProperty.getMinPrice()){
            error = 1;
            msg += "The weekly rent should be more then the minimum specified rent.\n";
        }

        if(error == 0){
            Application newRental = new RentalApplication(selectedProperty.getEmployeeId(), applyingUser.getId(), selectedProperty, weeklyRent);
            System.out.println("Your Application for Rental of " + selectedProperty.getPropertyName() + " is submitted with ID: " + newRental.getId() + "\n" +
                    "The Property Manager will be in touch with you!");
            applicationDB.add(newRental);
        }else{
            throw new ApplicationException(msg);
        }

    }

    public boolean hasAlreadyApplied(Customer currentUser, Property thisProp){
        for(Application app : applicationDB){
            if(currentUser.getId().equals(app.getCustID())) {
                return true;
            }
        }
        return false;
    }

    public void viewApplicationsByUser(Customer currentUser) {
        for(Application app : applicationDB){
            int totalApp = 0;
            String applications = "";
            if(currentUser.getId().equals(app.getCustID())){
                applications += app.getDetails()+"\n-----------------------------\n";
                totalApp++;
            }

            System.out.println("You have overall " + totalApp + " applications, please see the details below.\n\n" + applications);

        }
    }

    public void viewApplicationsByProperty(int propertID) {
        for(Application app : applicationDB){
            int totalApp = 0;
            String applications = "";
            if(propertID == app.getProperty().getPropertyId()){
                applications += app.getDetails()+"\n-----------------------------\n";
                totalApp++;
            }

            System.out.println("There are overall " + totalApp + " applications for property, please see the details below.\n\n" + applications);

        }
    }

    public void saleApplication(Property selectedProperty, Customer applyingUser, double price, String saletype) throws ApplicationException {

        int error = 0;
        String msg = "";

        if(!applyingUser.getType().equals(CustomerType.BUYER)){
            error = 1;
            msg += "You are not allowed to buy property.\n";
        }

        if(isInspectionDone(applyingUser, selectedProperty)){
            error = 1;
            msg += "You need to inspect the property before applying.\n";
        }

        if(hasAlreadyApplied(applyingUser, selectedProperty)){
            error = 1;
            msg += "You have already applied for this property.\n";
        }

//        if(weeklyRent < selectedProperty.getMinPrice()){
//            error = 1;
//            msg += "The weekly rent should be more then the minimum specified rent.\n";
//        }

        if(error == 0){
            Application newSale = new SalesApplication(selectedProperty.getEmployeeId(), applyingUser.getId(), selectedProperty, price, saletype);
            System.out.println("Your Application for Purchase of " + selectedProperty.getPropertyName() + " is submitted with ID: " + newSale.getId() + "\n" +
                    "The Property Manager will be in touch with you!");
            applicationDB.add(newSale);
        }else{
            throw new ApplicationException(msg);
        }

    }
}
