package com.example.admin.appquanan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.admin.appquanan.activity.LogInActivity;
import com.example.admin.appquanan.dialog.NotificationDialog;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/16/2018.
 */

public class AsyncUser extends AsyncTask<Void, Void, List<User>> {
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;
    private Context context;
    private String name;
    private String password;

    public AsyncUser(Context context, String name, String password) {
        this.context = context;
        this.name = name;
        this.password = password;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        final List<User> lstUser = new ArrayList<User>();

        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    lstUser.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return lstUser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = ProgressDialog.show(context, "Check user", "Please wait ...", false, false);
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
        if(users.size() == 0){

        } else {
            if (check(users)) {
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(context, "Tài khoản không tồn tại", Toast.LENGTH_LONG);
            }

        }
    }

    private boolean check(List<User> users) {
        boolean check = false;
        for (User user : users) {
            if ((user.getUsername().equals(name)) && (user.getPassword().equals(password))) {
                Intent intent = new Intent(context, SlideMenu.class);
                intent.putExtra("USERLOGIN", user.getIdUser());
                context.startActivity(intent);
                check = true;
                break;
            }
        }

        return check;
    }
}
