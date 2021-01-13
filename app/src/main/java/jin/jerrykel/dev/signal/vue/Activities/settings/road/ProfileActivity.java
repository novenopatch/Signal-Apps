package jin.jerrykel.dev.signal.vue.Activities.settings.road;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private LinearLayout linearLayoutUsername;
   // private LinearLayout linearLayoutTelephone;



    private ImageView imageViewProfile;
    private TextView textViewUsername;
    private TextView textViewEmail;
    //private  TextView textViewTelephone;
    private TextView textViewAbout;
    private ProgressBar progressBar;
    private CardView cardViewInput;
    private EditText editTextTextPersonName;
    //private Button buttonCancel;
   // private Button buttonSave;
    @Override
    public int getLayout() {
        return R.layout.activity_profile;
    }

    @Override
    public void initView() {
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewUsername  = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        progressBar = findViewById(R.id.progressBarFragmentProfile);
        linearLayoutUsername = findViewById(R.id.linearLayoutUsername);
        cardViewInput = findViewById(R.id. cardViewInput);
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        //linearLayoutTelephone = findViewById(R.id.linearLayoutTelephone);

        updateUIWhenCreating();
    }
    private String username;
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
            username = getCurrentUser().getDisplayName();
            textViewUsername.setText(username);
        }
    }

    public void linearLayoutOnclick(View view) {
        if (view.getId() == R.id.linearLayoutUsername) {//
            cardViewInput.setVisibility(View.VISIBLE);
            editTextTextPersonName.setText(username);
            Button buttonSave = findViewById(R.id.buttonSave);
            Button buttonCancel = findViewById(R.id.buttonCancel);

            View.OnClickListener onClickListener = v -> {
                if(v.getId() ==R.id.buttonSave){
                    progressBar.setVisibility(View.VISIBLE);
                    final String username = editTextTextPersonName.getText().toString();

                    if (getCurrentUser() != null){
                        if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                            UserHelper.updateUsername(username,
                                    getCurrentUser().getUid()
                            ).addOnFailureListener(
                                    onFailureListener()
                            ).addOnSuccessListener(aVoid -> {
                                textViewUsername.setText(username);
                                progressBar.setVisibility(View.INVISIBLE);
                            });
                        }
                    }
                }

                else {
                    cardViewInput.setVisibility(View.INVISIBLE);
                }
            };
            buttonSave.setOnClickListener(onClickListener);
            buttonCancel.setOnClickListener(onClickListener);

        }
    }


}