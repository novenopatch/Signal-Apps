package jin.jerrykel.dev.signal.vue.dashboard.fragment.managementUser;

import androidx.fragment.app.Fragment;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccountFragment extends BaseFragment {


    public AddAccountFragment() {
        // Required empty public constructor
    }


    public static AddAccountFragment newInstance(String param1, String param2) {
        AddAccountFragment fragment = new AddAccountFragment();

        return fragment;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_dash_add_account;
    }

    @Override
    public void initView() {

    }
}