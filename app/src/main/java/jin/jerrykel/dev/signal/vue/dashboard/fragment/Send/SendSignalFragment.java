package jin.jerrykel.dev.signal.vue.dashboard.fragment.Send;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.Signals.SignalsAdapterDash;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendSignalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendSignalFragment extends BaseFragment implements SignalsAdapterDash.Listener{


    // STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private static final int RC_CHOOSE_PHOTO = 200;

    private CoordinatorLayout coordinatorLayout;
    private Uri uriImageSelected;
    private FloatingActionButton floatingActionButtonSend;
    private ImageView ImageViewPreview;
    private ImageButton imageButtonAddFile;
    private static ArrayList<String> stringArrayList = new ArrayList<>();
    private RecyclerView recyclerViewSignal;
    private Spinner spinner1;
    private Spinner spinnerSignalStatut;
    private Spinner spinnerSignalType;
    private EditText editTextEntryPrice;
    private EditText ediTextStopLoss;
    private EditText editTextTakeProfit;


    private SignalsAdapterDash signalsAdapterDash;




    public SendSignalFragment() {
        // Required empty public constructor
    }

    public static SendSignalFragment newInstance() {
        SendSignalFragment fragment = new SendSignalFragment();

        return fragment;
    }



    @Override
    public int getLayout() {
        return R.layout.fragment_dash_send_signal;
    }

    @Override
    public void initView() {
       // this.getCurrentUserFromFirestore();
        stringArrayList = getTypeSignalsString();
       recyclerViewSignal = rootView.findViewById(R.id.recyclerViewSignal);
        floatingActionButtonSend =rootView.findViewById(R.id.floatingActionButtonSend);
        ImageViewPreview =rootView.findViewById(R.id.ImageViewPreview);
        imageButtonAddFile = rootView.findViewById(R.id.ImageButtonAddFile);
        coordinatorLayout = rootView.findViewById(R.id.coordinatorLayout);


        editTextEntryPrice = rootView.findViewById(R.id.editTextEntryPrice);
        ediTextStopLoss = rootView.findViewById(R.id. ediTextStopLoss);
        editTextTakeProfit  = rootView.findViewById(R.id.editTextTakeProfit);
        initSpinner();
        configureRecyclerView();
        initListener();
    }

    private ArrayList<String> getTypeSignalsString(){
        String TAG = "getTypeSignalsString()";
        ArrayList<String> stringArrayList = new ArrayList<>();
        SignalTypeListHelper.getAllSignalTypeSent().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {


                    TypeSignals typeSignals = document.toObject(TypeSignals.class);
                    stringArrayList.add(typeSignals.getName());
                    //Log.d(TAG, document.getId() + " => " + document.getData());
                }
            } else {
                stringArrayList.add("None");
                //Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        if(stringArrayList.size()<=1){
            stringArrayList.add("None");
        }
        return stringArrayList;
    }
    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        this.signalsAdapterDash = new SignalsAdapterDash( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()) ,Glide.with(this),this);
        signalsAdapterDash.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerViewSignal.smoothScrollToPosition(signalsAdapterDash.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerViewSignal.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerViewSignal.setAdapter(this.signalsAdapterDash);
    }
    private FirestoreRecyclerOptions<Signals> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Signals>()
                .setQuery(query, Signals.class)
                .setLifecycleOwner(this)
                .build();

    }
    private void initSpinner(){

        spinner1 = rootView.findViewById(R.id.spinner1Dash);
        spinnerSignalStatut = this.rootView.findViewById(R.id.spinnerSignalStatut);
        spinnerSignalType = this.rootView.findViewById(R.id.spinnerSignalType);
        ArrayAdapter<String> spinnerAdapters = new ArrayAdapter<>(this.context,R.layout.item_spinner, stringArrayList);//stringArrayList);
        ArrayAdapter<CharSequence> adapterSignalStatut = ArrayAdapter.createFromResource(this.context, R.array.configSpinnerStatut, R.layout.item_spinner);
        ArrayAdapter<CharSequence> adapterSpinnerSignalType = ArrayAdapter.createFromResource(this.context, R.array.configSpinnerType, R.layout.item_spinner);

        spinnerAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSignalStatut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinnerSignalType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(spinnerAdapters);
        spinnerSignalStatut.setAdapter(adapterSignalStatut);
        spinnerSignalType.setAdapter(adapterSpinnerSignalType);


    }
    private void initListener(){
        floatingActionButtonSend.setOnClickListener(v -> onClickSendMessage());
        imageButtonAddFile.setOnClickListener(v -> chooseImageFromPhone());
    }
    private void onClickSendMessage() {
        if( !editTextEntryPrice.getText().toString().isEmpty() && !ediTextStopLoss.getText().toString().isEmpty()
                && !editTextTakeProfit.getText().toString().isEmpty() && modelCurrentUser.getMentor()){
            SignalsHelper.createSignal(
                    modelCurrentUser.getUid(),
                    (String) spinner1.getSelectedItem(),
                    (String)spinnerSignalStatut.getSelectedItem(),
                    (String) spinnerSignalType.getSelectedItem(),
                    editTextEntryPrice.getText().toString(),
                    ediTextStopLoss.getText().toString(),
                    editTextTakeProfit.getText().toString()
            ).addOnSuccessListener(documentReference -> {
                clearEditText();
            }).addOnFailureListener(this.onFailureListener(coordinatorLayout,"error"));

        }

    }
    private void clearEditText(){
        editTextEntryPrice.setText("");
        ediTextStopLoss.setText("");
        editTextTakeProfit.setText("");
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
    @Override
    public void onDataChanged() {

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













     /*
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

     */
       /*
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

      void uploadImage() {
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



    }

     */

}