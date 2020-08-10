package com.ishanbhattacharya.hotelmanagement;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class PreviousAdapter extends ArrayAdapter<String> {
    String[] titles;
    String[] dates;
    String[] prices;
    String[] bookingDates;
    Context context;

    public PreviousAdapter(Context context, String[] titlesList, String[] datesList, String[] pricesList, String[] bookingList) {
        super(context, R.layout.room_card);
        this.titles = titlesList;
        this.dates = datesList;
        this.prices = pricesList;
        this.bookingDates = bookingList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
// First let’s verify the convertView is not null
        if (convertView == null) {
// This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.booking_card, null);
// Now we can fill the layout with the right values
            TextView nameView = (TextView) convertView.findViewById(R.id.name);
            TextView descView = (TextView) convertView.findViewById(R.id.desc);
            final TextView costOfView = convertView.findViewById(R.id.costOf);
            TextView dateOfView = convertView.findViewById(R.id.dateOf);
            final Button openCloseBtn = convertView.findViewById(R.id.arrowBtn);
            final CardView cardView = convertView.findViewById(R.id.cardView);
            Button cancelButton = convertView.findViewById(R.id.cancelButton);
            final ConstraintLayout explandable = convertView.findViewById(R.id.expandableView);

            openCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (explandable.getVisibility()==View.GONE){
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        explandable.setVisibility(View.VISIBLE);
                        openCloseBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                        explandable.setVisibility(View.GONE);
                        openCloseBtn.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child(costOfView.getText().toString().split("\\:\\s")[1]);
                    ref.removeValue();
                    Toast.makeText(context, "Booking cancelled!", Toast.LENGTH_LONG).show();
                }
            });

            holder.titleH = nameView;
            holder.datesH = descView;
            holder.priceH = costOfView;
            holder.bookingDateH = dateOfView;

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.titleH.setText(titles[position]);
        holder.datesH.setText(dates[position]);
        holder.priceH.setText(prices[position]);
        holder.bookingDateH.setText(bookingDates[position]);
        String[] datesCheck = dates[position].split("\\s\\-\\s");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String todayDate = df.format(c);
        Log.d("LOGGER", dates[0]+" "+todayDate);
        if (datesCheck[0].equals(todayDate)) convertView.findViewById(R.id.cancelButton).setVisibility(View.GONE);

        return convertView;
    }

    static class ViewHolder{
        TextView titleH;
        TextView datesH;
        TextView priceH;
        TextView bookingDateH;
    }
}
