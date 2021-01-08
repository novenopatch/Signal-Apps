package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

import jin.jerrykel.dev.signal.model.Message;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.model.User;

/**
 * Created by JerrykelDEV on 08/01/2021 10:15
 */
public class SignalHelper {
    private static final String COLLECTION_NAME = "Signals";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
    // --- GET ---

    public static Query getAllSignalSent(){

        return  getSignalCollection().orderBy("dateCreated");
    }


    // --- CREATE ---

    public static Task<DocumentReference> createSignalSent(TypeSignals typeSignals, String signalStatus, String sellOrBuy, String entryPrice,
                                                               String stopLoss, String takeProfit){

        Signals signals = new Signals(typeSignals,signalStatus,sellOrBuy,entryPrice,stopLoss,takeProfit);
        return getSignalCollection().add(signals);
    }

}
