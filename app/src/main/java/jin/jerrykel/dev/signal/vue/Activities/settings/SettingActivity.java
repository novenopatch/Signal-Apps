package jin.jerrykel.dev.signal.vue.Activities.settings;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.AboutActivity;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.AccountActivity;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.HelpActivity;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.ProfileActivity;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.dashboard.DashboardActivity;

public class SettingActivity extends BaseActivity {

    private ImageView imageViewProfile;

    private TextView textInputEditTextUsername;
    private TextView textViewEmail;
    private LinearLayout linearLayoutProfile;
    private LinearLayout linearLayoutAccount;
    private LinearLayout linearLayoutHelp;
    private LinearLayout linearLayoutAbout;
    private LinearLayout linearLayoutInvite;
    private LinearLayout linearLayoutINC;


    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }
    @Override
    public void initView(){
        imageViewProfile = findViewById(R.id.img_profile);
        textInputEditTextUsername  = findViewById(R.id.profile_activity_edit_text_username);
        textViewEmail = findViewById(R.id.profile_activity_text_view_email);


        linearLayoutProfile = findViewById(R.id.linearLayoutProfile);
        linearLayoutAccount = findViewById(R.id.linearLayoutAccount);
        linearLayoutHelp = findViewById(R.id.linearLayoutHelp);
        linearLayoutAbout = findViewById(R.id.linearLayoutAbout);
        linearLayoutInvite = findViewById(R.id.linearLayoutInvite);
        linearLayoutINC = findViewById(R.id.linearLayoutINC);





        updateUIWhenCreating();

    }
    private void updateUIWhenCreating(){


        if (getCurrentUser() != null){

            if (getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewProfile);

            }

            String email = TextUtils.isEmpty(getCurrentUser().getEmail())
                    ? getString(R.string.info_no_email_found) : getCurrentUser().getEmail();

            this.textViewEmail.setText(email);
            String username = getCurrentUser().getDisplayName();
            textInputEditTextUsername.setText(username);
            /*

            // 7 - Get additional data from Firestore (isMentor & Username)
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    //profileActivityCheckBoxIsMentor.setChecked(currentUser.getIsMentor());
                    textInputEditTextUsername.setText(username);
                }
            });

             */
            final ImageView  imageViewDashboard = findViewById(R.id.imageViewDashboard);
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                   // String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    //profileActivityCheckBoxIsMentor.setChecked(currentUser.getIsMentor());
                    //textInputEditTextUsername.setText(username);
                    if(currentUser.getIsMentor()){
                        imageViewDashboard.setVisibility(View.VISIBLE);
                        imageViewDashboard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startSuperActivity();
                            }
                        });
                    }else {
                        imageViewDashboard.setVisibility(View.INVISIBLE);
                    }

                }
            });
        }
    }
    private void startSuperActivity(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        //finish();
    }
    public  void linearLayoutOnclick(View view){
        switch (view.getId()){
            case R.id.linearLayoutProfile:
                ///
                ///
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.linearLayoutAccount:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.linearLayoutHelp:
                ///
                ///
                startActivity(new Intent(this, HelpActivity.class));
                break;

            case R.id.linearLayoutAbout:
                ///
                ///
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case  R.id.linearLayoutInvite:
                ///
                ///

                break;
            case R.id.linearLayoutINC:
                ///
                ///
                break;

            default:
                break;


        }
    }




















    /*




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
            new AlertDialog.Builder(getApplicationContext())
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

    private void initView(){
        imageViewProfile = findViewById(R.id.img_profile);
        textInputEditTextUsername  = findViewById(R.id.profile_activity_edit_text_username);
        textViewEmail = findViewById(R.id.profile_activity_text_view_email);
        progressBar = findViewById(R.id.profile_activity_progress_bar);
        profileActivityButtonUpdate = findViewById(R.id.profileActivityButtonUpdate);
        profileActivityButtonDelete = findViewById(R.id.profileActivityButtonDelete);
        //profileActivityButtonSignOut = findViewById(R.id.profileActivityButtonSignOut);
        //profileActivityCheckBoxIsMentor = findViewById(R.id.profileActivityCheckBoxIsMentor);
        linearLayout = findViewById(R.id.linearLayout);




        updateUIWhenCreating();
        addClickListener();

    }
     private void updateUIWhenCreating(){

        if (getCurrentUser() != null){

            if (getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewProfile);
            }

            String email = TextUtils.isEmpty(getCurrentUser().getEmail())
                    ? getString(R.string.info_no_email_found) : getCurrentUser().getEmail();

            this.textViewEmail.setText(email);

            // 7 - Get additional data from Firestore (isMentor & Username)
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    //profileActivityCheckBoxIsMentor.setChecked(currentUser.getIsMentor());
                    textInputEditTextUsername.setText(username);
                    if(!currentUser.getIsMentor()){
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0,30,0,0);
                        Button button = new Button(SettingActivity.this);
                        button.setLayoutParams(params);
                        button.setTextColor(Color.WHITE);
                        button.setText("Control");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startSuperActivity();
                            }
                        });
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
     private void deleteUserFromFirebase(){
        if (getCurrentUser() != null) {
            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(getCurrentUser().getUid()).addOnFailureListener(onFailureListener());
            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));

        }
    }
     */






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
     *                 .signOut(getContext()).addOnSuccessListener((AppsActivity)getContext(), this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
     *     }
     */
/*



 */

/**
    // 3 - Update User Username
    private void updateUsernameInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String username = this.textInputEditTextUsername.getText().toString();

        if (getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                UserHelper.updateUsername(username,
                        getCurrentUser().getUid()
                ).addOnFailureListener(
                        onFailureListener()
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
     *         if (getCurrentUser() != null) {
     *             UserHelper.updateIsMentor(getCurrentUser().getUid(), this.profileActivityCheckBoxIsMentor.isChecked()).addOnFailureListener(onFailureListener(getContext()));
     *         }
     *     }
     */






    /**
     *

    // 3 - Create OnCompleteListener called after tasks ended
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case SIGN_OUT_TASK:
                        //((AppsActivity)getContext()).finish();
                        startLoginActivity();
                        break;
                    case DELETE_USER_TASK:
                        // ((AppsActivity)getContext()).finish();
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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

     **/

}