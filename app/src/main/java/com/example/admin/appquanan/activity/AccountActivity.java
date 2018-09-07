package com.example.admin.appquanan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AccountActivity extends AppCompatActivity {
    private TextView tvNickname,tvUsername,tvPassword;
    private Button btnCapNhat,btnDoiMatKhau;
    private User user;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        setWidget();

        getData();


        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AccountActivity.this);
                dialog.setContentView(R.layout.dialog_update);

                final EditText etNewNickname = (EditText) dialog.findViewById(R.id.etChangeNickname);
                Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

                etNewNickname.setText(""+user.getNickName());

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.setNickName(etNewNickname.getText().toString());

                        mDatabase.child(user.getIdUser()+"").child("nickName").setValue(etNewNickname.getText().toString());
                        setDataWidget();
                        dialog.dismiss();
                        Toast.makeText(AccountActivity.this, "Đổi tên thành công", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.show();

            }
        });

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(AccountActivity.this);
                dialog.setContentView(R.layout.dialog_change_password);

                final EditText etOldPassword = (EditText) dialog.findViewById(R.id.etOldPassword);
                final EditText etNewPassword = (EditText) dialog.findViewById(R.id.etNewPassword);
                final EditText etTypeAgain = (EditText) dialog.findViewById(R.id.etTypeAgain);
                final TextView tvFail1 = (TextView) dialog.findViewById(R.id.tvFail1);
                final TextView tvFail2 = (TextView) dialog.findViewById(R.id.tvFail2);
                final TextView tvFail3 = (TextView) dialog.findViewById(R.id.tvFail3);
                Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tvFail1.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));
                        tvFail2.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));
                        tvFail3.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.white));

                        String oldPassword = user.getPassword();
                        String typeOldPassword = etOldPassword.getText().toString();

                        if(typeOldPassword.equals(oldPassword)){

                            String newPassword = etNewPassword.getText().toString();
                            String typeAgain = etTypeAgain.getText().toString();

                            if(newPassword.equals(typeAgain)){

                                if(Ulits.checkPassword(newPassword)){
                                   user.setPassword(newPassword);

                                    mDatabase.child(user.getIdUser()+"").child("password").setValue(newPassword);

                                   setDataWidget();
                                    Toast.makeText(AccountActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                   dialog.dismiss();
                                }
                                else{
                                    tvFail2.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.textFail));
                                    return;
                                }
                            }
                            else{
                                tvFail3.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.textFail));
                                return;
                            }
                        }
                        else {
                            tvFail1.setTextColor(ContextCompat.getColor(AccountActivity.this, R.color.textFail));
                            return;
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();

        Bundle bundle = intent.getBundleExtra("ACCOUNT");

        user = (User) bundle.getSerializable("USER");

        setDataWidget();

    }

    private void setWidget() {
        tvNickname = (TextView) findViewById(R.id.tvNickname);
        tvUsername = (TextView) findViewById(R.id.tvUserName);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        btnCapNhat = (Button) findViewById(R.id.btnCapNhat);
        btnDoiMatKhau = (Button) findViewById(R.id.btnDoiMatKhau);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
    }

    private void setDataWidget(){
        tvNickname.setText(user.getNickName());
        tvUsername.setText(user.getUsername());
        tvPassword.setText(user.getPassword());
    }
}
