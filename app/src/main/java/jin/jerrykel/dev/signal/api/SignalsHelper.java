package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import jin.jerrykel.dev.signal.model.Signals;

import static jin.jerrykel.dev.signal.utils.Values.ACTIVE;
import static jin.jerrykel.dev.signal.utils.Values.COLLECTION_SIGNALS_NAME;
import static jin.jerrykel.dev.signal.utils.Values.DATE_CREATED;
import static jin.jerrykel.dev.signal.utils.Values.READY;
import static jin.jerrykel.dev.signal.utils.Values.SIGNALS_PREMIUM_OR_NOT;
import static jin.jerrykel.dev.signal.utils.Values.SIGNALS_SENT_ACTIVE_OR_READY;
import static jin.jerrykel.dev.signal.utils.Values.SIGNALS_SENT_SELL_OR_BUY;
import static jin.jerrykel.dev.signal.utils.Values.TYPE_SIGNALS_NAME;

/**
 * Created by JerrykelDEV on 08/01/2021 10:15
 */
public class SignalsHelper {




    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_SIGNALS_NAME);
    }

    // --- GET ---

    public static Query getAllSignalSent(){


        return  getSignalCollection().orderBy(DATE_CREATED);
    }
    public static Query getAllSignalSentSignalName(String config){

        return  getSignalCollection().whereEqualTo( TYPE_SIGNALS_NAME,config).orderBy(DATE_CREATED);
    }
    public static Query getAllSignalSentActiveOrReady(String config){

        return  getSignalCollection().whereEqualTo(SIGNALS_SENT_ACTIVE_OR_READY,config).orderBy(DATE_CREATED);
    }
    public static Query getAllSignalSentSellOrBuy(String config){

        return  getSignalCollection().whereEqualTo(SIGNALS_SENT_SELL_OR_BUY,config).orderBy(DATE_CREATED);
    }



    // --- CREATE ---

    public static Task<Void> createSignal(String ui,String name, String typeSignals, String signalStatus, String sellOrBuy, String entryPrice,
                                                       String stopLoss, String takeProfit,Boolean premium){
        String uuid = UUID.randomUUID().toString();

        Signals signals = new Signals(uuid,ui,name,typeSignals,signalStatus,sellOrBuy,entryPrice,stopLoss,takeProfit,premium);
        return getSignalCollection().document(uuid).set(signals);
    }
    //Update

    public static Task<Void> updateStatut(String uid, boolean state) {
        if(state){
            return getSignalCollection().document(uid).update(SIGNALS_SENT_ACTIVE_OR_READY,ACTIVE);
        }else {
            return getSignalCollection().document(uid).update(SIGNALS_SENT_ACTIVE_OR_READY,READY);
        }

    }
    public static Task<Void> updatePremiumOrNot(String uid, boolean state) {

        return getSignalCollection().document(uid).update(SIGNALS_PREMIUM_OR_NOT,state);


    }

    // --- DELETE ---

    public static Task<Void> deleteSignal(String uid) {
        return getSignalCollection().document(uid).delete();
    }

}
