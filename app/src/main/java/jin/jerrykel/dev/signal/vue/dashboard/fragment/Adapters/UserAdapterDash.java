package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.utils.Utils;


public class UserAdapterDash extends FirestoreRecyclerAdapter<User, UserAdapterDash.UsersViewHolderDash> {

    private User curentUser;
    public interface Listener {
        void onDataChanged();
    }

    //FOR COMMUNICATION
    private Listener callback;

    public UserAdapterDash(@NonNull FirestoreRecyclerOptions<User> options, Listener callback, User user) {
        super(options);
        this.callback = callback;
        this.curentUser = user;
        //Log.e("UserAdapterDash",user.toString());
    }
    public static class UsersViewHolderDash extends RecyclerView.ViewHolder {



        TextView textViewName;
        TextView textViewDate;
        TextView textViewEmail;


        ImageButton imageButtonDelete;
        Switch switchDisableUser;
        Switch switchMakeOrDisableMentor;
        View rootView;
        ProgressBar progressBar;



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



    }
    @Override
    public UsersViewHolderDash onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersViewHolderDash(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolderDash holder, int position, @NonNull User model) {

        holder.textViewName.setText(model.getUsername());
        holder.textViewEmail.setText(model.getEmail());

        holder.textViewDate.setText(Utils.convertDateToString(model.getDateCreated()));
        if(model.getDisable()){
            holder.switchDisableUser.setChecked(false);
            holder.switchDisableUser.setText("User is disable");
            updateUiViewColor(holder.textViewName,holder.switchDisableUser,false);
        }else{
            holder.switchDisableUser.setChecked(true);
            holder.switchDisableUser.setText("User is active");
            updateUiViewColor(holder.textViewName,holder.switchDisableUser,true);

        }
        if(model.getMentor()){
            holder.switchMakeOrDisableMentor.setChecked(true);
            holder.switchMakeOrDisableMentor.setText("User is mentor");
        }else {
            holder.switchMakeOrDisableMentor.setChecked(false);
            holder.switchMakeOrDisableMentor.setText("User is not mentor");
        }
        deleteAction(holder.imageButtonDelete,model,holder.rootView.getContext(),holder.progressBar);
        switchDisableUserAction(holder,model,holder.rootView.getContext(),holder.progressBar);
        switchMakeOrDisableMentorAction(holder.switchMakeOrDisableMentor,model,holder.rootView.getContext(),holder.progressBar);


    }
    private void updateUiViewColor(TextView view,Switch switchView,Boolean bool){
        if(bool){
            view.setTextColor(Color.GREEN);
            switchView.setTextColor(Color.GREEN);
        }
        else {
            view.setTextColor(Color.RED);
            switchView.setTextColor(Color.RED);
        }
    }
    private void  switchDisableUserAction(UsersViewHolderDash holder,User user,Context context,ProgressBar progressBar){
        holder.switchDisableUser.setOnClickListener(v -> {
            if(!holder.switchDisableUser.isChecked() && !user.getDisable() && !user.getRoot()){
                new AlertDialog.Builder(context).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            progressBar.setVisibility(View.VISIBLE);
                            UserHelper.updateUserDisableOrNot(
                                    user.getUid(),true
                            ).addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    holder.switchDisableUser.setText("User is disable");
                                    updateUiViewColor(holder.textViewName,holder.switchDisableUser,false);

                                }
                                else {
                                    //TODO
                                }

                            });

                            dialog.dismiss();

                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();

            }else {
                if(user.getDisable() ){
                    new AlertDialog.Builder(context).setTitle("Confirm ?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("YES", (dialog, which) -> {
                                progressBar.setVisibility(View.VISIBLE);
                                UserHelper.updateUserDisableOrNot(
                                        user.getUid(),false
                                ).addOnCompleteListener(task -> {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        holder.switchDisableUser.setText("User is active");
                                        updateUiViewColor(holder.textViewName,holder.switchDisableUser,true);
                                    }
                                    else {
                                        //TODO
                                    }

                                });
                                dialog.dismiss();

                            })
                            .setNegativeButton("NO", (dialog, which) -> {
                                // Do nothing
                                dialog.dismiss();
                            })
                            .create()
                            .show();
                }
            }
        });

    }

    private void switchMakeOrDisableMentorAction(Switch switchMakeOrDisableMentor, User user, Context context,ProgressBar progressBar){
        switchMakeOrDisableMentor.setOnClickListener(
                v -> {

                    if(switchMakeOrDisableMentor.isChecked()  && !user.getMentor()){
                        new AlertDialog.Builder(context).setTitle("Confirm ?")
                                .setMessage("Are you sure?")
                                .setPositiveButton("YES", (dialog, which) -> {
                                    progressBar.setVisibility(View.VISIBLE);
                                    UserHelper.updateIsMentor(
                                            user.getUid(),true
                                    ).addOnCompleteListener(task -> {
                                        if(task.isSuccessful()){
                                            progressBar.setVisibility(View.INVISIBLE);
                                            user.setMentor(true);
                                            switchMakeOrDisableMentor.setText("User is mentor");
                                            notifyDataSetChanged();
                                        }
                                        else {
                                            //TODO
                                        }
                                    });

                                    dialog.dismiss();

                                })
                                .setNegativeButton("NO", (dialog, which) -> {
                                    // Do nothing
                                    dialog.dismiss();
                                })
                                .create()
                                .show();

                    }else {
                        if(!user.getRoot() ){
                            new AlertDialog.Builder(context).setTitle("Confirm ?")
                                    .setMessage("Are you sure?")
                                    .setPositiveButton("YES", (dialog, which) -> {
                                        progressBar.setVisibility(View.VISIBLE);
                                        UserHelper.updateIsMentor(
                                                user.getUid(),false
                                        ).addOnCompleteListener(task -> {
                                            if(task.isSuccessful()){
                                                progressBar.setVisibility(View.INVISIBLE);
                                                user.setMentor(false);
                                                switchMakeOrDisableMentor.setText("User is not Mentor");
                                            }
                                            else {
                                                //TODO
                                            }
                                        });
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

                    }
                });


    }





    private OnFailureListener onFailureListener() {
        return o -> {
            //Todo

        };
    }


    private void deleteAction(ImageButton  imageButtonDelete,User user,Context context,ProgressBar progressBar){
        imageButtonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        progressBar.setVisibility(View.VISIBLE);
                        UserHelper.deleteUser(user.getUid()).addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        //TODO
                                    }
                                }
                        ).addOnFailureListener(this.onFailureListener());
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
