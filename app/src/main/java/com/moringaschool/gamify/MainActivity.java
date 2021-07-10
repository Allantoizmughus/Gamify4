package com.moringaschool.gamify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.ViewCategoryButton) Button mViewCategoryButton;
    @BindView(R.id.category) EditText mCategory;
    @BindView(R.id.AppName) TextView mAppName;
    @BindView(R.id.contacts) Button mContactsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mViewCategoryButton.setOnClickListener(this);
        mContactsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mViewCategoryButton) {
            String category = mCategory.getText().toString();
            Intent intent = new Intent(MainActivity.this, GamesListActivity.class);
            intent.putExtra("category",category);
            startActivity(intent);
        }else if(v == mContactsButton){
            Intent intent = new Intent(MainActivity.this,ContactsActivity.class);
            startActivity(intent);
        }
    }
}