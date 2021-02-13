package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.utils.Utils;

import static jin.jerrykel.dev.signal.utils.Values.ACTIVE;
import static jin.jerrykel.dev.signal.utils.Values.READY;


public class SignalsAdapterDash extends FirestoreRecyclerAdapter<Signals, SignalsAdapterDash.SignalsViewHolderDash> {

    public interface Listener {
        void onDataChanged();
    }


    private final RequestManager glide;
    boolean click = false;
    private Listener callback;

    public SignalsAdapterDash(@NonNull FirestoreRecyclerOptions<Signals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }
    public static class SignalsViewHolderDash extends RecyclerView.ViewHolder {

        TextView textViewUserName;
        TextView textViewDateSend;

        TextView textViewSignalName;
        TextView textViewSignalStatut;
        TextView textViewSignalType;
        Button buttonEntryPrice;
        Button buttonStopLoss;
        Button buttonTakeProfit;
        Button buttonReadMore;
        ImageView imageViewSend;
        Switch switchIsPremiumType;
        Switch switchActiveOrReady;
        ImageButton imageButtonDelete;
        View rootView;
        ProgressBar progressBar;







        private void initView(View superView){
            this.textViewUserName = superView.findViewById(R.id.textViewUserName);
            this.textViewDateSend = superView.findViewById(R.id.textViewDateSend);
            this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);
            this.textViewSignalStatut = superView.findViewById(R.id.textViewSignalStatut);
            this.textViewSignalType = superView.findViewById(R.id.textViewSignalType);
            this.buttonEntryPrice  = superView.findViewById(R.id.buttonEntryPrice);
            this.buttonStopLoss  = superView.findViewById(R.id.buttonStopLoss);
            this.buttonTakeProfit    = superView.findViewById(R.id. buttonTakeProfit);
            this.buttonReadMore     = superView.findViewById(R.id.buttonReadMore);
            this.imageViewSend      = superView.findViewById(R.id.imageViewSend);
            this.imageButtonDelete = superView.findViewById(R.id.imageButtonDelete);
            this.switchActiveOrReady = superView.findViewById(R.id.switchActiveOrReady);
            this.progressBar = superView.findViewById(R.id.progressBar);
            this.switchIsPremiumType = superView.findViewById(R.id.switchIsPremium);





        }
        public SignalsViewHolderDash(View itemView) {
            super(itemView);
            rootView = itemView;
            initView(itemView);


        }


    }

    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolderDash holder, int position, @NonNull Signals model) {
        holder.textViewUserName.setText(model.getSenderName());


        //
        if(model.getDateCreated()!=null){
            holder.textViewDateSend.setText(Utils.convertDateToString(model.getDateCreated()));
        }
        holder.textViewSignalName.setText(model.getTypeSignalsName());
        holder.textViewSignalStatut.setText(model.getSignalStatus());
        if(model.getSignalStatus().equals(ACTIVE)){
            holder.switchActiveOrReady.setText(ACTIVE);
            holder.switchActiveOrReady.setTextColor(Color.GREEN);
            holder.switchActiveOrReady.setChecked(true);
            holder.textViewSignalName.setTextColor(Color.GREEN);
        }else {
            holder.switchActiveOrReady.setText(READY);
            holder.switchActiveOrReady.setTextColor(Color.RED);
            holder.switchActiveOrReady.setChecked(false);
            holder.textViewSignalName.setTextColor(Color.RED);
        }
        holder.textViewSignalType.setText(model.getSellOrBuy());
        holder.buttonEntryPrice.setText(model.getEntryPrice());
        holder.buttonStopLoss.setText(model.getStopLoss());
        holder.buttonTakeProfit.setText(model.getTakeProfit());
        if(model.isPremium()){
            holder.switchIsPremiumType.setText(Utils.getString(R.string.is_premium_signal,holder.imageButtonDelete.getContext()));
        }
        else {
            holder.switchIsPremiumType.setText(Utils.getString(R.string.is_not_premium_signal,holder.imageButtonDelete.getContext()));

        }
        if(model.getUrlImage()!=null){
            holder.buttonReadMore.setOnClickListener(v -> {
                updateImageView(model,holder.imageViewSend,glide);


            });
        }
        if(model.isPremium()){
            holder.switchIsPremiumType.setText(Utils.getString(R.string.is_premium_signal,holder.imageButtonDelete.getContext()));

        }
        else {
            holder.switchIsPremiumType.setText(Utils.getString(R.string.is_not_premium_signal,holder.imageButtonDelete.getContext()));
        }

        holder.switchIsPremiumType.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if(isChecked){
                holder.switchIsPremiumType.setText(Utils.getString(R.string.is_premium_signal,holder.imageButtonDelete.getContext()));
            }
            else {
                holder.switchIsPremiumType.setText(Utils.getString(R.string.is_not_premium_signal,holder.imageButtonDelete.getContext()));

            }
            SignalsHelper.updatePremiumOrNot(model.getUI(),isChecked);

        });
        holder.imageButtonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        deleteSignalsFromFirebase(model.getUI(),holder.progressBar);
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

        switchActiveOrReadyAction(holder,model);

    }
    private void  switchActiveOrReadyAction(SignalsViewHolderDash holder, Signals model){
        holder.switchActiveOrReady.setOnClickListener(v -> {

            if(holder.switchActiveOrReady.isChecked()){

                new AlertDialog.Builder(holder.rootView.getContext()).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            holder.progressBar.setVisibility(View.VISIBLE);
                            SignalsHelper.updateStatut(model.getUI(),true);
                            // Perform Action & Dismiss dialog
                            dialog.dismiss();

                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();

            }else {
                new AlertDialog.Builder(holder.rootView.getContext()).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            holder.progressBar.setVisibility(View.VISIBLE);
                            SignalsHelper.updateStatut(model.getUI(),false);
                            // Perform Action & Dismiss dialog
                            dialog.dismiss();

                        })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();
            }
        });
    }

    @Override
    public SignalsViewHolderDash onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsViewHolderDash(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_signal_dash, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
    }


    private void deleteSignalsFromFirebase(String ui,ProgressBar progressBar) {
        SignalsHelper.deleteSignal(ui).addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        //TODO
                    }
                }
        );
    }


    private void updateImageView(Signals signals,ImageView imageViewSend,RequestManager glide){
        if(click==false){
            glide.load(signals.getUrlImage()).into(imageViewSend);
            click = true;
        }else {
            click = false;

        }
    }


}
