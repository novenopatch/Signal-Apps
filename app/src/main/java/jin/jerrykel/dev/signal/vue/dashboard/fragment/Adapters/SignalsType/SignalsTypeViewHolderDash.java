package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.utils.Utils;


public class SignalsTypeViewHolderDash extends RecyclerView.ViewHolder {

    private TextView textViewUserName;
    private TextView textViewDateSend;
    private TextView textViewSignalName;
    private ImageButton imageButtonDelete;
    private ProgressBar progressBar;
    private View rootView;








    public SignalsTypeViewHolderDash(View itemView) {
        super(itemView);
        rootView = itemView;
        initView(itemView);


    }
    private void initView(View superView){
        this.textViewUserName = superView.findViewById(R.id.textViewUserName);
        this.textViewDateSend = superView.findViewById(R.id.textViewDateSend);
        this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);
        this.progressBar = superView.findViewById(R.id.progressBar);
        this.imageButtonDelete = superView.findViewById(R.id.imageButtonDelete);





    }

    public void updateWithMessage(final TypeSignals typeSignals, RequestManager glide){
        Log.e("TypeSignals",typeSignals.getName());

        this.textViewUserName.setText(typeSignals.getSenderName());
        this.textViewDateSend.setText(Utils.convertDateToString(typeSignals.getDateCreated()));
        this.textViewSignalName.setText(typeSignals.getName());
        imageButtonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        progressBar.setVisibility(View.VISIBLE);
                        deleteSignalsTypeFromFirebase(typeSignals.getUI());
                        // Perform Action & Dismiss dialog
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

    private void deleteSignalsTypeFromFirebase(String ui) {
        SignalTypeListHelper.deleteSignalType(ui).addOnCompleteListener(updateUIAfterRESTRequestsCompleted());
    }
    private OnCompleteListener updateUIAfterRESTRequestsCompleted() {
        return o -> {
            if(o.isSuccessful()){
                progressBar.setVisibility(View.INVISIBLE);
            }
            else {
                //TODO
            }
        };
    }

    // ---


}
