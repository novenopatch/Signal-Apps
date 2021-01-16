package jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.utils.Utils;


public class SignalsTypeViewHolderDash extends RecyclerView.ViewHolder {

    private TextView textViewUserName;
    private TextView textViewDateSend;
    private TextView textViewSignalName;







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


        this.textViewUserName.setText(typeSignals.getSenderName());
        this.textViewDateSend.setText(Utils.convertDateToString(typeSignals.getDateCreated()));
        this.textViewSignalName.setText(typeSignals.getName());


    }




    // ---


}
