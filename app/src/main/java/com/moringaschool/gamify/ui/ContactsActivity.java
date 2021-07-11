package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.moringaschool.gamify.R;
import com.moringaschool.gamify.contactFragment;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        FragmentManager fm = getSupportFragmentManager();
        contactFragment contactsFragment = new contactFragment();
        contactsFragment.show(fm,"Sample Fragment");
    }
}