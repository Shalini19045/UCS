package com.iiitd.ucsf.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
 import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.iiitd.ucsf.R;
import com.iiitd.ucsf.application.ucsf;


public class RegisterActivity extends Activity {

    private Button registerSubmit;
     private EditText passText;
    private EditText conpassText;
    private EditText emailText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private final String TAG = "REGISTERTAG";
    CheckBox check,check2;
    SharedPreferences preferences;
    public static String registered_mail;
    Button agree;
    Boolean consent=false;
    private ProgressDialog progressDialog;

    private ucsf app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailText = (EditText) findViewById(R.id.editText_name_register);
        passText = (EditText) findViewById(R.id.editText_password_register);
        conpassText = (EditText) findViewById(R.id.editText_confirmPassword_register);
        registerSubmit = (Button) findViewById(R.id.button_register_submit);
        app = ucsf.getInstance();
        db = app.getFirebaseDatabaseInstance();

        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        progressDialog= new ProgressDialog(this);
    }

    private void register() {

        final String email = emailText.getText().toString().trim();
        final String pass = passText.getText().toString().trim();
        String confirmPass = conpassText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(RegisterActivity.this, "Field empty", Toast.LENGTH_LONG).show();
        } else if (!pass.equals(confirmPass)) {
            Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_LONG).show();
        } else {

            preferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);

                app = ucsf.getInstance();
                db = app.getFirebaseDatabaseInstance();
                preferences = PreferenceManager.getDefaultSharedPreferences(this);


                preferences = PreferenceManager.getDefaultSharedPreferences(this);


                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                         /*   db.collection("consent_data/" + RegisterActivity.registered_mail + "/patient").document("info").
                                    set(values).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Successfully Saved your consent", Toast.LENGTH_SHORT).show();
                                }
                            });*/




                          Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(i);
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                            finish();


                        } else {
                             Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();


                    }
                });


            }

        }








}

