package jin.jerrykel.dev.signal.vue.Activities.main.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.utils.Utils;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class ProfileFragment extends BaseFragment {

   private EditText editTextUsername;
   private TextView textViewEmail;
   private Button buttonUpdate;
   private ProgressBar progressBar;
    private static String username;
    private static String email;
    private static boolean connectionStateBoolean;
private SwipeRefreshLayout swipeRefreshLayoutProfile;
    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(String temail, String tuserName, boolean connectionState) {
        ProfileFragment fragment = new ProfileFragment();

        username = temail;
        email = tuserName;
        connectionStateBoolean = connectionState;
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
        this.swipeRefreshLayoutProfile = rootView.findViewById(R.id.swipeRefreshLayoutProfile);
        updateUIWhenCreating();
        setButtonUpdate();
        this.textViewEmail.setText(email);

        this.editTextUsername.setText(username);


        this.swipeRefreshLayoutProfile.setOnRefreshListener(this::updateListView);
    }
    public void updateListView(){
        this.swipeRefreshLayoutProfile.setRefreshing(false);

        updateUIWhenCreating();
    }
    private void updateUIWhenCreating(){




        if (getCurrentUser() != null){

            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                User currentUser = documentSnapshot.toObject(User.class);
                username = currentUser.getUsername();
                email = currentUser.getEmail();

                this.textViewEmail.setText(email);

                this.editTextUsername.setText(username);

            });


        }
    }
    private void setButtonUpdate(){
        buttonUpdate.setOnClickListener(v -> {
            if(connectionStateBoolean){
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
            }
            else {
                buttonUpdate.setText(Utils.getString(R.string.offline,context));
            }

        });
    }
}