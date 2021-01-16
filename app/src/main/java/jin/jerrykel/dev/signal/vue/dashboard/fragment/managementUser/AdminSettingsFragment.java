package jin.jerrykel.dev.signal.vue.dashboard.fragment.managementUser;

import android.os.Bundle;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

public class AdminSettingsFragment extends BaseFragment {



    public AdminSettingsFragment() {
        // Required empty public constructor
    }


    public static AdminSettingsFragment newInstance() {
        AdminSettingsFragment fragment = new AdminSettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_dash_admin_settings;
    }

    @Override
    public void initView() {

    }
}