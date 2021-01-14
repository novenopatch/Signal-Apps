package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.Signals;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.Utils.Utils;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.User;


public class SignalsViewHolderDash extends RecyclerView.ViewHolder {

    private TextView textViewUserName;
    private TextView textViewDateSend;

    private TextView textViewSignalName;
    private TextView textViewSignalStatut;
    private TextView textViewSignalType;
    private Button buttonEntryPrice;
    private Button buttonStopLoss;
    private Button buttonTakeProfit;
    private Button buttonReadMore;
    private ImageView imageViewSend;
    private User modelCurrentUser;
    private boolean click = false;





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




    }
    public SignalsViewHolderDash(View itemView) {
        super(itemView);
        initView(itemView);


    }

    public void updateWithMessage(final Signals signals, RequestManager glide){

        UserHelper.getUser(signals.getSenderUi()).addOnSuccessListener(
                documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class)
        );

        this.textViewUserName.setText(modelCurrentUser.getUsername());
        this.textViewDateSend.setText(Utils.convertDateToString(signals.getDateCreated()));
        this.textViewSignalName.setText(signals.getTypeSignalsName());
        this.textViewSignalStatut.setText(signals.getSignalStatus());
        this.textViewSignalType.setText(signals.getSellOrBuy());
        this.buttonEntryPrice.setText(signals.getEntryPrice());
        this.buttonStopLoss.setText(signals.getStopLoss());
        this.buttonTakeProfit.setText(signals.getTakeProfit());
        if(signals.getUrlImage()!=null){
            this.buttonReadMore.setOnClickListener(v -> {
                updateImageView(signals,glide);


            });
        }








            /*
                   if (message.getUrlImage() != null){
                            Log.e("bbbbbbbbbbb",message.getUrlImage());
                    glide.load(message.getUrlImage())
                            .into(imageViewSent);
                    //glide.load(message.getUrlImage()).into(imageViewSent);
                    //imageViewSent.setImageBitmap( this.stringToBitmap(message.getUrlImage()));
                   // message.getUrlImage();
                    this.imageViewSent.setVisibility(View.VISIBLE);
                }
                else {
                    this.imageViewSent.setVisibility(View.GONE);
                }


             */




    }
    private void updateImageView(Signals signals,RequestManager glide){
        if(click==false){
            glide.load(signals.getUrlImage()).into(imageViewSend);
            click = true;
        }else {
            click = false;

        }
    }



    // ---


}
