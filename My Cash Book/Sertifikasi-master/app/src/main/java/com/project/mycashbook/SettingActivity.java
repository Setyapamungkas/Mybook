package com.project.mycashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.mycashbook.DataBase.DataBaseAccess;

public class SettingActivity extends AppCompatActivity {

    Button simpan, kembali;
    EditText oldpass, newpass, confirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.kembali);
        oldpass = findViewById(R.id.oldpass);
        newpass = findViewById(R.id.newpass);
        confirmation = findViewById(R.id.confirmation);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oldpass.getText().toString().equals("") || newpass.getText().toString().equals("") || confirmation.getText().toString().equals("")){
                    oldpass.setError("");
                    newpass.setError("");
                    confirmation.setError("");
                    Toast.makeText(SettingActivity.this, "Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
                } else {
                    if(!newpass.getText().toString().equals(confirmation.getText().toString()) || newpass.getText().toString().equals("")){
                        newpass.setError("");
                        confirmation.setError("");
                        Toast.makeText(SettingActivity.this, "Password Baru dan Konfirmasi Tidak Sama / Kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(SettingActivity.this);
                        dataBaseAccess.open();

                        Cursor data = dataBaseAccess.Where("user", "username = 'USER' AND password ='" + oldpass.getText().toString() + "'");

                        if (data.getCount() == 0) {
                            Toast.makeText(SettingActivity.this, "Password Lama yang Anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean isUpdated = dataBaseAccess.updateUser(newpass.getText().toString(), "USER");

                            if(isUpdated){
                                Toast.makeText(SettingActivity.this, "Password Anda Berhasil Diganti", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SettingActivity.this, MenuActivity.class));
                            } else {
                                Toast.makeText(SettingActivity.this, "Password Anda Gagal Diganti", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, MenuActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this, MenuActivity.class));
    }
}