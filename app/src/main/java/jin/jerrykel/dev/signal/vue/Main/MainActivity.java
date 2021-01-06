package jin.jerrykel.dev.signal.vue.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.Main.fragment.AlertFragment;
import jin.jerrykel.dev.signal.vue.Main.fragment.ProfileFragment;
import jin.jerrykel.dev.signal.vue.Main.fragment.SignalFragment;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;
import jin.jerrykel.dev.signal.vue.settings.SettingActivity;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private FrameLayout frameLayoutContent;
    private TabLayout tabs;
    private ViewPager pager;

    private SignalFragment signalFragment = SignalFragment.newInstance();
    private AlertFragment alertFragment = AlertFragment.newInstance();
    private ProfileFragment  profileFragment = ProfileFragment.newInstance();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;






    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    public void initView(){
        configureDrawerLayout();
        configureNavigationView();
        configureToolbarButon();
        configureViewPagerAndTabs();

    }
    //@Override
    public boolean onCbreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {

            case R.id.menuActionSettings:
                startSettingActivity();
                break;
            default:
                if(!this.drawerLayout.isDrawerOpen(Gravity.START))
                    drawerLayout.openDrawer(Gravity.START);
                else this.drawerLayout.closeDrawer(GravityCompat.START);





        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //open new fragment
        Fragment fragment = null;

        switch (id){
            case R.id.activity_main_drawer_menu_profile :
                // fragment = new NavDrawerMenuTestFragment();
                // TabLayout tabLayout = findViewById(R.id.activity_main_tabs);
                //tabLayout.setVisibility(View.INVISIBLE);

                //Intent intent2 = new Intent(MainActivity.this, ConnectAppActivity.class);
                //Intent intent = new Intent(SaveActivity.this, ConnectActivity.class);
                //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // startActivity(intent2);
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.activity_main_drawer_recycler_signal:
                //Intent intent = new Intent(MainActivity.this, ConnectAppActivity.class);
                //startActivity(intent);
                //overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                // pager.setCurrentItem(1,true);
                tabs.getTabAt(1).select();


                break;
            case R.id.activity_main_drawer_menu_settings:
                startSettingActivity();
                break;
            default:
                break;
        }
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(pager.getId(), fragment);
            ft.commit();
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

        }
    }
    private void startSettingActivity(){
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivity(intent);

    }
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_open_white_24);

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_white_24);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });





    }
    private void configureToolbarButon(){
        // Get the toolbar view inside the activity layout
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        //setSupportActionBar(toolbar);

        //// Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_white_24);

    }
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @SuppressLint("ResourceType")
    private void configureViewPagerAndTabs(){

        this.frameLayoutContent = (FrameLayout) findViewById(R.id.frameLayoutContent);
        tabs= (TabLayout)findViewById(R.id.activity_main_tabs);

        //add fragment
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(),0);
        pageAdapter.addFragmentAndFragmentTilte(signalFragment,getString(R.string.signal));
        pageAdapter.addFragmentAndFragmentTilte(alertFragment,getString(R.string.alert));
        pageAdapter.addFragmentAndFragmentTilte(profileFragment,getString(R.string.profile));


        //Get ViewPager from layout
        pager = new ViewPager(this);
        pager.setId(199020);
        frameLayoutContent.addView(pager);

        pager.setAdapter(pageAdapter);



        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);//Mode_Fixed

        tabs.getTabAt(0).setIcon(R.drawable.ic_baseline_show_chart_white_24);
        tabs.getTabAt(1).setIcon(R.drawable.ic_baseline_notifications_white_24);
        tabs.getTabAt(2).setIcon(R.drawable.ic_baseline_account_box_white_24);
        //BadgeDrawable badgeDrawable = tabs.getTabAt(1).getOrCreateBadge();
        //badgeDrawable.setVisible(true);
        // badgeDrawable.setNumber(0);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#039BE5"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab.getIcon().setColorFilter(Color.parseColor("#a8a8a8"), PorterDuff.Mode.SRC_IN);
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#0288D1"), PorterDuff.Mode.SRC_IN);

            }
        });
        //
    }


}