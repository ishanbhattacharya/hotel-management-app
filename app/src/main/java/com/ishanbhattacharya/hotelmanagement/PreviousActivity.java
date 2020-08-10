package com.ishanbhattacharya.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PreviousActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    ProgressBar progressBar;
    ListView previous;
    Booking[] userBookings;
    String[] titleList;
    String[] dateList;
    String[] priceList;
    String[] bookingDateList;
    int i;

    private static final String LOGGER = "LOGGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        //mDatabase = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());
        mDatabase = firebaseDatabase.getReference("Users").child(mAuth.getCurrentUser().getUid());
        progressBar = findViewById(R.id.progressBarP);

        Log.d("LOGGER", "Outside!");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /*for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Booking booking = snapshot.getValue(Booking.class);
                    Log.d("LOGGER", booking.nameC);
                    ings.get(i).priceC;
                    bookingDateList[i] = usetitleList[i] = userBookings.get(i).planC + " | " + userBookings.get(i).cityC;
                    dateList[i] = userBookings.get(i).inDateC + " - " + userBookings.get(i).outDateC;
                    priceList[i] = userBookrBookings.get(i).bookingDateC;
                    i++;

                }*/
                /*for (int i = 0; i < 10; i++) {
                    titleList[i] = userBookings.get(i).planC + " | " + userBookings.get(i).cityC;
                    dateList[i] = userBookings.get(i).inDateC + " - " + userBookings.get(i).outDateC;
                    priceList[i] = userBookings.get(i).priceC;
                    bookingDateList[i] = userBookings.get(i).bookingDateC;
                }*/
                i=0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    i++;
                }
                titleList = new String[i];
                dateList = new String[i];
                priceList = new String[i];
                bookingDateList = new String[i];
                i=0;
                for (DataSnapshot snapShot : dataSnapshot.getChildren()){
                    Booking booking = snapShot.getValue(Booking.class);
                    Log.d("LOGGER", booking.planC + booking.cityC);
                    titleList[i] = booking.planC + " | " + booking.cityC;
                    dateList[i] = booking.inDateC + " - " + booking.outDateC;
                    priceList[i] = "Booking ID: " + snapShot.getKey();
                    bookingDateList[i] = "Booked on " +booking.bookingDateC;
                    i++;
                }
                progressBar.setVisibility(View.GONE);
                previous = findViewById(R.id.previousList);
                PreviousAdapter myAdapter = new PreviousAdapter(PreviousActivity.this, titleList, dateList, priceList, bookingDateList);
                previous.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Log.d("LOGGER", "Outside again!");
    }
}