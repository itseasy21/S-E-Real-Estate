import java.util.Date;

public abstract class User {

//  Common Variables to All User
    protected String email, password, name, address, profilePic, gender;
    protected int id;
    protected double phoneNo;
    protected Date dob;

    public User(int id, String email, String password, String name, String address, double phoneNo, Date dob, String gender, String profilePic){
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
    }

    void updateUser(String email, String password, String name, String address, double phoneNo, Date dob, String gender, String profilePic){
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.profilePic = profilePic;
    }

    public String getName() {
        return this.name;
    }

    public Date getDob() {
        return this.dob;
    }

    public double getPhoneNo() {
        return this.phoneNo;
    }

    public String getAddress() {
        return this.address;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGender() {
        return this.gender;
    }

    public String getPassword() {
        return this.password;
    }

    public String getProfilePic() {
        return this.profilePic;
    }

    public String showDetails(){
        String printDetails = "";

        printDetails += "ID:\t"+this.id;
        printDetails += "\nName:\t"+getName();
        printDetails += "\nEmail:\t"+getEmail();
        printDetails += "\nGender:\t"+getGender();
        printDetails += "\nDOB:\t"+getDob();
        printDetails += "\nPhone No:\t"+getPhoneNo();

        return printDetails;

    }

}
