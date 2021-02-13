package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import jin.jerrykel.dev.signal.model.TypeSignals;

import static jin.jerrykel.dev.signal.utils.Values.COLLECTION_SIGNALTYPE_NAME;
import static jin.jerrykel.dev.signal.utils.Values.DATE_CREATED;

/**
 * Created by JerrykelDEV on 08/01/2021 10:22
 */
public class SignalTypeListHelper {



    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalTypeListCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_SIGNALTYPE_NAME);
    }

    // --- GET ---


    public static Query getAllSignalTypeSent(){

        return  getSignalTypeListCollection().orderBy(DATE_CREATED);
    }




    // --- CREATE ---

    public static Task<Void> createSignalType(String ui,String senderName,String name){
        String uuid = UUID.randomUUID().toString();
        TypeSignals typeSignals = new TypeSignals(uuid,ui,senderName,name);

        return getSignalTypeListCollection().document(uuid).set(typeSignals);
    }



    // --- DELETE ---

    public static Task<Void> deleteSignalType(String uid) {
        return getSignalTypeListCollection().document(uid).delete();
    }
}
