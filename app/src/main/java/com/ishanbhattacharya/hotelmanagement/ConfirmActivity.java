package com.ishanbhattacharya.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Locale;

public class ConfirmActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    int numberOfBookings=0;

    String city;
    String inDate;
    String outDate;
    int numberOfGuests;
    String plan;
    String price;
    int numberOfDays;
    String name;
    String bookingDate;

    TextView guestView;
    TextView nameView;
    TextView bookingView;
    TextView inView;
    TextView outView;
    TextView daysView;
    TextView locationView;
    TextView planView;
    TextView priceView;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        name = data.getString("Name");
        city = data.getString("City");
        inDate = data.getString("InDate");
        outDate = data.getString("OutDate");
        numberOfDays = data.getInt("Days");
        numberOfGuests = data.getInt("Guests");
        plan = data.getString("Package");
        price = data.getString("Price");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        bookingDate = df.format(c);

        nameView = findViewById(R.id.nameText);
        bookingView = findViewById(R.id.bookingText);
        inView = findViewById(R.id.inText);
        outView = findViewById(R.id.outText);
        daysView = findViewById(R.id.daysText);
        locationView = findViewById(R.id.locationText);
        planView = findViewById(R.id.planText);
        priceView = findViewById(R.id.priceText);
        guestView = findViewById(R.id.guestsText2);

        nameView.setText("Name: "+name);
        bookingView.setText("Booking Date: "+bookingDate);
        inView.setText("Check-in Date: "+inDate);
        outView.setText("Check-out Date: "+outDate);
        daysView.setText("Days of stay: "+numberOfDays);
        guestView.setText("Number of guests: "+numberOfGuests);
        locationView.setText("Location: "+city);
        planView.setText("Package: "+plan);
        priceView.setText("Price: "+price);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference().child("Bookings");
    }

    public void Confirm(View view){
        mDatabase = firebaseDatabase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        String pushKey = mDatabase.push().getKey();
        mDatabase.child(pushKey).setValue(new Booking(name, bookingDate, inDate, outDate, numberOfDays, numberOfGuests, city, plan, price));
        Intent intent = new Intent(ConfirmActivity.this, WelcomeActivity.class);
        Toast.makeText(ConfirmActivity.this, "Booking confirmed!", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

}