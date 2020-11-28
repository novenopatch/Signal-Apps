package jin.jerrykel.dev.signal.vue.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.vue.AppsActivity;

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
    private TextView textViewUserName;
    private TextView textViewEmail;
    private ImageView imageViewProfile;
    //FOR DATA
    // 2 - Identify each Http Request
    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;



    public SettingFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
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
        initView(rootView);
        return rootView;
    }
    private void initView(View rootView){
        textViewEmail = rootView.findViewById(R.id.textViewEmail);
        textViewUserName = rootView.findViewById(R.id.textViewUserName);
        imageViewProfile = rootView.findViewById(R.id.img_profile);
        updateUIWhenCreating();
    }
    private void  updateUIWhenCreating(){
        if (controler.getCurrentUser() != null){

            //Get picture URL from Firebase
            if (controler.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this).load(controler.getCurrentUser().getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewProfile);
            }
        //Get email & username from Firebase
        String email = TextUtils.isEmpty(controler.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : controler.getCurrentUser().getEmail();
        String username = TextUtils.isEmpty(controler.getCurrentUser().getDisplayName()) ? getString(R.string.info_no_username_found) : controler.getCurrentUser().getDisplayName();

        //Update views with data
        this.textViewUserName.setText(username);
        this.textViewEmail.setText(email);

        }
    }




    // 4 - Adding requests to button listeners

   // @OnClick(R.id.profile_activity_button_sign_out)
    public void onClickSignOutButton(View view) { this.signOutUserFromFirebase(); }

   // @OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton(View view) {
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

    // --------------------
    // REST REQUESTS
    // --------------------
    // 1 - Create http requests (SignOut & Delete)

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(rootView.getContext()).addOnSuccessListener((AppsActivity)rootView.getContext(), this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private void deleteUserFromFirebase(){
        if (controler.getCurrentUser() != null) {
            AuthUI.getInstance()
                    .delete(rootView.getContext())
                    .addOnSuccessListener((AppsActivity)rootView.getContext(), this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(controler.getCurrentUser().getUid()).addOnFailureListener(controler.onFailureListener(rootView.getContext()));
        }
    }

    // --------------------
    // UI
    // --------------------



    // 3 - Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        ((AppsActivity)rootView.getContext()).finish();
                        break;
                    case DELETE_USER_TASK:
                        ((AppsActivity)rootView.getContext()).finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public void onClickUpdateButton(View view) { this.updateUsernameInFirebase(); }

   // @OnClick(R.id.profile_activity_check_box_is_mentor)
    public void onClickCheckBoxIsMentor(View view) { this.updateUserIsMentor(); }

    // 3 - Update User Username
    private void updateUsernameInFirebase(){

        //this.progressBar.setVisibility(View.VISIBLE);
        String username = this.textViewUserName.getText().toString();

        if (controler.getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                //UserHelper.updateUsername(username, controler.getCurrentUser().getUid()).addOnFailureListener(controler.onFailureListener(rootView.getContext())).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }

    // 2 - Update User Mentor (is or not)
    private void updateUserIsMentor(){
        if (controler.getCurrentUser() != null) {
            //UserHelper.updateIsMentor(controler.getCurrentUser().getUid(), this.checkBoxIsMentor.isChecked()).addOnFailureListener(controler.onFailureListener(rootView.getContext()));
        }
    }

    // --------------------
    // UI
    // --------------------


}