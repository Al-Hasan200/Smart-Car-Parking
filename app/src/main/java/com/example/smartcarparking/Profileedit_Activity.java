package com.example.smartcarparking;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Profileedit_Activity extends AppCompatActivity {

    //variable declare
    private CardView update;
    private ImageButton backBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    private TextInputEditText inputName, inputPhone, inputAddress, inputCarNumber, inputRfidNumber;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileedit);

        //find id
        update = findViewById(R.id.update);
        backBtn = findViewById(R.id.backBtn);
        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        inputCarNumber = findViewById(R.id.inputCarNumber);
        inputRfidNumber = findViewById(R.id.inputRfidNumber);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseFirestore = FirebaseFirestore.getInstance();

        //user data show
        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("User").document(userId);
        progressDialog.setTitle("Please wait information loading...");
        progressDialog.show();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                inputName.setText(value.getString("name"));
                inputPhone.setText(value.getString("phone"));
                inputAddress.setText(value.getString("address"));
                inputCarNumber.setText(value.getString("carNumber"));
                inputRfidNumber.setText(value.getString("rfidNumber"));
                progressDialog.cancel();
            }
        });

        //back homepage
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profileedit_Activity.this, Home_Activity.class));
                finish();

            }
        });

        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Profileedit_Activity.this, Home_Activity.class));
                finish();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);

        //update profile info
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                String address = inputAddress.getText().toString();
                String carNumber = inputCarNumber.getText().toString();
                String rfidNumber = inputRfidNumber.getText().toString();

                if (name.matches("") || phone.matches("") || address.matches("") || carNumber.matches("") || rfidNumber.matches("")){
                    Toast.makeText(Profileedit_Activity.this, "Enter all field details", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Please wait...");
                    progressDialog.show();

                    DocumentReference documentReference = firebaseFirestore.collection("User").document(userId);
                    Map<String, Object> edit = new HashMap<>();
                    edit.put("name", name);
                    edit.put("phone", phone);
                    edit.put("address", address);
                    edit.put("carNumber", carNumber);
                    edit.put("rfidNumber", rfidNumber);
                    documentReference.update(edit).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(Profileedit_Activity.this, Home_Activity.class));
                            finish();

                            progressDialog.cancel();
                            Toast.makeText(Profileedit_Activity.this, "Update Successful...", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Profileedit_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
                }
            }
        });
    }
}