package com.example.admin.appquanan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.admin.appquanan.activity.AboutUsActivity;
import com.example.admin.appquanan.activity.AccountActivity;
import com.example.admin.appquanan.activity.DistrictActivity;
import com.example.admin.appquanan.activity.UpdateDataActivity;
import com.example.admin.appquanan.adapter.PagerAdapter;
import com.example.admin.appquanan.fragment.AllFragment;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SlideMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private User user;
    private NavigationView navigationView;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getData();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager manager = getSupportFragmentManager();
        PagerAdapter adapter = new PagerAdapter(manager);
        adapter.setUser(user);
        adapter.setContext(SlideMenu.this);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    private void getData() {
        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("USERLOGIN");

        user = (User) bundle.getSerializable("USER");
        Log.d("TAG","ádads");

        Menu menuNav = navigationView.getMenu();
        MenuItem item = menuNav.findItem(R.id.nav_morefood);
        MenuItem item2 = menuNav.findItem(R.id.nav_logout);
        //neu la admin thi moi hien thi menu cap nhat
        if(user.getRoleId() == 1){
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }

        if(user.getRoleId() == 3){
            item2.setTitle("Đăng nhập");
        }
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile: {
                if(user.getRoleId() == 3){
                    showAlertDialog2();
                } else {
                    Intent intent = new Intent(SlideMenu.this, AccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER", user);
                    intent.putExtra("ACCOUNT", bundle);
                    startActivity(intent);
                }
                break;
            }
            case R.id.nav_district: {
                if(user.getRoleId() == 3){
                    showAlertDialog2();
                } else {
                    Intent intent = new Intent(SlideMenu.this, DistrictActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER", user);
                    intent.putExtra("USERLOGIN", bundle);
                    startActivity(intent);
                }
                break;
            }

            case R.id.nav_morefood: {
                Intent intent = new Intent(SlideMenu.this, UpdateDataActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.nav_about: {
                Intent intent = new Intent(SlideMenu.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.nav_logout: {
                showAlertDialog();
                break;
            }

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAlertDialog() {
        if(user.getRoleId() ==3 ){
            finish();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Đăng xuất");
            builder.setMessage("Bạn có muốn đăng xuất không?");
            builder.setCancelable(false);
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }


    public void showAlertDialog2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Không thể sử dụng chức năng này");
        builder.setMessage("Vui lòng đăng nhập để sử dụng chức năng này");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


}
