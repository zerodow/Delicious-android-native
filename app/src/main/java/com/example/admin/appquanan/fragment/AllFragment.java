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
public class AllFragment extends Fragment {
    private StaggeredGridView sgrFood;
    private List<Food> lstFood;
    private FoodAdapter adapter;
    private SearchView searchView;
    private User user;
    private TextView tvTitle;
    private DatabaseReference mDatabase;

    public AllFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container, false);
        //lấy dữ liệu
        getData();

        setWidget(view);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstFood.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    lstFood.add(data.getValue(Food.class));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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

    public void setWidget(View view){
        //ánh xạ thành phần view
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText("");
        sgrFood = (StaggeredGridView) view.findViewById(R.id.grid_view);

        //khởi tạo list food
        lstFood = new ArrayList<Food>();

        //khởi tạo firebase db
        mDatabase = FirebaseDatabase.getInstance().getReference().child("food");

        //set adapter cho list food
        adapter = new FoodAdapter(getActivity().getApplicationContext(), R.layout.item_gridview, lstFood);
        sgrFood.setAdapter(adapter);

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
        if(lstFood.size() > 0){
            tvTitle.setText(" ");
        } else {
            tvTitle.setText("Vui lòng vào menu -> Cập nhật món ăn để cập nhật thêm món ăn");
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
