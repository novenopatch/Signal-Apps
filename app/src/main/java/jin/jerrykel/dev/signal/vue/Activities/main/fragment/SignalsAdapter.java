package jin.jerrykel.dev.signal.vue.Activities.main.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.utils.Utils;

import static jin.jerrykel.dev.signal.utils.Values.SELL;


public class SignalsAdapter extends FirestoreRecyclerAdapter<Signals, SignalsAdapter.SignalsViewHolder> {

    

    boolean click = false;
    private final RequestManager glide;
    private final User userC;
    //private final String idCurrentUser;

    //FOR COMMUNICATION
    private Listener callback;
    public interface Listener {
        void onDataChanged();
    }
    public SignalsAdapter(@NonNull FirestoreRecyclerOptions<Signals> options, RequestManager glide, Listener callback,@NonNull User myUser) {
        super(options);
        this.glide = glide;
        this.callback = callback;
        this.userC = myUser;
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
        ImageButton imageButtonMenu;
        





        private void initView(View superView){
            this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);
            this.textViewSignalStatut = superView.findViewById(R.id.textViewSignalStatut);
            this.textViewSignalType = superView.findViewById(R.id.textViewSignalType);
            this.buttonEntryPrice  = superView.findViewById(R.id.buttonEntryPrice);
            this.buttonStopLoss  = superView.findViewById(R.id.buttonStopLoss);
            this.buttonTakeProfit    = superView.findViewById(R.id.buttonTakeProfit);
            this.buttonReadMore     = superView.findViewById(R.id.buttonReadMore);
            this.imageViewSend      = superView.findViewById(R.id.imageViewSend);
            this.imageButtonMenu = itemView.findViewById(R.id.imageButtonMenu);




        }
        public SignalsViewHolder(View itemView) {
            super(itemView);
            initView(itemView);


        }




    }
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onBindViewHolder(@NonNull SignalsViewHolder holder, int position, @NonNull Signals model) {

      if(!model.isPremium()){
          holder.textViewSignalName.setText(model.getTypeSignalsName());
          holder.textViewSignalStatut.setText(model.getSignalStatus());
          if(model.getSellOrBuy().equals(SELL)){
              holder.textViewSignalType.setTextColor(Color.RED);
              //holder.textViewSignalType.setTextColor(Color.parseColor("#c60c2b"));
          }else {

              holder.textViewSignalType.setTextColor(Color.parseColor("#039BE5"));
              //holder.textViewSignalType.setTextColor(Color.parseColor("#28557d"));
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
      }else{
          if(userC.getPremium()!=null ){
              if(userC.getPremium() || userC.getMentor()||userC.getRoot()){
                  holder.textViewSignalName.setText(model.getTypeSignalsName());
                  holder.textViewSignalStatut.setText(model.getSignalStatus());
                  if(model.getSellOrBuy().equals(SELL)){
                      holder.textViewSignalType.setTextColor(Color.RED);
                      //holder.textViewSignalType.setTextColor(Color.parseColor("#c60c2b"));
                  }else {
                      //holder.textViewSignalType.setTextColor(Color.RED);
                      holder.textViewSignalType.setTextColor(Color.parseColor("#c60c2b"));
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
              }else{

              }

          }

      }
        holder.imageButtonMenu.setOnClickListener(v -> makePopMenu( holder.imageButtonMenu.getContext(), holder.imageButtonMenu,position));

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
    private void makePopMenu(Context context, ImageButton imageButtonMenu, int position){
        PopupMenu popupMenu = new PopupMenu(context,imageButtonMenu);

        popupMenu.setOnMenuItemClickListener(item -> {
            switch(item.getItemId()){
                case R.id.menu_share_signal:
                    Intent intent = new Intent(Intent.ACTION_SEND);// Uri.parse(generatePasswordArrayList.get(position).getPassword()));
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT,getItem(position).toString());
                    context.startActivity(Intent.createChooser(intent,"Share via"));
                    return true;
                case R.id.menu_copy_entryPrice:
                    Utils.copyPasswordInclipBoard("Copy entryPrice",getItem(position).getEntryPrice(),context);
                    return true;
                case R.id.menu_copy_stopLoss:
                    Utils.copyPasswordInclipBoard("Copy StopLoss",getItem(position).getStopLoss(),context);
                    return true;
                case R.id.menu_copy_takeProfite:
                    Utils.copyPasswordInclipBoard("Copy TakeProfite",getItem(position).getTakeProfit(),context);
                    return true;
                default:
                    return true;

            }

        });
        popupMenu.inflate(R.menu.menu_signal_popup);
        try {

            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            Object menuPopupHelper = field.get(popupMenu);
            Class<?> cls = Class.forName("com.android.internal.view.menu.MenuPopupHelper");
            Method method = cls.getDeclaredMethod("setForceShowIcon", new Class[]{boolean.class});
            method.setAccessible(true);
            method.invoke(menuPopupHelper, new Object[]{true});


        }catch (Exception e){
            Log.d("popupMenu error", Objects.requireNonNull(e.getMessage()));
        }finally {
            popupMenu.show();
        }

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
