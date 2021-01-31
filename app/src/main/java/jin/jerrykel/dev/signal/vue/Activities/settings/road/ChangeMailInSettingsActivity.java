package jin.jerrykel.dev.signal.vue.Activities.settings.road;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;

public class ChangeMailInSettingsActivity extends BaseActivity {
        TextView  textViewEmail;
        EditText editTextTextEmailAddress;
        Controler controler;
        User user;
    private ProgressBar progressBarU;


    @Override
    protected int getLayout() {
        return R.layout.activity_change_mail_in_settings;
    }

    @Override
    protected void initView() {
        controler = Controler.getInstance(this);
        User user = controler.getUser();

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        textViewEmail = findViewById(R.id.textViewEmail);
        progressBarU = findViewById(R.id.progressBarU);
        if(user!=null){
            textViewEmail.setText(user.getEmail());
            editTextTextEmailAddress.setText(user.getEmail());
        }
        else {
            progressBarU.setVisibility(View.VISIBLE);
            updateUIWhenCreating();
            if(user!=null){
                textViewEmail.setText(user.getEmail());
                editTextTextEmailAddress.setText(user.getEmail());
                controler.setUser(user);
            }else {
                onRestart();
            }
        }

    }

    public void myOnClick(View view) {
        if(editTextTextEmailAddress.getText().toString()!=null){
            if(editTextTextEmailAddress.getText().toString() !=user.getEmail()){

            }
        }

    }
    private void updateUIWhenCreating(){

        if (getCurrentUser() != null){
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                progressBarU.setVisibility(View.GONE);
                 user = documentSnapshot.toObject(User.class);



            });


        }
    }
}