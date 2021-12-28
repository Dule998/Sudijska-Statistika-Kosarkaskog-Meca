package Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import Models.Helpers.FragmentTabItemModel;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context context;

    private final ArrayList<FragmentTabItemModel> fragments;

    public SectionsPagerAdapter(Context context, @NonNull FragmentManager fm, ArrayList<FragmentTabItemModel> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragments != null) {
            return fragments.get(position).getFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragments != null) {
            return fragments.size();
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (fragments != null) {
            return fragments.get(position).getTitle();
        }
        return "null";
    }
}//[Class]
