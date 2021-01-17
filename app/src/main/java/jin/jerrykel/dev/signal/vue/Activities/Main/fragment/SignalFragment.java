package jin.jerrykel.dev.signal.vue.Activities.Main.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.vue.Activities.Main.fragment.Adapters.SignalsAdapter;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class SignalFragment extends BaseFragment implements SignalsAdapter.Listener{



    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;

    private Spinner spinnerSignalsName;
    private Spinner spinnerSignalState;
    private static ArrayList<String> stringArrayList = new ArrayList<>();



    private SignalsAdapter mentorChatAdapter;
    
    



    public SignalFragment() {
        // Required empty public constructor
    }


    public static SignalFragment newInstance() {
        SignalFragment fragment = new SignalFragment();
        return fragment;
    }


    @Override
    public void initView( ){
        stringArrayList = getTypeSignalsString();
        recyclerView = this.rootView.findViewById(R.id.recyclerViewSignal);
        textViewRecyclerViewEmpty = this.rootView.findViewById(R.id.fragment_signal_not_found_textView);
        this.configureRecyclerView();

        initgraph();


    }


    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()), Glide.with(this), this);
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
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSignalName(config)), Glide.with(this), this);
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
    }
    
    private void configureRecyclerView(String config){
        switch (config){
            case "Active":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this);
                break;


            case  "Ready":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this);
                break;
            case  "Buy":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this);
                break;
            case  "Sell":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this);
                break;
            case "All":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()), Glide.with(this), this);
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
        spinnerSignalState = this.rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> spinnerAdapters = new ArrayAdapter<>(this.context,R.layout.item_spinner, stringArrayList);//stringArrayList);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.context, R.array.configSpinner, R.layout.item_spinner);

        spinnerAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerSignalsName.setAdapter(spinnerAdapters);
        spinnerSignalState.setAdapter(adapter);


        spinnerListener();
    }
    private void spinnerListener(){
        spinnerSignalsName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                configureRecyclerViewSpinnerForName((String) spinnerSignalsName.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerSignalState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                configureRecyclerView((String) spinnerSignalState.getSelectedItem());
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
                stringArrayList.add("type signal");
                //Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

        if(stringArrayList.size()<=1){
            stringArrayList.add("type signal");
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