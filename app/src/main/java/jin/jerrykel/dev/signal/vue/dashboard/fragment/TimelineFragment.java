package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import androidx.fragment.app.Fragment;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends BaseFragment {



    public TimelineFragment() {
        // Required empty public constructor
    }


    public static TimelineFragment newInstance() {
        TimelineFragment fragment = new TimelineFragment();

        return fragment;
    }



    @Override
    public int getLayout() {
        return R.layout.fragment_dash_timeline;
    }

    @Override
    public void initView() {

    }
}