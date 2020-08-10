package com.ishanbhattacharya.hotelmanagement;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;

public class RoomActivity extends AppCompatActivity {

    String city;
    String inDate;
    String outDate;
    int numberOfGuests;
    int numberOfDays;
    String name;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        name = data.getString("Name");
        city = data.getString("City");
        inDate = data.getString("InDate");
        outDate = data.getString("OutDate");
        numberOfGuests = data.getInt("Guests");
        numberOfDays=1;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy");

        try {
            Date date1 = simpleDateFormat.parse(inDate);
            Date date2 = simpleDateFormat.parse(outDate);
            numberOfDays = (int) ((date2.getTime() - date1.getTime())/86400000);

        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }

        TextView cityT = findViewById(R.id.cityText);
        cityT.setText("Location: "+city);
        TextView daysT = findViewById(R.id.daysText);
        daysT.setText("Number of days: "+numberOfDays);
        TextView guestsT = findViewById(R.id.guestsText2);
        guestsT.setText("Number of guests: "+numberOfGuests);

        int[] guestCounter = {numberOfGuests, (int) Math.ceil((double) numberOfGuests/2), (int) Math.ceil((double) numberOfGuests/3), (int) Math.ceil((double) numberOfGuests/6)};

        final String[] roomList = new String[4];
        roomList[0] = "Single Deluxe x"+guestCounter[0];
        roomList[1] = "Double Deluxe x"+guestCounter[1];
        roomList[2] = "Triple Deluxe x"+guestCounter[2];
        roomList[3] = "Presidential Suite x"+guestCounter[3];

        final String[] priceList = new String[4];
        priceList[0] = String.valueOf((1000*guestCounter[0]*numberOfDays));
        priceList[1] = String.valueOf((1800*guestCounter[1]*numberOfDays));
        priceList[2] = String.valueOf((2350*guestCounter[2]*numberOfDays));
        priceList[3] = String.valueOf((4500*guestCounter[3]*numberOfDays));

        int[] imageList = {R.drawable.singledeluxe, R.drawable.doubledeluxe, R.drawable.tripledeluxe, R.drawable.presidentialsuite};

        RoomAdapter myAdapter = new RoomAdapter(this, roomList, priceList, imageList);
        ListView myList = findViewById(R.id.roomList);
        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomActivity.this, ConfirmActivity.class);
                intent.putExtra("Name", name);
                intent.putExtra("City", city);
                intent.putExtra("InDate", inDate);
                intent.putExtra("OutDate", outDate);
                intent.putExtra("Days", numberOfDays);
                intent.putExtra("Guests", numberOfGuests);
                intent.putExtra("Package", roomList[position]);
                intent.putExtra("Price", priceList[position]);
                startActivity(intent);
            }
        });
    }
}