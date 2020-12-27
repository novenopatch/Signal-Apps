package jin.jerrykel.dev.signal.vue;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.UUID;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.MessageHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.Message;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.MessageSend.MentorChatAdapter;
import jin.jerrykel.dev.signal.vue.settings.SettingActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements MentorChatAdapter.Listener{



    CarouselView carouselView;
    int[] sampleImages = {R.mipmap.img_1, R.mipmap.img_2, R.mipmap.img_3};

    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;


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
    public int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    public void initView(){
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        recyclerView = findViewById(R.id.activity_mentor_chat_recycler_view);
        textViewRecyclerViewEmpty = findViewById(R.id.activity_mentor_chat_text_view_recycler_view_empty);

        this.configureRecyclerView(currentChatName);
        this.getCurrentUserFromFirestore();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {

            case R.id.menuActionSettings:
                startSettingActivity();
                break;
            default:


        }
        return super.onOptionsItemSelected(item);
    }
    private void startSettingActivity(){
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu

        super.onBackPressed();


    }






    // --------------------
    // REST REQUESTS
    // --------------------
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

    // --------------------
    // UI
    // --------------------

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();

    }

    // --------------------
    // CALLBACK
    // --------------------
    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }
}