package com.iiitd.ucsf.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iiitd.ucsf.R;
import com.iiitd.ucsf.application.ucsf;
import com.iiitd.ucsf.pojo.User;


import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;


public class LoginActivity extends Activity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 9220;
    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final String PREF_PID = "user_id";
    private String userId;
    private ucsf app;
    private FirebaseAuth mAuth;
    private EditText userIdEt, passworEt;
    private ProgressDialog progressDialog;
    private AnimationDrawable animationDrawable;
    private ConstraintLayout constraintLayout;
    private SharedPreferences preferences;
    Button signInButton;
    private Button registerButton;
    public static final int RequestPermissionCode = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        preferences = this.getSharedPreferences(PREF_PID, MODE_PRIVATE);
        userId = preferences.getString(PREF_PID, null);

        app = ucsf.getInstance() ;

         app.setUserID(userId);


        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In...");



        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        userIdEt = findViewById(R.id.user_id_et);

        passworEt = findViewById(R.id.password_et);

        if (userId != null) {
            signIn();
            return;
        }


    }

    private void signIn() {
//        Log.d(TAG, "Sign In Called");
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//
//        startActivityForResult(signInIntent, RC_SIGN_IN);

        updateUI(null);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        animationDrawable.start();
    }

    private void updateUI(FirebaseUser currentUser) {
        progressDialog.dismiss();

        if (
                 userId != null) {
            User p = new User(userId, userId, userId );
            app.getAppUser(p);




            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }



    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_button) {

            final String password = passworEt.getText().toString().trim();
            userId = userIdEt
                    .getText().toString().toUpperCase().trim();
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(userId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                     Intent i=new Intent(LoginActivity.this,MainActivity.class);
                     startActivity(i);

                    }
                }
            });

        }
    }





}