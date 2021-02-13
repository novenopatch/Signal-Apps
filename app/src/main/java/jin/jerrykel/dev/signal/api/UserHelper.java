package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.model.User;

import static jin.jerrykel.dev.signal.utils.Values.COLLECTION_USER_NAME;
import static jin.jerrykel.dev.signal.utils.Values.DATE_CREATED;
import static jin.jerrykel.dev.signal.utils.Values.DELETE_ACTION;
import static jin.jerrykel.dev.signal.utils.Values.DISABLE;
import static jin.jerrykel.dev.signal.utils.Values.MENTOR;
import static jin.jerrykel.dev.signal.utils.Values.ROOT;
import static jin.jerrykel.dev.signal.utils.Values.USERNAME;

/**
 * Created by JerrykelDEV on 25/11/2020 23:09
 */
public class UserHelper {


    private static boolean  isExist = false;


    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection( COLLECTION_USER_NAME);
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


        return UserHelper.getUsersCollection().orderBy(DATE_CREATED);
    }
    public static boolean ifUserIsExist(String ui){
        getUser(ui).addOnSuccessListener(documentSnapshot -> {
            User currentUser = documentSnapshot.toObject(User.class);
            if(currentUser !=null){

                isExist = true;
            }
        });
        return isExist;
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update(USERNAME, username);
    }
    public static Task<Void> updateUserDisableOrNot(String uid, boolean disable) {
        return UserHelper.getUsersCollection().document(uid).update(DISABLE, disable);
    }

    public static Task<Void> updateIsMentor(String uid, boolean isMentor) {
        return UserHelper.getUsersCollection().document(uid).update(MENTOR, isMentor);
    }
    public static Task<Void> updateIsRoot(String uid,Boolean isMentor, Boolean isRoot) {
        return UserHelper.getUsersCollection().document(uid).update(ROOT,isRoot);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {

        return UserHelper.getUsersCollection().document(uid).update(DELETE_ACTION, true);
    }
    public static Task<Void> deleteAction(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}