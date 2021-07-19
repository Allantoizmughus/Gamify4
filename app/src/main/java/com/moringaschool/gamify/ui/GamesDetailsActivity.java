package com.moringaschool.gamify.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcel;

import com.moringaschool.gamify.R;
import com.moringaschool.gamify.adapters.GamesPagerAdapter;
import com.moringaschool.gamify.models.GameSearchResponse;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GamesDetailsActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private GamesPagerAdapter adapterViewPager;
    List<GameSearchResponse> mGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_details);
        ButterKnife.bind(this);

        mGames = Parcels.unwrap(getIntent().getParcelableExtra("game"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new GamesPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mGames);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}