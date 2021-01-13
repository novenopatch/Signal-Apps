package jin.jerrykel.dev.signal.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JerrykelDEV on 26/12/2020 09:17
 */
public class Utils {
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

}
