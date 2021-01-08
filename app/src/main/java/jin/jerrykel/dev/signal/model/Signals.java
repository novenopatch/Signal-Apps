package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 08/01/2021 08:24
 */
public class Signals {
    private TypeSignals typeSignals;
    private String signalStatus;
    private String sellOrBuy;
    private String entryPrice;
    private  String stopLoss;
    private String takeProfit;
    private Date dateCreated;
    private String urlImage;
    private boolean active = true;

    public Signals(TypeSignals typeSignals,
                   String signalStatus, String sellOrBuy, String entryPrice,
                   String stopLoss, String takeProfit, String urlImage) {
        this.typeSignals = typeSignals;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.urlImage = urlImage;
    }

    public Signals(TypeSignals typeSignals, String signalStatus, String sellOrBuy, String entryPrice, String stopLoss, String takeProfit) {
        this.typeSignals = typeSignals;
        this.signalStatus = signalStatus;
        this.sellOrBuy = sellOrBuy;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;

    }


    public void setTypeSignals(TypeSignals typeSignals) {
        this.typeSignals = typeSignals;
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

    //getter


    public TypeSignals getTypeSignals() {
        return typeSignals;
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


    public String getUrlImage() {
        return urlImage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}