package model;
import config.CustomerType;
import config.EmployeeType;
import controller.InspectionController;

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

//                    try {
//                        stmt = this.conn.createStatement();
//                        String findReplies = "select * from reply where postID = '" + rsCustomer.getString("id") + "';";
//                        ResultSet rsReply = stmt.executeQuery(findReplies);
//                        while (rsReply.next()) {
//                            newEvent.handleReply(new Reply(rsReply.getString("postID"), rsReply.getInt("value"), rsReply.getString("responderID")));
//                        }
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
            }
            rsCustomer.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //TODO: Populating UserDB with Employees
        userDB.add(new Employee("admin@branch.com","pa33w0rd","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                EmployeeType.FullTIme, EmployeeType.SalesConsultant, 45000,20));
        userDB.add(new Employee("s3801882@student.rmit.edu.au","sa52521","Shubham",
                "673 La Trobe","401717860",(new Date()).toString(),"Male",
                EmployeeType.PartTime,EmployeeType.BranchAdmin, 22000,10));

        //TODO: Populating PropertyDB with Properties
        addProperty(new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Thornbury", 2,3,3,234_000.00, PropertyCategory.Flat));
        addProperty(new Property( "Jersey parade", PropertyType.Rent,"102 Plenty road", 26000,"Bundoora", 2,1,2,324_000.00, PropertyCategory.House));
        addProperty(new Property( "Residency Towers", PropertyType.Sale,"2 Moreland road", 26000,"Coburg", 2,2,3,426_000.00, PropertyCategory.Unit));
        addProperty(new Property( "Spring Waters", PropertyType.Sale,"200 Clifton Hill ", 26000,"Preston", 4,3,2,204_000.00, PropertyCategory.Studio));
        addProperty(new Property( "Salt Waters", PropertyType.Sale,"18 ivanhoe crescent", 26000,"Mill Park", 2,3,1,403_000.00, PropertyCategory.House));
        addProperty(new Property( "Jelly Craig", PropertyType.Rent,"5 Flinders street", 26000,"Reservoir", 1,3,3,304_000.00, PropertyCategory.Townhouse));
        addProperty(new Property( "France City", PropertyType.Rent,"10 Koornang road", 26000,"Carnegie", 3,3,2,236_000.00, PropertyCategory.Flat));

        /*
        try {
            stmt = this.conn.createStatement();
            String findPosts = "select * from Employee;";
            ResultSet rsCustomer = stmt.executeQuery(findPosts);
            while(rsCustomer.next()){

                CustomerType thisCustomerType= null;
                if(rsCustomer.getString("type").equals("VENDOR")){
                    thisCustomerType = CustomerType.VENDOR;
                }else if(rsCustomer.getString("type").equals("LANDLORD")){
                    thisCustomerType = CustomerType.LANDLORD;
                }else if(rsCustomer.getString("type").equals("CUSTOMER")){
                    thisCustomerType = CustomerType.CUSTOMER;
                }

                Customer newCustomer = new Customer(
                        rsCustomer.getInt("customer_id "),
                        rsCustomer.getString("email"),
                        rsCustomer.getString("password"),
                        rsCustomer.getString("name"),
                        rsCustomer.getString("address"),
                        rsCustomer.getString("phoneNo"),
                        rsCustomer.getDate("DOB"),
                        rsCustomer.getString("gender"),
                        rsCustomer.getString("nationality"),
                        rsCustomer.getDouble("income"),
                        thisCustomerType);
                userDB.add(newCustomer);
            }
            rsCustomer.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
         */
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
                            "VALUES(" + thisCustomer.getId() + ", '" + thisCustomer.getName() + "', " +
                            "'" + thisCustomer.getEmail() + "', '" + thisCustomer.getPassword() + "', '" + thisCustomer.getPhoneNo() + "'" +
                            ", '" + thisCustomer.getAddress() + "', '" + thisCustomer.getGender() + "', '" + thisCustomer.getDob() + "'" +
                            ", '" + thisCustomer.getNationality() + "', " + thisCustomer.getIncome() + ", '" + thisCustomer.getType().toString() + "', '" + intSuburb + "')";
//                    System.out.println(insertQuery);
                    stmt.executeUpdate(insertQuery);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else{
                //TODO: If user is a employee
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
    public double EmployeeSalary(String id)
    { for(User user : userDB){
        if(user instanceof Employee) {
            if ((user.getId()).equalsIgnoreCase(id)) {
                return ((Employee) user).getSalary();
            }
        }

    }
    return 0;

    }
    public double getEmployeeHour(String id)
    { for(User user : userDB){
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

    public void createInspection(int propertyID, Employee currentEmployee, String getdateslot, String timeslots1, String status) throws PropertyException, UserException {
        Property thisProp = null;

        /*int propertyId = propertyDB.size() + 1;
        if(property.getPropertyId() == 0)
            property.setPropertyId(propertyId);
        propertyDB.put(propertyId,property);
        */



        for (Map.Entry<Integer, Property> set : propertyDB.entrySet()) {
            if(set.getValue().getPropertyId() == propertyID){
                thisProp = set.getValue();
            }
        }

        if(thisProp != null && thisProp.getEmployeeId().equals(currentEmployee.getId())&&thisProp.isEmployeeAssigned()){
            InspectionController inspect = new InspectionController();
            Inspection tempIns = new Inspection("3",thisProp.getPropertyId(), thisProp.getEmployeeId(), getdateslot,timeslots1 ,status);

            inspect.createInspection(thisProp, tempIns);
            inspectionDB.add(tempIns);
            for(Inspection a:inspectionDB){
                System.out.println(a.showDetails());
            }
        }
    }

    public void listInspection(){
        for(Inspection a:inspectionDB){
            System.out.println(a.showDetails());
        }
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
}
