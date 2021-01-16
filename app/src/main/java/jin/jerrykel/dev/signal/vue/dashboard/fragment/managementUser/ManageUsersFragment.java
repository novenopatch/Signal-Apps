package jin.jerrykel.dev.signal.vue.dashboard.fragment.managementUser;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.User.UserAdapterDash;

public class ManageUsersFragment extends BaseFragment  implements UserAdapterDash.Listener{


    private UserAdapterDash userAdapter;
    private RecyclerView recyclerView;
    private User curentUser;
    public ManageUsersFragment() {
        // Required empty public constructor
    }
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
        recyclerView = rootView.findViewById(R.id.recyclerViewUser);
        getCurrentUserFromFirestore();
        //Log.e("UserAdapterDash",curentUser.toString());
        //configureRecyclerView();
    }
    protected void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> curentUser = documentSnapshot.toObject(User.class)).addOnFailureListener(
                e -> Log.e("Le probleme",e.toString())
        );
    }
    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        this.userAdapter = new UserAdapterDash( generateOptionsForAdapter(UserHelper.getAllUsers()) ,this,curentUser);
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