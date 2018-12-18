package com.example.admin.appquanan.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.model.Food;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ItemActivity extends AppCompatActivity {
    private TextView tvIconLike, tvIconCmt, tvTotalLike, tvTotalCmt;
    private TextView tvPrice, tvAddress, tvTitleFood;
    private ImageView imgFood;
    private Button btnCmt, btnLike;
    private Food food;
    private boolean checkLike;
    private User user;
    private DatabaseReference mDatabase;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        setWidget();

        getData();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // clear list hiện tại và add list mới sau đó set lại adapter
                food = dataSnapshot.getValue(Food.class);
                showDetail(food);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        mDatabase.addValueEventListener(postListener);
        tvIconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getRoleId() == 3){
                    showAlertDialog();
                } else {
                    int like = food.getTotalLike();
                    if(food.getCheckLike() == 0){
                        mDatabase.child("checkLike").setValue(1);
                        mDatabase.child("totalLike").setValue(like+1);
                    } else {
                        mDatabase.child("checkLike").setValue(0);
                        mDatabase.child("totalLike").setValue(like-1);
                    }
                }
            }
        });

        tvIconCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getRoleId() == 3){
                    showAlertDialog();
                } else{
                    Intent intent = new Intent(ItemActivity.this, CmtActivity.class);

                    Bundle bundle1 = new Bundle();
                    Bundle bundle2 = new Bundle();

                    bundle1.putSerializable("FOOD", food);
                    intent.putExtra("BUNDLE1", bundle1);

                    bundle2.putSerializable("USER", user);
                    intent.putExtra("BUNDLE2",bundle2);

                    startActivity(intent);
                }
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle1 = intent.getBundleExtra("BUNDLE1");

        //lấy ra food tương ứng
        food = (Food) bundle1.getSerializable("FOOD");

        Bundle bundle2 = intent.getBundleExtra("BUNDLE2");

        //lấy ra id của người đăng nhập
        user = (User) bundle2.getSerializable("USER");

        showDetail(food);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("food").child(String.valueOf(food.getId()));
    }

    private void showDetail(Food food){
        Typeface face = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        tvTitleFood.setText(food.getNameFood());
        tvAddress.setText("Địa chỉ : " + food.getAddress());
        tvPrice.setText(food.getPrice()+"đ");
        tvTotalCmt.setText("" + food.getTotalCmt());
        tvTotalLike.setText("" + food.getTotalLike());
        tvIconCmt.setTypeface(face);
        if (food.getCheckLike() == 0 || user.getRoleId() == 3) {
            tvIconLike.setTypeface(face);
            tvIconLike.setTextColor(ContextCompat.getColor(ItemActivity.this,R.color.white));
            checkLike = false;
        } else {
            tvIconLike.setTypeface(face);
            tvIconLike.setTextColor(ContextCompat.getColor(ItemActivity.this,R.color.checklike));
            checkLike = true;
        }
        Picasso.with(this).load(food.getImageFood()).into(imgFood);

    }

    private void setWidget() {
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvIconLike = (TextView) findViewById(R.id.tvIconLike);
        tvIconCmt = (TextView) findViewById(R.id.tvIconComment);
        tvTotalCmt = (TextView) findViewById(R.id.tvTotalCmt);
        tvTotalLike = (TextView) findViewById(R.id.tvTotalLike);
        tvTitleFood = (TextView) findViewById(R.id.tvTitleFood);
        imgFood = (ImageView) findViewById(R.id.imgFood);
    }

    public void showAlertDialog(){
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
