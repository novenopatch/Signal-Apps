package jin.jerrykel.dev.signal.vue.dashboard.fragment.Send;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.SignalTypeListHelper;
import jin.jerrykel.dev.signal.api.SignalsHelper;
import jin.jerrykel.dev.signal.model.TypeSignals;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Adapters.SignalsType.SignalsTypeAdapterDash;


public class SendNewSignalTypeFragment extends BaseFragment  implements SignalsTypeAdapterDash.Listener{

    private FloatingActionButton floatingActionButtonSend;


    private RecyclerView recyclerViewSignalType;
    private TextView textViewRecyclerViewEmpty;
    private SignalsTypeAdapterDash signalsTypeAdapterDash;
    private EditText editTextSignalName;
    private LinearLayout linearLayoutNewST;
    private ImageButton imageViewBala;
    private boolean  linearLayoutEditVisible = false;

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

        floatingActionButtonSend =rootView.findViewById(R.id.floatingActionButtonSend);
        recyclerViewSignalType = rootView.findViewById(R.id.recyclerViewSignalType);
        textViewRecyclerViewEmpty = rootView.findViewById(R.id.fragment_signal_not_found_textView);
        editTextSignalName = rootView.findViewById(R.id.editTextSignalName);
        linearLayoutNewST = rootView.findViewById(R.id.linearLayoutNewST);
        imageViewBala = rootView.findViewById(R.id.imageViewBala);
        imageViewBala.setOnClickListener(v -> {
            if(!linearLayoutEditVisible){
                linearLayoutEditVisible = true;
                imageViewBala.setImageResource(R.drawable.ic_baseline_arrow_drop_up_white_24);
                linearLayoutNewST.setVisibility(View.VISIBLE);
            }else{
                linearLayoutEditVisible = false;
                imageViewBala.setImageResource(R.drawable.ic_baseline_arrow_drop_down_white_24);
                linearLayoutNewST.setVisibility(View.INVISIBLE);
            }
        });
        floatingActionButtonSend.setOnClickListener(v -> {
            onClickSendMessage();
        });
        configureRecyclerView();

    }


    private void configureRecyclerView(){

        //Configure Adapter & RecyclerView
        this.signalsTypeAdapterDash = new SignalsTypeAdapterDash( generateOptionsForAdapter(SignalsHelper.getAllSignalSent()) , Glide.with(this),this);
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
        if( !editTextSignalName.getText().toString().isEmpty() && modelCurrentUser.getMentor()){
            SignalTypeListHelper.createSignalType( modelCurrentUser.getUid(), modelCurrentUser.getUsername(), editTextSignalName.getText().toString()
            ).addOnSuccessListener(documentReference -> {
                signalsTypeAdapterDash.notifyDataSetChanged();
                clearEditText();
            })
                    .addOnFailureListener(e -> {

            });

        }

    }
    private void clearEditText(){
        editTextSignalName.setText("");

    }

    @Override
    public void onDataChanged() {
        textViewRecyclerViewEmpty.setVisibility(this.signalsTypeAdapterDash.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    }
}