package jin.jerrykel.dev.signal.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.vue.Activities.LoginActivity;
import jin.jerrykel.dev.signal.vue.Activities.Main.MainActivity;

/**
 * Created by JerrykelDEV on 26/12/2020 09:17
 */
public class Utils {
    private  static final int NOTIFICATION_ID = 00731;
    private static final String NOTIFICATION_TAG = "OneSignal";


    public static String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        return dfTime.format(date);
    }
    /**
     * conversion d'une date en chaine sous la forme yyyy-MM-dd hh:mm:ss
     * @param uneDate
     * @return
     */
    public static String convertDateToString(Date uneDate){
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd- hh:mm:ss");
        return date.format(uneDate);
    }
    public static ArrayList<String> getTypeSignalsString(){
        String TAG = "getTypeSignalsString()";
        ArrayList<String> stringArrayList = new ArrayList<>();
        SignalTypeListHelper.getAllSignalTypeSent().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {


                    TypeSignals typeSignals = document.toObject(TypeSignals.class);
                    stringArrayList.add(typeSignals.getName());
                    //Log.d(TAG, document.getId() + " => " + document.getData());
                }
            } else {
                stringArrayList.add("type signal");
                //Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

        if(stringArrayList.size()<=1){
            stringArrayList.add("Select Market");
        }


        return stringArrayList;
    }


    public static void copyPasswordInclipBoard(String toastText, String stringCopy, Context contexte) {
        ClipboardManager clipboardManager = (ClipboardManager)contexte.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("copy Text",stringCopy);
        clipboardManager.setPrimaryClip(clipData);
        makeToast(toastText,contexte);

    }
    public static void makeToast(String toastText,Context contexte){
        Toast toast = Toast.makeText(contexte, toastText, Toast.LENGTH_LONG);
        View toastView = toast.getView();
        TextView tv = (TextView) toastView.findViewById(android.R.id.message);
        tv.setTextSize(18);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_settings_blue_24,0,0,0);
        tv.setCompoundDrawablePadding(15);
        toastView.setBackgroundColor(Color.parseColor("#00000000"));


        toast.show();
    }
    /**
     * my personal getString for take String for resource to get translate foncton all over
     * @param resId
     * @param context
     * @return
     */
    @NonNull
    public  static String getString(@StringRes int resId, Context context) {
        return context.getString(resId);
    }


    public static void sendVisualNotification(String title,String messageBody,Context context,boolean bool) {

        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent = null;
        if(bool){
            intent = new Intent(context, MainActivity.class);
        }else {
            intent = new Intent(context, LoginActivity.class);

        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Style for the Notification
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(getString(R.string.notification_title,context));
        inboxStyle.addLine(messageBody);

        // 3 - Create a Channel (Android 8)
        String channelId = getString(R.string.default_notification_channel_id,context);

        // 4 - Build a Notification object
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.img_icone)
                        .setContentTitle(title)
                        .setContentText(getString(R.string.notification_title,context))
                        .setAutoCancel(true)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent)
                        .setStyle(inboxStyle);

        // 5 - Add the Notification to the Notification Manager and show it.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = getString(R.string.app_name,context)+ " notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        // 7 - Show notification
        notificationManager.notify(NOTIFICATION_TAG, NOTIFICATION_ID, notificationBuilder.build());
    }

}
