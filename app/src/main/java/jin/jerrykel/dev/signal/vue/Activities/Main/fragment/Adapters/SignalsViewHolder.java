package jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.Utils.Utils;
import jin.jerrykel.dev.signal.api.MessageHelper;
import jin.jerrykel.dev.signal.model.Message;


public class SignalsViewHolder extends RecyclerView.ViewHolder {




    private ImageView imageViewMentorImage;
    private ImageView imageViewLike;
    private LinearLayout linearLayoutMessageContainer;
    private ImageView imageViewSent;
    private TextView textViewMessage;
    private TextView textViewMentorName;
    private  TextView textViewLikeCount;
    private TextView textViewDate;
    private TextView textViewMentorP;


    //FOR DATA
    private final int colorCurrentUser;
    private final int colorRemoteUser;
    private boolean likeMessage = false;
    private Message cMessage;
    private String cIdCurrentUser;



    private void initView(View superView){



        textViewMentorName = superView.findViewById(R.id.textViewMentorName);
        imageViewMentorImage= superView.findViewById(R.id.imageViewMentorImage);
        imageViewLike = superView.findViewById(R.id.imageViewLike);
        linearLayoutMessageContainer= superView.findViewById(R.id.linearLayoutMessageContainer);

        imageViewSent= superView.findViewById(R.id.imageViewSend);
        textViewMessage= superView.findViewById(R.id.activity_mentor_chat_item_message_container_text_message_container_text_view);

        textViewDate= superView.findViewById(R.id.textViewDate);
        textViewLikeCount = superView.findViewById(R.id.textViewLikeCount);
        textViewMentorP = superView.findViewById(R.id.textViewMentorP);

    }
    public SignalsViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
        colorCurrentUser = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
        colorRemoteUser = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);

    }

    public void updateWithMessage(final Message message, final String idCurrentUser, RequestManager glide){

        cMessage = message;
        cIdCurrentUser = idCurrentUser;


        boolean backgroundColorDefined = message.getBackgroundColor() != null;

        textViewLikeCount.setText(String.valueOf(message.getIdStringArrayList().size()));
        // Update message TextView
        this.textViewMessage.setText(message.getMessage());
        if(backgroundColorDefined)this.textViewMessage.setTextColor(Color.parseColor(message.getMessageColor()));
        //this.textViewMessage.setTextAlignment(isCurrentUser ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);

        // Update date TextView
        if (message.getDateCreated() != null) {
            this.textViewDate.setText(Utils.convertDateToHour(message.getDateCreated()));
        }
        // Update profile picture ImageView
        if(message.getUserSender().getUrlPicture()!=null){
            glide.load(message.getUserSender().getUrlPicture()).apply(RequestOptions.circleCropTransform()).into(imageViewMentorImage);
        }
        this.textViewMentorP.setVisibility(message.getUserSender().getIsMentor() ? View.VISIBLE : View.INVISIBLE);
        this.textViewMentorName.setText(message.getUserSender().getUsername());
        // Update image sent ImageView

        if (message.getUrlImage() != null){
            /*



             */
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
        /*
        if(backgroundColorDefined) {
            ((GradientDrawable)  linearLayoutMessageContainer.getBackground())
                .setColor(Color.parseColor(message.getBackgroundColor()));
        } else {
            ((GradientDrawable)  linearLayoutMessageContainer.getBackground())
                    .setColor( colorRemoteUser);
        }

         */
        imageViewLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewLikeOnClickListener(message,idCurrentUser);
            }
        });

        updateImageViewLike(message,idCurrentUser);


    }
    private void imageViewLikeOnClickListener(Message message,String idCurrentUser){
        if(!this.likeMessage){
            this.likeMessage = true;
            message.getIdStringArrayList().add(idCurrentUser);
            imageViewLike.setImageResource(R.drawable.ic_baseline_favorite_black_24);
        }else {
            this.likeMessage = false;
            message.getIdStringArrayList().remove(idCurrentUser);
            imageViewLike.setImageResource(R.drawable.ic_baseline_favorite_border_black_24);
        }
        MessageHelper.updateUserLike(idCurrentUser,message.getIdStringArrayList()).addOnFailureListener(
                onFailureListener()
        ).addOnSuccessListener(this.updateImageViewLikeRequestsCompleted());
        textViewLikeCount.setText(String.valueOf(message.getIdStringArrayList().size()));

    }
    private boolean updateImageViewLike(Message message,String idCurrentUser){
        for(String string : message.getIdStringArrayList()){
            if(string.equals(idCurrentUser)){
                this.likeMessage = true;
                imageViewLike.setImageResource(R.drawable.ic_baseline_favorite_black_24);
                return true;
            }
        }
        this.likeMessage = false;
        imageViewLike.setImageResource(R.drawable.ic_baseline_favorite_border_black_24);
        return false;
    }



    private OnSuccessListener<Void> updateImageViewLikeRequestsCompleted(){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                updateImageViewLike(cMessage,cIdCurrentUser);
            }
        };
    }

    public OnFailureListener onFailureListener(){
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(imageViewMentorImage.getContext(), "error", Toast.LENGTH_LONG).show();

            }
        };
    }
    private  Bitmap stringToBitmap(String picture){
        Bitmap bitmap = null;
        try {
            byte[] decodeString = Base64.decode(picture, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);

        }catch (Exception e){

        }
        return bitmap;
    }

    // ---


}
