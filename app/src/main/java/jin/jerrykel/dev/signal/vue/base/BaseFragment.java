package jin.jerrykel.dev.signal.vue.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;


public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected  Context  context;
    protected User modelCurrentUser;
    protected boolean isLogin;
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener authStateListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void onStart() {
        super.onStart();
        addAuthStateListener();


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(this.getLayout(), container, false);
        context = this.rootView.getContext();
        this.initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        addAuthStateListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        removeAuthStateListener();
    }

    public abstract int getLayout();
    public abstract void initView();


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
        FirebaseUser firebaseUser = this.getCurrentUser();
        if(firebaseUser != null){
            isLogin = true;
        }
        else {
            isLogin = false;
        }
        return isLogin;
    }

    public OnFailureListener onFailureListener(final CoordinatorLayout coordinatorLayout, final String message){
        return e -> showSnackBar(coordinatorLayout,message);
    }
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        // Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }

}