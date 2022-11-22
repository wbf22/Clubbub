package com.freedommuskrats.clubbub.ui.club;

import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.ANNOUNCEMENT;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.CHAT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.google.android.material.tabs.TabLayout;

public class OwnerClubViewFragment extends Fragment {



    private Club club;

    public OwnerClubViewFragment(Club club) {
        this.club = club;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_club_view, container, false);

        TabLayout tabLayout = view.findViewById(R.id.ownerClubViewTabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.ownerClubViewViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });


        return view;
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {


        public ViewPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0 :
                    return new OwnerClubViewHomeFragment(club);
                case 1 :
                    return new AnnouncementChatFragment(ANNOUNCEMENT, true);
                case 2 :
                    return new AnnouncementChatFragment(CHAT, true);
                default :
                    return new OwnerClubViewHomeFragment(club);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}