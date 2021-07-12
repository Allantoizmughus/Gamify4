package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        ButterKnife.bind(this);

//        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                String game = ((TextView)view).getText().toString();
//                Toast.makeText(GamesListActivity.this, game, Toast.LENGTH_LONG).show();
//            }
//        } );

        Intent intent= getIntent();
        String category = intent.getStringExtra("category");
        mCategoryView.setText("Here are all the games in Category "+category);

        ApiCallInterface client = GamesClient.getClient();

        Call<GameSearchResponse> call = client.getGames(category, "games");

        call.enqueue(new Callback<GameSearchResponse>() {
            @Override
            public void onResponse(Call<GameSearchResponse> call, Response<GameSearchResponse> response) {
                if(response.isSuccessful()){
                    games = response.body().getGames();
                    mAdapter = new GamesListAdapter(GamesListActivity.this, games);

                }
            }

            @Override
            public void onFailure(Call<GameSearchResponse> call, Throwable t) {

            }
        });
    }
}