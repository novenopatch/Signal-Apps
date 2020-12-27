package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;

import jin.jerrykel.dev.signal.model.IMGpub;

/**
 * Created by JerrykelDEV on 27/12/2020 09:30
 */
public class ImgPubHelper {
    private static final String COLLECTION_NAME = "pubImg";

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }
    public static Task<Void> createImgPub(String uid, ArrayList<String> stringUrlImageArrayList) {
        IMGpub imGpub = new IMGpub(uid, stringUrlImageArrayList);
        return ImgPubHelper.getUsersCollection().document(uid).set(imGpub);
    }
    public static Task<Void> deleteImgPub() {
        return ImgPubHelper.getUsersCollection().document().delete();
    }
}
