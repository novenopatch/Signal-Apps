package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.User.UserAdapter;

public class ManageUsersFragment extends BaseFragment  implements UserAdapter.Listener{

    private User  modelCurrentUser;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;

    public ManageUsersFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ManageUsersFragment newInstance() {
        ManageUsersFragment fragment = new ManageUsersFragment();
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_dash_manage_users;
    }

    @Override
    public void initView() {
        getCurrentUserFromFirestore();
        recyclerView = rootView.findViewById(R.id.recyclerViewUser);
        configureRecyclerViewSpinnerForName();
    }
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(
                documentSnapshot ->
                        modelCurrentUser = documentSnapshot.toObject(User.class));
    }
    private void configureRecyclerViewSpinnerForName(){

        //Configure Adapter & RecyclerView
        this.userAdapter = new UserAdapter( generateOptionsForAdapter(UserHelper.getAllUsers()) ,this,modelCurrentUser);
        userAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(userAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.userAdapter);
    }
    private FirestoreRecyclerOptions<User> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(query, User.class)
                .setLifecycleOwner(this)
                .build();

    }

    @Override
    public void onDataChanged() {

    }
}