package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import androidx.fragment.app.Fragment;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePubImgFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePubImgFragment extends BaseFragment {



    public UpdatePubImgFragment() {
        // Required empty public constructor
    }


    public static UpdatePubImgFragment newInstance() {
        UpdatePubImgFragment fragment = new UpdatePubImgFragment();

        return fragment;
    }



    @Override
    public int getLayout() {
        return R.layout.fragment_dash_update_pub_img;
    }

    @Override
    public void initView() {

    }
}