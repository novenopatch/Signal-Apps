package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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


    // --- CREATE ---

    public static Task<DocumentReference> createSignal(String ui, String typeSignals, String signalStatus, String sellOrBuy, String entryPrice,
                                                       String stopLoss, String takeProfit){

        Signals signals = new Signals(ui,typeSignals,signalStatus,sellOrBuy,entryPrice,stopLoss,takeProfit);
        return getSignalCollection().add(signals);
    }

}
