package com.moringaschool.gamify.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moringaschool.gamify.models.GameSearchResponse;
import com.moringaschool.gamify.ui.GamesDetailsFragment;

import java.util.List;

public class GamesPagerAdapter extends FragmentPagerAdapter{
    private List<GameSearchResponse> mGames;

    public GamesPagerAdapter(@NonNull FragmentManager fm, int behavior, List<GameSearchResponse> games) {
        super(fm, behavior);
        mGames = games;
    }
    @Override
    public Fragment getItem(int position) {
        return GamesDetailsFragment.newInstance(mGames.get(position));
    }

    @Override
    public int getCount() {
        return mGames.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mGames.get(position).getTitle();
    }
}
