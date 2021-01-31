package jin.jerrykel.dev.signal.vue.Activities.main.fragment;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.Date;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.InfomationAppUser;
import jin.jerrykel.dev.signal.model.Signals;
import jin.jerrykel.dev.signal.utils.DatabaseManager;
import jin.jerrykel.dev.signal.utils.Utils;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class SignalFragment extends BaseFragment implements SignalsAdapter.Listener{



    RecyclerView recyclerView;
    TextView textViewRecyclerViewEmpty;

    private Spinner spinnerSignalsName;
    private Spinner spinnerSignalState;
    private static ArrayList<String> stringArrayList = new ArrayList<>();



    private SignalsAdapter mentorChatAdapter;

    private static int lastItemNbr =0;
    private static Boolean connetionState;
    private static  Controler localControler;
    private static DatabaseManager manager;
    private static InfomationAppUser infomationAppUser;

    
    



    public SignalFragment() {
        // Required empty public constructor
    }


    public static SignalFragment newInstance(boolean connectionStatep, Controler controler) {
        SignalFragment fragment = new SignalFragment();
        connetionState = connectionStatep;
        localControler = controler;
        manager= localControler.getManager();
        infomationAppUser = manager.getInformation();
        lastItemNbr = infomationAppUser.getLastSignalNbr();
        infomationAppUser.setWhen(new Date());
        return fragment;
    }


    @Override
    public void initView( ){
        stringArrayList = Utils.getTypeSignalsString();
        recyclerView = this.rootView.findViewById(R.id.recyclerViewSignal);
        textViewRecyclerViewEmpty = this.rootView.findViewById(R.id.fragment_signal_not_found_textView);
        initgraph();
        if(infomationAppUser.getLastSignalName() ==null){
            this.configureRecyclerView();
        }else {
            Log.d("configureRecyclerViewSp","il passe");
            this.configureRecyclerViewSpinnerForName(infomationAppUser.getLastSignalName());
        }





    }



    private void configureRecyclerView(){
        this.configureRecyclerView();
        //Configure Adapter & RecyclerView
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()), Glide.with(this), this,modelCurrentUser);
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
        this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSignalName(config)), Glide.with(this), this,modelCurrentUser);
        mentorChatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mentorChatAdapter.getItemCount()); // Scroll to bottom on new messages
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        recyclerView.setAdapter(this.mentorChatAdapter);
        infomationAppUser.setLastSignalName(config);
    }
    
    private void configureRecyclerViewSpinnerForState(String config){
        switch (config){
            case "Active":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this,modelCurrentUser);
                break;


            case  "Ready":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentActiveOrReady(config)), Glide.with(this), this,modelCurrentUser);
                break;
            case  "Buy":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this,modelCurrentUser);
                break;
            case  "Sell":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSentSellOrBuy(config)), Glide.with(this), this,modelCurrentUser);
                break;
            case "All":
                this.mentorChatAdapter = new SignalsAdapter( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()), Glide.with(this), this,modelCurrentUser);
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
                configureRecyclerViewSpinnerForState((String) spinnerSignalState.getSelectedItem());
                mentorChatAdapter.onDataChanged();
                mentorChatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }





    @Override
    public int getLayout() {
        return R.layout.fragment_signal;
    }


    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.mentorChatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
        if(connetionState){
            if(lastItemNbr<mentorChatAdapter.getItemCount()){
                int dif =lastItemNbr- mentorChatAdapter.getItemCount();
                lastItemNbr =mentorChatAdapter.getItemCount();
                infomationAppUser.setLastSignalNbr(lastItemNbr);
                Utils.sendVisualNotification("OneSignal",Utils.getString(R.string.notification,context) +"("+String.valueOf(dif)+")",context,true);
            }
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        infomationAppUser.setWhen(new Date());
        manager.updateInformation(infomationAppUser);
    }
}