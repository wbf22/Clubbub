package com.freedommuskrats.clubbub;

import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.ANNOUNCEMENT;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.CHAT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;

import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment;
import com.freedommuskrats.clubbub.ui.club.MemberClubView;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewHomeFragment;
import com.freedommuskrats.clubbub.ui.club.OwnerClubViewHomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class OwnerClubView extends AppCompatActivity {


    public static final String CLUB_KEY = "CLUB";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_club_view);

        Club club = (Club) getIntent().getSerializableExtra(CLUB_KEY);



        TabLayout tabLayout = this.findViewById(R.id.ownerClubViewTabLayout);

        ViewPager2 viewPager2 = this.findViewById(R.id.ownerClubViewViewPager);
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
    }


    private class ViewPagerAdapter extends FragmentStateAdapter {


        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0 :
                    return new OwnerClubViewHomeFragment();
                case 1 :
                    return new AnnouncementChatFragment(ANNOUNCEMENT, true);
                case 2 :
                    return new AnnouncementChatFragment(CHAT, true);
                default :
                    return new OwnerClubViewHomeFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}