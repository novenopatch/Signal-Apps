package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 08/01/2021 10:09
 */
public class TypeSignals {
    private String senderUi;
    private String senderName;
    private String name;
    private String urlImage;
    private  @ServerTimestamp Date dateCreated;

    public TypeSignals() {

    }
    public TypeSignals(String senderUii,String senderName,String name, String urlImage) {
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.name = name;
        this.urlImage = urlImage;
    }
    public TypeSignals(String senderUii,String name) {
        this.senderUi  =senderUii;
        this.name = name;

    }
    public String getSenderUi() {
        return senderUi;
    }

    public void setSenderUi(String senderUi) {
        this.senderUi = senderUi;
    }

    public Date getDateCreated() {
        return dateCreated;
    }


    public String getName() {
        return name;
    }

    public String getUrlImage() {
        return urlImage;
    }
    public String getSenderName() {
        return senderName;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
