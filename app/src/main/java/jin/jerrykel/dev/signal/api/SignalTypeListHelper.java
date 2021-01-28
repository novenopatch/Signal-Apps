package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import jin.jerrykel.dev.signal.model.TypeSignals;

/**
 * Created by JerrykelDEV on 08/01/2021 10:22
 */
public class SignalTypeListHelper {

    private static final String COLLECTION_NAME = "signalType";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalTypeListCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---


    public static Query getAllSignalTypeSent(){

        return  getSignalTypeListCollection().orderBy("dateCreated");
    }




    // --- CREATE ---

    public static Task<Void> createSignalType(String ui,String senderName,String name,Boolean premium){
        String uuid = UUID.randomUUID().toString();
        TypeSignals typeSignals = new TypeSignals(uuid,ui,senderName,name,premium);

        return getSignalTypeListCollection().document(uuid).set(typeSignals);
    }

    //Update

    public static Task<Void> updateSignalTypeName(String uid, String name) {
        return getSignalTypeListCollection().document(uid).update("name", name);
    }

    // --- DELETE ---

    public static Task<Void> deleteSignalType(String uid) {
        return getSignalTypeListCollection().document(uid).delete();
    }
}
