package com.freedommuskrats.clubbub.ui.club.member;

import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.ANNOUNCEMENT;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.CHAT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.PersonSearchCaller;
import com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment;
import com.google.android.material.tabs.TabLayout;

public class MemberClubViewFragment extends Fragment {


    private Club club;
    private PersonSearchCaller personSearchCaller;

    public MemberClubViewFragment(Club club, PersonSearchCaller personSearchCaller) {
        this.club = club;
        this.personSearchCaller = personSearchCaller;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_member_club_view, container, false);

        TabLayout tabLayout = view.findViewById(R.id.memberClubViewTabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.memberClubViewViewPager);
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
                    return new MemberClubViewHomeFragment(club, personSearchCaller);
                case 1 :
                    return new AnnouncementChatFragment(ANNOUNCEMENT, false);
                case 2 :
                    return new AnnouncementChatFragment(CHAT, false);
                default :
                    return new MemberClubViewHomeFragment(club, personSearchCaller);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}