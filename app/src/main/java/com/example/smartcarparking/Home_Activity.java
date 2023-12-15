package com.example.smartcarparking;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.rpc.context.AttributeContext;

public class Home_Activity extends AppCompatActivity {

    //variable declare
    private TextView name, phone, email, address, carNumber, rfidNumber;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;
    String userId;
    private CardView logOut, editProfile, slotBooked;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //find id
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        carNumber = findViewById(R.id.carNumber);
        rfidNumber = findViewById(R.id.rfidNumber);
        logOut = findViewById(R.id.logOut);
        slotBooked = findViewById(R.id.slotBooked);
        editProfile = findViewById(R.id.editProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        firebaseFirestore = FirebaseFirestore.getInstance();

        sessionManager = new SessionManager(getApplicationContext());

        //user data show
        userId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("User").document(firebaseAuth.getCurrentUser().getUid());
        progressDialog.setTitle("Please wait information loading...");
        progressDialog.show();
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                name.setText("Name : "+value.getString("name"));
                phone.setText("Phone : "+value.getString("phone"));
                email.setText("Email : "+value.getString("email"));
                address.setText("Address : "+value.getString("address"));
                carNumber.setText("Car Number : "+value.getString("carNumber"));
                rfidNumber.setText("RFID Number : "+value.getString("rfidNumber"));
                progressDialog.cancel();
            }
        });

        //logout
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                        if (userId != null){
                            SharedPreferences preferences =getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                            //firebaseAuth.signOut();
                            finish();
                            Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
                            startActivity(intent);

                        }else {

                            SharedPreferences preferences =getSharedPreferences("AndroidLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.clear();
                            editor.apply();
                            //firebaseAuth.signOut();
                            finish();
                            Intent intent = new Intent(Home_Activity.this, Login_Activity.class);
                            startActivity(intent);
                        }


            }
        });

        //edit profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Profileedit_Activity.class));
                finish();

            }
        });

        //slot booking
        slotBooked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home_Activity.this, Booking_Activity.class));
                finish();
            }
        });

        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Dialog dialog = new Dialog(Home_Activity.this);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations
                        = android.R.style.Animation_Dialog;
                dialog.setContentView(R.layout.exit_dialog);
                dialog.setCancelable(false);

        ImageButton no_btn = dialog.findViewById(R.id.no_btn);
        ImageButton yes_btn = dialog.findViewById(R.id.yes_btn);

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        dialog.show();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }
}