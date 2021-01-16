package jin.jerrykel.dev.signal.vue.dashboard.fragment.other;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;


public class BugFragment extends BaseFragment {


    public BugFragment() {
        // Required empty public constructor
    }


    public static BugFragment newInstance() {
        BugFragment fragment = new BugFragment();

        return fragment;
    }



    @Override
    public int getLayout() {
        return R.layout.fragment_dash_bug;
    }

    @Override
    public void initView() {

    }
}