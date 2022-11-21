package com.freedommuskrats.clubbub.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentHomeBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.club.MemberClubView;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewFragment;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewHomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        FragmentContainerView containerView = root.findViewById(R.id.homeContainerView);


        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.homeFrame, new ClubLists(this));
        transaction.commit();

        return root;
    }


    public void goToClubPage(Club club) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFrame, new MemberClubViewFragment(club));
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}