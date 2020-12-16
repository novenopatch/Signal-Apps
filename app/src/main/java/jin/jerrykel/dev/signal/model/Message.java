package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 25/11/2020 22:45
 */
public class Message {

    private String message;
    private Date dateCreated;
    private User userSender;
    private String urlImage;
    private String backgroundColor;
    private  String messageColor;

    public Message() { }

    public Message(String message, User userSender) {
        this.message = message;
        this.userSender = userSender;
    }
    public Message(String message, String urlImage, User userSender) {
        this.message = message;
        this.urlImage = urlImage;
        this.userSender = userSender;
    }
    public Message(String message, User userSender,String color,String messageColor) {
        this.message = message;
        this.backgroundColor = color;
        this.messageColor = messageColor;
        this.userSender = userSender;
    }

    public Message(String message, String urlImage,String color,String messageColor, User userSender) {
        this.message = message;
        this.urlImage = urlImage;
        this.backgroundColor = color;
        this.messageColor = messageColor;
        this.userSender = userSender;
    }





    // --- GETTERS ---
    public String getMessage() { return message; }
    @ServerTimestamp public Date getDateCreated() { return dateCreated; }
    public User getUserSender() { return userSender; }
    public String getUrlImage() { return urlImage; }
    public String getBackgroundColor() {
        return backgroundColor;
    }
    public String getMessageColor() {
        return messageColor;
    }

    // --- SETTERS ---
    public void setMessage(String message) { this.message = message; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
    public void setUserSender(User userSender) { this.userSender = userSender; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }
    public void setBackgroundColor(String backgroundColor) {
        backgroundColor = backgroundColor;
    }
    public void setMessageColor(String messageColor) {
        this.messageColor = messageColor;
    }
}
