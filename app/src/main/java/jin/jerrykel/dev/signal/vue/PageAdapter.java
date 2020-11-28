package jin.jerrykel.dev.signal.vue;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {


    ArrayList<Fragment> fragmentArrayList = new  ArrayList<>();
    ArrayList<String> fragmentStringtitle  = new ArrayList<>();

    // 2 - Default Constructor
    public PageAdapter(@NonNull FragmentManager fm, int Behavior) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


    }

    public void addFragmentAndFragmentTilte(Fragment fragment){
        //Todo Add icone
        this.fragmentArrayList.add(fragment);

    }
    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        return fragmentArrayList.get(position);
    }




}
