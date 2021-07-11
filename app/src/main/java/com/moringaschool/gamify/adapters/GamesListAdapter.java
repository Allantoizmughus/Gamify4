package com.moringaschool.gamify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;

import java.util.List;

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


        public GamesViewHolder( View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }

        public void bindGames(GameSearchResponse gameSearchResponse) {
        }

    }
}
