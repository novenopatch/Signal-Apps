package jin.jerrykel.dev.signal.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.UUID;

import jin.jerrykel.dev.signal.model.AppInfo;

/**
 * Created by JerrykelDEV on 31/01/2021 20:43
 */
public class AppInfoHelper {
    private static final String COLLECTION_NAME = "appInfo";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getAppInfoListCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- GET ---


    public static Query getAppInfo(){
        return getAppInfoListCollection().orderBy("dateCreated", Query.Direction.ASCENDING).limit(1);
    }



    // --- CREATE ---

    public static Task<Void> createAppInfo(int version, String name){
        String uuid = UUID.randomUUID().toString();
        AppInfo appInfo = new AppInfo(uuid,version,name);

        return getAppInfoListCollection().document(uuid).set(appInfo);
    }


}