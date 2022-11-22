package com.freedommuskrats.clubbub.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentHomeBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.club.MemberClubViewFragment;

public class HomeFragment extends Fragment {
    public static final String MEMBER = "MEMBER";
    public static final String OWNER = "OWNER";
    public static final String NON_MEMBER = "NON_MEMBER";

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


    public void goToClubPage(Club club, String type) {
        if (type.equals(MEMBER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.homeFrame, new MemberClubViewFragment(club));
            transaction.commit();
        } else if (type.equals(NON_MEMBER)) {

        } else if (type.equals(OWNER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.homeFrame, new MemberClubViewFragment(club));
            transaction.commit();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}