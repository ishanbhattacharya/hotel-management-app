package com.ishanbhattacharya.hotelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText nameText;
    private TextInputEditText emailText;
    private TextInputEditText passText;
    private TextInputEditText cPassText;
    private FirebaseAuth mAuth;
    private ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        nameText = findViewById(R.id.nameEditText);
        emailText = findViewById(R.id.emailEditText);
        passText = findViewById(R.id.passEditText);
        cPassText = findViewById(R.id.cPassEditText);
        pBar = findViewById(R.id.progressBar);
    }

    public void SignInOpen(View View){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }

    public void SignUp(View view){
        String name = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String p = passText.getText().toString();
        String cp = cPassText.getText().toString();
        if(!name.equals("") && !email.equals("") && !p.equals("") && !cp.equals("")){
            if(p.equals(cp)){
                pBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, p)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(nameText.getText().toString().trim()).build();
                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getApplicationContext(), "Account Creation Successful", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
                                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(i);
                                                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
                                                    }
                                                }
                                            });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    pBar.setVisibility(View.INVISIBLE);
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException) emailText.setError("Account with this e-mail already exists");
                                    if(task.getException() instanceof FirebaseAuthWeakPasswordException) passText.setError("Password must be at least 6 characters");
                                    else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException) emailText.setError("Invalid Credentials");
                                }

                                // ...
                            }
                        });
            }
            else{
                cPassText.setError("Passwords don't match");
            }
        }
        else{
            if(name.equals("")) nameText.setError("Name cannot be blank");
            if(email.equals("")) emailText.setError("E-mail cannot be blank");
            if(p.equals("")) passText.setError("Password cannot be blank");
            else if(cp.equals("")) cPassText.setError("Please retype your password");
        }
    }
}