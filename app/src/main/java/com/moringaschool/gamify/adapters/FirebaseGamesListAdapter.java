package com.moringaschool.gamify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;

import org.jetbrains.annotations.NotNull;

import util.ItemTouchHelperAdapter;
import util.OnStartDragListener;

public class FirebaseGamesListAdapter extends FirebaseRecyclerAdapter<GameSearchResponse, FirebaseGamesViewHolder> implements ItemTouchHelperAdapter {
   private DatabaseReference mRef;
   private OnStartDragListener mOnStartDragListener;
   private Context mContext;

    public FirebaseGamesListAdapter(@NonNull FirebaseRecyclerOptions<GameSearchResponse> options, DatabaseReference ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener= onStartDragListener;
        mContext= context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseGamesViewHolder firebaseGamesViewHolder, int position, @NonNull GameSearchResponse game) {
        firebaseGamesViewHolder.bindGame(game);
        firebaseGamesViewHolder.mGameImageView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()== MotionEvent.ACTION_DOWN){
                    mOnStartDragListener.onStartDrag(firebaseGamesViewHolder);
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public FirebaseGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item_drag,parent, false);
        return new FirebaseGamesViewHolder(view);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        getRef(position).removeValue();
    }
}
