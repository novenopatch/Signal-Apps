package jin.jerrykel.dev.signal.vue.Activities.Main.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalHelper;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters.SignalsAdapter;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class SignalFragment extends BaseFragment implements SignalsAdapter.Listener{



    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;

    private Spinner spinnerSignalsName;
    private Spinner spinnerSignatState;
    private static ArrayList<String> stringArrayList = new ArrayList<>();



    private SignalsAdapter mentorChatAdapter;
    @Nullable
    private User modelCurrentUser;



    public SignalFragment() {
        // Required empty public constructor
    }


    public static SignalFragment newInstance() {
        SignalFragment fragment = new SignalFragment();
        return fragment;
    }


    @Override
    public void initView( ){

        recyclerView = this.rootView.findViewById(R.id.recyclerViewSignal);
        textViewRecyclerViewEmpty = this.rootView.findViewById(R.id.fragment_signal_not_found_textView);
        this.configureRecyclerView();
        this.getCurrentUserFromFirestore();
        stringArrayList = getTypeSignalsString();
        initgraph();


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


    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSent()), Glide.with(this), this);
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private void configureRecyclerViewSpinnerForName(String config){

        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSentSignalName(config)), Glide.with(this), this);
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private void getCurrentUserFromFirestore(){
        UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                modelCurrentUser = documentSnapshot.toObject(User.class);

            }
        });
    }
    private void configureRecyclerView(String config){
        switch (config){
            case "Active":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this);
                break;


            case  "Ready":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this);
                break;
            case  "Buy":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this);
                break;
            case  "Sell":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this);
                break;
            case "All":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalHelper.getAllSignalSent()), Glide.with(this), this);
                break;
            default:

        }
        //Configure Adapter & RecyclerView

        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    private FirestoreRecyclerOptions<Signals> generateOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Signals>()
                .setQuery(query, Signals.class)
                .setLifecycleOwner(this)
                .build();

    }
    private void initgraph(){

        spinnerSignalsName = this.rootView.findViewById(R.id.spinner1);
        spinnerSignatState = this.rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> spinnerAdapters = new ArrayAdapter<>(this.context,R.layout.spinner_item, stringArrayList);//stringArrayList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.context, R.array.configSpinner, R.layout.spinner_item);

        spinnerAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerSignalsName.setAdapter(spinnerAdapters);
        spinnerSignatState.setAdapter(adapter);

        spinnerListener();
    }
    private void spinnerListener(){
        spinnerSignalsName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //updateConversion(valeur1.getText().toString(),valeur2, spinner1,spinner2);
                //spinnerSignalsName.getSelectedItemPosition();
                configureRecyclerViewSpinnerForName((String) spinnerSignalsName.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSignatState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // updateConversion(valeur2.getText().toString(),valeur1, spinner2,spinner1);
                configureRecyclerView((String) spinnerSignatState.getSelectedItem());
                mentorChatAdapter.onDataChanged();
                mentorChatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private   ArrayList<String> getTypeSignalsString(){
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





    @Override
    public int getLayout() {
        return R.layout.fragment_signal;
    }


    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }


}