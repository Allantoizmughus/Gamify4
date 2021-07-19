package com.moringaschool.gamify.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moringaschool.gamify.R;

import butterknife.BindView;


public class GamesDetailsFragment extends Fragment {
    @BindView(R.id.gameImageView) ImageView mImageLabel;
    @BindView(R.id.gameNameTextView) TextView mNameLabel;
    @BindView(R.id.dateTextView) TextView mDateLabel;
    @BindView(R.id.descriptionTextView) TextView mDescriptionLabel;
    @BindView(R.id.developerTextView) TextView mDeveloperLabel;
    @BindView(R.id.publisherTextView) TextView mPublisherLabel;
    @BindView(R.id.linkTextView) TextView mLinkLabel;
    @BindView(R.id.saveGameButton) Button mSaveButtonLabel;


    public GamesDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GamesDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesDetailsFragment newInstance(String param1, String param2) {
        GamesDetailsFragment fragment = new GamesDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_details, container, false);
    }
}