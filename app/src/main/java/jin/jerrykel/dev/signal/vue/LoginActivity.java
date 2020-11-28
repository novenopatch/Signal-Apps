package jin.jerrykel.dev.signal.vue;

import androidx.appcompat.app.AppCompatActivity;

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
import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;

public class LoginActivity extends BaseActivity {

    //FOR DATA
    // 1 - Identifier for Sign-In Activity
    private static final int RC_SIGN_IN = 123;
    private  @BindView(R.id.lineraLayoutLogin)LinearLayout linearLayout;
    private Controler controler;
    private @BindView(R.id.buttonLogin) Button  buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
         controler = Controler.getInstance();
         ButterKnife.bind(this);
         startAppropriateActivity();
    }

    public void login(View v){
        if(v.getId() == R.id.buttonLogin) {
            /*
            final EditText editextEmail = findViewById(R.id.editextEmail);
            final  EditText editextPassword = findViewById(R.id.editextPassword);
            if(!editextEmail.getText().toString().isEmpty() && !editextPassword.getText().toString().isEmpty()){
                Controler controler = Controler.getInstance();
                if(controler.connnect(editextEmail.getText().toString(),editextPassword.getText().toString())){

                    buttonLogin.setText("SUCCESS");
                    buttonLogin.setBackgroundColor(Color.GREEN);
                    Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"error TO DO",Toast.LENGTH_SHORT).show();
                    buttonLogin.setText("ERROR");
                    buttonLogin.setBackgroundColor(Color.RED);
                    editextEmail.setTextColor(Color.RED);
                    editextPassword.setTextColor(Color.RED);
                    int times = 1000;
                    new Handler().postDelayed(new Runnable() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void run() {
                            buttonLogin.setBackgroundColor(R.color.loginConnectBackgroundColor);
                            buttonLogin.setText(R.string.login);
                            editextEmail.setTextColor(Color.WHITE);
                            editextPassword.setTextColor(Color.WHITE);
                        }
                    }, times);
                }
            }
        }else if(v.getId() == R.id.textViewSinscrire){
            // 3 - Launch Sign-In Activity when user clicked on Login Button
            //this.startSignInActivity();
        }

             */
        }else if(v.getId() == R.id.textViewSinscrire){
                // 3 - Launch Sign-In Activity when user clicked on Login Button
                this.startSignInActivity();
            }

    }
    // --------------------
    // NAVIGATION
    // --------------------

    // 2 - Launch Sign-In Activity
    private void startSignInActivity(){
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
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



    // --------------------
    // UI
    // --------------------

    // 2 - Show Snack Bar with a message
    private void showSnackBar(LinearLayout linearLayout, String message){
        Snackbar.make(linearLayout, message, Snackbar.LENGTH_SHORT).show();
    }

    // --------------------
    // UTILS
    // --------------------

    // 3 - Method that handles response after SignIn Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                showSnackBar(this.linearLayout, "SUCCESS");
                Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
                startActivity(intent);
                finish();
            } else { // ERRORS
                if (response == null) {
                    //showSnackBar(this.linearLayout, getString(R.string.error_authentication_canceled));
                    showSnackBar(this.linearLayout, "cancel");
                } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    //showSnackBar(this.coordinatorLayout, getString(R.string.error_no_internet));
                    showSnackBar(this.linearLayout, "no connection");

                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    //showSnackBar(this.linearLayout, getString(R.string.error_unknown_error));
                    showSnackBar(this.linearLayout, "error inconnue");

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


    // 1 - Http request that create user in firestore
    private void createUserInFirestore(){

        if (controler.getCurrentUser() != null){

            String urlPicture = (controler.getCurrentUser().getPhotoUrl() != null) ? controler.getCurrentUser().getPhotoUrl().toString() : null;
            String username = controler.getCurrentUser().getDisplayName();
            String uid = controler.getCurrentUser().getUid();

            UserHelper.createUser(uid, username, urlPicture).addOnFailureListener(controler.onFailureListener(this));
        }
    }

    // 3 - Launching Profile Activity
    private void startAppActivity(){
        Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
        startActivity(intent);
        finish();
    }
    // 2 - Update UI when activity is resuming
    private void updateUIWhenResuming(){
        this.buttonLogin.setText(controler.isCurrentUserLogged() ? getString(R.string.button_login_text_logged) : getString(R.string.button_login_text_not_logged));
    }





}