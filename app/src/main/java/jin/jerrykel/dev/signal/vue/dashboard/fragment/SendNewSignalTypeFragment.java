package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalHelper;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType.SignalsTypeAdapterDash;


public class SendNewSignalTypeFragment extends BaseFragment  implements SignalsTypeAdapterDash.Listener{

    private FloatingActionButton floatingActionButtonSend;
    private static ArrayList<String> stringArrayList = new ArrayList<>();
    private User modelCurrentUser;
    private RecyclerView recyclerViewSignalType;
    private SignalsTypeAdapterDash signalsTypeAdapterDash;
    private EditText editTextSignalName;

    public SendNewSignalTypeFragment() {
        // Required empty public constructor
    }


    public static SendNewSignalTypeFragment newInstance() {
        SendNewSignalTypeFragment fragment = new SendNewSignalTypeFragment();
        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_dash_send_new_signal_type;
    }

    @Override
    public void initView() {
        this.getCurrentUserFromFirestore();
        stringArrayList = getTypeSignalsString();
        floatingActionButtonSend =rootView.findViewById(R.id.floatingActionButtonSend);
        recyclerViewSignalType = rootView.findViewById(R.id.recyclerViewSignalType);
        editTextSignalName = rootView.findViewById(R.id.editTextSignalName);
        configureRecyclerView();
        floatingActionButtonSend.setOnClickListener(v -> {
            onClickSendMessage();
        });

    }
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(
                documentSnapshot -> modelCurrentUser = documentSnapshot.toObject(User.class));
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
        this.signalsTypeAdapterDash = new SignalsTypeAdapterDash( generateOptionsForAdapter(SignalHelper.getAllSignalSent()) , Glide.with(this),this);
        signalsTypeAdapterDash.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerViewSignalType.smoothScrollToPosition(signalsTypeAdapterDash.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerViewSignalType.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerViewSignalType.setAdapter(this.signalsTypeAdapterDash);
    }
    private FirestoreRecyclerOptions<TypeSignals> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<TypeSignals>()
                .setQuery(query, TypeSignals.class)
                .setLifecycleOwner(this)
                .build();

    }
    private void onClickSendMessage() {
        if( !editTextSignalName.getText().toString().isEmpty() && modelCurrentUser.getIsMentor()){
            SignalTypeListHelper.createSignalType(modelCurrentUser.getUid(),editTextSignalName.getText().toString())
            .addOnSuccessListener(documentReference -> {
                clearEditText();
            }).addOnFailureListener(e -> {

            });

        }

    }
    private void clearEditText(){
        editTextSignalName.setText("");

    }

    @Override
    public void onDataChanged() {

    }
}