package com.example.admin.appquanan.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanan.AsyncFood;
import com.example.admin.appquanan.R;
import com.example.admin.appquanan.model.Food;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class UpdateDataActivity extends AppCompatActivity {
    private Button btnUpdate,btnCheck;
    private List<Food> lst;
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lst = new ArrayList<Food>();

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = ProgressDialog.show(UpdateDataActivity.this,"Getting data","Please wait...",false,false);

                String url = "https://latte.lozi.vn/v1.2/topics/1/photos?t=popular&cityId=1";
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Content-Type", "application/json");

                client.get(UpdateDataActivity.this, url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(responseBody));
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            lst = handleJsonArray(jsonArray);
                            AsyncFood asyncFood = new AsyncFood(lst);
                            asyncFood.execute();

                            progressDialog.dismiss();
                            Toast.makeText(UpdateDataActivity.this, "Getting data successfully", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("TAG", "hehehe");
                        Toast.makeText(UpdateDataActivity.this, "Thất bại ", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public List<Food> handleJsonArray(JSONArray jsonArray) {
        List<Food> lst = new ArrayList<Food>();
        for (int i = 0; i < 20; i++) {
            try {
                lst.add(convertObject(jsonArray.getJSONObject(i)));
                Log.d("TAG", "ahihi");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return lst;
    }

    public Food convertObject(JSONObject jsonObject) {
        Food food = new Food();
        Log.d("TAG", "ahihihi");
        try {
            String id = jsonObject.getString("_id");

            String image = jsonObject.getString("image");

            JSONObject dish = jsonObject.getJSONObject("dish");
            String price = dish.getString("price");

            JSONObject eatery = dish.getJSONObject("eatery");
            JSONObject address = eatery.getJSONObject("address");
            String name = eatery.getString("name");
            String district = address.getString("district");
            String addressFood = address.getString("full");

            JSONObject count = jsonObject.getJSONObject("count");
            String like = count.getString("like");
            String comment = count.getString("comment");

            String realPrice = formatNumbersAsCode(price);
            food.setId(Integer.parseInt(id));
            food.setPrice(realPrice);
            food.setDistrict(district);
            food.setAddress(addressFood);

            food.setNameFood(name);
            food.setImageFood(image);
            food.setType("Đồ ăn");
            food.setLove(0);
            food.setCheckLike(0);
            food.setTotalLike(Integer.parseInt(like));
            food.setTotalCmt(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return food;

    }

    public String formatNumbersAsCode(CharSequence s) {
        int groupDigits = 0;
        String tmp = "";
        try {
            for (int i = s.length() - 1; i >= 0; --i) {
                tmp = s.charAt(i) + tmp;
                ++groupDigits;
                if (groupDigits == 3 && i > 0) {
                    tmp = "." + tmp;
                    groupDigits = 0;
                }
            }
            return tmp;
        } catch (Exception e) {
            return s.toString();
        }
    }
}
