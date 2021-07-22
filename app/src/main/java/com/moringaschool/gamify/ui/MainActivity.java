package com.moringaschool.gamify.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.gamify.Constant;
import com.moringaschool.gamify.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.ViewCategoryButton) Button mViewCategoryButton;
    @BindView(R.id.AppName) TextView mAppName;
    @BindView(R.id.contacts) Button mContactsButton;
   // @BindView(R.id.planets_spinner) Spinner mSelect;
    @BindView(R.id.savedGamesButton) Button mSavedGamesButton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;
//    private DatabaseReference mSearchedCategoryReference;
//    private ValueEventListener mSearchedCategoryReferenceListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        mSearchedCategoryReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constant.FIREBASE_CHILD_SEARCHED_CATEGORY);
//
//       mSearchedCategoryReferenceListener = mSearchedCategoryReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange( DataSnapshot datasnapshot) {
//                for(DataSnapshot categorySnapshot: datasnapshot.getChildren()){
//                    String category = categorySnapshot.getValue().toString();
//                    Log.d("Category update","Category" +category);
//                }
//            }
//
//            @Override
//            public void onCancelled( DatabaseError databaseError) {
//                //update UI here if error occurred.
//            }
//        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();


//        ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.cate));
//        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        mSelect.setAdapter(myAdapter);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    getSupportActionBar().setTitle("Welcome," + user.getDisplayName()+ "!");
                }else{

                }
            }
        };

        Log.e("TAG","Before onClick");
        mViewCategoryButton.setOnClickListener(this);
        mContactsButton.setOnClickListener(this);
        mSavedGamesButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mViewCategoryButton) {
//            String category = mSelect.getSelectedItem().toString();
//
//            saveCategoryToFirebase(category);

//            if(!(category).equals("")){
//                addToSharedPreferences(category);
//            }
            Intent intent = new Intent(MainActivity.this, GamesListActivity.class);
//            intent.putExtra("category",category);
            Log.e("TAG","Before startActivity");
            startActivity(intent);
        }else if(v == mContactsButton){
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        } else if(v== mSavedGamesButton){
            Intent intent = new Intent(MainActivity.this,SavedGamesListActivity.class);
            startActivity(intent);
        }
    }@Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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

//    public void saveCategoryToFirebase(String category){
//        mSearchedCategoryReference.push().setValue(category);
//    }
//
////    private void addToSharedPreferences(String category) {
////        mEditor.putString(Constant.PREFERENCES_CATEGORY_KEY, category).apply();
////    }
//
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        mSearchedCategoryReference.removeEventListener(mSearchedCategoryReferenceListener);
//    }
}