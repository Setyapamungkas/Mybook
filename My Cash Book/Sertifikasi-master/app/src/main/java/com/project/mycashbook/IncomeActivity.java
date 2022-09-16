package com.project.mycashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mycashbook.DataBase.DataBaseAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class IncomeActivity extends AppCompatActivity {

    Button simpan, kembali;
    EditText nominal, keterangan;
    ImageButton btntanggal;
    TextView tanggal;

    String tanggalIndo = "";

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdfTanggalIndo = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        simpan = findViewById(R.id.simpan);
        kembali = findViewById(R.id.kembali);
        nominal = findViewById(R.id.nominal);
        keterangan = findViewById(R.id.keterangan);
        btntanggal = findViewById(R.id.btntanggal);
        tanggal = findViewById(R.id.tanggal);

        tanggalIndo = sdfTanggalIndo.format(new Date());

        tanggal.setText(tanggalIndo);

        btntanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(IncomeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        try {
                            tanggalIndo = sdfTanggalIndo.format(Objects.requireNonNull(sdfTanggalIndo.parse(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tanggal.setText(tanggalIndo);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nominal.getText().toString().equals("") || keterangan.getText().toString().equals("")){
                    Toast.makeText(IncomeActivity.this, "Harap Lengkapi Data Anda", Toast.LENGTH_SHORT).show();
                } else {
                    Integer jumlah = Integer.valueOf(nominal.getText().toString());
                    DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(IncomeActivity.this);
                    dataBaseAccess.open();

                    boolean isInserted = dataBaseAccess.insertMoney(jumlah, keterangan.getText().toString(), tanggal.getText().toString(), "income");

                    if(isInserted){
                        Toast.makeText(IncomeActivity.this, "Berhasil Memasukkan Pemasukkan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(IncomeActivity.this, MenuActivity.class));
                    } else {
                        Toast.makeText(IncomeActivity.this, "Gagal Memasukkan Pemasukkan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IncomeActivity.this, MenuActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(IncomeActivity.this, MenuActivity.class));
    }
}