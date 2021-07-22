package com.moringaschool.gamify.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.moringaschool.gamify.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.nameEditText)
    EditText mNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.createUserButton)
    Button mCreateUserButton;
    @BindView(R.id.loginTextView) TextView mLoginTextView;
    @BindView(R.id.firebaseProgressBar)
    ProgressBar mSignInProgressBar;
    @BindView(R.id.loadingTextView) TextView mLoadingSignUp;
    private String mName;


    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        createAuthStateListener();

        mCreateUserButton.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
    }

    private void showProgressBar() {
        mSignInProgressBar.setVisibility(View.VISIBLE);
        mLoadingSignUp.setVisibility(View.VISIBLE);
        mLoadingSignUp.setText("Sign Up process in Progress");
    }

    private void hideProgressBar() {
        mSignInProgressBar.setVisibility(View.GONE);
        mLoadingSignUp.setVisibility(View.GONE);
    }

    private void createNewUser(){
        mName = mNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        final String password= mPasswordEditText.getText().toString().trim();
        final String confirmPassword= mConfirmPasswordEditText.getText().toString().trim();

        boolean validName=isValidName(mName);
        boolean validEmail=isValidEmail(email);
        boolean validPassword=isValidPassword(password,confirmPassword);
        if(!validName || !validEmail || !validPassword) return;

        showProgressBar();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    hideProgressBar();
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Authentication is successful");
                        createFirebaseUserProfile(Objects.requireNonNull(task.getResult().getUser()));
                    } else {
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createFirebaseUserProfile(final FirebaseUser user) {

        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(mName)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, Objects.requireNonNull(user.getDisplayName()));
                        Toast.makeText(CreateAccountActivity.this, "The display name has ben set", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void createAuthStateListener(){
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user =firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }

            }
        };
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginTextView){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if(v == mCreateUserButton){
            createNewUser();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean isValidEmail(String email){
        boolean isGoodEmail =(email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }
        return true;
    }

    private boolean isValidName(String name) {
        if (name.equals("")) {
            mNameEditText.setError("Please enter your name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        if (password.length() < 6) {
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            mPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}