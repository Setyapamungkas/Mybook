package com.project.mycashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.data.Entry;
//
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
import com.project.mycashbook.DataBase.DataBaseAccess;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    LinearLayout income, outcome, detail, setting;
    TextView pengeluaran, pemasukkan;

//    ArrayList<Entry> jumlah;
//    String[] tanggal;

//    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        income = findViewById(R.id.income);
        outcome = findViewById(R.id.outcome);
        detail = findViewById(R.id.detail);
        setting = findViewById(R.id.setting);
        pengeluaran = findViewById(R.id.pengeluaran);
        pemasukkan = findViewById(R.id.pemasukkan);

//        lineChart = findViewById(R.id.lineChart);
        
        getSumIncome();
        getSumOutcome();
//        getGraphicsData();

        //untuk pindah halaman
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, IncomeActivity.class));
            }
        });

        outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, OutcomeActivity.class));
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, DetailActivity.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SettingActivity.class));
            }
        });
    }

    private void getSumIncome() {
        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(MenuActivity.this);
        dataBaseAccess.open();

        Cursor data = dataBaseAccess.Sum("jumlah", "money", "flow = 'income'");

        if(data.getCount() == 0){
            pemasukkan.setText("Pemasukkan : Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    pemasukkan.setText("Pemasukkan : Rp. " + data.getString(0) + ".-");
                } else {
                    pemasukkan.setText("Pemasukkan : Rp. 0.-");
                }
            }
        }
    }

    private void getSumOutcome() {
        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(MenuActivity.this);
        dataBaseAccess.open();

        Cursor data = dataBaseAccess.Sum("jumlah", "money", "flow = 'outcome'");

        if(data.getCount() == 0){
            pengeluaran.setText("Pengeluaran : Rp. 0.-");
        } else {
            while(data.moveToNext()){
                if(data.getString(0) != null) {
                    pengeluaran.setText("Pengeluaran : Rp. " + data.getString(0) + ".-");
                } else {
                    pengeluaran.setText("Pengeluaran : Rp. 0.-");
                }
            }
        }
    }

//    private void getGraphicsData() {
//        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(MenuActivity.this);
//        dataBaseAccess.open();
//
//        Cursor data = dataBaseAccess.SumGroup("jumlah", "money");
//
//        if(data.getCount() == 0){
//            Toast.makeText(MenuActivity.this, "No Graphics", Toast.LENGTH_SHORT).show();
//        } else {
//            jumlah = new ArrayList<>();
//            tanggal = new String[data.getCount()];
//            int i = 0;
//            while (data.moveToNext()){
//                jumlah.add(new Entry(data.getInt(0), i));
//                tanggal[i] = data.getString(1);
//                i++;
//            }
//            LineDataSet lineDataSet = new LineDataSet(jumlah, "Jumlah");
//            LineData lineData = new LineData(lineDataSet);
//            lineChart.setData(lineData);
//        }
//    }

    //keluar aplikasi
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true);
    }
}