package jin.jerrykel.dev.signal.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by JerrykelDEV on 28/01/2021 03:36
 */
@DatabaseTable
public class InfomationAppUser {
    @DatabaseField(generatedId = true)
    private int Id;
    @DatabaseField
    private boolean firstLaunch = true;
    @DatabaseField
    private String LastSignalName;
    @DatabaseField
    private Date when;
    @DatabaseField
    private int lastSignalNbr=0;

    public InfomationAppUser() {

    }

    public InfomationAppUser(boolean firstLaunch, String lastSignalName, Date when) {
        this.firstLaunch = firstLaunch;
        LastSignalName = lastSignalName;
        this.when = when;
    }
    public InfomationAppUser( Date when) {
        this.when = when;
    }



    public void setWhen(Date when) {
        this.when = when;
    }

    public boolean isFirstLaunch() {
        return firstLaunch;
    }

    public void setFirstLaunch(boolean firstLaunch) {
        this.firstLaunch = firstLaunch;
    }

    public String getLastSignalName() {
        return LastSignalName;
    }

    public void setLastSignalName(String lastSignalName) {
        LastSignalName = lastSignalName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getWhen() {
        return when;
    }

    public int getLastSignalNbr() {
        return lastSignalNbr;
    }

    public void setLastSignalNbr(int lastSignalNbr) {
        this.lastSignalNbr = lastSignalNbr;
    }
}
