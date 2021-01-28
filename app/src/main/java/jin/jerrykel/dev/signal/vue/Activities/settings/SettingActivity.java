package jin.jerrykel.dev.signal.vue.Activities.settings;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import jin.jerrykel.dev.signal.BuildConfig;
import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.Activities.OpenSourceActivity;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.AboutActivity;
import jin.jerrykel.dev.signal.vue.Activities.settings.road.AccountActivity;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.dashboard.DashboardActivity;

public class SettingActivity extends BaseActivity {



    private Button buttonUsername;
    private Button buttonEmail;
    private Button buttonVersion;
    private String preUsername;
    private String preEmail;


    private ProgressBar progressBarAccount;

    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }
    @Override
    public void initView(){

        buttonUsername = findViewById(R.id.buttonUsername);
        buttonEmail = findViewById(R.id.buttonEmail);
        buttonVersion = findViewById(R.id.buttonVersion);
        progressBarAccount = findViewById(R.id.progressBarAccount);
        preEmail = buttonEmail.getText().toString();
        preUsername = buttonUsername.getText().toString();








        updateUIWhenCreating();

    }
    private void updateUIWhenCreating(){
        String version = buttonVersion.getText().toString();
        buttonVersion.setText(version.concat(" :").concat("\n").concat(BuildConfig.VERSION_NAME));
        progressBarAccount.setVisibility(View.VISIBLE);

        if (getCurrentUser() != null){



            final ImageView  imageViewDashboard = findViewById(R.id.imageViewDashboard);
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                progressBarAccount.setVisibility(View.INVISIBLE);
                User currentUser = documentSnapshot.toObject(User.class);
                if(currentUser !=null){
                    String email = TextUtils.isEmpty(currentUser.getEmail()) ? getString(R.string.info_no_email_found) : getCurrentUser().getEmail();
                    String username = currentUser.getUsername();
                    this.buttonUsername.setText(preUsername.concat(" :").concat("\n").concat(username));
                    this.buttonEmail.setText(preEmail.concat(" :").concat("\n").concat(email));
                    //TODO
                    //DAsh
                    if(currentUser.getMentor()){
                        imageViewDashboard.setVisibility(View.VISIBLE);
                        imageViewDashboard.setOnClickListener(v -> startSuperActivity());
                    }else {
                        imageViewDashboard.setVisibility(View.INVISIBLE);
                    }

                }else {
                    finish();
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
            case R.id.linearLayoutAccount:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.buttonVersion:
                ///
                ///
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case  R.id.buttonInviteFriend:
                ///
                ///

                break;
            case  R.id.buttonOpenSourceLicence:
                ///
                ///
                startActivity(new Intent(this, OpenSourceActivity.class));

                break;

            default:
                break;


        }
    }








}