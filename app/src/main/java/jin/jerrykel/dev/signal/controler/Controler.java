package jin.jerrykel.dev.signal.controler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by JerrykelDEV on 23/11/2020 13:49
 */
public class Controler {
    //intialise property
    private static Controler instance = null;
    private Controler(){
        super();
    }
    /**
     * création de l'instance
     * @return Controler.instance
     */
    public static Controler getInstance(){

        if(Controler.instance == null){
            Controler.instance = new Controler();
        }
        return  Controler.instance;
    }






}
