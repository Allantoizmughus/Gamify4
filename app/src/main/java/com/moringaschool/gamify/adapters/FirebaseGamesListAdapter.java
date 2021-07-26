package com.moringaschool.gamify.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.ui.GamesDetailsActivity;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import util.ItemTouchHelperAdapter;
import util.OnStartDragListener;

public class FirebaseGamesListAdapter extends FirebaseRecyclerAdapter<GameSearchResponse, FirebaseGamesViewHolder> implements ItemTouchHelperAdapter {
   private DatabaseReference mRef;
   private OnStartDragListener mOnStartDragListener;
   private Context mContext;

   private ChildEventListener mChildEventListener;
   private ArrayList<GameSearchResponse> mGameSearchResponse = new ArrayList<>();

    public FirebaseGamesListAdapter(@NonNull FirebaseRecyclerOptions<GameSearchResponse> options, Query ref, OnStartDragListener onStartDragListener, Context context) {
        super(options);
        mRef = ref.getRef();
        mOnStartDragListener= onStartDragListener;
        mContext= context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mGameSearchResponse.add(dataSnapshot.getValue(GameSearchResponse.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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

        firebaseGamesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GamesDetailsActivity.class);
                intent.putExtra("position", firebaseGamesViewHolder.getAdapterPosition());
                intent.putExtra("games", Parcels.wrap(mGameSearchResponse));
                mContext.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public FirebaseGamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_item_drag,parent, false);
        return new FirebaseGamesViewHolder(view);
    }


    private void setIndexInFirebase(){
        for(GameSearchResponse gameSearchResponse:mGameSearchResponse){
            int index =mGameSearchResponse.indexOf(gameSearchResponse);
            DatabaseReference ref = getRef(index);
            gameSearchResponse.setIndex(Integer.toString(index));
            ref.setValue(gameSearchResponse);
        }
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mGameSearchResponse, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        setIndexInFirebase();
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mGameSearchResponse.remove(position);
        getRef(position).removeValue();
    }

    @Override
    public void stopListening() {
        super.stopListening();
        mRef.removeEventListener(mChildEventListener); }
}
