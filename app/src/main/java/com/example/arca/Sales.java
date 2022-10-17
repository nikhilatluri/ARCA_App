package com.example.arca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;
import java.text.*;

import java.util.ArrayList;

public class Sales extends AppCompatActivity {
    Spinner spinner;
    Button btAdd;
    EditText etQty, etPrice;
    float pri;

    EditText name;
    Button insert, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        etQty = findViewById(R.id.et_qty);
        etPrice = findViewById(R.id.et_price);
        spinner = findViewById(R.id.et_spinner);
        TextView tv_date = findViewById(R.id.et_date);
        name = findViewById(R.id.et_name);

        insert = findViewById(R.id.bt_adds);
        delete = findViewById(R.id.bt_deletes);
        view = findViewById(R.id.bt_views);

        String date_n  = new SimpleDateFormat("HH:mm:ss a, dd/MM/yyyy", Locale.getDefault()).format(new Date() );
        tv_date.setText(date_n);


        String[] spinnerList = new String[]{"Apollo" , "Roopchand" , "Black Tilapia", "Red Tilapia" , "Korameenu"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if(position == 0){
                    pri = 270.00f;
                }
                if(position == 1){
                    pri = 300.00f;
                }
                if(position == 2){
                    pri = 290.00f;
                }
                if(position == 3){
                    pri = 280.00f;
                }
                if(position == 4){
                    pri = 450.00f;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detailsTXT = name.getText().toString();
                String dateTXT = tv_date.getText().toString();
                String nameTXT = spinner.getSelectedItem().toString();
                String contactTXT = etQty.getText().toString();
                String dobTXT = etPrice.getText().toString();


                Boolean checkinsertdata = DB.insertuserdata(nameTXT, contactTXT, dobTXT, detailsTXT, dateTXT);
                if(checkinsertdata==true)
                    Toast.makeText(Sales.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Sales.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                name.setText("");
                tv_date.setText(date_n);
                etQty.setText("");
                etPrice.setText("");
            }        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkudeletedata = DB.deletedata();
                if(checkudeletedata==true)
                    Toast.makeText(Sales.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Sales.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(Sales.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Name :"+res.getString(0)+"\n");
                    buffer.append("Quantity :"+res.getString(1)+"\n");
                    buffer.append("Price :"+res.getString(2)+"\n");
                    buffer.append("Customer : " +res.getString(3) + "\n");
                    buffer.append("Date and Time : " +res.getString(4) + "\n\n");


                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Sales.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });



    }
}