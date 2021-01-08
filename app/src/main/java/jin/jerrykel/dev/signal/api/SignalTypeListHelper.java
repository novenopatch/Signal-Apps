package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.model.Message;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.model.User;

/**
 * Created by JerrykelDEV on 08/01/2021 10:22
 */
public class SignalTypeListHelper {

    private static final String COLLECTION_NAME = "SignalType";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getSignalListCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---

    public static Query getAllSignalSent(){

        return  getSignalListCollection().orderBy("dateCreated");
    }

    /**
     public static Query getAllSignalSent(){

     return  getSignalListCollection().whereEqualTo("",);
     }
     */



    // --- CREATE ---

    public static Task<DocumentReference> createSignalTyp(String name){

        TypeSignals typeSignals = new TypeSignals(name);

        return getSignalListCollection().add(typeSignals);
    }
}
