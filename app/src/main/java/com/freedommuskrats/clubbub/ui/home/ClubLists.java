package com.freedommuskrats.clubbub.ui.home;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freedommuskrats.clubbub.R;
import com.google.android.material.tabs.TabLayout;

public class ClubLists extends Fragment {


    private HomeFragment parent;

    public ClubLists(HomeFragment parent) {
        this.parent = parent;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_club_lists, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerHomeScreen);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, this.parent);
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


        public ViewPagerAdapter(@NonNull Fragment fragment, Fragment parent) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0 :
                    return new HomeScreenMembership(parent);
                case 1 :
                    return new ProfileFragment(parent, defaultPerson());
                case 2 :
                    return new HomeScreenCreatedClubs(parent);
                default :
                    return new HomeScreenMembership(parent);
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}