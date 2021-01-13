package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.TypeSignals;


public class SignalsTypeAdapterDash extends FirestoreRecyclerAdapter<TypeSignals, SignalsTypeViewHolderDash> {

    public interface Listener {
        void onDataChanged();
    }

    //FOR DATA
    private final RequestManager glide;
    //private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;

    public SignalsTypeAdapterDash(@NonNull FirestoreRecyclerOptions<TypeSignals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }

    @Override
    protected void onBindViewHolder(@NonNull SignalsTypeViewHolderDash holder, int position, @NonNull TypeSignals model) {
        holder.updateWithMessage(model,  this.glide);

    }

    @Override
    public SignalsTypeViewHolderDash onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsTypeViewHolderDash(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.signal_type_item_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
