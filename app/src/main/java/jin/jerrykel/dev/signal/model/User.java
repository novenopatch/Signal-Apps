package jin.jerrykel.dev.signal.model;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by JerrykelDEV on 23/11/2020 13:42
 */
public class User {

    private String uid;
    private String username;
    private Boolean isMentor;
    private Boolean isRoot ;
    @Nullable private String urlPicture;

    public User() { }

    public User(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.username = username;
        this.urlPicture = urlPicture;
        this.isMentor = false;
        this.isRoot = false;
    }






    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getUrlPicture() { return urlPicture; }
    public Boolean getIsMentor() { return isMentor; }
    public Boolean getRoot() {
        return isRoot;
    }
    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
    public void setRoot(Boolean root) {
        this.isRoot = root;
    }


}