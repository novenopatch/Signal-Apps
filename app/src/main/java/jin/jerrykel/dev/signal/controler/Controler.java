package jin.jerrykel.dev.signal.controler;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.User;

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

    public boolean connnect(String email,String password){

        if(email.equals("root")&& password.equals("root")){
            return true;
        }
        return false;

    }
    @Nullable
    public FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    public Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    public OnFailureListener onFailureListener(final Context context){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, context.getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
            }
        };
    }



}
