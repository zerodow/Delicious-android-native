package com.example.admin.appquanan;

import android.os.AsyncTask;
import com.example.admin.appquanan.model.Food;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

/**
 * Created by Admin on 5/14/2018.
 */

public class AsyncFood extends AsyncTask<Void,Void,List<Food>>{
    private List<Food> lst;


    public AsyncFood(List<Food> lst) {
        this.lst = lst;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("food");
        for(Food food : lst){
            myRef.child(String.valueOf(food.getId())).setValue(food);
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Food> foods) {
        super.onPostExecute(foods);
    }

}
