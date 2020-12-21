package jin.jerrykel.dev.signal.vue.mentor_chat;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.model.Message;


public class MessageViewHolder extends RecyclerView.ViewHolder {

    //ROOT VIEW
   LinearLayout rootView;

    //PROFILE CONTAINER
   // LinearLayout profileContainer;
    ImageView imageViewIsMentor;

    //MESSAGE CONTAINER
    LinearLayout linearLayoutMessageContainer;
    //IMAGE SENDED CONTAINER
    CardView cardViewImageSent;
    ImageView imageViewSent;
    //TEXT MESSAGE CONTAINER
    LinearLayout textMessageContainer;
    TextView textViewMessage;
    TextView textViewMentorName;
    //DATE TEXT
    TextView textViewDate;


    //FOR DATA
    private final int colorCurrentUser;
   private final int colorRemoteUser;



    private void initView(View superView){
        rootView = superView.findViewById(R.id.activity_mentor_chat_item_root_view);


        //PROFILE CONTAINER
        //profileContainer= superView.findViewById(R.id.linearLayoutTitleContainer);
        textViewMentorName = superView.findViewById(R.id.textViewMentorName);
        imageViewIsMentor= superView.findViewById(R.id.imageViewMentorImage);

        //MESSAGE CONTAINER
        linearLayoutMessageContainer= superView.findViewById(R.id.linearLayoutMessageContainer);
        //IMAGE SENDED CONTAINER
       // cardViewImageSent= superView.findViewById(R.id.activity_mentor_chat_item_message_container_image_sent_cardview);
        imageViewSent= superView.findViewById(R.id.imageViewImageSend);
        //TEXT MESSAGE CONTAINER
       textMessageContainer= superView.findViewById(R.id.linearMessageContainerTextMessageContainer);
       textViewMessage= superView.findViewById(R.id.activity_mentor_chat_item_message_container_text_message_container_text_view);
        //DATE TEXT
        textViewDate= superView.findViewById(R.id.textViewDate);

    }
    public MessageViewHolder(View itemView) {
        super(itemView);
        initView(itemView);
        colorCurrentUser = ContextCompat.getColor(itemView.getContext(), R.color.colorAccent);
        colorRemoteUser = ContextCompat.getColor(itemView.getContext(), R.color.colorPrimary);

    }

    public void updateWithMessage(Message message, String currentUserId, RequestManager glide){

        // Check if current user is the sender
       // Boolean isCurrentUser = message.getUserSender().getUid().equals(currentUserId);

        boolean backgroundColorDefined = message.getBackgroundColor() != null;

        // Update message TextView
        this.textViewMessage.setText(message.getMessage());
        if(backgroundColorDefined)this.textViewMessage.setTextColor(Color.parseColor(message.getMessageColor()));
        //this.textViewMessage.setTextAlignment(isCurrentUser ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);

        // Update date TextView
        if (message.getDateCreated() != null) {
            this.textViewDate.setText(this.convertDateToHour(message.getDateCreated()));
        }
        // Update isMentor ImageView
        this.imageViewIsMentor.setVisibility(message.getUserSender().getIsMentor() ? View.VISIBLE : View.INVISIBLE);
        this.textViewMentorName.setText(message.getUserSender().getUsername());

        // Update profile picture ImageView


        // Update image sent ImageView
        if (message.getUrlImage() != null){
            glide.load(message.getUrlImage())
                    .into(imageViewSent);
            this.imageViewSent.setVisibility(View.VISIBLE);
        }
        else {
            this.imageViewSent.setVisibility(View.GONE);
        }

        //Update Message Bubble Color Background
        //((GradientDrawable) textMessageContainer.getBackground()).setColor(Color.WHITE);



        //((GradientDrawable) textMessageContainer.getBackground()).setColor(backgroundColorDefined ? colorRemoteUser : Color.parseColor(message.getBackgroundColor()));
        if(backgroundColorDefined) {
            ((GradientDrawable)  linearLayoutMessageContainer.getBackground())
                .setColor(Color.parseColor(message.getBackgroundColor()));
        } else {
            ((GradientDrawable)  linearLayoutMessageContainer.getBackground())
                    .setColor( colorRemoteUser);
        }
        // Update all views alignment depending is current user or not

    }



    // ---

    private String convertDateToHour(Date date){
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        return dfTime.format(date);
    }
}
