package me.arvin.ezylib.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import me.arvin.ezylib.ui.fragment.admin.AdminDashboardFragment;
import me.arvin.ezylib.ui.fragment.admin.AdminProfileFragment;
import me.arvin.ezylib.ui.fragment.universal.BookListFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
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
                return new AdminDashboardFragment();
            case 1:
                return new BookListFragment();
            case 2:
                return new AdminProfileFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }
}



