package com.example.arca;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity {
    EditText mFirstName, mLastName, mPhone, mPassword, mPassword1, mMail;
    Button mLoginBtn;
    CheckBox rem;
    TextView mCreateBtn, mreset;
    FirebaseAuth fAuth;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;
    Toolbar toolbar;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mMail = findViewById(R.id.uc_email);
        mPassword = findViewById(R.id.uc_pwd1);
        progressBar = findViewById(R.id.progressbarL);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.bt_submit);
        mCreateBtn = findViewById(R.id.bt_newuser);
        mreset = findViewById(R.id.bt_forgetpwd);
        rem = findViewById(R.id.uc_remember);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        preferences.getString("rem", "");
        String checkbox = preferences.getString("rem", "");

        if(checkbox.equals("true") ){
            Intent intent = new Intent(LoginActivity.this,  Home.class);
            startActivity(intent);
            Toast.makeText(this,"Login Successful..", Toast.LENGTH_SHORT).show();
        }
        else if(checkbox.equals("false")) {
            Toast.makeText(this, "Please Sign In", Toast.LENGTH_SHORT).show();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mMail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mMail.setError("Username cannot be empty, try your E-mail as username");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Please enter your password");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password strength error: Password contains more than 6 characters");
                }

                progressBar.setVisibility(View.VISIBLE);


                // Firebase Authentication Code

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Welcome, Sign in successful...", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "We can’t sign you in at the moment. Please enter your email followed by password correctly", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserCreation.class));
            }
        });

        mreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetpwd = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialogue = new AlertDialog.Builder(v.getContext());
                passwordResetDialogue.setTitle("Reset password ?");
                passwordResetDialogue.setMessage("Please enter your E-mail address");
                passwordResetDialogue.setView(resetpwd);

                passwordResetDialogue.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetpwd.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Reset Failed..! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialogue.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialogue.create().show();

            }
        });



    }

}
