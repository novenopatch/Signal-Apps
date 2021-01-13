package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.User;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.User;


public class UserAdapter extends FirestoreRecyclerAdapter<User, UsersViewHolder> {

    private User curentUser;
    public interface Listener {
        void onDataChanged();
    }

    //FOR COMMUNICATION
    private Listener callback;

    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options,  Listener callback,User user) {
        super(options);
        this.callback = callback;
        this.curentUser = user;
    }

    @Override
    protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull User model) {
        holder.updateWithMessage(model,curentUser);

    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UsersViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
