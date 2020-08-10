package com.ishanbhattacharya.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.ParseException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Locale;

public class WelcomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean exit = false;
    private TextView nameText;
    private TextView navName;
    private DrawerLayout navDrawer;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText checkInText;
    private EditText checkOutText;
    private EditText nGuests;
    private Spinner spinner;
    private SimpleDateFormat df;
    private String name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        getWindow().setSharedElementEnterTransition(enterTransition());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navDrawer = findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        name = mAuth.getCurrentUser().getDisplayName();
        String[] parts = name.split("\\s+");
        nameText = findViewById(R.id.nameField);
        nameText.setText(parts[0]);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navName = (TextView) headerView.findViewById(R.id.navNameText);
        navName.setText(name);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id==R.id.previousBookings) {
                    Intent i = new Intent(WelcomeActivity.this, PreviousActivity.class);
                    startActivity(i);
                }
                if (id==R.id.settings){
                    Toast.makeText(WelcomeActivity.this, "Will be added if required.", Toast.LENGTH_LONG).show();
                }
                if (id==R.id.terms){
                    Toast.makeText(WelcomeActivity.this, "Will be added if required.", Toast.LENGTH_LONG).show();
                }
                if(id==R.id.contact){
                    Toast.makeText(WelcomeActivity.this, "thenamesishan@gmail.com", Toast.LENGTH_LONG).show();
                }
                navDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);
        LinearLayout wt = findViewById(R.id.welcomeLayout);
        wt.startAnimation(in);

        if (mAuth.getCurrentUser().getProviders().get(0).equals("google.com")) {
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            // Build a GoogleSignInClient with the options specified by gso.
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        }

        checkInText = findViewById(R.id.editTextDate);
        checkInText.setInputType(InputType.TYPE_NULL);

        Date c = Calendar.getInstance().getTime();
        df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        checkInText.setText(df.format(c));

        checkInText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(WelcomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                checkInText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });

        checkOutText = findViewById(R.id.editTextDate2);
        Calendar cldr = Calendar.getInstance();
        cldr.add(Calendar.DATE, 3);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        checkOutText.setText(df.format(cldr.getTime()));
        checkOutText.setInputType(InputType.TYPE_NULL);
        checkOutText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                DatePickerDialog picker = new DatePickerDialog(WelcomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                checkOutText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                try {
                    Date date = df.parse(checkInText.getEditableText().toString());
                    picker.getDatePicker().setMinDate(date.getTime()+86400000);
                } catch (ParseException | java.text.ParseException e) {
                    e.printStackTrace();
                }
                picker.show();
            }
        });

        nGuests = findViewById(R.id.editTextNumber);
        spinner = findViewById(R.id.spinner);
    }

    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(800);

        return bounds;
    }

    public void SignOut(View view){
        if (mAuth.getCurrentUser().getProviders().get(0).equals("google.com")){
            mGoogleSignInClient.signOut();
        }
        mAuth.signOut();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        if (navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (exit) {
                System.gc();
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press again to exit",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3000);

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void BookNow(View view){
        String inDate = checkInText.getEditableText().toString();
        String outDate = checkOutText.getEditableText().toString();
        Date date1 = new Date();
        Date date2 = new Date();
        try {
            date1 = df.parse(inDate);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        try {
            date2 = df.parse(outDate);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        String city = String.valueOf(spinner.getSelectedItem());
        String noCus = nGuests.getEditableText().toString();
        if (noCus.equals("")) nGuests.setError("This cannot be blank");
        else if (date2.getTime() - date1.getTime() <=0){
            checkOutText.setError("Please select appropriately");
        }
        else{
            int numberOfGuests = Integer.parseInt(noCus);
            if (numberOfGuests>20) nGuests.setError("Maximum number is 20");
            else{
                Intent intent = new Intent(this, RoomActivity.class);
                intent.putExtra("Name", name);
                intent.putExtra("City", city);
                intent.putExtra("InDate", inDate);
                intent.putExtra("OutDate", outDate);
                intent.putExtra("Guests", numberOfGuests);
                startActivity(intent);
            }
        }
    }
}