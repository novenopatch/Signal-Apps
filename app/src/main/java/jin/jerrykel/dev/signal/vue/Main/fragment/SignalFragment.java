package jin.jerrykel.dev.signal.vue.Main.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.MessageHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.Message;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.MessageSend.MentorChatAdapter;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class SignalFragment extends BaseFragment implements MentorChatAdapter.Listener{



    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;
    CarouselView carouselView;
    int[] sampleImages = {R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3};

    // FOR DATA
    private MentorChatAdapter mentorChatAdapter;
    @Nullable
    private User modelCurrentUser;
    private String  currentChatName = "MessageSent";



    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            imageView.setImageResource(sampleImages [position]);

        }
    };


    @Override
    public void initView( ){
        carouselView = this.rootView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        recyclerView = this.rootView.findViewById(R.id.activity_mentor_chat_recycler_view);
        textViewRecyclerViewEmpty = this.rootView.findViewById(R.id.activity_mentor_chat_text_view_recycler_view_empty);

        this.configureRecyclerView(currentChatName);
        this.getCurrentUserFromFirestore();
        /*
        Button crashButton = new Button(this);
        crashButton.setText("Crash!");
        crashButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                throw new RuntimeException("Test Crash"); // Force a crash
            }
        });

        addContentView(crashButton, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

         */


    }


    public SignalFragment() {
        // Required empty public constructor
    }


    public static SignalFragment newInstance() {
        SignalFragment fragment = new SignalFragment();
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_signal;
    }


    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);
                if(modelCurrentUser.getIsMentor()){


                }
            }
        });
    }
    private void configureRecyclerView(String chatName){
        //Track current chat name
        this.currentChatName = chatName;
        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new MentorChatAdapter(
                generateOptionsForAdapter(
                        MessageHelper.getAllMessageForChat(this.currentChatName)),
                Glide.with(this), this, getCurrentUser().getUid());
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();

    }



}