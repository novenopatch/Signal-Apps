package jin.jerrykel.dev.signal.api;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by JerrykelDEV on 28/01/2021 21:18
 */
public class ServerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
        if(context !=null && intent !=null){
            while (intent.getAction()== Intent.ACTION_BOOT_COMPLETED){
                Intent intento = new Intent(context, ServiceGetSignal.class);
                context.startService(intento);
            }
        }

         */
    }
}
