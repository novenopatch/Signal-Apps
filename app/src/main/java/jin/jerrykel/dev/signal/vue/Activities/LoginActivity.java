package jin.jerrykel.dev.signal.vue.Activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.vue.Activities.Main.MainActivity;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    private CoordinatorLayout coordinatorLayout;
    private Button  buttonLogin;
    private int milis = 2000;


    @Override
    protected void onStart() {
        super.onStart();
        updateModelWhenCreating();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }
    @Override
    public void initView(){

        coordinatorLayout = findViewById(R.id.main_activity_coordinator_layout);
        buttonLogin =  findViewById(R.id.buttonLogin);
    }

    public void login(View v){

        startAppropriateActivity();
    }
    public void startAppropriateActivity() {
        // 4 - Start appropriate activity
        if (isCurrentUserLogged()){
            this.startAppActivity();
        } else {
            this.startSignInActivity();
        }

    }

    private void startAppActivity(){
        if(!ifInternet()){
            buttonLogin.setText("You are in offline");
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void startSignInActivity(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build()
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                //new AuthUI.IdpConfig.GoogleBuilder().build()
                //new AuthUI.IdpConfig.FacebookBuilder().build(),
                //new AuthUI.IdpConfig.TwitterBuilder().build()
                );
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false,true)
                        .setLogo(R.drawable.ic_baseline_account_circle_white_24)
                        .build(),
                RC_SIGN_IN);
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4 - Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    private void handleResponseAfterSignIn( int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                // 2 - CREATE USER IN FIRESTORE
                if(getCurrentUser()!= null && !UserHelper.ifUserIsExist(getCurrentUser().getUid())){
                    this.createUserInFirestore();
                }
                showSnackBar(this.coordinatorLayout, getString(R.string.SUCCESS));
                startAppActivity();


            } else { // ERRORS
                if (response == null) {

                    showSnackBar(this.coordinatorLayout, getString(R.string.error_authentication_canceled));
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {

                    showSnackBar(this.coordinatorLayout,  getString(R.string.error_no_internet));

                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {

                    showSnackBar(this.coordinatorLayout, getString(R.string.error_unknown_error));

                }
            }
        }
    }
    // 2 - Update UI when activity is resuming
    private void updateUIWhenResuming(){

        boolean isLogin =isCurrentUserLogged();
        this.buttonLogin.setText( isLogin? getString(R.string.button_login_text_logged) : getString(R.string.button_login_text_not_logged));
        ProgressBar progressBarC = findViewById(R.id.progressBarC);
        progressBarC.setVisibility(View.VISIBLE);
        Runnable runnable = () -> {
            // progressBarC.setVisibility(View.GONE);
            if(isLogin) {

                /*
                    if(modelCurrentUser.getDisable() || modelCurrentUser.isDeleteAction()){
                        buttonLogin.setEnabled(false);
                        if(modelCurrentUser.getDisable()){
                            this.buttonLogin.setText("We are sorry,Can not access  to server");

                        }else {
                            modelCurrentUser.isDeleteAction();
                            UserHelper.deleteAction(modelCurrentUser.getUid());
                            AuthUI.getInstance().delete(this).addOnSuccessListener(aVoid ->
                                    finish()
                            );
                        }
                    }else {
                    startAppActivity();
                }
                 */
                //TODO
                startAppActivity();

            }else {
                startSignInActivity();
            }
        };
        new Handler().postDelayed(runnable,milis);

    }
    private void createUserInFirestore(){

        if (getCurrentUser() != null){

            String urlPicture = (getCurrentUser().getPhotoUrl() != null) ? getCurrentUser().getPhotoUrl().toString() : null;
            String username = getCurrentUser().getDisplayName();
            String uid = getCurrentUser().getUid();
            String email = getCurrentUser().getEmail();

            UserHelper.createUser(uid, username,email, urlPicture)
                    .addOnFailureListener(
                            onFailureListener()
                    );
        }
    }
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
    // 1 - Http request that create user in firestore


    private boolean ifInternet(){
        ConnectivityManager connec = (ConnectivityManager)getSystemService(LoginActivity.CONNECTIVITY_SERVICE);
        if (connec != null &&
                ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))
        ) {
            //You are connected, do something online.
            return true;
            }
        else if (
                connec != null &&
                        ( (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED ))
        ) { //Not connected. Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
            return false;
             }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 5 - Update UI when activity is resuming
        this.updateUIWhenResuming();
    }





}