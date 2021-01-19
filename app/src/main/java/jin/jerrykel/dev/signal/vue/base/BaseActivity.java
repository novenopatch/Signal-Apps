package jin.jerrykel.dev.signal.vue.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;

/**
 * Created by JerrykelDEV on 22/11/2020 16:22
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected User modelCurrentUser;
    protected boolean isLogin;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onStart() {
        super.onStart();
        addAuthStateListener();


    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(this.getLayout());
        if(isCurrentUserLogged()){
            getCurrentUserFromFirestore();
        }

        this.initView();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //addAuthStateListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeAuthStateListener();
    }


    protected void addAuthStateListener(){
        authStateListener = firebaseAuth -> {
            FirebaseUser currentUser1 =firebaseAuth.getCurrentUser();
            if(currentUser1 != null){
                isLogin = true;
                UserHelper.getUser(currentUser1.getUid()
                ).addOnSuccessListener(
                        documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class)
                );

                //updateUI(currentUser);
            }else {
                isLogin = false;
                //updateUI(currentUser);
            }
        };

        mAuth  = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);
    }
    protected void removeAuthStateListener(){

        mAuth.removeAuthStateListener(authStateListener);
    }
    @Nullable
    public FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    protected Boolean isCurrentUserLogged(){
        FirebaseUser firebaseUser = getCurrentUser();
        if(firebaseUser != null){
            isLogin = true;
        }
        else {
            isLogin = false;
        }
        return isLogin;
    }
    protected abstract int getLayout();
    protected abstract void initView();
    protected void configureToolbar(){
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }




    protected OnFailureListener onFailureListener(){
        return e -> Toast.makeText(getApplicationContext(), getString(R.string.error_unknown_error), Toast.LENGTH_LONG).show();
    }

    protected void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(
                documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class)
        );
    }
    protected boolean updateModelWhenCreating(){


        if (getCurrentUser() != null){
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                User currentUser = documentSnapshot.toObject(User.class);
                modelCurrentUser = currentUser;

                if(currentUser==null || currentUser.getEmail()==null || currentUser.getUsername()==null || currentUser.getDisable()){
                    isLogin= false;
                }else {
                    isLogin =true;
                }
            });


        }
        return isLogin;
    }


}
