package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.moringaschool.gamify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.ViewCategoryButton) Button mViewCategoryButton;
    @BindView(R.id.AppName) TextView mAppName;
    @BindView(R.id.contacts) Button mContactsButton;
    @BindView(R.id.planets_spinner) Spinner mSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        ArrayAdapter<String> myAdapter =new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.cate));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mSelect.setAdapter(myAdapter);

        Log.e("TAG","Before onClick");
        mViewCategoryButton.setOnClickListener(this);
        mContactsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == mViewCategoryButton) {
            String category = mSelect.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this, GamesListActivity.class);
            intent.putExtra("category",category);
            Log.e("TAG","Before startActivity");
            startActivity(intent);
        }else if(v == mContactsButton){
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        }else if(v== mSelect){
        }
    }
}