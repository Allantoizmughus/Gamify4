package com.moringaschool.gamify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesListAdapter extends RecyclerView.Adapter<GamesListAdapter.GamesViewHolder> {
    private List<GameSearchResponse> mGames;
    private Context mContext;

    public GamesListAdapter(Context context, List<GameSearchResponse> games){
        mContext =context;
        mGames = games;
    }

    @Override
    public GamesListAdapter.GamesViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_games_list, parent, false);
        GamesViewHolder viewHolder = new GamesViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(GamesListAdapter.GamesViewHolder holder, int position) {
        holder.bindGames(mGames.get(position));
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class GamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.gameImageView)
        ImageView mGameImageView;
        @BindView(R.id.gameNameTextView)
        TextView mGameNameTextView;
        @BindView(R.id.descriptionTextView) TextView mDescriptionNameTextView;
        @BindView(R.id.dateTextView) TextView mDateTextView;

        private Context mContext;


        public GamesViewHolder( View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        public void bindGames(GameSearchResponse game) {
            mGameNameTextView.setText(game.getTitle());
            mDescriptionNameTextView.setText(game.getShortDescription());
            mDateTextView.setText(game.getReleaseDate());
            Picasso.get().load(game.getThumbnail()).into(mGameImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
