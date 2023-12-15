package com.example.smartcarparking;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class Signup_Activity extends AppCompatActivity {

    //variable declare
    private ImageButton backBtn;
    private TextView loginPage;
    private CardView signUp;
    private TextInputEditText inputName, inputPhone, inputEmail,
            inputAddress, inputCarNumber, inputRfidNumber, inputPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //find id
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputEmail = findViewById(R.id.inputEmail);
        inputAddress = findViewById(R.id.inputAddress);
        inputCarNumber = findViewById(R.id.inputCarNumber);
        inputRfidNumber = findViewById(R.id.inputRfidNumber);
        inputPassword = findViewById(R.id.inputPassword);
        backBtn = findViewById(R.id.backBtn);
        loginPage = findViewById(R.id.loginPage);
        signUp = findViewById(R.id.signUp);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //back to login page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                finish();
            }
        });

        //signup
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                String email = inputEmail.getText().toString().trim();
                String address = inputAddress.getText().toString();
                String carNumber = inputCarNumber.getText().toString();
                String rfidNumber = inputRfidNumber.getText().toString();
                String password = inputPassword.getText().toString();

                if (name.matches("") || phone.matches("") || email.matches("") || address.matches("") || carNumber.matches("") || rfidNumber.matches("") || password.matches("")){
                    Toast.makeText(Signup_Activity.this, "Enter all field details", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                                    finish();

                                    progressDialog.cancel();
                                    Toast.makeText(Signup_Activity.this, "Signup successful...", Toast.LENGTH_SHORT).show();


                                    firebaseFirestore.collection("User")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .set(new UserModel(name, phone, email, address, carNumber, rfidNumber, password));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signup_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }
                            });
                }

                /*inputName.setText("");
                inputPhone.setText("");
                inputEmail.setText("");
                inputAddress.setText("");
                inputCarNumber.setText("");
                inputRfidNumber.setText("");
                inputPassword.setText("");*/

            }
        });

        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                finish();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        //login page open
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                finish();
            }
        });
    }
}