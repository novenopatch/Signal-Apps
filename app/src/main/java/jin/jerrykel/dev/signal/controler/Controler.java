package jin.jerrykel.dev.signal.controler;

import android.content.Context;

import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.utils.DatabaseManager;

/**
 * Created by JerrykelDEV on 23/11/2020 13:49
 */
public class Controler {
    //intialise property
    private  static User user;
    private static Controler instance = null;
    private static  DatabaseManager manager;
    private Controler(){
        super();
    }
    /**
     * création de l'instance
     * @return Controler.instance
     */
    public static Controler getInstance(Context context){

        if(Controler.instance == null){
            Controler.instance = new Controler();
          manager = new  DatabaseManager(context);
        }
        return  Controler.instance;
    }
    public static Controler getInstance(Context context,User userG){

        if(Controler.instance == null){
            Controler.instance = new Controler();
            manager = new  DatabaseManager(context);
            user = userG;
        }else {
            if(userG!=null && user==null){
                user = userG;
            }
        }
        return  Controler.instance;
    }

    public  DatabaseManager getManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }

    public  void setUser(User user) {
        Controler.user = user;
    }
}
