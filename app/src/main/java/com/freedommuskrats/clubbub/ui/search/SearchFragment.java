package com.freedommuskrats.clubbub.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentSearchBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.PersonSearchCaller;
import com.freedommuskrats.clubbub.ui.club.member.MemberClubViewFragment;
import com.freedommuskrats.clubbub.ui.club.NonMemberClubViewFragment;
import com.freedommuskrats.clubbub.ui.club.owner.OwnerClubViewFragment;


public class SearchFragment extends Fragment implements PersonSearchCaller {

    private FragmentSearchBinding binding;

    public static final String MEMBER = "MEMBER";
    public static final String OWNER = "OWNER";
    public static final String NON_MEMBER = "NON_MEMBER";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.searchFrame, new SearchClubsFragment(this));
        transaction.commit();

        return root;
    }

    public void goToClubPage(Club club, String type) {
        if (type.equals(MEMBER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.searchFrame, new MemberClubViewFragment(club));
            transaction.commit();
        } else if (type.equals(NON_MEMBER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.searchFrame, new NonMemberClubViewFragment(club));
            transaction.commit();
        } else if (type.equals(OWNER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.searchFrame, new OwnerClubViewFragment(club, SearchFragment.this));
            transaction.commit();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void goToSearch(Club currentClub) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.searchFrame, new SearchPeopleFragment(currentClub));
        transaction.commit();
    }

    @Override
    public void goToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.searchFrame, fragment);
        transaction.commit();
    }
}