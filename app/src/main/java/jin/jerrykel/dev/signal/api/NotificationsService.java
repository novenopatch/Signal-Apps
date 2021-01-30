package jin.jerrykel.dev.signal.api;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import jin.jerrykel.dev.signal.utils.Utils;

/**
 * Created by JerrykelDEV on 29/11/2020 10:31
 */
public class NotificationsService   extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String message = remoteMessage.getNotification().getBody();
            // 8 - Show notification after received message
            Utils.sendVisualNotification("OneSignal",message,this,false);
        }
    }



}