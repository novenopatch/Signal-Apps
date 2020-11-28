package jin.jerrykel.dev.signal.vue;

import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.tabs.TabLayout;
import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.vue.fragment.AddFragment;
import jin.jerrykel.dev.signal.vue.fragment.HomeFragment;
import jin.jerrykel.dev.signal.vue.fragment.SettingFragment;

public class AppsActivity extends BaseActivity {

    private FrameLayout frameLayoutContent;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private AddFragment addFragment = AddFragment.newInstance();
    private SettingFragment settingFragment = SettingFragment.newInstance();
    private TabLayout tabs;
    private ViewPager pager;
    private Controler controler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        configureViewPagerAndTabs();
    }
    @SuppressLint("ResourceType")
    private void configureViewPagerAndTabs(){

        this.frameLayoutContent = (FrameLayout) findViewById(R.id.frameLayoutContent);
        tabs= (TabLayout)findViewById(R.id.activity_main_tabs);

        //add fragment
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),0);
        pageAdapter.addFragmentAndFragmentTilte(homeFragment,"Home(TODO)");
        pageAdapter.addFragmentAndFragmentTilte(addFragment,"add(TODO)");
        pageAdapter.addFragmentAndFragmentTilte(settingFragment,"setting(Todo)");

        //Get ViewPager from layout
        pager = new ViewPager(this);
        pager.setId(199020);
        frameLayoutContent.addView(pager);

        pager.setAdapter(pageAdapter);



        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);//Mode_Fixed

        tabs.getTabAt(0).setIcon(R.drawable.ic_baseline_account_circle_black_24);
        //BadgeDrawable badgeDrawable = tabs.getTabAt(1).getOrCreateBadge();
        //badgeDrawable.setVisible(true);
        // badgeDrawable.setNumber(0);

    }
}