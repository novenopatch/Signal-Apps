package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 08/01/2021 10:09
 */
public class TypeSignals {
    private String UI;
    private String senderUi;
    private String senderName;
    private String name;
    private String urlImage;
    private  @ServerTimestamp Date dateCreated;

    public TypeSignals() {

    }
    public TypeSignals(String ui,String senderUii,String senderName,String name, String urlImage) {
        this.UI = ui;
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.name = name;
        this.urlImage = urlImage;
    }
    public TypeSignals(String ui,String senderUii,String senderName,String name) {
        this.UI = ui;
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.name = name;

    }

    public Date getDateCreated() {
        return dateCreated;
    }
    public String getUI() {
        return UI;
    }
    public String getName() {
        return name;
    }
    public String getSenderName() {
        return senderName;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public String getSenderUi() {
        return senderUi;
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
    public void setUI(String UI) {
        this.UI = UI;
    }
    public void setSenderUi(String senderUi) {
        this.senderUi = senderUi;
    }


}
