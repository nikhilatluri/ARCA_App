package com.example.arca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Expences extends AppCompatActivity {

    EditText  exp, d_exp, d_exp2,qty, price, trans;
    EditText date_ex;
    Button insert, delete, view;
    DBHelpere DBe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expences);

        String datee  = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date() );
        date_ex = findViewById(R.id.et_dateex);
        date_ex.setText(datee);

        exp = findViewById(R.id.et_expensese);
        d_exp = findViewById(R.id.et_descriptione);
        d_exp2 = findViewById(R.id.et_sizee);
        qty = findViewById(R.id.et_qtye);
        price = findViewById(R.id.et_pricee);
        trans = findViewById(R.id.et_transe);

        insert = findViewById(R.id.bt_addex);
        delete = findViewById(R.id.bt_deleteex);
        view = findViewById(R.id.bt_viewsex);

        DBe = new DBHelpere(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateTXT = date_ex.getText().toString();
                String expTXT = exp.getText().toString();
                String d_expTXT = d_exp.getText().toString();
                String d_exp2TXT = d_exp2.getText().toString();
                String qtyTXT = qty.getText().toString();
                String priceTXT = price.getText().toString();
                String transTXT = trans.getText().toString();


                Boolean checkinsertdata = DBe.insertuserdata(dateTXT, expTXT, d_expTXT, d_exp2TXT, qtyTXT, priceTXT, transTXT);
                if(checkinsertdata==true)
                    Toast.makeText(Expences.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Expences.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                date_ex.setText(datee);
                exp.setText("");
                d_exp.setText("");
                d_exp2.setText("");
                qty.setText("");
                price.setText("");
                trans.setText("");
            }        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expTXT = exp.getText().toString();
                Boolean checkudeletedata = DBe.deletedata(expTXT);
                if(checkudeletedata==true)
                    Toast.makeText(Expences.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Expences.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DBe.getdata();
                if(res.getCount()==0){
                    Toast.makeText(Expences.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer1 = new StringBuffer();
                while(res.moveToNext()){

                    buffer1.append("Date :"+res.getString(0)+"\n");
                    buffer1.append("Expense :"+res.getString(1)+"\n");
                    buffer1.append("Description :"+res.getString(2)+"\n");
                    buffer1.append("Size : " +res.getString(3) + "\n");
                    buffer1.append("Quantity : " +res.getString(4) + "\n");
                    buffer1.append("Price : " +res.getString(5) + "\n");
                    buffer1.append("Transportation : " +res.getString(6) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Expences.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer1.toString());
                builder.show();
            }        });





    }
}