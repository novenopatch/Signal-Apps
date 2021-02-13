package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.utils.Utils;


public class SignalsTypeAdapterDash extends FirestoreRecyclerAdapter<TypeSignals, SignalsTypeAdapterDash.SignalsTypeViewHolderDash> {

    public interface Listener {
        void onDataChanged();
    }


    private final RequestManager glide;
    private Listener callback;

    public SignalsTypeAdapterDash(@NonNull FirestoreRecyclerOptions<TypeSignals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }
    public static class SignalsTypeViewHolderDash extends RecyclerView.ViewHolder {

        TextView textViewUserName;
        TextView textViewDateSend;
        TextView textViewSignalName;
        ImageButton imageButtonDelete;
        ProgressBar progressBar;
        View rootView;


        public SignalsTypeViewHolderDash(View itemView) {
            super(itemView);
            rootView = itemView;
            initView(itemView);


        }
        private void initView(View superView){
            this.textViewUserName = superView.findViewById(R.id.textViewUserName);
            this.textViewDateSend = superView.findViewById(R.id.textViewDateSend);
            this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);
            this.progressBar = superView.findViewById(R.id.progressBarDelete);
            this.imageButtonDelete = superView.findViewById(R.id.imageButtonDelete);





        }


    }
    @Override
    protected void onBindViewHolder(@NonNull SignalsTypeViewHolderDash holder, int position, @NonNull TypeSignals model) {


        if(model.getName()!=null){
            holder.textViewSignalName.setText(model.getName());
        }
        holder.textViewUserName.setText(model.getSenderName());
        if(model.getDateCreated()!=null){
            holder.textViewDateSend.setText(Utils.convertDateToString(model.getDateCreated()));
        }

        holder.imageButtonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        deleteSignalsTypeFromFirebase(model.getUI(),holder.progressBar);
                        dialog.dismiss();

                    })
                    .setNegativeButton("NO", (dialog, which) -> {
                        // Do nothing
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        });



    }

    @Override
    public SignalsTypeViewHolderDash onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsTypeViewHolderDash(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_signaltype_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }


    private void deleteSignalsTypeFromFirebase(String ui,ProgressBar progressBar) {
        SignalTypeListHelper.deleteSignalType(ui).addOnSuccessListener(aVoid -> progressBar.setVisibility(View.INVISIBLE));
    }
}






























