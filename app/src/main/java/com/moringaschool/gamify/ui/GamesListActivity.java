package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moringaschool.gamify.adapters.GamesListAdapter;
import com.moringaschool.gamify.network.ApiCallInterface;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.network.GamesClient;
import com.moringaschool.gamify.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesListActivity extends AppCompatActivity {
    @BindView(R.id.CategoryList) TextView mCategoryView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private GamesListAdapter mAdapter;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    public List<GameSearchResponse> games;
    private static final String TAG = GamesListActivity.class.getSimpleName();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        ButterKnife.bind(this);

        Intent intent= getIntent();
        String category = intent.getStringExtra("category");
        mCategoryView.setText("Here are all the games in Category "+category);

        ApiCallInterface client = GamesClient.getClient();

        Call<GameSearchResponse> call = client.getGames(category, "games");

        call.enqueue(new Callback<GameSearchResponse>() {
            @Override
            public void onResponse(Call<GameSearchResponse> call, Response<GameSearchResponse> response) {
                Log.e(TAG, "This is Okay!!");
                hideProgressBar();
                if(response.isSuccessful()){
                    games = response.body().getGames();
                    mAdapter = new GamesListAdapter(GamesListActivity.this, games);
                    mRecyclerView.setAdapter(mAdapter);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GamesListActivity.this);
                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    showGames();

                }
            }

            @Override
            public void onFailure(Call<GameSearchResponse> call, Throwable t) {
                hideProgressBar();
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        mErrorTextView.setText("Something went wrong. Please check your Internet connection and try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showUnsuccessfulMessage() {
        mErrorTextView.setText("Something went wrong. Please try again later");
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void showGames() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

}
