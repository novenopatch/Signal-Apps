package jin.jerrykel.dev.signal.vue.Activities.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.api.UserHelper;
import jin.jerrykel.dev.signal.vue.Activities.Main.fragment.AlertFragment;
import jin.jerrykel.dev.signal.vue.Activities.Main.fragment.ProfileFragment;
import jin.jerrykel.dev.signal.vue.Activities.Main.fragment.SignalFragment;
import jin.jerrykel.dev.signal.vue.Activities.settings.SettingActivity;
import jin.jerrykel.dev.signal.vue.base.BaseActivity;

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

                tabs.getTabAt(2).select();
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                break;
            case R.id.activity_main_drawer_recycler_signal:
                tabs.getTabAt(0).select();


                break;
            case R.id.activity_main_drawer_menu_settings:
                startSettingActivity();
                break;
            case R.id.activity_main_drawer_menu_logout:
                new AlertDialog.Builder(this).setTitle("Confirm ?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("YES", (dialog, which) -> {
                            signOutUserFromFirebase();
                                    // Perform Action & Dismiss dialog
                                    dialog.dismiss();

                                })
                        .setNegativeButton("NO", (dialog, which) -> {
                            // Do nothing
                            dialog.dismiss();
                        })
                        .create()
                        .show();

                break;
            case R.id.activity_main_drawer_recycler_tutorial:
                startSettingActivity();
                break;
            case R.id.activity_main_drawer_recycler_share:
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
    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());

    }
    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {

            //4 - We also delete user from firestore storage
            UserHelper.deleteUser(this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted());
        }
    }
    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(){
        return aVoid -> finish();
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
    private boolean ifInternet(){
        ConnectivityManager connec = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        if (connec != null &&
                ((connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED))
        ) {
            //You are connected, do something online.
            return true;
        }
        else if (
                connec != null &&
                        ( (connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.DISCONNECTED) || (connec.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.DISCONNECTED ))
        ) { //Not connected. Toast.makeText(getApplicationContext(), "You must be connected to the internet", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }

}