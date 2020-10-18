package model;
import config.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class mainModel {

    private static ArrayList<User> userDB = new ArrayList<>();
    private static ArrayList<Inspection> inspectionDB = new ArrayList<Inspection>();
    private static ArrayList<Application> applicationDB = new ArrayList<Application>();
    private static HashMap<Integer, Property> propertyDB =  new HashMap<>();;
    private static ArrayList<SalesMedium> contractDB = new ArrayList<>();
    private Connection conn;
    private Statement stmt;

    public mainModel() {
        dbConnect dbHandler = new dbConnect();
        this.conn = dbHandler.getConn();

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
        //popluating property DB
        try{
            Statement stmt1 = this.conn.createStatement();
            String findEmp = "select * from Property;";
            ResultSet rsProp = stmt1.executeQuery(findEmp);
            while(rsProp.next()) {
                double pricing = 0;
                PropertyType ptype = PropertyType.valueOf(rsProp.getString("type"));
                PropertyCategory pCategory = PropertyCategory.valueOf(rsProp.getString("property_category"));
                if(rsProp.getDouble("rental_price") == 0){
                    pricing = rsProp.getDouble("selling_price");
                }else{
                    pricing = rsProp.getDouble("rental_price");
                }
                Property property = new Property(
                        rsProp.getInt("property_id"),
                        rsProp.getString("name"),
                        ptype,
                        rsProp.getString("street_address"),
                        rsProp.getDouble("min_price"),
                        rsProp.getString("suburb"),
                        rsProp.getInt("bedroom_count"),
                        rsProp.getInt("bathroom_count"),
                        rsProp.getInt("parking_count"),
                        pricing,
                        pCategory
                        );

                if (!rsProp.getString("employee_id").equalsIgnoreCase("null")) {
                    property.setEmployeeId(rsProp.getString("employee_id"));
                }
                property.setCustomerId(rsProp.getString("customer_id"));

                property.setAvailability(PropertyState.valueOf(rsProp.getString("availability")));
                if (!rsProp.getString("emp_role").equalsIgnoreCase("null")) {
                    property.setEmpRole(EmployeeType.valueOf(rsProp.getString("emp_role")));
                }
                propertyDB.put(property.getPropertyId(),property);

            }
            rsProp.close();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }


//        userDB.add(new Employee("amanda@mail.comm","11111111","Amanda",
//                "673 La Trobe","401717860",(new Date()).toString(),"Female",
//                EmployeeType.FullTIme, EmployeeType.PropertyManager, 25000,20));
//        userDB.add(new Employee("paul@mail.comm","11111111","Paul",
//                "673 La Trobe","401717860",(new Date()).toString(),"Male",
//                EmployeeType.PartTime, EmployeeType.PropertyManager, 30000,10));
//        userDB.add(new Employee("john@gmail.com","11111111","John",
//                "673 La Trobe","401717860",(new Date()).toString(),"Male",
//                EmployeeType.PartTime,EmployeeType.SalesConsultant, 29000,25));

        //TODO: Populating PropertyDB with Properties
/*       Property p1 = new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Thornbury", 2,3,3,234_000.00, PropertyCategory.Flat);
        p1.setEmployeeId("EMP01");
        addProperty(p1);
//        addProperty(new Property( "Green Brigade", PropertyType.Rent,"1216 coorkston road", 26000,"Thornbury", 2,3,3,234_000.00, PropertyCategory.Flat));
        addProperty(new Property( "Jersey parade", PropertyType.Rent,"102 Plenty road", 26000,"Bundoora", 2,1,2,324_000.00, PropertyCategory.House));
        addProperty(new Property( "Residency Towers", PropertyType.Sale,"2 Moreland road", 26000,"Bundoora", 2,2,3,426_000.00, PropertyCategory.Unit));
        addProperty(new Property( "Spring Waters", PropertyType.Sale,"200 Clifton Hill ", 26000,"Preston", 4,3,2,204_000.00, PropertyCategory.Studio));
        addProperty(new Property( "Salt Waters", PropertyType.Sale,"18 ivanhoe crescent", 26000,"Mill Park", 2,3,1,403_000.00, PropertyCategory.House));
        addProperty(new Property( "Jelly Craig", PropertyType.Rent,"5 Flinders street", 26000,"Reservoir", 1,3,3,304_000.00, PropertyCategory.Townhouse));
        addProperty(new Property( "France City", PropertyType.Rent,"10 Koornang road", 26000,"Carnegie", 3,3,2,236_000.00, PropertyCategory.Flat));
*/
    }

    public void savetoDB() throws SQLException {
        //Save userDB to Database
        stmt = this.conn.createStatement();
        stmt.executeUpdate("DELETE FROM Customer;");
        stmt.executeUpdate("DELETE FROM Employee;");
        stmt.executeUpdate("DELETE FROM Property;");

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
                    String insertQuery = "insert into Employee(employee_id, name, email, password, phone_number, address, gender, DOB, salary, employee_type, employee_role, hours)" +
                            "VALUES('" + thisEmp.getId() + "', '" + thisEmp.getName() + "', " +
                            "'" + thisEmp.getEmail() + "', '" + thisEmp.getPassword() + "', '" + thisEmp.getPhoneNo() + "'" +
                            ", '" + thisEmp.getAddress() + "', '" + thisEmp.getGender() + "', '" + thisEmp.getDob() + "'" +
                            ", '" + thisEmp.getSalary() + "', '" + thisEmp.getEmpType().toString() + "', '" + thisEmp.getEmpRole().toString() + "', " + thisEmp.getWorkingHours() +")";

                    stmt.executeUpdate(insertQuery);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        for( Property property : propertyDB.values()){
            String empRole = null ;
            if(property.getEmpRole() != null){
                empRole = property.getEmpRole().toString();
            }
            try{
                String insertQuery = "insert into Property(property_id, employee_id, name, type, street_address,min_price, suburb, bedroom_count, bathroom_count , parking_count, selling_price, rental_price, availability, customer_id, emp_role, property_category)" +
                        "VALUES(" + property.getPropertyId() +", '"+ property.getEmployeeId() +"', '"+ property.getPropertyName() +"', '"+ property.getPropertyType().toString() +"', '"+ property.getPropertyAddress() +"', "+ property.getMinPrice() +", '"+ property.getSuburb() +
                        "', "+ property.getBedroomCount() +", "+ property.getBathroomCount() +", "+ property.getParkingCount() +", "+ property.getSellingPrice() +", "+ property.getRentalPrice() +", '"+ property.getAvailability().toString() +
                        "', "+ property.getCustomerId() +", '"+ empRole  +"', '"+ property.getPropertyCategory().toString() + "')";

                stmt.executeUpdate(insertQuery);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
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

    public User getUserByID(String ID){

        for(User user : userDB){
            if ( user.getId().equals(ID) ) {
                return user;
            }
        }
        return null;
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
        System.out.println("LIST INSPECTION");
        System.out.println("---------------");
        if(inspectionDB.size() == 0) {
            System.out.println("No inspection available!");
        }
        else {
            if (currentCustomer instanceof Customer) {
                for (Inspection a : inspectionDB) {
                    if (currentCustomer.getId().equals(a.getcId())) {
                        System.out.println(a.showDetails());
                        System.out.println("*************************************");
                    }
                }
            }
        }
    }

    public void listInspectionEmployee(User currentuser){
        System.out.println("LIST INSPECTION");
        System.out.println("---------------");
        if(inspectionDB.size() == 0) {
            System.out.println("No inspection available!");
        }
        else {
            if (currentuser instanceof Employee) {
                for (Inspection a : inspectionDB) {
                    if (currentuser.getId().equals(a.geteId())) {
                        System.out.println(a.showDetails());
                        System.out.println("*************************************");
                    }
                }
            }
        }
    }

    public void listInspectionIDCustomer(User currentCustomer){
        if(currentCustomer instanceof Customer) {
            for (Inspection a : inspectionDB) {
                if(currentCustomer.getId().equals(a.getcId())) {
                    System.out.println(a.getId());
                }
            }
        }
        }

    public void listassignedProperties(Employee currentEmployee) {
        try {
            System.out.println(" ");
            System.out.println("Assigned Properties");
            propertyDB.values()
                    .stream()
                    .filter(i -> i.getEmployeeId().equals(currentEmployee.getId()))
                    .forEach(p -> System.out.println(p.getPropertyId()));
        } catch(NullPointerException e){
            //System.out.println("None");
            return;
        }
    }

    public boolean validpropertyEmployee(int propertyID,Employee id){
        boolean flag=false;
        try {
            for (Map.Entry<Integer, Property> set : propertyDB.entrySet()) {
                if (set.getValue().getPropertyId() == propertyID) {
                    if (set.getValue().getEmployeeId().equals(id.getId())) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            }
        }
        catch(Exception i){}
        return flag;
    }

    public boolean validproperty(int propertyID){
        boolean flag=false;
        for (Map.Entry<Integer, Property> set : propertyDB.entrySet()) {
            if(set.getValue().getPropertyId() ==propertyID){
                flag=true;
            }
            else{
                flag=false;
            }
        }return flag;
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
        if(inspectionDB.size()==0) {
            System.out.println("No inspections available!");
        }
        else {
            for (Inspection a : inspectionDB) {
                if (a.getStatus().equals("Created")) {
                    System.out.println("Inspecion ID:" + a.getId() + "\tProperty ID:" + a.getpId());
                }
                if (a.getId().equals(null)) {
                    System.out.println("No inpections are available");
                }
            }
        }
    }

    public boolean validateInspection(String id){
        Boolean flag=false;
        if(inspectionDB.size()==0) {
            System.out.println("No inspections available!");
            flag=false;
        }
        else {
            for (Inspection a : inspectionDB) {
                if (a.getId().equals(id)) {
                    flag = true;
                } else {
                    flag = false;
                    System.out.println("Invalid id entered!");
                }
            }
        }
        return flag;
    }

    public void listInspection(){
        System.out.println("LIST INSPECTION");
        System.out.println("---------------");
        if(inspectionDB.size() == 0) {
            System.out.println("No inspection available!");
        }
        else {
            for (Inspection a : inspectionDB) {
                System.out.println(a.showDetails());
                System.out.println("*************************************");
            }
        }
       }

    public void listInspectionID(Employee currentEmp){
        System.out.println("Available inspections are:");
        if(inspectionDB.size()==0) {
            System.out.println("No inspections available!");
        }
        else {
            for (Inspection a : inspectionDB) {
                if (a.geteId().equals(currentEmp.getId())) {
                    System.out.println(a.getId());
                }
            }
        }
    }

    public void cancellInspection(String id,Employee currentEmployee) throws PropertyException {
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                if(a.geteId().equals(currentEmployee.getId())&&a.getStatus().equals("Created")||a.getStatus().equals("Scheduled")) {
                        a.setStatus("Cancelled");
                        a.setDate(null);
                        a.setTime(null);
                        a.setTimeSlot(null);
                        a.setdatesSlot(null);
                        System.out.println("Inspection cancelled sucessfully");
                        System.out.println(a.showDetails());
                }
                else{
                    System.out.println("Inspection cannot be cancelled!!");
                }
            }
        }
         }

    public void cancellInspectionCustomer(String id,Customer currentUser) throws PropertyException {
        for(Inspection a:inspectionDB){
            if(a.getId().equals(id)){
                if(a.getcId().equals(currentUser.getId())&&a.getStatus().equals("Created")||a.getStatus().equals("Scheduled")) {
                    a.setStatus("cancelled");
                    a.setDate(null);
                    a.setTime(null);
                    a.setTimeSlot(null);
                    a.setdatesSlot(null);
                    System.out.println("Inspection cancelled sucessfully");
                    System.out.println(a.showDetails());
                }
                else{
                    System.out.println("Error!! Inspection already completed!! Cannot cancell it.");
                }
            }
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

    public double getSalary(String empid, Payroll payroll) {
        double salary;
       salary= payroll.getSalary();
        //System.out.println("hello"+payroll.getSalary());
        return salary;
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

        if(weeklyRent < selectedProperty.getPropertyPrice()){
            error = 1;
            msg += "The weekly rent should be more then the specified rent.\n";
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
    public boolean isApplicationExist(String appID) {
        for (Application app : applicationDB) {
            if (app.getId().equals(appID)) {
                return true;
            }


        }
        return false;
    }


    public void viewApplicationsByUser(User currentUser) {
        for(Application app : applicationDB){
            int totalApp = 0;
            String applications = "";
            if(currentUser instanceof Customer){
                if(currentUser.getId().equals(app.getCustID())){
                    applications += app.getDetails()+"\n-----------------------------\n";
                    totalApp++;
                }
            }else{
                if(currentUser.getId().equals(app.getEmpID())){
                    applications += app.getDetails()+"\n-----------------------------\n";
                    totalApp++;
                }
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

//        if(isInspectionDone(applyingUser, selectedProperty)){
//            error = 1;
//            msg += "You need to inspect the property before applying.\n";
//        }

        if(hasAlreadyApplied(applyingUser, selectedProperty)){
            error = 1;
            msg += "You have already applied for this property.\n";
        }

        if(error == 0){
            Application newSale = new SalesApplication(selectedProperty.getEmployeeId(), applyingUser.getId(), selectedProperty, price, saletype);
            System.out.println("Your Application for Purchase of " + selectedProperty.getPropertyName() + " is submitted with ID: " + newSale.getId() + "\n" +
                    "The Property Manager will be in touch with you!");
            applicationDB.add(newSale);
        }else{
            throw new ApplicationException(msg);
        }

    }

    public int countMyProperties(Customer currentUser){
        int counter = 0;

        for (Map.Entry<Integer, Property> entry : propertyDB.entrySet()) {
            Integer key = entry.getKey();
            Property value = entry.getValue();
            if(value.getCustomerId().equals(currentUser.getId()))
                counter++;
        }
        return counter;

    }

    public void createAuction(String auctionDate, Property thisProperty){
        Auction newAuc = new Auction(auctionDate, thisProperty);
        contractDB.add(newAuc);
        thisProperty.setAuctionStatus(true);
        thisProperty.setAvailability(PropertyState.UNDER_CONTRACT);
        System.out.println("Auction Created with ID " + newAuc.getId() + " for Property " + thisProperty.getPropertyName() + " on date: "+auctionDate);
    }

    public void listAuctions(){
        if(contractDB.size() > 0) {
            for (SalesMedium contract : contractDB) {
                if(contract instanceof Auction) {
                    Auction auction = (Auction) contract;
                    System.out.println("Auction ID:" + auction.getId() + "\n" +
                            "Property: " + auction.getProperty().getPropertyName() + "(" + auction.getProperty().getPropertyId() + ")\n" +
                            "Auction Date: " + auction.getContractDate());
                }
            }
        }else{
            System.out.println("No Auction Available!");
        }
    }

    public void addBid(String contractID, double bid, Customer currentUser) throws ApplicationException {
        Bids newBid = new Bids(contractID, bid, currentUser.getId());
        for(SalesMedium contract : contractDB){
            if(contract instanceof Auction) {
                Auction auction = (Auction) contract;
                if (auction.getId().equals(contractID)) {
                    auction.handleBids(newBid);
                    if (auction.getSaleStatus().equals(SaleStatus.COMPLETED)) {
                        auction.getProperty().setAvailability(PropertyState.SOLD);
                        this.saleApplication(auction.getProperty(), (Customer) this.getUserByID(auction.getHighestBidder()), auction.getHighestBid(), "auction");
                    }
                }
            }else if(contract instanceof Negotiation) {
                Negotiation thisNegotiation = (Negotiation) contract;
                if (thisNegotiation.getId().equals(contractID) && thisNegotiation.getSaleStatus().equals(SaleStatus.ONGOING)) {
                    thisNegotiation.handleBids(newBid);
                }
            }
        }
    }

    public String getAuctionDetailsByID(String auctID) {
        for(SalesMedium contract : contractDB){
            if(contract instanceof Auction) {
                Auction auction = (Auction) contract;
                if (auctID.equals(auction.getId())) {
                    return ("Auction ID:" + auction.getId() + "\n" +
                            "Property: " + auction.getProperty().getPropertyName() + "(" + auction.getProperty().getPropertyId() + ")\n" +
                            "Auction Date: " + auction.getContractDate());
                }
            }
        }
        return "Invalid Auction ID";
    }

    public boolean isValidAuction(String auctID) {
        for(SalesMedium contract : contractDB){
            if(contract instanceof Auction) {
                Auction auction = (Auction) contract;
                if (auctID.equals(auction.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public double getNextValidBid(String auctID){
        for(SalesMedium contract : contractDB){
            if(contract instanceof Auction) {
                Auction auction = (Auction) contract;
                if (auctID.equals(auction.getId())) {
                    return (auction.getHighestBid() + auction.getMinIncrease());
                }
            }
        }
        return 0;
    }

    public void completeInspection(User currentuser,String id) throws PropertyException {
        for(Inspection i : inspectionDB){
            if(i.getId().equals(id)){
                if(i.getStatus().equals("Scheduled")){
                    if(i.getcId().equals(currentuser.getId()) ||i.geteId().equals(currentuser.getId())){
                        i.setStatus("Completed");
                    }
                    else {
                        System.out.println("You are not allowed to update this inspection status!!");
                    }
                }
                else if(!i.getStatus().equalsIgnoreCase("Scheduled")){
                    System.out.println("Inspection has not yet been scheduled");
                }

            }
        }
    }

    public void calBonus(String empid, Payroll payroll,double bonus) throws UserException {
        payroll.setBonus(bonus);
        double salary = payroll.getSalary();
        for(User user : userDB){
            if(user instanceof Employee) {
                if ((user.getId()).equalsIgnoreCase(empid)) {
                    ((Employee) user).setSalary(salary);
                }

            }
        }
    }

    public void listNegotiation(Customer currentUser){
        if(contractDB.size() > 0) {
            for (SalesMedium contract : contractDB) {
                if(contract instanceof Negotiation) {
                    Negotiation thisNegotiation = (Negotiation) contract;
                    if(currentUser.getId().equals(thisNegotiation.getBidderID())) {
                        System.out.println("Negotiation ID:" + thisNegotiation.getId() + "\n" +
                                "Property: " + thisNegotiation.getProperty().getPropertyName() + "(" + thisNegotiation.getProperty().getPropertyId() + ")\n" +
                                "Reserve Price: " + thisNegotiation.getMinPrice() +
                                "Current Bid: " + thisNegotiation.getCurrentPrice());
                    }
                }
            }
        }else{
            System.out.println("No Ongoing/Past Negotiations Available!");
        }
    }

    public void createNegotiation(Customer customer, String negDate, Property thisProperty, Double bidPrice) throws ApplicationException {

        Negotiation newNegotiation = new Negotiation(thisProperty.getMinPrice(), bidPrice, customer.getId(), thisProperty, negDate);
        contractDB.add(newNegotiation);

        //add initial bid
        newNegotiation.setBidderID(customer.getId());
        this.addBid(newNegotiation.getId(), bidPrice, customer);

        System.out.println("Negotiation Created with ID " + newNegotiation.getId() + " for Property " + thisProperty.getPropertyName() + " with first Bid of $" +bidPrice);

    }

    public void setApplication(String appID) {
        for (Application app : applicationDB) {
            if (app.getId().equals(appID)) {
                app.completeApplication();
                System.out.println(app.getStatus());

            }

        }
        System.out.println("done");
    }
}
