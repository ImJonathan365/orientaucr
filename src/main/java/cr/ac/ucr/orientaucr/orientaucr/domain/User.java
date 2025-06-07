package cr.ac.ucr.orientaucr.orientaucr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "user_lastname", length = 100)
    private String userLastname;

    @Column(name = "user_email", nullable = false, unique = true, length = 255)
    private String userEmail;

    @Column(name = "user_birthdate")
    @Temporal(TemporalType.DATE)
    private Date userBirthdate;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "user_admission_average", precision = 5, scale = 2)
    private BigDecimal userAdmissionAverage;

    @Column(name = "user_profile_picture", columnDefinition = "TEXT")
    private String userProfilePicture;

    @Column(name = "user_allow_email_notification")
    private boolean userAllowEmailNotification;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonIgnoreProperties("users")
    private LinkedList<Roles> userRoles = new LinkedList<>();

    public User() {}

    public User(String userId, String userName, String userLastname, String userEmail, Date userBirthdate, String userPassword, BigDecimal userAdmissionAverage, String userProfilePicture, boolean userAllowEmailNotification) {
        this.userId = userId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.userBirthdate = userBirthdate;
        this.userPassword = userPassword;
        this.userAdmissionAverage = userAdmissionAverage;
        this.userProfilePicture = userProfilePicture;
        this.userAllowEmailNotification = userAllowEmailNotification;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(Date userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public BigDecimal getUserAdmissionAverage() {
        return userAdmissionAverage;
    }

    public void setUserAdmissionAverage(BigDecimal userAdmissionAverage) {
        this.userAdmissionAverage = userAdmissionAverage;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public boolean isUserAllowEmailNotification() {
        return userAllowEmailNotification;
    }

    public void setUserAllowEmailNotification(boolean userAllowEmailNotification) {
        this.userAllowEmailNotification = userAllowEmailNotification;
    }

    public LinkedList<Roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(LinkedList<Roles> userRoles) {
        this.userRoles = userRoles;
    }
    
}