package com.freedommuskrats.clubbub.ui.search;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;
import static com.freedommuskrats.clubbub.domain.FakeData.filterResults;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubByName;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakeClubs;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakePeople;
import static com.freedommuskrats.clubbub.domain.FakeData.isMember;
import static com.freedommuskrats.clubbub.domain.FakeData.isOwner;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.ANNOUNCEMENT;
import static com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment.CHAT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentSearchBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.AnnouncementChatFragment;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewFragment;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewHomeFragment;
import com.freedommuskrats.clubbub.ui.home.HomeFragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchViewContainer extends Fragment {



    private SearchFragment parent;

    public SearchViewContainer(SearchFragment parent) {
        this.parent = parent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_view_container, container, false);


        ViewPager2 viewPager2 = view.findViewById(R.id.toggleClubPersonViewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.toggleClubPerson);
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
                    return new SearchClubsFragment(parent);
                case 1 :
                    return new SearchPeopleFragment(parent);
                default :
                    return new SearchClubsFragment(parent);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }


}