package jin.jerrykel.dev.signal.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by JerrykelDEV on 27/12/2020 09:38
 */
public class IMGpub {
    private String uid;
    private Date dateCreated;

    private ArrayList<String> stringUrlImageArrayList;
    private int ImgCount;

    public IMGpub(String uid, ArrayList<String> stringUrlImageArrayList) {
        this.uid = uid;

        this.stringUrlImageArrayList = stringUrlImageArrayList;
        this.ImgCount =stringUrlImageArrayList.size();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    @ServerTimestamp
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }





    public ArrayList<String> getStringUrlImageArrayList() {
        return stringUrlImageArrayList;
    }

    public void setStringUrlImageArrayList(ArrayList<String> stringUrlImageArrayList) {
        this.stringUrlImageArrayList = stringUrlImageArrayList;
    }

    public int getImgCount() {
        return ImgCount;
    }

    public void setImgCount(int imgCount) {
        ImgCount = imgCount;
    }
}
