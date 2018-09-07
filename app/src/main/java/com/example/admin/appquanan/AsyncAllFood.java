package com.example.admin.appquanan;

import android.os.AsyncTask;

import com.example.admin.appquanan.model.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/14/2018.
 */

public class AsyncAllFood extends AsyncTask<Void,Void,List<Food>> {
    @Override
    protected void onPostExecute(List<Food> foods) {
        super.onPostExecute(foods);
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        final List<Food> lst = new ArrayList<Food>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    lst.add(data.getValue(Food.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return lst;
    }
}
