package com.example.admin.appquanan.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.etsy.android.grid.StaggeredGridView;
import com.example.admin.appquanan.R;
import com.example.admin.appquanan.activity.ItemActivity;
import com.example.admin.appquanan.adapter.FoodAdapter;
import com.example.admin.appquanan.model.Food;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DistrictFragment extends Fragment {

    private StaggeredGridView sgrFood;
    private List<Food> lstFood;
    private DatabaseReference mDatabase;
    private FoodAdapter adapter;
    private SearchView searchView;
    private TextView tvTitle;
    private ImageView imgAddress;
    private User user;

    public void setDistrict(String district) {
        this.district = district;
    }

    private String district;

    public DistrictFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_district, container, false);

        getData();

        setWidget(view);

        if(district.equals("---- Chọn địa điểm ----")){
            imgAddress.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);

        } else {
            imgAddress.setVisibility(View.INVISIBLE);
            tvTitle.setVisibility(View.INVISIBLE);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    lstFood.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Food food = data.getValue(Food.class);
                        if(food.getDistrict().equals(district)){
                            lstFood.add(food);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        sgrFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ItemActivity.class);
                Bundle bundle1 = new Bundle();
                Bundle bundle2 = new Bundle();

                Food food = lstFood.get(i);
                bundle1.putSerializable("FOOD", food);
                intent.putExtra("BUNDLE1", bundle1);

                bundle2.putSerializable("USER", user);
                intent.putExtra("BUNDLE2", bundle2);

                startActivity(intent);
            }
        });
        return view;
    }

    private void setWidget(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        imgAddress = (ImageView) view.findViewById(R.id.imgAddress);

        sgrFood = (StaggeredGridView) view.findViewById(R.id.grid_view);

        lstFood = new ArrayList<Food>();

        adapter = new FoodAdapter(getActivity().getApplicationContext(), R.layout.item_gridview, lstFood);

        sgrFood.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("food");
    }

    private void getData() {
        user = (User) getArguments().getSerializable("USERACCOUNT");
    }

    public void doFilter(String name) {
        List<Food> lstOutput = new ArrayList<Food>();

        for (Food food : lstFood) {
            if (food.getNameFood().toLowerCase().contains(name)) {
                lstOutput.add(food);
            }
        }

        adapter = new FoodAdapter(getActivity().getApplicationContext(), R.layout.item_gridview, lstOutput);
        sgrFood.setAdapter(adapter);
    }

    public void showList() {
        if (lstFood.size() > 0) {
            tvTitle.setText(" ");
        } else {
            tvTitle.setText("Hiện địa điểm này chưa có món ăn nào");
        }
        adapter = new FoodAdapter(getActivity().getApplicationContext(), R.layout.item_gridview, lstFood);
        sgrFood.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);
        searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!s.isEmpty()) {
                    doFilter(s);
                } else {
                    showList();
                }
                return false;
            }
        });
    }
}
