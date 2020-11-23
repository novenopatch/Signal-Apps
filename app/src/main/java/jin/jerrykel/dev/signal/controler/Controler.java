package jin.jerrykel.dev.signal.controler;

import android.content.Context;

import java.util.Date;

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
    public User createUser(String email,String password,String  userName,boolean sexe, Date userBirthDay){
        return  new User (email,password,userName,sexe,userBirthDay);
    }
    public boolean connnect(String email,String password){

        if(email.equals("root")&& password.equals("root")){
            return true;
        }
        return false;

    }

}
