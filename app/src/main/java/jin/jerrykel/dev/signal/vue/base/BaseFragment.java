package jin.jerrykel.dev.signal.vue.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public abstract class BaseFragment extends Fragment {

    protected View rootView;
    protected  Context  context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(this.getLayout(), container, false);
        context = this.rootView.getContext();
        this.initView();
        return rootView;
    }
    public abstract int getLayout();
    public abstract void initView();
    @Nullable
    public FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }

    public Boolean isCurrentUserLogged(){ return (this.getCurrentUser() != null); }

    public OnFailureListener onFailureListener(final CoordinatorLayout coordinatorLayout, final String message){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showSnackBar(coordinatorLayout,message);
            }
        };
    }
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
       // Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
}