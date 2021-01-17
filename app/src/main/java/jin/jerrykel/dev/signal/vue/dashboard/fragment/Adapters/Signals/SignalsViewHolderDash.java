package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.Signals;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.utils.Utils;


public class SignalsViewHolderDash extends RecyclerView.ViewHolder {

    private TextView textViewUserName;
    private TextView textViewDateSend;

    private TextView textViewSignalName;
    private TextView textViewSignalStatut;
    private TextView textViewSignalType;
    private Button buttonEntryPrice;
    private Button buttonStopLoss;
    private  Button buttonTakeProfit;
    private Button buttonReadMore;
    private ImageView imageViewSend;

    private Switch switchActiveOrReady;
    private ImageButton imageButtonDelete;
    private View rootView;
    private ProgressBar progressBar;

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
        this.imageButtonDelete = superView.findViewById(R.id.imageButtonDelete);
        this.switchActiveOrReady = superView.findViewById(R.id.switchActiveOrReady);
        this.progressBar = superView.findViewById(R.id.progressBar);




    }
    public SignalsViewHolderDash(View itemView) {
        super(itemView);
        rootView = itemView;
        initView(itemView);


    }

    public void updateWithMessage(final Signals signals, RequestManager glide){

        this.textViewUserName.setText(signals.getSenderName());
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
        imageButtonDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("YES", (dialog, which) -> {
                        progressBar.setVisibility(View.VISIBLE);
                        deleteSignalsFromFirebase(signals.getUI());
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
        this.switchActiveOrReady.setOnClickListener(v -> {

            if(switchActiveOrReady.isChecked()){

                new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            progressBar.setVisibility(View.VISIBLE);
                            SignalsHelper.updateStatut(signals.getUI(),true);
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
                new AlertDialog.Builder(rootView.getContext()).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            progressBar.setVisibility(View.VISIBLE);
                            SignalsHelper.updateStatut(signals.getUI(),false);
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

    private void deleteSignalsFromFirebase(String ui) {
        SignalsHelper.deleteSignal(ui).addOnCompleteListener(
                updateUIAfterRESTRequestsCompleted()
        );
    }
    private OnCompleteListener updateUIAfterRESTRequestsCompleted() {
        return o -> {
            if(o.isSuccessful()){
                progressBar.setVisibility(View.INVISIBLE);
               if(switchActiveOrReady.isChecked()){
                   switchActiveOrReady.setText("Active");
               }else {
                   switchActiveOrReady.setText("Ready");
               }
            }
            else {
                //TODO
            }
        };
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
