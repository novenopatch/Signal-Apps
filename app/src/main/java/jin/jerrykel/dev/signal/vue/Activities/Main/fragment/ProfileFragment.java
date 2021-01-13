package jin.jerrykel.dev.signal.vue.Activities.Main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends BaseFragment {

   private EditText editTextUsername;
   private TextView textViewEmail;
   private Button buttonUpdate;
   private ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();

        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    public void initView() {
        this.editTextUsername = rootView.findViewById(R.id.editTextUsername);
        this.textViewEmail = rootView.findViewById(R.id.textViewEmail);
        this.buttonUpdate = rootView.findViewById(R.id.buttonUpdate);
        this.progressBar = rootView.findViewById(R.id.progressBarFragmentProfile);
        updateUIWhenCreating();
        setButtonUpdate();

    }
    private void updateUIWhenCreating(){


        if (getCurrentUser() != null){

            if (getCurrentUser().getPhotoUrl() != null) {


            }

            String email = TextUtils.isEmpty(getCurrentUser().getEmail())
                    ? getString(R.string.info_no_email_found) : getCurrentUser().getEmail();

            this.textViewEmail.setText(email);
            String username = getCurrentUser().getDisplayName();
            editTextUsername.setText(username);
        }
    }
    private void setButtonUpdate(){
        buttonUpdate.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            final String username = editTextUsername.getText().toString();


            if (getCurrentUser() != null){
                if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                    UserHelper.updateUsername(username,
                            getCurrentUser().getUid()).addOnFailureListener(
                            e -> {
                                //TODO
                            }
                    ).addOnSuccessListener(aVoid -> {
                        editTextUsername.setText(username);
                        progressBar.setVisibility(View.INVISIBLE);
                    });
                }
            }
        });
    }
}