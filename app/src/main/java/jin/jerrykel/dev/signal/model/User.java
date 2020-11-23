package jin.jerrykel.dev.signal.model;

import java.util.Date;
import java.util.Objects;

/**
 * Created by JerrykelDEV on 23/11/2020 13:42
 */
public class User {
    private String userMail;
    private String userPassword;
    private String userName;
    private  boolean sexe;
    private  Date UserBirthDay;

    public User(String userMail, String userPassword, String userName, boolean sexe, Date userBirthDay) {
        this.userMail = userMail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.sexe = sexe;
        UserBirthDay = userBirthDay;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSexe() {
        return sexe;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public Date getUserBirthDay() {
        return UserBirthDay;
    }

    public void setUserBirthDay(Date userBirthDay) {
        UserBirthDay = userBirthDay;
    }
}
