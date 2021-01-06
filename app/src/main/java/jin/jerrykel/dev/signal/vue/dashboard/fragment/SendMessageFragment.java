package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.MessageHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.DashboardActivity;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMessageFragment extends BaseFragment {


    // STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;

    private CoordinatorLayout coordinatorLayout;
    private Uri uriImageSelected;
    private EditText editTextMessage;
    private FloatingActionButton floatingActionButtonSend;
    private ImageView ImageViewPreview;
    private ImageButton imageButtonAddFile;
    private User modelCurrentUser;
    private String currentChatName;



    public SendMessageFragment() {
        // Required empty public constructor
    }

    public static SendMessageFragment newInstance() {
        SendMessageFragment fragment = new SendMessageFragment();

        return fragment;
    }



    @Override
    public int getLayout() {
        return R.layout.fragment_send_message;
    }

    @Override
    public void initView() {

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

    private void handleResponse(int requestCode, int resultCode, Intent data){
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();

                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.ImageViewPreview);

            } else {
                //Toast.makeText(this, getString(R.string.toast_title_no_image_chosen), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initView(View view){
        currentChatName = "MessageSent";

        editTextMessage = view.findViewById(R.id.editTextMessage);
        floatingActionButtonSend =view.findViewById(R.id.floatingActionButtonSend);
        ImageViewPreview =view.findViewById(R.id.ImageViewPreview);
        imageButtonAddFile = view.findViewById(R.id.ImageButtonAddFile);
        coordinatorLayout =view.findViewById(R.id.coordinatorLayout);

        this.getCurrentUserFromFirestore();

        initListener();
    }
    private void initListener(){
        floatingActionButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendMessage();
            }
        });
        imageButtonAddFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromPhone();
            }
        });
    }
    // --------------------
    // FILE MANAGEMENT
    // --------------------

    private void chooseImageFromPhone(){
        if (!EasyPermissions.hasPermissions(rootView.getContext(), PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_files_access), RC_IMAGE_PERMS, PERMS);
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }


    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);
            }
        });
    }

    public void onClickSendMessage() {
        if (!(editTextMessage.getText().toString().isEmpty()) || modelCurrentUser != null){
            // Check if the ImageView is set
            if (!(editTextMessage.getText().toString().isEmpty()) && this.ImageViewPreview.getDrawable() == null) {
                // SEND A TEXT MESSAGE
                MessageHelper.createMessageForChat(editTextMessage.getText().toString(), this.currentChatName, modelCurrentUser).addOnFailureListener(this.onFailureListener(coordinatorLayout,"error"));
                this.editTextMessage.setText("");
            } else {
                if(editTextMessage.getText().toString().isEmpty()){
                    this.uploadPhotoInFirebaseAndSendMessage(" ");

                }else {
                    this.uploadPhotoInFirebaseAndSendMessage(
                            editTextMessage.getText().toString());

                }
                this.editTextMessage.setText("");
                this.ImageViewPreview.setImageDrawable(null);
            }
        }

    }
    private void uploadImage() {
        String path = UUID.randomUUID().toString();
        StorageReference mImageRef = FirebaseStorage.getInstance().getReference(path);

        final ProgressDialog progressDialog = new ProgressDialog(rootView.getContext());
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageMetadata metadata = new StorageMetadata.Builder().
                setCustomMetadata("text","text")
                .build();
        UploadTask uploadTask =  mImageRef.putFile(this.uriImageSelected,metadata);
        uploadTask.addOnProgressListener((DashboardActivity)rootView.getContext(),new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100.0*snapshot.getBytesTransferred()/snapshot
                        .getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int)progress+"%");
            }
        });
        uploadTask.addOnSuccessListener((DashboardActivity)rootView.getContext(),new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(rootView.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                Uri url = taskSnapshot.getMetadata().getReference().getDownloadUrl().getResult();
            }
        });
        /*
        mImageRef.putFile(this.uriImageSelected)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(rootView.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            //Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

         */

    }
    private void uploadPhotoInFirebaseAndSendMessage(final String message) {
        String uuid = UUID.randomUUID().toString();
        StorageReference mImageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uuid);
        mImageRef.putFile(this.uriImageSelected).addOnSuccessListener(taskSnapshot -> {
            mImageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String pathImageSavedInFirebase = uri.toString();
                MessageHelper.createMessageWithImageForChat(
                        pathImageSavedInFirebase,
                        message,
                        currentChatName,
                        modelCurrentUser).
                        addOnFailureListener(
                                onFailureListener(coordinatorLayout,"error"));
            });

        });
        /*
        mImageRef.putFile(this.uriImageSelected)
                .addOnSuccessListener((DashboardActivity)rootView.getContext(), taskSnapshot -> {
                    //ERROR
                    String pathImageSavedInFirebase =
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                    MessageHelper.createMessageWithImageForChat(
                            mImageRef.getDownloadUrl().toString(),
                            message,
                            currentChatName,
                            modelCurrentUser).
                            addOnFailureListener(
                                    onFailureListener(coordinatorLayout,"error"));
                }).addOnFailureListener(this.onFailureListener(coordinatorLayout,"error"));

         */

    }
    private void uploadPhotoInFirebaseAndSendMesage(final String message){
        String uuid = UUID.randomUUID().toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference(uuid);
        UploadTask uploadTask = storageRef.putFile(this.uriImageSelected);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            String pathImageSavedInFirebase = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
            MessageHelper.createMessageWithImageForChat(
                    pathImageSavedInFirebase,
                    message,
                    currentChatName,
                    modelCurrentUser).
                    addOnFailureListener(
                            onFailureListener(coordinatorLayout,"error"));
            // ...
        });

    }




}