package jin.jerrykel.dev.signal.vue.dashboard.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAccountFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAccountFragment newInstance(String param1, String param2) {
        AddAccountFragment fragment = new AddAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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