package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import jin.jerrykel.dev.signal.model.Signals;

/**
 * Created by JerrykelDEV on 08/01/2021 10:15
 */
public class SignalsHelper {

    private static final String COLLECTION_NAME = "signal";


    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---

    public static Query getAllSignalSent(){


        return  getSignalCollection().orderBy("dateCreated");
    }
    public static Query getAllSignalSentSignalName(String config){

        return  getSignalCollection().whereEqualTo("typeSignalsName",config).orderBy("dateCreated");
    }
    public static Query getAllSignalSentActiveOrReady(String config){

        return  getSignalCollection().whereEqualTo("signalStatus",config).orderBy("dateCreated");
    }
    public static Query getAllSignalSentSellOrBuy(String config){

        return  getSignalCollection().whereEqualTo("sellOrBuy",config).orderBy("dateCreated");
    }
    public static Query getAllSignalFreeOrPremium(Boolean config){

        return  getSignalCollection().whereEqualTo("premium",config).orderBy("dateCreated");
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
            return getSignalCollection().document(uid).update("signalStatus","Active");
        }else {
            return getSignalCollection().document(uid).update("signalStatus","Ready");
        }

    }
    // --- DELETE ---

    public static Task<Void> deleteSignal(String uid) {
        return getSignalCollection().document(uid).delete();
    }

}
