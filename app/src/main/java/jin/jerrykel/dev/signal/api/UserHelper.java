package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.model.User;

/**
 * Created by JerrykelDEV on 25/11/2020 23:09
 */
public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username,String email, String urlPicture) {
        User userToCreate = new User(uid, username,email, urlPicture);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }
    public static Query getAllUsers(){


        return UserHelper.getUsersCollection().orderBy("dateCreated");
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }
    public static Task<Void> updateUserDisableOrNot(String uid, boolean disable) {
        return UserHelper.getUsersCollection().document(uid).update("isDisable", disable);
    }

    public static Task<Void> updateIsMentor(String uid, boolean isMentor) {
        return UserHelper.getUsersCollection().document(uid).update("isMentor", isMentor);
    }
    public static Task<Void> updateIsRoot(String uid,Boolean isMentor, Boolean isRoot) {
        return UserHelper.getUsersCollection().document(uid).update("isRoot",isRoot);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).update("DeleteAction", true);
    }
    public static Task<Void> deleteAction(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}