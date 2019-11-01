package com.shashank.attendancemanager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rey.material.widget.EditText;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT="com.shashank.attendancemanager.EXTRA_TEXT";

    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private EditText Email;
    private EditText Password;
    private Button Login;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Email=(EditText)findViewById(R.id.etEmail);
        Password=(EditText)findViewById(R.id.etPassword);
        Login=findViewById(R.id.btnLogin);
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            finish();
            startActivity(new Intent(MainActivity.this,Dashboard.class));
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Email.getText().toString())|| TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(MainActivity.this,"You Should Enter All Details",Toast.LENGTH_SHORT).show();
                }
                else{
                    validate(Email.getText().toString(),Password.getText().toString());
                }
            }
        });

        userRegistration=(TextView)findViewById(R.id.tvSignUp);
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });

    }
    private void validate(String Email, String Password){
        final String email=Email;
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Successful!",Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(MainActivity.this,Dashboard.class);
                   // intent.putExtra(EXTRA_TEXT,email);
                    startActivity(intent);

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Login Failed! Please check if Email and Password entered correctly",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
