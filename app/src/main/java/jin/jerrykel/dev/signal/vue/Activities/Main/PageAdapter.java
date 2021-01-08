package jin.jerrykel.dev.signal.vue.Activities.Main;

/**
 * Created by JerrykelDEV on 05/01/2021 23:57
 */


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class PageAdapter extends FragmentPagerAdapter  {
    ArrayList<Fragment> fragmentArrayList = new  ArrayList<>();
    ArrayList<String> fragmentStringTitle  = new ArrayList<>();

    // 2 - Default Constructor
    public PageAdapter(@NonNull FragmentManager fm, int Behavior) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


    }

    public void addFragmentAndFragmentTilte(Fragment fragment, String title){
        //Todo Add icone
        this.fragmentArrayList.add(fragment);
        this.fragmentStringTitle.add(title);
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentStringTitle.get(position);
    }
}
