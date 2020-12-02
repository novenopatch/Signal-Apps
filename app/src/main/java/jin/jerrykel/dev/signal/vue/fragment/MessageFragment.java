package jin.jerrykel.dev.signal.vue.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;


import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.MessageHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.Message;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.AppsActivity;
import jin.jerrykel.dev.signal.vue.mentor_chat.MentorChatAdapter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


public class MessageFragment extends Fragment implements MentorChatAdapter.Listener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    // FOR DATA
    // FOR DESIGN
    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;
    TextInputEditText editTextMessage;
    ImageView imageViewPreview;
    Button send;
    ImageButton chat_android;
    ImageButton chat_firebase_chat_button;
    ImageButton bug_chat_button;
    ImageButton addFileButton;
    // FOR DATA
    private MentorChatAdapter mentorChatAdapter;
    @Nullable private User modelCurrentUser;
    private String currentChatName;
    private Uri uriImageSelected;

    // STATIC DATA FOR CHAT
    private static final String CHAT_NAME_ANDROID = "android";
    private static final String CHAT_NAME_BUG = "bug";
    private static final String CHAT_NAME_FIREBASE = "firebase";

    // STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;
    private Controler controler;
    private View rootView;
    private Context context;
    private View.OnClickListener addFileButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickAddFile();
        }
    };
    View.OnClickListener onClickChatButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickChatButtons(v);
        }
    };
    private View.OnClickListener  onClickSendMessageClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClickSendMessage();
        }
    };


    public MessageFragment() {
        // Required empty public constructor
    }



    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        context = rootView.getContext();
        initAllView(rootView);
        controler = Controler.getInstance();

        this.configureRecyclerView(CHAT_NAME_ANDROID);
        this.getCurrentUserFromFirestore();
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void initAllView(View rootView){
        recyclerView = rootView.findViewById(R.id.activity_mentor_chat_recycler_view);
        textViewRecyclerViewEmpty = rootView.findViewById(R.id.activity_mentor_chat_text_view_recycler_view_empty);
        editTextMessage = rootView.findViewById(R.id.activity_mentor_chat_message_edit_text);
        imageViewPreview = rootView.findViewById(R.id.activity_mentor_chat_image_chosen_preview);

        send  = rootView.findViewById( R.id.activity_mentor_chat_send_button);
        chat_android = rootView.findViewById(R.id.activity_mentor_chat_android_chat_button);
        chat_firebase_chat_button = rootView.findViewById( R.id.activity_mentor_chat_firebase_chat_button);
        bug_chat_button = rootView.findViewById( R.id.activity_mentor_chat_bug_chat_button);
        addFileButton= rootView.findViewById( R.id.activity_mentor_chat_add_file_button);

        addFileButton.setOnClickListener(addFileButtonOnClickListener);
        send.setOnClickListener(onClickSendMessageClickListener);
        chat_android.setOnClickListener(onClickChatButtonListener);
        chat_firebase_chat_button.setOnClickListener(onClickChatButtonListener);
        bug_chat_button.setOnClickListener(onClickChatButtonListener);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponse(requestCode, resultCode, data);
    }
    //-----------------
    // Actions
    //---------------
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(controler.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);
            }
        });
    }
    public void onClickSendMessage() {
        if (!(editTextMessage.getText().toString().isEmpty()) && modelCurrentUser != null){
            // Check if the ImageView is set
            if (this.imageViewPreview.getDrawable() == null) {
                // SEND A TEXT MESSAGE
                MessageHelper.createMessageForChat(editTextMessage.getText().toString(),
                        this.currentChatName, modelCurrentUser).addOnFailureListener(controler.onFailureListener(context));
                this.editTextMessage.setText("");
            } else {
                // SEND A IMAGE + TEXT IMAGE
                this.uploadPhotoInFirebaseAndSendMessage(editTextMessage.getText().toString());
                this.editTextMessage.setText("");
                this.imageViewPreview.setImageDrawable(null);
            }
        }
    }
    private void uploadPhotoInFirebaseAndSendMessage(final String message) {
        String uuid = UUID.randomUUID().toString(); // GENERATE UNIQUE STRING
        // A - UPLOAD TO GCS
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(uuid);
        mImageRef.putFile(this.uriImageSelected)
                .addOnSuccessListener((AppsActivity)context, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String pathImageSavedInFirebase =
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                        // B - SAVE MESSAGE IN FIRESTORE
                        MessageHelper.createMessageWithImageForChat(pathImageSavedInFirebase,
                                message, currentChatName, modelCurrentUser).addOnFailureListener(
                                        controler.onFailureListener(context));
                    }
                })
                .addOnFailureListener(controler.onFailureListener(context));
    }
    public void onClickChatButtons(View v) {
        switch (Integer.valueOf(( (ImageButton)v).getTag().toString())){
            case 10:
                this.configureRecyclerView(CHAT_NAME_ANDROID);

                break;
            case 20:
                this.configureRecyclerView(CHAT_NAME_FIREBASE);
                break;
            case 30:
                this.configureRecyclerView(CHAT_NAME_BUG);
                break;
        }
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    public void onClickAddFile() {
        this.chooseImageFromPhone(); }
    private void chooseImageFromPhone(){
        if (!EasyPermissions.hasPermissions(context, PERMS)) {
            EasyPermissions.requestPermissions((AppsActivity) context,
                    getString(R.string.popup_title_permission_files_access), RC_IMAGE_PERMS, PERMS);
            return;
        }
        Toast.makeText(context, "Vous avez le droit d'accéder aux images !", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }
    private void handleResponse(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.imageViewPreview);
            } else {
                Toast.makeText(context, getString(R.string.toast_title_no_image_chosen),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    // --------------------
    // REST REQUESTS
    // --------------------




    // --------------------
    // UI
    // --------------------

    private void configureRecyclerView(String chatName){
        //Track current chat name
        this.currentChatName = chatName;
        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new MentorChatAdapter(generateOptionsForAdapter(
                MessageHelper.getAllMessageForChat(this.currentChatName)),
                Glide.with(context), this, controler.getCurrentUser().getUid());
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query){
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner((AppsActivity)context)
                .build();
    }
    // --------------------
    // CALLBACK
    // --------------------
    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }


    // --------------------
    // FILE MANAGEMENT
    // --------------------










}