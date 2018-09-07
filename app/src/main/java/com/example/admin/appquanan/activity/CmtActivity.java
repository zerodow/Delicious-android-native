package com.example.admin.appquanan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.TimeComparator;
import com.example.admin.appquanan.adapter.CommentAdapter;
import com.example.admin.appquanan.model.Comment;
import com.example.admin.appquanan.model.Food;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CmtActivity extends AppCompatActivity {
    private ListView lvCmt;
    private Button btnSend;
    private EditText etCmt;
    private List<Comment> lstCmt;
    private CommentAdapter adapter;
    private User user;
    private Food food;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmt);

        setWidget();

        getData();

        adapter = new CommentAdapter(CmtActivity.this, R.layout.item_list_cmt, lstCmt);

        lvCmt.setAdapter(adapter);
        adapter.setUser(user);

        mDatabase.child("comment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstCmt.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Comment cmt = data.getValue(Comment.class);
                    if (cmt.getFoodId() == food.getId()) {
                        lstCmt.add(cmt);
                    }
                }
                Collections.sort(lstCmt, new TimeComparator());
                adapter.setUser(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CmtActivity.this, "Có lỗi xảy ra, vui lòng kiểm tra lại đường truyền", Toast.LENGTH_LONG).show();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmt = etCmt.getText().toString();
                if (cmt.isEmpty()) {
                    return;
                } else {
                    Comment comment = new Comment();
                    comment.setContent("" + etCmt.getText().toString());
                    comment.setFoodId(food.getId());
                    comment.setUserId(user.getIdUser());

                    long time = System.currentTimeMillis();
                    comment.setTime(String.valueOf(time));

                    Random rand = new Random();
                    comment.setIdComment(rand.nextInt(1000000));//1 tỷ;
                    int totalCmt = food.getTotalCmt();

                    totalCmt += 1;

                    food.setTotalCmt(totalCmt);

                    mDatabase.child("comment").child(comment.getIdComment() + "").setValue(comment);
                    mDatabase.child("food").child(food.getId() + "").setValue(food);

                    etCmt.setText("");
                }
            }
        });

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle1 = intent.getBundleExtra("BUNDLE1");

        food = (Food) bundle1.getSerializable("FOOD");

        Bundle bundle2 = intent.getBundleExtra("BUNDLE2");

        user = (User) bundle2.getSerializable("USER");

        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (user.getIdUser() == Integer.parseInt(data.getKey())) {
                        user = data.getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CmtActivity.this, "Có lỗi xảy ra, vui lòng kiểm tra lại đường truyền", Toast.LENGTH_LONG).show();
            }
        });

        mDatabase.child("food").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (food.getId() == Integer.parseInt(data.getKey())) {
                        food = data.getValue(Food.class);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CmtActivity.this, "Có lỗi xảy ra, vui lòng kiểm tra lại đường truyền", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setWidget() {
        lvCmt = (ListView) findViewById(R.id.lvCmt);
        etCmt = (EditText) findViewById(R.id.etCmt);
        btnSend = (Button) findViewById(R.id.btnSend);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lstCmt = new ArrayList<Comment>();


    }
}
