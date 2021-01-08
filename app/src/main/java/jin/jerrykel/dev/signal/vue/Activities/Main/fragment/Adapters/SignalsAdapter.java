package jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Message;


public class SignalsAdapter extends FirestoreRecyclerAdapter<Message, SignalsViewHolder> {

    public interface Listener {
        void onDataChanged();
    }

    //FOR DATA
    private final RequestManager glide;
    private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;

    public SignalsAdapter(@NonNull FirestoreRecyclerOptions<Message> options, RequestManager glide, Listener callback, String idCurrentUser) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        this.idCurrentUser = idCurrentUser;
    }

    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolder holder, int position, @NonNull Message model) {
        holder.updateWithMessage(model, this.idCurrentUser, this.glide);

    }

    @Override
    public SignalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_chat_itemn, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
