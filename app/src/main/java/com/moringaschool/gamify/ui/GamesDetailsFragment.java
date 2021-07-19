package com.moringaschool.gamify.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.gamify.Constant;
import com.moringaschool.gamify.R;
import com.moringaschool.gamify.models.GameSearchResponse;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GamesDetailsFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.gameImageView) ImageView mImageLabel;
    @BindView(R.id.gameNameTextView) TextView mNameLabel;
    @BindView(R.id.dateTextView) TextView mDateLabel;
    @BindView(R.id.descriptionTextView) TextView mDescriptionLabel;
    @BindView(R.id.developerTextView) TextView mDeveloperLabel;
    @BindView(R.id.publisherTextView) TextView mPublisherLabel;
    @BindView(R.id.linkTextView) TextView mLinkLabel;
    @BindView(R.id.saveGameButton) Button mSaveGameButtonLabel;

    private GameSearchResponse mGames;


    public GamesDetailsFragment() {
        // Required empty public constructor
    }


    public static GamesDetailsFragment newInstance(GameSearchResponse game) {
        GamesDetailsFragment gamesDetailsFragment = new GamesDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("game", Parcels.wrap(game));
        gamesDetailsFragment.setArguments(args);
        return gamesDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() !=null;
        mGames = Parcels.unwrap(getArguments().getParcelable("game"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_games_details,container,false);
        ButterKnife.bind(this,view);
        Picasso.get().load(mGames.getThumbnail()).into(mImageLabel);

        mNameLabel.setText(mGames.getTitle());
        mDateLabel.setText(mGames.getReleaseDate());
        mDescriptionLabel.setText(mGames.getShortDescription());
        mDeveloperLabel.setText(mGames.getDeveloper());
        mPublisherLabel.setText(mGames.getPublisher());
        mLinkLabel.setText(mGames.getGameUrl());

        mDeveloperLabel.setOnClickListener(this);
        mPublisherLabel.setOnClickListener(this);
        mLinkLabel.setOnClickListener(this);

        mSaveGameButtonLabel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v== mSaveGameButtonLabel){
            DatabaseReference gameRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constant.FIREBASE_CHILD_GAMES);

            gameRef.push().setValue(mGames);
            Toast.makeText(getContext(), "saved",Toast.LENGTH_SHORT).show();
        }
    }
}