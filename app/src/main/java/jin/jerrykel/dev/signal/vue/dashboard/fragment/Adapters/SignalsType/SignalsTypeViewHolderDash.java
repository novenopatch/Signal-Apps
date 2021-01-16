package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.Utils.Utils;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.model.User;


public class SignalsTypeViewHolderDash extends RecyclerView.ViewHolder {

    private TextView textViewUserName;
    private TextView textViewDateSend;
    private TextView textViewSignalName;
    private User modelCurrentUser;






    private void initView(View superView){
        this.textViewUserName = superView.findViewById(R.id.textViewUserName);
        this.textViewDateSend = superView.findViewById(R.id.textViewDateSend);
        this.textViewSignalName = superView.findViewById(R.id.textViewSignalName);






    }
    public SignalsTypeViewHolderDash(View itemView) {
        super(itemView);
        initView(itemView);


    }

    public void updateWithMessage(final TypeSignals typeSignals, RequestManager glide){

        UserHelper.getUser(typeSignals.getSenderUi()).addOnSuccessListener(
                documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class)
        );
        if(modelCurrentUser!=null){
            this.textViewUserName.setText(modelCurrentUser.getUsername());
            this.textViewDateSend.setText(Utils.convertDateToString(typeSignals.getDateCreated()));
            this.textViewSignalName.setText(typeSignals.getName());
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




    // ---


}
