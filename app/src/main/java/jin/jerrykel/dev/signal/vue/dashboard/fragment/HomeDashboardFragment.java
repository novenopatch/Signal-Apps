package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

public class HomeDashboardFragment extends BaseFragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnButtonClickedListener mCallback;
    private LinearLayout linearLayoutSendMessage;
    private LinearLayout linearLayoutSendAlertMessage;
    private LinearLayout linearLayoutUpdateImagePub;
    private LinearLayout linearLayoutRootTimeLine;
    private LinearLayout linearLayoutManageUser;
    private LinearLayout linearLayoutAddProAccount;
    private LinearLayout linearLayoutGetGraphicUsage;
    private LinearLayout LinearLayoutAdminSettings;
    private LinearLayout linearLayoutSendBugReport;

    private ImageView imageViewUser;

    private TextView textViewCountActionAllowed;
    private TextView textViewAppVersion;
    private  TextView textViewDashboardVersion;
    private TextView textViewUsername;

    private LinearLayout[] linearLayouts;



    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnButtonClickedListener {
        public void onButtonClicked(View view);
    }
    private void  initAllView(View rootView){


    }



    public HomeDashboardFragment() {
        // Required empty public constructor
    }


    public static HomeDashboardFragment newInstance(String param1, String param2) {
        HomeDashboardFragment fragment = new HomeDashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static HomeDashboardFragment newInstance() {
        HomeDashboardFragment fragment = new HomeDashboardFragment();
        return fragment;
    }





    @Override
    public int getLayout() {
        return R.layout.fragment_home_dashboard;
    }

    @Override
    public void initView() {
        linearLayoutSendMessage = rootView.findViewById(R.id.linearLayoutSendMessage);
        linearLayoutSendAlertMessage = rootView.findViewById(R.id.linearLayoutSendAlertMessage);
        linearLayoutUpdateImagePub = rootView.findViewById(R.id.linearLayoutUpdateImagePub);
        linearLayoutRootTimeLine = rootView.findViewById(R.id.linearLayoutRootTimeLine);
        linearLayoutManageUser = rootView.findViewById(R.id.linearLayoutManageUser);
        linearLayoutAddProAccount = rootView.findViewById(R.id.linearLayoutAddProAccount);
        linearLayoutGetGraphicUsage = rootView.findViewById(R.id.linearLayoutGetGraphicUsage);
        LinearLayoutAdminSettings = rootView.findViewById(R.id.LinearLayoutAdminSettings);
        linearLayoutSendBugReport = rootView.findViewById(R.id.linearLayoutSendBugReport);

        imageViewUser = rootView.findViewById(R.id.imageViewUser);

        textViewCountActionAllowed = rootView.findViewById(R.id.textViewCountActionAllowed);
        textViewAppVersion = rootView.findViewById(R.id.textViewAppVersion);
        textViewUsername = rootView.findViewById(R.id.textViewUsername);
        updateUIWhenCreating();
        linearLayouts = new LinearLayout[]{
                linearLayoutSendMessage,linearLayoutSendAlertMessage,
                linearLayoutUpdateImagePub,linearLayoutRootTimeLine,
                linearLayoutManageUser,linearLayoutAddProAccount,linearLayoutGetGraphicUsage,
                LinearLayoutAdminSettings,linearLayoutSendBugReport
        };
        for(LinearLayout v : linearLayouts){
            v.setOnClickListener(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }
    @Override
    public void onClick(View v) {
        // 5 - Spread the click to the parent activity
        mCallback.onButtonClicked(v);
    }
    // --------------
    // FRAGMENT SUPPORT
    // --------------

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
    private void updateUIWhenCreating(){


        if (getCurrentUser() != null){

            if (getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into( imageViewUser);

            }


            String username = getCurrentUser().getDisplayName();
            textViewUsername.setText(username);
            /*

            // 7 - Get additional data from Firestore (isMentor & Username)
            UserHelper.getUser(getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    //profileActivityCheckBoxIsMentor.setChecked(currentUser.getIsMentor());
                    textInputEditTextUsername.setText(username);
                }
            });

             */

        }
    }

}