package com.prm392.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    TextView nameText, emailText, phoneText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setProfile();
    }

    public void backToHome(View view) {
        Intent intent = new Intent(this, ProductList.class);
        startActivity(intent);
    }

    public void setProfile() {
        nameText = findViewById(R.id.profile_fullname);
        emailText = findViewById(R.id.profile_email);
        phoneText = findViewById(R.id.profile_phoneNumber);
        String userID = auth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(userID).child("fullName").getValue().toString();
                String email = snapshot.child(userID).child("email").getValue().toString();
                String phone = snapshot.child(userID).child("phoneNumber").getValue().toString();

                nameText.setText(name);
                emailText.setText(email);
                phoneText.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}