package jin.jerrykel.dev.signal.vue.dashboard;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.model.User;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.AddAccountFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.AdminSettingsFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.BugFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.HomeDashboardFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.ManageUsersFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.SendMessageFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.TimelineFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.UpdatePubImgFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.UsageFragment;

public class DashboardActivity extends BaseActivity implements  HomeDashboardFragment.OnButtonClickedListener {
    private FrameLayout frameLayoutContent;
    private Fragment fragment = null;


    @Override
    public int getLayout() {
        return R.layout.activity_dashboard;
    }

    @Override
    public void initView() {
        initContentView();
    }

    public void initContentView(){
        Fragment fragment = new HomeDashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContent,fragment);
        fragmentTransaction.commit();
    }
    private void initContentView(View view){

        switch (view.getId()){
            case R.id.linearLayoutSendMessage:
                 fragment = new SendMessageFragment();
                break;
            case R.id.linearLayoutSendAlertMessage:
                fragment = new SendMessageFragment();
                ///
                break;
            case R.id.linearLayoutUpdateImagePub:
                fragment = new UpdatePubImgFragment();
                ///
                break;
            case R.id.linearLayoutRootTimeLine:
                fragment = new TimelineFragment();
                ///
                break;
            case R.id.linearLayoutManageUser:
                fragment = new ManageUsersFragment();
                ///
                break;
            case R.id.linearLayoutAddProAccount:
                fragment = new AddAccountFragment();
                ///
                break;
            case R.id.linearLayoutGetGraphicUsage:
                fragment = new UsageFragment();
                ///
                break;
            case R.id.LinearLayoutAdminSettings:
                fragment = new AdminSettingsFragment();
                ///
                break;
            case R.id.linearLayoutSendBugReport:
                fragment = new BugFragment();
                ///
                break;

            default:
                break;
        }
        if(fragment !=null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayoutContent,fragment);
            fragmentTransaction.commit();
        }else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragment = new HomeDashboardFragment();
            fragmentTransaction.replace(R.id.frameLayoutContent,fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onBackPressed() {

        if(! (fragment instanceof HomeDashboardFragment)){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragment = new HomeDashboardFragment();
            fragmentTransaction.replace(R.id.frameLayoutContent,fragment);
            fragmentTransaction.commit();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onButtonClicked(View view) {
        initContentView(view);
    }
}