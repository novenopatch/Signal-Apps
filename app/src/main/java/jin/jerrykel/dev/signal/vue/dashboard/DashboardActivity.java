package jin.jerrykel.dev.signal.vue.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.BaseActivity;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
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
        super.onBackPressed();
    }

    @Override
    public void onButtonClicked(View view) {
        initContentView(view);
    }
}