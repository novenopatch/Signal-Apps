package jin.jerrykel.dev.signal.vue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;

public class LoginActivity extends BaseActivity {

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    @BindView(R.id.main_activity_coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.buttonLogin) Button  buttonLogin;
    private Controler controler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
         controler = Controler.getInstance();
         ButterKnife.bind(this);

    }

    public void login(View v){
        if(v.getId() == R.id.buttonLogin) {
            if (controler.isCurrentUserLogged()){
                this.startAppActivity();
            } else {
                this.startSignInActivity();
            }
        }else if(v.getId() == R.id.buttonChat){
                // 3 - Launch Sign-In Activity when user clicked on Login Button
            this.startAppropriateActivity();
            }

    }


    // 2 - Launch Sign-In Activity
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
                        .setLogo(R.drawable.ic_baseline_account_circle_black_24)
                        .build(),
                RC_SIGN_IN);
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4 - Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }


    // 2 - Show Snack Bar with a message
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }


    // 3 - Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                // 2 - CREATE USER IN FIRESTORE
                this.createUserInFirestore();
                showSnackBar(this.coordinatorLayout, getString(R.string.SUCCESS));
                Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
                startActivity(intent);
                finish();
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

    @Override
    protected void onResume() {
        super.onResume();
        // 5 - Update UI when activity is resuming
        this.updateUIWhenResuming();
    }


    public void startAppropriateActivity() {
        // 4 - Start appropriate activity
        if (controler.isCurrentUserLogged()){
            this.startAppActivity();
        } else {
            this.startSignInActivity();
        }

    }
    // 3 - Launching Profile Activity
    private void startAppActivity(){
        Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
        startActivity(intent);
        finish();
    }

    // 1 - Http request that create user in firestore
    private void createUserInFirestore(){

        if (controler.getCurrentUser() != null){

            String urlPicture = (controler.getCurrentUser().getPhotoUrl() != null) ? controler.getCurrentUser().getPhotoUrl().toString() : null;
            String username = controler.getCurrentUser().getDisplayName();
            String uid = controler.getCurrentUser().getUid();

            UserHelper.createUser(uid, username, urlPicture).addOnFailureListener(controler.onFailureListener(this));
        }
    }

    // 2 - Update UI when activity is resuming
    private void updateUIWhenResuming(){
        this.buttonLogin.setText(controler.isCurrentUserLogged() ? getString(R.string.button_login_text_logged) : getString(R.string.button_login_text_not_logged));
    }








}