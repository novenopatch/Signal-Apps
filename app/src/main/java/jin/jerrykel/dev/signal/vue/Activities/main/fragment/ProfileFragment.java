package jin.jerrykel.dev.signal.vue.Activities.main.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class ProfileFragment extends BaseFragment {

   private EditText editTextUsername;
   private TextView textViewEmail;
   private Button buttonUpdate;
   private ProgressBar progressBar;
    private String username;
    private String email;

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

            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                User currentUser = documentSnapshot.toObject(User.class);
                username = currentUser.getUsername();
                email = currentUser.getEmail();

                this.textViewEmail.setText(email);

                editTextUsername.setText(username);

            });


        }
    }
    private void setButtonUpdate(){
        buttonUpdate.setOnClickListener(v -> {

            final String usernameN = editTextUsername.getText().toString();
            if (getCurrentUser() != null){
                if (
                        !usernameN.isEmpty() && !usernameN.equals(username) && !usernameN.equals(getString(R.string.info_no_username_found))
                ){
                    progressBar.setVisibility(View.VISIBLE);
                    buttonUpdate.setEnabled(false);
                    UserHelper.updateUsername(usernameN,
                            getCurrentUser().getUid()).addOnFailureListener(
                            e -> {
                                //TODO
                            }
                    ).addOnSuccessListener(aVoid -> {

                        editTextUsername.setText(usernameN);
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonUpdate.setEnabled(true);
                    });
                }
            }
        });
    }
}