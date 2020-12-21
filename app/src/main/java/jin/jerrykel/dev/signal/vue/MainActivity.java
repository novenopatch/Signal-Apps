package jin.jerrykel.dev.signal.vue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import jin.jerrykel.dev.signal.vue.mentor_chat.MentorChatAdapter;
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
    private String currentChatName;
    private Uri uriImageSelected;

    // STATIC DATA FOR CHAT
    private static final String CHAT_NAME_ANDROID = "android";

    // STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;

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

        this.configureRecyclerView(CHAT_NAME_ANDROID);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void onClickChatButtons(View v) {
        switch (Integer.valueOf(( (ImageButton)v).getTag().toString())){
            case 10:
                this.configureRecyclerView(CHAT_NAME_ANDROID);

                break;
            case 20:
               // this.configureRecyclerView(CHAT_NAME_FIREBASE);
                break;
            case 30:
                //this.configureRecyclerView(CHAT_NAME_BUG);
                break;
        }
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddFile() {
        this.chooseImageFromPhone(); }


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

    private void uploadPhotoInFirebaseAndSendMessage(final String message) {
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING

        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
        mImageRef.putFile(this.uriImageSelected)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String pathImageSavedInFirebase =
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        // B - SAVE MESSAGE IN FIRESTORE
                        MessageHelper.createMessageWithImageForChat(
                                pathImageSavedInFirebase,
                                message, currentChatName, modelCurrentUser).addOnFailureListener(
                                onFailureListener());
                    }
                })
                .addOnFailureListener(onFailureListener());
    }
    // --------------------
    // FILE MANAGEMENT
    // --------------------
    private void chooseImageFromPhone(){
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions( this,
                    getString(R.string.popup_title_permission_files_access), RC_IMAGE_PERMS, PERMS);
            return;
        }
        ///Toast.makeText(context, "Vous avez le droit d'accéder aux images !", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
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
        mentorChatAdapter.registerAdapterDataObserver(
                new RecyclerView.AdapterDataObserver() {
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