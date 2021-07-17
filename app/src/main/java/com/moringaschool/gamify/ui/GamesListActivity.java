package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.gamify.Constant;
import com.moringaschool.gamify.adapters.GamesListAdapter;
import com.moringaschool.gamify.network.ApiCallInterface;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.network.GamesClient;
import com.moringaschool.gamify.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesListActivity extends AppCompatActivity {
    @BindView(R.id.CategoryList) TextView mCategoryView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    GamesListAdapter mAdapter;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    LinearLayoutManager layoutManager;

    private SharedPreferences mSharedPreferences;
    private String mRecentCategory;

    List<GameSearchResponse> games = new ArrayList<>();
    private static final String TAG = GamesListActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        ButterKnife.bind(this);
        layoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter=new GamesListAdapter(games);
        mRecyclerView.setAdapter(mAdapter);

        Log.e("TAG", "onCreate GamesActivity");
        Intent intent = getIntent();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentCategory = mSharedPreferences.getString(Constant.PREFERENCES_CATEGORY_KEY, null);
        String category = mRecentCategory;
//        String category = intent.getStringExtra("category");
        mCategoryView.setText("Here are all the games in Category " + category);

        fetchPosts();


    }
    private void fetchPosts(){
        Log.e("TAG", "fetchPosts");
        mProgressBar.setVisibility(View.VISIBLE);
        GamesClient.getClient().getGames().enqueue(new Callback<List<GameSearchResponse>>(){

            @Override
            public void onResponse(Call<List<GameSearchResponse>> call, Response<List<GameSearchResponse>> response) {
                if(response.isSuccessful() && response.body() != null){
                    games.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<GameSearchResponse>> call, Throwable t) {
                Log.e("TAG","Inside onFailure");
                hideProgressBar();
                showFailureMessage();
            }
    });

    }


    public void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    public void showGames() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
