package jin.jerrykel.dev.signal.vue.dashboard;

import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.HomeDashboardFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Send.SendNewSignalTypeFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.Send.SendSignalFragment;
import jin.jerrykel.dev.signal.vue.dashboard.fragment.managementUser.ManageUsersFragment;

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
            case R.id.linearLayoutSendSignal:
                 fragment = new SendSignalFragment();
                break;
            case R.id.linearLayoutSendNewSignalType:
                fragment = new SendNewSignalTypeFragment();
                ///
                break;
            case R.id.linearLayoutManageUser:
                fragment = new ManageUsersFragment();
                ///
                break;
            case R.id.linearLayoutUpdateImagePub:
                //fragment = new UpdatePubImgFragment();
                makeNote();
                ///
                break;
            case R.id.linearLayoutRootTimeLine:
                makeNote();
                // fragment = new TimelineFragment();
                ///
                break;

            case R.id.linearLayoutAddProAccount:
                //fragment = new AddAccountFragment();
                makeNote();
                ///
                break;
            case R.id.linearLayoutGetGraphicUsage:
                //fragment = new UsageFragment();
                makeNote();
                ///
                break;
            case R.id.LinearLayoutAdminSettings:
                //fragment = new AdminSettingsFragment();
                makeNote();
                ///
                break;
            case R.id.linearLayoutSendBugReport:
                //fragment = new BugFragment();
                makeNote();
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
    private void makeNote(){
        new AlertDialog.Builder(this).setTitle("Dev Message ?")
                .setMessage("Cette option est en cours de construction. \n" +
                        " veillez contater le programmeur pour assistance.\n" +
                        " Contacter?")
                .setPositiveButton("YES", (dialog, which) -> {

                    // Perform Action & Dismiss dialog
                    dialog.dismiss();

                })
                .setNegativeButton("NO", (dialog, which) -> {
                    // Do nothing
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}