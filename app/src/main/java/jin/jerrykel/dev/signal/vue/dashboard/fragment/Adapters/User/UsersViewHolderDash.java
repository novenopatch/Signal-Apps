package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.User;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;


public class UsersViewHolderDash extends RecyclerView.ViewHolder {



    private TextView textViewName;
    private TextView textViewDate;


    private ImageButton imageButtonDelete;
    private View rootView;
    private ProgressBar progressBar;



    public UsersViewHolderDash(View itemView) {
        super(itemView);

        rootView = itemView;
        initView(itemView);


    }
    private void initView(View superView){

        this.textViewName = superView.findViewById(R.id.textViewName);

        this.textViewDate = superView.findViewById(R.id.textViewDate);
        this.imageButtonDelete = superView.findViewById(R.id.imageButtonDelete);

        this.progressBar = superView.findViewById(R.id.progressBar);



    }
    public void updateWithMessage(final User user,final User root){

        this.textViewName.setText(user.getUsername());
        //TODO
        //this.textViewDate.setText(Utils.convertDateToString(user.getDateCreated()));
        if(root.getMentor() && !user.getRoot()  && !user.getMentor()){
            imageButtonDelete.setOnClickListener(v -> {
                new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            progressBar.setVisibility(View.VISIBLE);
                           deleteUserFromFirebase(user);
                            // Perform Action & Dismiss dialog
                            dialog.dismiss();

                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();
                });
        }







    }

        private void deleteUserFromFirebase(User user){
            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(user.getUid()).addOnFailureListener(this.onFailureListener());
            AuthUI.getInstance()
                    .delete(rootView.getContext())
                    .addOnSuccessListener( updateUIAfterRESTRequestsCompleted());

        }

    private OnFailureListener onFailureListener() {
        return o -> {
            //Todo

        };
    }

    private OnSuccessListener updateUIAfterRESTRequestsCompleted() {
        return o -> {
                progressBar.setVisibility(View.INVISIBLE);
        };
    }


}
