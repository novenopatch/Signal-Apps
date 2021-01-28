package jin.jerrykel.dev.signal.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;

/**
 * Created by JerrykelDEV on 28/01/2021 05:33
 */
public class ServiceGetSignal extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startEcout();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return  START_REDELIVER_INTENT;
        //return super.onStartCommand(intent, flags, startId);
    }

    private void startEcout() {
        SignalsHelper.getAllSignalFreeOrPremium(false).addSnapshotListener((value, error) -> {
            for (DocumentChange dc :value.getDocumentChanges()) {
                switch (dc.getType()) {
                    case ADDED:
                        System.err.println(">> " + dc.getDocument().toString());
                        break;
                }
            }

        });
    }
    private void stopEcout() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
