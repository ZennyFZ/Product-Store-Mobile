package com.prm392.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {

    EditText email;
    Button forgetPasswordBtn;
    TextView successMessage;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_input);
        forgetPasswordBtn = findViewById(R.id.forget_password_button);
        successMessage = findViewById(R.id.forget_password_success);
    }

    public void forgetPassword(View view){
        String email = this.email.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please fill your email", Toast.LENGTH_SHORT).show();
        }
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onClearFieldName();
                Toast.makeText(ForgetPassword.this, "Please check your email", Toast.LENGTH_SHORT).show();
                showSuccessMessage();
            } else {
                Toast.makeText(ForgetPassword.this, "Email not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showSuccessMessage() {
        email.setVisibility(View.INVISIBLE);
        forgetPasswordBtn.setVisibility(View.INVISIBLE);
        successMessage.setVisibility(View.VISIBLE);
    }

    public void goToLogin(View view) {
        Intent loginIntent = new Intent(ForgetPassword.this, Login.class);
        startActivity(loginIntent);
    }

    public void onClearFieldName(){
        email.setText("");
    }
}