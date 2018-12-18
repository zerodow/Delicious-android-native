package com.example.admin.appquanan.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.model.User;
import com.example.admin.appquanan.ulits.Ulits;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    private EditText etUser, etPass, etName;
    private Button btnSignUp;
    private DatabaseReference mDatabase;
    private TextView tvFailUsername, tvFailPassword;

    public static final int RESULT_OK = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setWidget();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUser.getText().toString();
                String password = etPass.getText().toString();


                if (!Ulits.checkUsername(username)) {
                    tvFailUsername.setTextColor(ContextCompat.getColor(SignUpActivity.this,R.color.black2));
                }

                if (!Ulits.checkPassword(password)) {
                    tvFailPassword.setTextColor(ContextCompat.getColor(SignUpActivity.this,R.color.black2));
                }

                if ((Ulits.checkUsername(username)) && (Ulits.checkPassword(password))) {
                    Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    User user = new User();

                    user.setNickName(etName.getText().toString());
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setRoleId(2);

                    Random rand = new Random();
                    user.setIdUser(rand.nextInt(1000000));//1 tỷ;

                    mDatabase.child(user.getIdUser()+"").setValue(user);

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("USER", user);
                    intent.putExtra("BUNDLE", bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }

        });
    }

    private void setWidget() {
        etUser = (EditText) findViewById(R.id.etUser);
        etPass = (EditText) findViewById(R.id.etPass);
        etName = (EditText) findViewById(R.id.etName);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvFailUsername = (TextView) findViewById(R.id.tvFailUsername);
        tvFailPassword = (TextView) findViewById(R.id.tvFailPassword);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
    }
}
