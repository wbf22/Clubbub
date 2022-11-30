package com.freedommuskrats.clubbub.ui.club.owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.club.EditClubCaller;
import com.freedommuskrats.clubbub.ui.club.EditClubFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class OwnerClubViewContainer extends Fragment{

    public static final String CLUB_KEY = "CLUB";

    private Club club;

    public OwnerClubViewContainer(Club club) {
        this.club = club;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_owner_club_view_home, container, false);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ownerClubContainer, new OwnerClubViewHomeFragment(club, this));
        transaction.commit();

        return root;
    }

    public void setContainerToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.ownerClubContainer, fragment);
        transaction.commit();
    }


}
