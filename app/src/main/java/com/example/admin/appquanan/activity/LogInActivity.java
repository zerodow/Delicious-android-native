package com.example.admin.appquanan.activity;

import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.SlideMenu;
import com.example.admin.appquanan.dialog.NotificationDialog;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LogInActivity extends AppCompatActivity {
    private EditText etUserName, etPassword;
    private Button btnLogin,btnQuickLogin;
    private int i = 0;
    private User user;
    private List<User> lstUser;
    private DatabaseReference mDatabase;
    private ProgressDialog progressDialog;

    public static final int REQUEST_CODE = 111;
    public static final int RESULT_OK = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setWidget();

        mDatabase.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    lstUser.add(user);
                }
                btnLogin.setEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(LogInActivity.this,"Kiểm tra tài khoản","Xin chờ trong giây lát",false,false);
                String name = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                int id = check(lstUser, name, password);
                if (id == 0) {
                    progressDialog.dismiss();
                    NotificationDialog dialog = new NotificationDialog();
                    dialog.setTitle("Tài khoản không tồn tại");
                    dialog.setMessage("Vui lòng kiểm tra lại");
                    dialog.setPositiveLabel("Ok");
                    dialog.setListener(new NotificationDialog.NotificationDialogFragmentListener() {
                        @Override
                        public void onClickYes(int which) {
                            return;
                        }

                        @Override
                        public void onClickNo(int which) {

                        }
                    });
                    dialog.show(getFragmentManager(), "Failed");
                } else {
                    for (User user : lstUser) {
                        if (user.getIdUser() == id){
                            progressDialog.dismiss();
                            Toast.makeText(LogInActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LogInActivity.this, SlideMenu.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("USER", user);
                            intent.putExtra("USERLOGIN", bundle);
                            startActivity(intent);
                        }
                    }

                }
            }
        });

        btnQuickLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User();
                user.setIdUser(0);
                user.setNickName(null);
                user.setUsername(null);
                user.setPassword(null);
                user.setRoleId(3);
                Intent intent = new Intent(LogInActivity.this, SlideMenu.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("USER", user);
                intent.putExtra("USERLOGIN", bundle);
                startActivity(intent);
            }
        });
    }

    private int check(List<User> users, String name, String password) {
        int id = 0;
        for (User user : users) {
            if ((user.getUsername().equals(name)) && (user.getPassword().equals(password))) {
                id = user.getIdUser();
                break;
            }
        }
        return id;
    }

    public void onClick(View view) {
        Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getBundleExtra("BUNDLE");
                user = (User) bundle.getSerializable("USER");
                etUserName.setText(user.getUsername().toString());
                etPassword.setText(user.getPassword().toString());
            }
        }
    }

    private void setWidget() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnQuickLogin= (Button) findViewById(R.id.btnQuickLogin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lstUser = new ArrayList<User>();
    }
}
