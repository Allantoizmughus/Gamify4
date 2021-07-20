package com.moringaschool.gamify.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.gamify.Constant;
import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.ui.GamesDetailsActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseGamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View mView;
    Context mContext;

    public FirebaseGamesViewHolder( View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

    }

    public void bindGame(GameSearchResponse game){
        ImageView gameImageView = (ImageView) mView.findViewById(R.id.gameImageView);
        TextView gameNameTextView = (TextView) mView.findViewById(R.id.gameNameTextView);
        TextView dateTextView = (TextView) mView.findViewById(R.id.dateTextView);
        TextView descriptionTextView = (TextView) mView.findViewById(R.id.descriptionTextView);

        Picasso.get().load(game.getThumbnail()).into(gameImageView);

        gameNameTextView.setText(game.getTitle());
        dateTextView.setText("Released on:"+game.getReleaseDate());
        descriptionTextView.setText(game.getShortDescription());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<GameSearchResponse> games = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constant.FIREBASE_CHILD_GAMES);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot:datasnapshot.getChildren()){
                    games.add(snapshot.getValue(GameSearchResponse.class));
                }
                int itemPosition = getLayoutPosition();

                Intent intent =new Intent(mContext, GamesDetailsActivity.class);
                intent.putExtra("position",itemPosition + "");
                intent.putExtra("games", Parcels.wrap(games));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled( DatabaseError databaseerror) {

            }
        });
    }
}
