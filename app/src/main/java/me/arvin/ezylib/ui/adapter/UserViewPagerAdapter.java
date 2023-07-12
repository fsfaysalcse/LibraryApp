package me.arvin.ezylib.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import me.arvin.ezylib.ui.fragment.universal.BookListFragment;
import me.arvin.ezylib.ui.fragment.user.UserDashboardFragment;
import me.arvin.ezylib.ui.fragment.user.UserProfileFragment;

public class UserViewPagerAdapter extends FragmentStateAdapter {
    public UserViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 3; // Number of fragments
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserDashboardFragment();
            case 1:
                return new BookListFragment();
            case 2:
                return new UserProfileFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }
}



