package com.example.smartcarparking;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword_Activity extends AppCompatActivity {

    //variable declare
    private CardView forgetPass;
    private ImageButton backBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private TextInputEditText forgetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        //find id
        backBtn = findViewById(R.id.backBtn);
        forgetEmail = findViewById(R.id.forgetEmail);
        forgetPass = findViewById(R.id.forgetPass);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        //back to login page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forgetpassword_Activity.this, Login_Activity.class));
                finish();
            }
        });

        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Forgetpassword_Activity.this, Login_Activity.class));
                finish();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        //forget password
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailm = forgetEmail.getText().toString();

                if (emailm.matches("")){
                    Toast.makeText(Forgetpassword_Activity.this, "Enter all field details", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Sending email...");
                    progressDialog.show();

                    firebaseAuth.sendPasswordResetEmail(emailm)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(Forgetpassword_Activity.this, "Email sent...", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(Forgetpassword_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}