package com.example.arca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserCreation extends AppCompatActivity {
    EditText mFirstName, mLastName, mPhone, mPassword, mPassword1, mMail;
    Button mRegisterButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView BackLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);

        mFirstName = findViewById(R.id.uc_firstName);
        mLastName = findViewById(R.id.uc_lastName);
        mPhone = findViewById(R.id.uc_phoneNumber);
        mPassword = findViewById(R.id.uc_pwd1);
        mPassword1 = findViewById(R.id.uc_pwd2);
        mRegisterButton = findViewById(R.id.uc_submit);
        BackLogin = findViewById(R.id.backLogin);
        mMail = findViewById(R.id.uc_email);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.uc_progressbar);

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mMail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String pass = mPassword1.getText().toString().trim();


                if (TextUtils.isEmpty(email) || email.length() < 10) {
                    mMail.setError("Invalid Email address");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password field cannot be empty");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password strength error: Please enter more than 6 characters");
                }
                if (!(TextUtils.equals(pass, password))) {
                    mPassword1.setError("Both Passwords mismatch");
                    mPassword.setError("Both Passwords mismatch");

                }
                progressBar.setVisibility(View.VISIBLE);

                //Firebase-Code

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserCreation.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        } else {
                            Toast.makeText(UserCreation.this, "Error..!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }

        });


        BackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}