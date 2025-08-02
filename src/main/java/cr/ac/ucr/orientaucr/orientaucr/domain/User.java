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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate userBirthdate;

    @Column(name = "user_password", nullable = false, length = 255)
    private String userPassword;

    @Column(name = "user_diversified_average", precision = 5, scale = 2)
    private BigDecimal userDiversifiedAverage;

    @Column(name = "user_profile_picture", columnDefinition = "TEXT")
    private String userProfilePicture;

    @Column(name = "user_allow_email_notification")
    private boolean userAllowEmailNotification;
    
    @Column(name = "jwt_token", columnDefinition = "TEXT")
    private String jwtToken;
    
    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonIgnoreProperties("users")
    private List<Roles> userRoles = new ArrayList<>();

    public User() {}

    public User(String userId, String userName, String userLastname, String userEmail, LocalDate userBirthdate, String userPassword, BigDecimal userDiversifiedAverage, String userProfilePicture, boolean userAllowEmailNotification, String jwtToken, boolean isEmailVerified) {
        this.userId = userId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.userBirthdate = userBirthdate;
        this.userPassword = userPassword;
        this.userDiversifiedAverage = userDiversifiedAverage;
        this.userProfilePicture = userProfilePicture;
        this.userAllowEmailNotification = userAllowEmailNotification;
        this.jwtToken = jwtToken;
        this.isEmailVerified = isEmailVerified;
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

    public LocalDate getUserBirthdate() {
        return userBirthdate;
    }

    public void setUserBirthdate(LocalDate userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public BigDecimal getUserDiversifiedAverage() {
        return userDiversifiedAverage;
    }

    public void setUserDiversifiedAverage(BigDecimal userDiversifiedAverage) {
        this.userDiversifiedAverage = userDiversifiedAverage;
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

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean isIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public List<Roles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<Roles> userRoles) {
        this.userRoles = userRoles;
    }

}