package jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Signals;


public class SignalsAdapter extends FirestoreRecyclerAdapter<Signals, SignalsViewHolder> {

    public interface Listener {
        void onDataChanged();
    }

    //FOR DATA
    private final RequestManager glide;
    //private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;

    public SignalsAdapter(@NonNull FirestoreRecyclerOptions<Signals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }

    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolder holder, int position, @NonNull Signals model) {
        holder.updateWithMessage(model,  this.glide);

    }

    @Override
    public SignalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signal_item, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
