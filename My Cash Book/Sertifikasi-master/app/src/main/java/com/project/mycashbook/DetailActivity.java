package com.project.mycashbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mycashbook.DataBase.DataBaseAccess;

public class DetailActivity extends AppCompatActivity {

    Button kembali;
    ListView listView;

    Integer[] ID, Nominal;
    String[] Keterangan, Tanggal, Flow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        kembali = findViewById(R.id.kembali);
        listView = findViewById(R.id.listView);

        getData();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, MenuActivity.class));
            }
        });
    }

    private void getData() {
        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(DetailActivity.this);
        dataBaseAccess.open();

        Cursor data = dataBaseAccess.Get("money");

        if(data.getCount() == 0){
            Toast.makeText(DetailActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            ID = new Integer[data.getCount()];
            Nominal = new Integer[data.getCount()];
            Keterangan = new String[data.getCount()];
            Tanggal = new String[data.getCount()];
            Flow = new String[data.getCount()];
            int i = 0;
            while (data.moveToNext()){
                ID[i] = data.getInt(0);
                Nominal[i] = data.getInt(1);
                Keterangan[i] = data.getString(2);
                Tanggal[i] = data.getString(3);
                Flow[i] = data.getString(4);
                i++;
            }
            listView.setAdapter(new AdapterDetail(DetailActivity.this, Nominal, Keterangan, Tanggal, Flow));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailActivity.this, MenuActivity.class));
    }
}