package jin.jerrykel.dev.signal.model;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 23/11/2020 13:42
 */
public class User {

    private String uid;
    private String username;
    private String email;
    private Boolean isMentor =false;
    private Boolean isRoot = false;
    private Boolean isDisable = false;
    private Boolean isPremium = false;
    private Boolean DeleteAction = false;
    private   Date dateCreated;

    @Nullable private String urlPicture;

    public User() { }

    public User(String uid, String username,String email, String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.urlPicture = urlPicture;

    }
    public User(String uid, String username,String email) {
        this.uid = uid;
        this.username = username;
        this.email = email;



    }







    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public Boolean getMentor() { return isMentor; }
    public Boolean getRoot() {
        return isRoot;
    }
    public Boolean getDisable() {
        return isDisable;
    }
    @ServerTimestamp
    public Date getDateCreated() { return dateCreated; }

    public String getEmail() {
        return email;
    }
    public Boolean isDeleteAction() {
        return DeleteAction;
    }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setMentor(Boolean mentor) { isMentor = mentor; }
    public void setRoot(Boolean root) {
        this.isRoot = root;
    }

    public void setDisable(Boolean disable) {
        isDisable = disable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }


}