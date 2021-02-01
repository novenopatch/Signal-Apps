package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

/**
 * Created by JerrykelDEV on 31/01/2021 20:34
 */
public class AppInfo {
    private String UI;
    private int  version = 1;
    private String versionName ;
    private Date dateCreated;


    public AppInfo() {

    }
    public AppInfo(String UI, int version, String versionName) {
        this.UI = UI;
        this.version = version;
        this.versionName = versionName;
    }

    public String getUI() {
        return UI;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersion() {
        return version;
    }

    public @ServerTimestamp Date getDateCreated() {
        return dateCreated;
    }


}
