package jin.jerrykel.dev.signal.model;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by JerrykelDEV on 23/11/2020 13:42
 */
public class User {
    private String uid;
    private String userName;
    private  boolean sexe;
    private  Date UserBirthDay;
    private Boolean isMentor;
    @Nullable
    private String urlPicture;


    public User() { }

    public User( String uid,String userName, boolean sexe, Date userBirthDay, String urlPicture) {
        this.uid = uid;
        this.userName = userName;
        this.sexe = sexe;
        UserBirthDay = userBirthDay;
        this.urlPicture = urlPicture;
        this.isMentor = false;
    }

    public User(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.userName = userName;
        this.urlPicture = urlPicture;
        this.isMentor = false;
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
    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUrlPicture() { return urlPicture; }
    public Boolean getIsMentor() { return isMentor; }

    // --- SETTERS ---
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
}
