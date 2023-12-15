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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class Booking_Activity extends AppCompatActivity {

    //variable declare
    private ImageButton backBtn;
    private CardView slot_1, slot_2, slot_3, slot_4;
    private TextView aORnSlot1, aORnSlot2, aORnSlot3, aORnSlot4;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //find id
        backBtn = findViewById(R.id.backBtn);
        slot_1 = findViewById(R.id.slot_1);
        slot_2 = findViewById(R.id.slot_2);
        slot_3 = findViewById(R.id.slot_3);
        slot_4 = findViewById(R.id.slot_4);

        //back to login page
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Activity.this, Home_Activity.class));
                finish();
            }
        });

        // Set up the OnBackPressedCallback
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(Booking_Activity.this, Home_Activity.class));
                finish();
            }
        };
        // Add the callback to the OnBackPressedDispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);


        //slot booking page open
        slot_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Activity.this, Slot_Booking_Activity.class));
                finish();

            }
        });

        slot_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Activity.this, Slot_2_Activity.class));
                finish();
            }
        });

        slot_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Activity.this, Slot_3_Activity.class));
                finish();
            }
        });

        slot_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Booking_Activity.this, Slot_4_Activity.class));
                finish();
            }
        });
    }
}