package cr.ac.ucr.orientaucr.orientaucr.domain;

import java.util.Date;
import java.util.LinkedList;

public class User {
    private String user_id;
    private String user_name;
    private String user_lastname;
    private String user_email;
    private int user_phone_number;
    private Date user_birthdate;
    private String user_password;
    private double user_admission_average;
    private String user_profile_picture;
    private boolean user_allow_email_notification;
    private LinkedList<Roles> user_roles;
    
    public User() {}

    public User(String user_id, String user_name, String user_lastname, String user_email, int user_phone_number, Date user_birthdate, String user_password, double user_admission_average, String user_profile_picture, boolean user_allow_email_notification, LinkedList<Roles> user_roles) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_lastname = user_lastname;
        this.user_email = user_email;
        this.user_phone_number = user_phone_number;
        this.user_birthdate = user_birthdate;
        this.user_password = user_password;
        this.user_admission_average = user_admission_average;
        this.user_profile_picture = user_profile_picture;
        this.user_allow_email_notification = user_allow_email_notification;
        this.user_roles = user_roles;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_phone_number() {
        return user_phone_number;
    }

    public void setUser_phone_number(int user_phone_number) {
        this.user_phone_number = user_phone_number;
    }

    public Date getUser_birthdate() {
        return user_birthdate;
    }

    public void setUser_birthdate(Date user_birthdate) {
        this.user_birthdate = user_birthdate;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public double getUser_admission_average() {
        return user_admission_average;
    }

    public void setUser_admission_average(double user_admission_average) {
        this.user_admission_average = user_admission_average;
    }

    public String getUser_profile_picture() {
        return user_profile_picture;
    }

    public void setUser_profile_picture(String user_profile_picture) {
        this.user_profile_picture = user_profile_picture;
    }

    public boolean isUser_allow_email_notification() {
        return user_allow_email_notification;
    }

    public void setUser_allow_email_notification(boolean user_allow_email_notification) {
        this.user_allow_email_notification = user_allow_email_notification;
    }

    public LinkedList<Roles> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(LinkedList<Roles> user_roles) {
        this.user_roles = user_roles;
    }
    
}
