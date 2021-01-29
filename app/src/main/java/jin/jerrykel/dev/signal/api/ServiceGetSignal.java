package jin.jerrykel.dev.signal.api;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.DocumentChange;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.utils.Utils;

/**
 * Created by JerrykelDEV on 28/01/2021 05:33
 */
public class ServiceGetSignal extends IntentService {



    public ServiceGetSignal() {
        super("ServiceGetSignal");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(ifInternet()){
            startEcout();
        }


        //return super.onStartCommand(intent, flags, startId);
        return  START_REDELIVER_INTENT;
    }


    private void startEcout() {
        SignalsHelper.getAllSignalFreeOrPremium(false).addSnapshotListener((value, error) -> {
            if(value !=null){
                value.getDocumentChanges();
                for (DocumentChange dc :value.getDocumentChanges()) {
                    switch (dc.getType()) {
                        case ADDED:
                            Utils.sendVisualNotification("OneSignal",getString(R.string.notificationW),this);
                            break;
                    }
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
    }
}
