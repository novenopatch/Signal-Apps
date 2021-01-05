package jin.jerrykel.dev.signal.vue.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jin.jerrykel.dev.signal.R;


public class BaseFragment extends Fragment {


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