package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.Signals;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Signals;


public class SignalsAdapterDash extends FirestoreRecyclerAdapter<Signals, SignalsViewHolderDash> {

    public interface Listener {
        void onDataChanged();
    }

    //FOR DATA
    private final RequestManager glide;
    //private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;

    public SignalsAdapterDash(@NonNull FirestoreRecyclerOptions<Signals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }

    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolderDash holder, int position, @NonNull Signals model) {
        holder.updateWithMessage(model,  this.glide);

    }

    @Override
    public SignalsViewHolderDash onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsViewHolderDash(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signal_item_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
