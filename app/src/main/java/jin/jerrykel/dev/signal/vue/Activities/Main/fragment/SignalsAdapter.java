package jin.jerrykel.dev.signal.vue.Activities.Main.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Signals;


public class SignalsAdapter extends FirestoreRecyclerAdapter<Signals, SignalsAdapter.SignalsViewHolder> {

    

    boolean click = false;
    private final RequestManager glide;
    //private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;
    public interface Listener {
        void onDataChanged();
    }
    public SignalsAdapter(@NonNull FirestoreRecyclerOptions<Signals> options, RequestManager glide, Listener callback) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        //this.idCurrentUser = idCurrentUser;
    }
    public static class SignalsViewHolder extends RecyclerView.ViewHolder {


        TextView textViewSignalName;
        TextView textViewSignalStatut;
        TextView textViewSignalType;
        Button buttonEntryPrice;
        Button buttonStopLoss;
        Button buttonTakeProfit;
        Button buttonReadMore;
        ImageView imageViewSend;
        





        private void initView(View superView){
            this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);
            this.textViewSignalStatut = superView.findViewById(R.id.textViewSignalStatut);
            this.textViewSignalType = superView.findViewById(R.id.textViewSignalType);
            this.buttonEntryPrice  = superView.findViewById(R.id.buttonEntryPrice);
            this.buttonStopLoss  = superView.findViewById(R.id.buttonStopLoss);
            this.buttonTakeProfit    = superView.findViewById(R.id.buttonTakeProfit);
            this.buttonReadMore     = superView.findViewById(R.id.buttonReadMore);
            this.imageViewSend      = superView.findViewById(R.id.imageViewSend);




        }
        public SignalsViewHolder(View itemView) {
            super(itemView);
            initView(itemView);


        }




    }
    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolder holder, int position, @NonNull Signals model) {
      
        holder.textViewSignalName.setText(model.getTypeSignalsName());
        holder.textViewSignalStatut.setText(model.getSignalStatus());
        if(model.getSellOrBuy().equals("Sell")){
            holder.textViewSignalType.setTextColor(Color.GREEN);
        }else {
            holder.textViewSignalType.setTextColor(Color.RED);
        }
        holder.textViewSignalType.setText(model.getSellOrBuy());
        holder.buttonEntryPrice.setText(model.getEntryPrice());
        holder.buttonStopLoss.setText(model.getStopLoss());
        holder.buttonTakeProfit.setText(model.getTakeProfit());
        if(model.getUrlImage()!=null){
            holder.buttonReadMore.setOnClickListener(v -> {
                updateImageView(model,holder.imageViewSend,glide);


            });
        }

    }

    @Override
    public SignalsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignalsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_signal, parent, false));
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        this.callback.onDataChanged();
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
