package com.freedommuskrats.clubbub.ui.club.owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.PersonSearchCaller;
import com.freedommuskrats.clubbub.ui.home.HomeFragment;

public class OwnerClubViewContainer extends Fragment{

    public static final String CLUB_KEY = "CLUB";

    private final PersonSearchCaller homeFragment;
    private Club club;

    public OwnerClubViewContainer(Club club, PersonSearchCaller homeFragment) {
        this.club = club;
        this.homeFragment = homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_owner_club_view_home, container, false);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ownerClubContainer, new OwnerClubViewHomeFragment(club, this, homeFragment));
        transaction.commit();

        return root;
    }

    public void setContainerToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ownerClubContainer, fragment);
        transaction.commit();
    }


}
