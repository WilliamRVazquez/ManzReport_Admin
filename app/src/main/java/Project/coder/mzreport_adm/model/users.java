package Project.coder.mzreport_adm.model;

public class users {
    String fName;
    String email;
    String phone;

    public users(){

    }

    public users(String fName,String email, String phone) {
        this.fName = fName;
        this.email = email;
        this.phone = phone;


    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}