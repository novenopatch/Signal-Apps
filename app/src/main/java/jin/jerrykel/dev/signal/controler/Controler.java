package jin.jerrykel.dev.signal.controler;

import android.content.Context;

import jin.jerrykel.dev.signal.utils.DatabaseManager;

/**
 * Created by JerrykelDEV on 23/11/2020 13:49
 */
public class Controler {
    //intialise property
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

    public  DatabaseManager getManager() {
        return manager;
    }
}
