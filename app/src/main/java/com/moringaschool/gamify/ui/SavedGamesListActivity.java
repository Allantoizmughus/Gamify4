package com.moringaschool.gamify.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.moringaschool.gamify.Constant;
import com.moringaschool.gamify.R;
import com.moringaschool.gamify.adapters.FirebaseGamesListAdapter;
import com.moringaschool.gamify.adapters.FirebaseGamesViewHolder;
import com.moringaschool.gamify.models.GameSearchResponse;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import util.OnStartDragListener;
import util.SimpleItemTouchHelperCallback;

public class SavedGamesListActivity extends AppCompatActivity implements OnStartDragListener {
    private DatabaseReference mGamesReference;
    private FirebaseGamesListAdapter mFirebaseAdapter;
    private ItemTouchHelper mItemTouchHelper;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.errorTextView) TextView mErrorTextView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        ButterKnife.bind(this);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        mGamesReference = FirebaseDatabase.getInstance().getReference(Constant.FIREBASE_CHILD_GAMES).child(uid);

        setUpFirebaseAdapter();
        hideProgressBar();
        showGames();


    }


    private void setUpFirebaseAdapter() {
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constant.FIREBASE_CHILD_GAMES)
                .child(uid)
                .orderByChild(Constant.FIREBASE_QUERY_INDEX);

        FirebaseRecyclerOptions<GameSearchResponse> options= new FirebaseRecyclerOptions.Builder<GameSearchResponse>()
                .setQuery(query, GameSearchResponse.class)
                .build();

        mFirebaseAdapter = new FirebaseGamesListAdapter(options, query, this, this) {
            @Override
            protected void onBindViewHolder(@NonNull FirebaseGamesViewHolder firebaseGamesViewholder, int position, @NonNull  GameSearchResponse game) {
                firebaseGamesViewholder.bindGame(game);
            }

            @NonNull
            @Override
            public FirebaseGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item_drag, parent, false);
                return new FirebaseGamesViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        mRecyclerView.setHasFixedSize(true);
        ItemTouchHelper.Callback callback= new SimpleItemTouchHelperCallback(mFirebaseAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mFirebaseAdapter != null){
            mFirebaseAdapter.stopListening();
        }
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showGames() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.stopListening(); }
}