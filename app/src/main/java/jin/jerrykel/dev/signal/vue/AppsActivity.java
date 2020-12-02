package jin.jerrykel.dev.signal.vue;

import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.vue.fragment.MessageFragment;
import jin.jerrykel.dev.signal.vue.fragment.HomeFragment;
import jin.jerrykel.dev.signal.vue.fragment.SettingFragment;

public class AppsActivity extends BaseActivity {

    private FrameLayout frameLayoutContent;
    private HomeFragment homeFragment = HomeFragment.newInstance();
    private MessageFragment messageFragment = MessageFragment.newInstance();
    private SettingFragment settingFragment = SettingFragment.newInstance();
    private TabLayout tabs;
    private ViewPager pager;
    private Controler controler;
    private BadgeDrawable badgeDrawableM;
    private  BadgeDrawable badgeDrawableH;
    private  BadgeDrawable badgeDrawableS;
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
        pageAdapter.addFragmentAndFragmentTilte(homeFragment);
        pageAdapter.addFragmentAndFragmentTilte(messageFragment);
        pageAdapter.addFragmentAndFragmentTilte(settingFragment);

        //Get ViewPager from layout
        pager = new ViewPager(this);
        pager.setId(199020);
        frameLayoutContent.addView(pager);

        pager.setAdapter(pageAdapter);



        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);//Mode_Fixed

        tabs.getTabAt(0).setIcon(R.drawable.ic_round_home_black_48);
        tabs.getTabAt(1).setIcon(R.drawable.ic_round_message_black_48);
        tabs.getTabAt(2).setIcon(R.drawable.ic_round_settings_black_48);


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#039BE5"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#0288D1"), PorterDuff.Mode.SRC_IN);

            }
        });



        updateNotificationBadge(1,40);

    }
    public void updateNotificationBadge( int tab,int nbr){
        switch (tab){
            case 0:
                badgeDrawableH = tabs.getTabAt(0).getOrCreateBadge();
                if(nbr<=0){
                    badgeDrawableH.setVisible(false);
                }else {
                    badgeDrawableH.setVisible(true);
                    badgeDrawableH.setNumber(nbr);
                }
                break;
            case 1:
                badgeDrawableM = tabs.getTabAt(1).getOrCreateBadge();
                if(nbr<=0){
                    badgeDrawableM.setVisible(false);
                }else {
                    badgeDrawableM.setVisible(true);
                    badgeDrawableM.setNumber(nbr);
                }
                break;
            case 2:
                badgeDrawableS = tabs.getTabAt(2).getOrCreateBadge();
                if(nbr<=0){
                    badgeDrawableS.setVisible(false);
                }else {
                    badgeDrawableS.setVisible(true);
                    badgeDrawableS.setNumber(nbr);
                }
                break;
            default:
                break;


        }


    }
}