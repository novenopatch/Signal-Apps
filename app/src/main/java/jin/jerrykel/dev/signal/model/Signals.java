package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 08/01/2021 08:24
 */
public class Signals {
    private String senderUi;
    private String senderName;
    private String typeSignalsName;
    private String signalStatus;
    private String sellOrBuy;
    private String entryPrice;
    private  String stopLoss;
    private String takeProfit;
    private  @ServerTimestamp Date dateCreated;
    private String urlImage;
    private boolean active = true;

    public Signals() {

    }

    public Signals(String senderUii,String senderName, String typeSignalsName,
                   String signalStatus, String sellOrBuy, String entryPrice,
                   String stopLoss, String takeProfit, String urlImage) {
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.typeSignalsName = typeSignalsName;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.urlImage = urlImage;
    }

    public Signals(String senderUii,String senderName,String typeSignalsName, String signalStatus, String sellOrBuy, String entryPrice, String stopLoss, String takeProfit) {
        this.senderUi  =senderUii;
        this.senderName = senderName;
        this.typeSignalsName = typeSignalsName;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;

    }



    public void setSenderUi(String senderUi) {
        this.senderUi = senderUi;
    }

    public void setTypeSignalsName(String typeSignals) {
        this.typeSignalsName = typeSignals;
    }

    public void setSignalStatus(String signalStatus) {
        this.signalStatus = signalStatus;
    }

    public void setSellOrBuy(String sellOrBuy) {
        this.sellOrBuy = sellOrBuy;
    }

    public void setEntryPrice(String entryPrice) {
        this.entryPrice = entryPrice;
    }

    public void setStopLoss(String stopLoss) {
        this.stopLoss = stopLoss;
    }

    public void setTakeProfit(String takeProfit) {
        this.takeProfit = takeProfit;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    //getter


    public String getTypeSignalsName() {
        return typeSignalsName;
    }
    public String getSignalStatus() {
        return signalStatus;
    }
    public String getSellOrBuy() {
        return sellOrBuy;
    }
    public String getEntryPrice() {
        return entryPrice;
    }
    public String getStopLoss() {
        return stopLoss;
    }
    public String getTakeProfit() {
        return takeProfit;
    }
    public Date getDateCreated() {
        return dateCreated;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public String getSenderUi() {
        return senderUi;
    }
    public boolean isActive() {
        return active;
    }

    public String getSenderName() {
        return senderName;
    }
}
