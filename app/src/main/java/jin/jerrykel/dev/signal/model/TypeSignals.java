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
    private   Date dateCreated;
    private boolean premium = false;

    public TypeSignals() {

    }
    public TypeSignals(String ui,String senderUii,String senderName,String name, String urlImage,Boolean forPro) {
        this.UI = ui;
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.name = name;
        this.urlImage = urlImage;
        this.premium = forPro;
    }
    public TypeSignals(String ui,String senderUii,String senderName,String name,Boolean forPro) {
        this.UI = ui;
        this.name = name;
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.premium = forPro;

    }

    public @ServerTimestamp Date getDateCreated() {
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
    public boolean isPremium() {
        return premium;
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
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
