package com.example.arca;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Feed extends AppCompatActivity {

    EditText datef, tank, qtyf, timef;
    Button insertf, deletef, viewf;
    DBHelperf DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        DateFormat tf = new SimpleDateFormat("KK:mm a",Locale.getDefault());
        String currentDateAndTime = df.format(new Date());
        String currentDateAndTime1 = tf.format(new Date());

        datef = findViewById(R.id.et_datef);
        timef = findViewById(R.id.et_timef);

        datef.setText(currentDateAndTime);
        timef.setText(currentDateAndTime1);

        tank = findViewById(R.id.et_Tank);
        qtyf = findViewById(R.id.et_qtyf);
        insertf = findViewById(R.id.bt_addf);
        deletef = findViewById(R.id.bt_deletef);
        viewf = findViewById(R.id.bt_viewf);

        DB = new DBHelperf(this);

        insertf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTXT = datef.getText().toString();
                String timeTXT = timef.getText().toString();
                String tankTXT = tank.getText().toString();
                String qtyTXT = qtyf.getText().toString();


                Boolean checkinsertdata = DB.insertuserdata(dateTXT, timeTXT, tankTXT, qtyTXT);
                if(checkinsertdata==true)
                    Toast.makeText(Feed.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Feed.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                datef.setText(currentDateAndTime);
                timef.setText(currentDateAndTime1);
                tank.setText("");
                qtyf.setText("");
            }        });

        deletef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTXT = datef.getText().toString();
                Boolean checkudeletedata = DB.deletedata(dateTXT);
                if(checkudeletedata==true)
                    Toast.makeText(Feed.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Feed.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        viewf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(Feed.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Date :"+res.getString(0)+"\n");
                    buffer.append("Time :"+res.getString(1)+"\n");
                    buffer.append("Tank :"+res.getString(2)+"\n");
                    buffer.append("Qty : " +res.getString(3) + "\n\n");


                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Feed.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });









    }
}