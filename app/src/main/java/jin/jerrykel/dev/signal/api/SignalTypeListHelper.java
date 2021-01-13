package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.model.TypeSignals;

/**
 * Created by JerrykelDEV on 08/01/2021 10:22
 */
public class SignalTypeListHelper {

    private static final String COLLECTION_NAME = "SignalType";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalTypeListCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---


    public static Query getAllSignalTypeSent(){

        return  getSignalTypeListCollection().orderBy("dateCreated");
    }




    // --- CREATE ---

    public static Task<DocumentReference> createSignalType(String ui,String name){

        TypeSignals typeSignals = new TypeSignals(ui,name);

        return getSignalTypeListCollection().add(typeSignals);
    }
}
