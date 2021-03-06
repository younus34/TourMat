package com.example.tour;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tour.Data.MainData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private TextView singUp, forgotPassword;
    private EditText emailET, passwordET;
    private String email, password;
    private Button singInBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singUp = findViewById(R.id.singUpTV);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        forgotPassword = findViewById(R.id.forgotPasswordTV);
        singInBtn = findViewById(R.id.singInBtn);

        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                //finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });

        singInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString().trim();
                login(new MainData(email, password));
            }
        });
    }

    private void login(MainData mainData) {

        if (mainData.getEmail().isEmpty() || !mainData.getEmail().contains("@") || !mainData.getEmail().endsWith(".com")) {
            Toast.makeText(MainActivity.this, "Please enter a valied email addrss.", Toast.LENGTH_SHORT).show();
        } else if (mainData.getPassword().isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
        } else if (mainData.getPassword().length() < 6) {
            Toast.makeText(MainActivity.this, "Please enter 6 digit password.", Toast.LENGTH_SHORT).show();
        } else {
            //sing in code
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(mainData.getEmail(),mainData.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(MainActivity.this, AddTourActivity.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Please SingUp.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Toast.makeText(this, "You enter: "+mainData.getEmail()+"/"+mainData.getPassword(), Toast.LENGTH_SHORT).show();
        }
    }


}
