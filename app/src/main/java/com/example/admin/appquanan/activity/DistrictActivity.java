package com.example.admin.appquanan.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.fragment.DistrictFragment;
import com.example.admin.appquanan.model.User;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {
    private Spinner spnDistrict;
    private List<String> lstDistrict;
    private FragmentManager fragmentManager;
    private DistrictFragment districtFragment;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        setWidget();

        getData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lstDistrict);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnDistrict.setAdapter(adapter);
        spnDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                districtFragment = new DistrictFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);

                districtFragment.setArguments(bundle);
                districtFragment.setHasOptionsMenu(true);
                districtFragment.setDistrict(lstDistrict.get(i).toString());

                fragmentTransaction.replace(R.id.frFragment, districtFragment);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getData() {
        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("USERLOGIN");

        user = (User) bundle.getSerializable("USER");
    }

    private void setWidget() {
        spnDistrict = (Spinner) findViewById(R.id.spnDistrict);
        lstDistrict = new ArrayList<String>();
        lstDistrict.add("---- Chọn địa điểm ----");
        lstDistrict.add("Quận Hoàn Kiếm");
        lstDistrict.add("Quận Đống Đa");
        lstDistrict.add("Quận Hai Bà Trưng");
        lstDistrict.add("Quận Thanh Xuân");
        lstDistrict.add("Quận Long Biên");
        lstDistrict.add("Quận Ba Đình");

    }


}
