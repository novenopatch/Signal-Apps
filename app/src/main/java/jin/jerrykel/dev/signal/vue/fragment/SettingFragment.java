package jin.jerrykel.dev.signal.vue.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;


import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.AppsActivity;
import jin.jerrykel.dev.signal.vue.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Controler controler;
    private  View rootView ;
    //FOR DESIGN
     ImageView imageViewProfile;
     TextInputEditText textInputEditTextUsername;
     TextView textViewEmail;
     ProgressBar progressBar;
     Button profileActivityButtonUpdate;
    // Button profileActivityButtonSignOut;
     Button profileActivityButtonDelete;
     Context context;
     LinearLayout linearLayout;

     //CheckBox profileActivityCheckBoxIsMentor;

    // 4 - Adding requests to button listeners
    private View.OnClickListener onClickUpdateButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateUsernameInFirebase();
        }


    };


    private View.OnClickListener onClickDeleteButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(rootView.getContext())
                    .setMessage(R.string.popup_message_confirmation_delete_account)
                    .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteUserFromFirebase();
                        }
                    })
                    .setNegativeButton(R.string.popup_message_choice_no, null)
                    .show();
        }


    };

    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;
    private static final int UPDATE_USERNAME = 30;
    private View.OnClickListener OnClickListenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startSuperActivity();
        }
    };


    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controler = Controler.getInstance();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        context = rootView.getContext();
        initView(rootView);
        return rootView;
    }
    private void initView(View rootView){
        imageViewProfile = rootView.findViewById(R.id.img_profile);
        textInputEditTextUsername  = rootView.findViewById(R.id.profile_activity_edit_text_username);
        textViewEmail = rootView.findViewById(R.id.profile_activity_text_view_email);
        progressBar = rootView.findViewById(R.id.profile_activity_progress_bar);
        profileActivityButtonUpdate = rootView.findViewById(R.id.profileActivityButtonUpdate);
        profileActivityButtonDelete = rootView.findViewById(R.id.profileActivityButtonDelete);
        //profileActivityButtonSignOut = rootView.findViewById(R.id.profileActivityButtonSignOut);
        //profileActivityCheckBoxIsMentor = rootView.findViewById(R.id.profileActivityCheckBoxIsMentor);
        linearLayout = rootView.findViewById(R.id.linearLayout);




        updateUIWhenCreating();
        addClickListener();
    }
    private void updateUIWhenCreating(){

        if (controler.getCurrentUser() != null){

            if (controler.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(controler.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewProfile);
            }

            String email = TextUtils.isEmpty(controler.getCurrentUser().getEmail())
                    ? getString(R.string.info_no_email_found) : controler.getCurrentUser().getEmail();

            this.textViewEmail.setText(email);

            // 7 - Get additional data from Firestore (isMentor & Username)
            UserHelper.getUser(controler.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    //profileActivityCheckBoxIsMentor.setChecked(currentUser.getIsMentor());
                    textInputEditTextUsername.setText(username);
                    if(currentUser.getIsMentor()){
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,30,0,0);
                        Button button = new Button(context);
                        button.setLayoutParams(params);
                        button.setTextColor(Color.WHITE);
                        button.setText("Control");
                        button.setOnClickListener(OnClickListenerButton);
                        linearLayout.addView(button);
                    }
                }
            });
        }
    }
    private void  addClickListener(){
        profileActivityButtonUpdate.setOnClickListener(onClickUpdateButton);
       // profileActivityButtonSignOut.setOnClickListener(onClickSignOutButton);
        profileActivityButtonDelete.setOnClickListener(onClickDeleteButton);
       // profileActivityCheckBoxIsMentor.setOnClickListener(onClickCheckBoxIsMentor);
    }




    // --------------------
    // REST REQUESTS
    // --------------------
    // 1 - Create http requests (SignOut & Delete)

    /**
     *   private View.OnClickListener onClickSignOutButton = new View.OnClickListener() {
     *         @Override
     *         public void onClick(View v) {
     *             signOutUserFromFirebase();
     *         }
     *
     *
     *     };
     * private void signOutUserFromFirebase(){
     *         AuthUI.getInstance()
     *                 .signOut(rootView.getContext()).addOnSuccessListener((AppsActivity)rootView.getContext(), this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
     *     }
     */


    private void deleteUserFromFirebase(){
        if (controler.getCurrentUser() != null) {
            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(controler.getCurrentUser().getUid()).addOnFailureListener(controler.onFailureListener(rootView.getContext()));
            AuthUI.getInstance()
                    .delete(rootView.getContext())
                    .addOnSuccessListener((AppsActivity)rootView.getContext(), this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));

        }
    }


    // 3 - Update User Username
    private void updateUsernameInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String username = this.textInputEditTextUsername.getText().toString();

        if (controler.getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                UserHelper.updateUsername(username,
                        controler.getCurrentUser().getUid()
                ).addOnFailureListener(
                        controler.onFailureListener(rootView.getContext())
                ).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }

    /**
     * private View.OnClickListener onClickCheckBoxIsMentor = new View.OnClickListener() {
     *         @Override
     *         public void onClick(View v) {
     *             updateUserIsMentor();
     *         }
     *
     *
     *     };
     *  // 2 - Update User Mentor (is or not)
     *     private void updateUserIsMentor(){
     *         if (controler.getCurrentUser() != null) {
     *             UserHelper.updateIsMentor(controler.getCurrentUser().getUid(), this.profileActivityCheckBoxIsMentor.isChecked()).addOnFailureListener(controler.onFailureListener(rootView.getContext()));
     *         }
     *     }
     */







    // 3 - Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        //((AppsActivity)rootView.getContext()).finish();
                        startLoginActivity();
                        break;
                    case DELETE_USER_TASK:
                       // ((AppsActivity)rootView.getContext()).finish();
                        startLoginActivity();
                        break;
                    case UPDATE_USERNAME:
                        progressBar.setVisibility(View.INVISIBLE);
                    default:
                        break;
                }
            }
        };
    }

    // 3 - Launching Profile Activity
    private void startLoginActivity(){
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
    private void startSuperActivity(){
        //TODO
    }

}