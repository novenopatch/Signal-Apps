package jin.jerrykel.dev.signal.vue.Activities;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.InfomationAppUser;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.Activities.main.MainActivity;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    //FOR DATA
    private static final int RC_SIGN_IN = 123;
    private CoordinatorLayout coordinatorLayout;
    private Button  buttonLogin;
    private final int millis = 2000;
    ProgressBar progressBarC;
    private User modelCurrentUser;
    public static InfomationAppUser infomationAppUser = new InfomationAppUser(new Date());

    private LinearLayout linearLayout;


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
       linearLayout = findViewById(R.id.linearLayoutContent);


        coordinatorLayout = findViewById(R.id.main_activity_coordinator_layout);
        buttonLogin =  findViewById(R.id.buttonLogin);
        progressBarC = findViewById(R.id.progressBarC);
        TextView textViewLineOrNot = findViewById(R.id.textViewLineOrNot);


        licenceCheck();

        new Handler().postDelayed(() -> {
            linearLayout.setVisibility(View.VISIBLE);
            progressBarC.setVisibility(View.VISIBLE);
        }, millis / 3);

        Runnable runnable = () -> {
            if(isCurrentUserLogged()) {
                this.buttonLogin.setText(getString(R.string.button_login_text_logged));
                if(!ifInternet()){
                    textViewLineOrNot.setText("You are in offline.");
                }

                startAppropriateActivity(true);
            }else {
                this.buttonLogin.setText(getString(R.string.button_login_text_not_logged));

            }
            progressBarC.setVisibility(View.GONE);
        };
        new Handler().postDelayed(runnable, millis);

    }
    private void startAppropriateActivity(Boolean bool) {
        if(bool){
            testUsers();
        }else {
            if (this.isCurrentUserLogged()){
                testUsers();

            } else {
                this.startSignInActivity();
            }
        }

    }
    private void testUsers(){
        if(modelCurrentUser!=null ){
            progressBarC.setVisibility(View.VISIBLE);

            if(modelCurrentUser.getDisable() || modelCurrentUser.isDeleteAction()){
                Log.d("testUser","leIf");
                buttonLogin.setEnabled(false);
                progressBarC.setVisibility(View.VISIBLE);
                if(modelCurrentUser.getDisable()){
                    //TODO
                    //this.buttonLogin.setText("We are sorry,Can not access  to WTG group server.");

                }else{
                    //this.buttonLogin.setText("We are sorry,Can not access  to WTG group server.");
                    //TODO
                    /*
                        UserHelper.deleteAction(modelCurrentUser.getUid());
                        AuthUI.getInstance().delete(this).addOnSuccessListener(aVoid ->{
                            //finish();
                            this.buttonLogin.setText("Can not access  to server");
                        });
                      */
                }
                Runnable runnable = () -> {
                    progressBarC.setVisibility(View.GONE);
                    this.buttonLogin.setText("We are sorry,Can not access  to WTG group server.");
                };
                new Handler().postDelayed(runnable, millis/2);
            }else {

                startAppActivity();
            }


        }

    }
    private void startAppActivity(){

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("email",modelCurrentUser.getEmail());
        intent.putExtra("userName",modelCurrentUser.getUsername());
        intent.putExtra("login",true);
        startActivity(intent);
        finish();
    }
    private void licenceCheck() {
        Controler controler = Controler.getInstance(this);
        controler.getManager().insertInformation(infomationAppUser);
        InfomationAppUser infomationAppUserR = controler.getManager().getInformation();
        if(infomationAppUserR!=null && infomationAppUserR.isFirstLaunch()){
            startLicenceActivity();
        }
    }
    private void startLicenceActivity(){
        Intent intent = new Intent(getApplicationContext(), LicenceAccepteActivity.class);
        startActivity(intent);
        finish();
    }

    public void login(View v){

        startAppropriateActivity(false);

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
                        .setLogo(R.mipmap.img_icone)
                        .build(),
                RC_SIGN_IN);
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 4 - Handle SignIn Activity response on activity result
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 5 - Update UI when activity is resuming
        //this.updateUIWhenResuming();
    }
    private void updateUIWhenResuming(){
        Runnable runnable = () -> {

            progressBarC.setVisibility(View.VISIBLE);
            if(isCurrentUserLogged()) {
                this.buttonLogin.setText(getString(R.string.button_login_text_logged));
                if(!ifInternet()){
                    buttonLogin.setText("You are in offline.");
                }
                progressBarC.setVisibility(View.GONE);
                startAppropriateActivity(true);
            }else {
                progressBarC.setVisibility(View.GONE);
                Log.d("progressBarC"," OnResume(InVisible)");
                this.buttonLogin.setText(getString(R.string.button_login_text_not_logged));

            }
        };

        new Handler().postDelayed(runnable, millis);

    }


    private void handleResponseAfterSignIn( int requestCode, int resultCode, Intent data){

        IdpResponse response = IdpResponse.fromResultIntent(data);


        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) { // SUCCESS
                // 2 - CREATE USER IN FIRESTORE
                if(!UserHelper.ifUserIsExist(getCurrentUser().getUid())){
                    this.createUserInFirestore();

                    getCurrentUser().sendEmailVerification();
                }
            }
            showSnackBar(this.coordinatorLayout, getString(R.string.SUCCESS));
            startAppropriateActivity(true);
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

    // 1 - Http request that create user in firestore




    protected void updateModelWhenCreating(){


        if (getCurrentUser() != null){
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                User currentUser = documentSnapshot.toObject(User.class);
                modelCurrentUser = currentUser;

                if(currentUser==null || currentUser.getEmail()==null || currentUser.getUsername()==null || currentUser.getDisable()){
                    isLogin= false;
                }else {
                    isLogin =true;
                }
            });


        }

    }
    private boolean ifInternet(){
        ConnectivityManager connec = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
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
    private void showSnackBar(CoordinatorLayout coordinatorLayout, String message){
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }


}