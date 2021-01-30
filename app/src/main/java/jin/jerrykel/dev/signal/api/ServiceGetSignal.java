package jin.jerrykel.dev.signal.api;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.ListenerRegistration;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.utils.Utils;

/**
 * Created by JerrykelDEV on 28/01/2021 05:33
 */
public class ServiceGetSignal extends Service {



    ListenerRegistration registration;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ifInternet()){
            startlisten();
        }


        //return super.onStartCommand(intent, flags, startId);
        return  super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
    }


    private void startlisten() {
        registration = SignalsHelper.getSignalCollection().addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                //Log.w(TAG, "listen:error", e);
                return;
            }
            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                switch (dc.getType()) {
                    case ADDED:
                        Utils.sendVisualNotification("OneSignal",getString(R.string.notification),this);
                        break;

                }
            }

        });
    }
    private boolean ifInternet(){
        ConnectivityManager connec = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        if (connec != null &&
                ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))
        ) {
            //You are connected, do something online.
            return true;
        }
        else if (
                connec != null &&
                        ( (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED ))
        ) { //Not connected. Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        registration.remove();
    }
}
