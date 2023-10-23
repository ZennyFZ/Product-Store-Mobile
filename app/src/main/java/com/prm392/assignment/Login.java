package com.prm392.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    EditText email, password;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
    }

    public void userLogin(View view) {
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        if(!emailData.isEmpty() || !passwordData.isEmpty()) {
            auth.signInWithEmailAndPassword(emailData, passwordData).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        onClearFieldName();
                        Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(Login.this, ProductList.class);
                        startActivity(loginIntent);
                    }else{
                        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onClearFieldName(){
        email.setText("");
        password.setText("");
    }

    public void goToRegistration(View view) {
        Intent registerIntent = new Intent(Login.this, Registration.class);
        startActivity(registerIntent);
    }

    public void goToForgetPassword(View view) {
        Intent forgetPasswordIntent = new Intent(Login.this, ForgetPassword.class);
        startActivity(forgetPasswordIntent);
    }

    public void onPause() {
        super.onPause();
        onClearFieldName();
    }

    public void onResume() {
        super.onResume();
        onClearFieldName();
    }
}