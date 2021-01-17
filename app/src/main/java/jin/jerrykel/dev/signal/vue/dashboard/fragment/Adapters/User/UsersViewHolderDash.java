package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.User;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.utils.Utils;


public class UsersViewHolderDash extends RecyclerView.ViewHolder {



    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewEmail;


    private ImageButton imageButtonDelete;
    private Switch switchDisableUser;
    private Switch switchMakeOrDisableMentor;
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
        this.textViewEmail = superView.findViewById(R.id.textViewEmail);
        this.switchDisableUser = superView.findViewById(R.id.switchDisableUser);
        this.switchMakeOrDisableMentor = superView.findViewById(R.id.switchMakeOrDisableMentor);
        this.progressBar = superView.findViewById(R.id.progressBar);



    }
    public void updateWithMessage(final User user,@NonNull final User root){

        this.textViewName.setText(user.getUsername());
        this.textViewEmail.setText(user.getEmail());
        //TODO
        this.textViewDate.setText(Utils.convertDateToString(user.getDateCreated()));
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
        this.switchDisableUser.setOnClickListener(v -> {

                    if(switchDisableUser.isChecked()){
                        new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                                .setMessage("Are you sure?")
                                .setPositiveButton("YES", (dialog, which) -> {
                                    progressBar.setVisibility(View.VISIBLE);
                                    UserHelper.updateUserDisableOrNot(user.getUid(),true);
                                    //Todo
                                    // Perform Action & Dismiss dialog
                                    dialog.dismiss();

                                })
                                .setNegativeButton("NO", (dialog, which) -> {
                                    // Do nothing
                                    dialog.dismiss();
                                })
                                .create()
                                .show();

                    }else {
                        new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                                .setMessage("Are you sure?")
                                .setPositiveButton("YES", (dialog, which) -> {
                                    progressBar.setVisibility(View.VISIBLE);
                                    UserHelper.updateUserDisableOrNot(user.getUid(),false);
                                    // Perform Action & Dismiss dialog
                                    dialog.dismiss();

                                })
                                .setNegativeButton("NO", (dialog, which) -> {
                                    // Do nothing
                                    dialog.dismiss();
                                })
                                .create()
                                .show();
                    }
        });
        this.switchMakeOrDisableMentor.setOnClickListener(
                v -> {
        if(switchMakeOrDisableMentor.isChecked()){
            new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        progressBar.setVisibility(View.VISIBLE);
                        UserHelper.updateIsMentor(user.getUid(),true);
                        //Todo
                        // Perform Action & Dismiss dialog
                        dialog.dismiss();

                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        // Do nothing
                        dialog.dismiss();
                    })
                    .create()
                    .show();

        }else {
            new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        progressBar.setVisibility(View.VISIBLE);
                        UserHelper.updateIsMentor(user.getUid(),false);
                        // Perform Action & Dismiss dialog
                        dialog.dismiss();

                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        // Do nothing
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        }
        });
       // if(root.getMentor() && !user.getRoot()  && !user.getMentor()){

       // }







    }

        private void deleteUserFromFirebase(User user){
            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(user.getUid()).addOnFailureListener(this.onFailureListener());
            AuthUI.getInstance()
                    .delete(rootView.getContext())
                    .addOnCompleteListener(
                            updateUIAfterRESTRequestsCompleted()
                    );

        }

    private OnFailureListener onFailureListener() {
        return o -> {
            //Todo

        };
    }

    private OnCompleteListener updateUIAfterRESTRequestsCompleted() {
        return o -> {
                if(o.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    //TODO
                }
        };
    }


}
