package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.moringaschool.gamify.network.ApiCallInterface;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.network.GamesClient;
import com.moringaschool.gamify.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GamesListActivity extends AppCompatActivity {
    @BindView(R.id.CategoryList) TextView mCategoryView;
    @BindView(R.id.listView) ListView mListView;

    private String[] games = new String[]{"White Tiles","Candy Crush","Pokemon","Word Cookie","Zombie Highway","Car Race","Ludo","Pool Billiard","Fifa 21","T.K.O","Temple Run2","Maze Runner","Escape Room","Fortnite","Arrows"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,games);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String game = ((TextView)view).getText().toString();
                Toast.makeText(GamesListActivity.this, game, Toast.LENGTH_LONG).show();
            }
        } );

        Intent intent= getIntent();
        String category = intent.getStringExtra("category");
        mCategoryView.setText("Here are all the games in Category "+category);

        ApiCallInterface client = GamesClient.getClient();

        Call<GameSearchResponse> call = client.getGames(category, "games");

        call.enqueue(new Callback<GameSearchResponse>() {
            @Override
            public void onResponse(Call<GameSearchResponse> call, Response<GameSearchResponse> response) {
                if(response.isSuccessful()){
                    Class<? extends GameSearchResponse> gamesList=response.body().getClass();
                }
            }

            @Override
            public void onFailure(Call<GameSearchResponse> call, Throwable t) {

            }
        });
    }
}