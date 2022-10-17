package com.example.arca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Activity {
    private Button button;
    Button userlogout;
    TextView userEmail;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userEmail = findViewById(R.id.usermail_e);
        userlogout = findViewById(R.id.bt_signout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String user = firebaseUser.getEmail();
        int index =user.indexOf('@');
        user = user.substring(0,index);
        userEmail.setText("Welcome :" + user);


        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Home.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        button = (Button)findViewById(R.id.bt_sale);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSale();
            }
        });
        button = (Button)findViewById(R.id.bt_expences);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                openExp();
            }
        });
        button = (Button)findViewById(R.id.bt_feed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                openFeed();
            }
        });


    }
    public void openFeed() {
        Intent intent = new Intent(this, Feed.class);
        startActivity(intent);
    }
    public void openExp() {
        Intent intent = new Intent(this, Expences.class);
        startActivity(intent);
    }
    public void openSale() {
        Intent intent = new Intent(this, Sales.class);
        startActivity(intent);
    }
}