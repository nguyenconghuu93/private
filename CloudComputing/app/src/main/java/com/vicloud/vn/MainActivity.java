package com.vicloud.vn;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.vicloud.vn.fragments.ServerFragment;
import com.vicloud.vn.fragments.SettingFragment;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ServerFragment serverFragment;
    SettingFragment settingFragment;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    int currentItemSelected;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadFragmentServer();
//        loadNotify();
    }

    private void loadNotify(){
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_server) {
            loadFragmentServer();
        } else if (id == R.id.nav_settings) {
            loadFragmentSetting();
        } else if (id == R.id.nav_logout){
            AlertDialog.Builder dialogConfirmExit = new AlertDialog.Builder(MainActivity.this);
            dialogConfirmExit.setTitle("Bạn có muốn thoát đăng nhập");
            dialogConfirmExit.setNegativeButton("Hủy", null);
            dialogConfirmExit.setPositiveButton("Đồng ý", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
//                    editor = share.edit();
//                    editor.clear();
//                    editor.apply();
//                    finish();
//                    finish();
//                    System.exit(0);
                }
            });
            dialogConfirmExit.show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loadFragmentServer(){
        serverFragment = new ServerFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, serverFragment).addToBackStack(null);
        fragmentTransaction.commit();
        toolbar.setTitle("Servers");
        currentItemSelected = R.id.nav_server;
    }

    private void loadFragmentSetting(){
        settingFragment = new SettingFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, settingFragment);
        fragmentTransaction.commit();
        toolbar.setTitle("Thiết đặt cảnh báo");
        currentItemSelected = R.id.nav_settings;
        navigationView.setCheckedItem(R.id.nav_server);
    }
}
