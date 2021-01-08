package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 08/01/2021 08:24
 */
public class Signals {
    private String signalsName;
    private String signalStatus;
    private String sellOrBuy;
    private String entryPrice;
    private  String stopLoss;
    private String takeProfit;
    private Date dateCreated;
    private Date dateExpired;
    private String urlImage;

    public Signals(String signalsName, String signalStatus, String sellOrBuy, String entryPrice, String stopLoss, String takeProfit, Date dateCreated, Date dateExpired, String urlImage) {
        this.signalsName = signalsName;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.dateCreated = dateCreated;
        this.dateExpired = dateExpired;
        this.urlImage = urlImage;
    }

    public Signals(String signalsName, String signalStatus, String sellOrBuy, String entryPrice, String stopLoss, String takeProfit, Date dateCreated, Date dateExpired) {
        this.signalsName = signalsName;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.dateCreated = dateCreated;
        this.dateExpired = dateExpired;
    }

    public void setSignalsName(String signalsName) {
        this.signalsName = signalsName;
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

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    //getter

    public String getSignalsName() {
        return signalsName;
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

    @ServerTimestamp
    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
