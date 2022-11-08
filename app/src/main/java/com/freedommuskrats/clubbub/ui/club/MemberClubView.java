package com.freedommuskrats.clubbub.ui.club;

import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.ANNOUNCEMENT;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.CHAT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.home.HomeFragment;
import com.freedommuskrats.clubbub.ui.home.HomeScreenCreatedClubs;
import com.freedommuskrats.clubbub.ui.home.HomeScreenMembership;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MemberClubView extends AppCompatActivity {

    public static final String CLUB_KEY = "CLUB";
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_club_view);

        Club club = (Club) getIntent().getSerializableExtra(CLUB_KEY);

        TextView clubName = this.findViewById(R.id.memberClubViewTitle);
        clubName.setText(club.getName());
        TextView description = this.findViewById(R.id.memberClubViewDescription);
        description.setText(club.getDescription());

        TabLayout tabLayout = this.findViewById(R.id.memberClubViewTabLayout);
        ViewPager2 viewPager2 = this.findViewById(R.id.memberClubViewViewPager);
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
                    return new AnnouncementChatFragment(ANNOUNCEMENT);
                case 1 :
                    return new AnnouncementChatFragment(CHAT);
                default :
                    return new AnnouncementChatFragment(ANNOUNCEMENT);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }


}