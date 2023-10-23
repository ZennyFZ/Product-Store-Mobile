package com.prm392.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.prm392.assignment.model.UserModel;

public class Registration extends AppCompatActivity {
    EditText email, password, confirm_password, full_name, phone_number;
    FirebaseAuth auth;
    FirebaseUser authUser;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email_input);
        password = findViewById(R.id.password_input);
        confirm_password = findViewById(R.id.confirm_password_input);
        full_name = findViewById(R.id.full_name_input);
        phone_number = findViewById(R.id.phone_number_input);
    }

    public void userRegister(View view) {
        String emailData = email.getText().toString();
        String passwordData = password.getText().toString();
        String confirmPasswordData = confirm_password.getText().toString();
        String fullNameData = full_name.getText().toString();
        String phoneNumberData = phone_number.getText().toString();
        if (!emailData.isEmpty() || !passwordData.isEmpty() || !confirmPasswordData.isEmpty() || !fullNameData.isEmpty() || !phoneNumberData.isEmpty()) {
            if (passwordData.equals(confirmPasswordData)) {
                auth.createUserWithEmailAndPassword(emailData, passwordData).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserModel user = new UserModel(emailData, fullNameData, phoneNumberData, passwordData);
                        String id = task.getResult().getUser().getUid();
                        setDisplayName(fullNameData);
                        database.getReference().child("User").child(id).setValue(user);
                        sendVerificationMail();
                    } else {
                        Toast.makeText(Registration.this, "Register failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(Registration.this, "Password and confirm password must be the same", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Registration.this, "Fields must not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void setDisplayName(String fullNameData) {
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        authUser.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(fullNameData).build());
    }

    public void sendVerificationMail() {
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        authUser.sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                onClearFieldName();
                Toast.makeText(Registration.this, "Register successful. Verification email sent", Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(Registration.this, Login.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(Registration.this, "Register failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToLogin(View view) {
        Intent loginIntent = new Intent(Registration.this, Login.class);
        startActivity(loginIntent);
    }

    public void onClearFieldName() {
        email.setText("");
        password.setText("");
        confirm_password.setText("");
        full_name.setText("");
        phone_number.setText("");
    }

}