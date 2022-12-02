package com.freedommuskrats.clubbub.ui.club.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.PersonSearchCaller;
import com.freedommuskrats.clubbub.ui.club.EditCaller;
import com.freedommuskrats.clubbub.ui.club.EditClubFragment;
import com.freedommuskrats.clubbub.ui.home.HomeFragment;
import com.freedommuskrats.clubbub.ui.search.SearchPeopleFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class OwnerClubViewHomeFragment extends Fragment implements EditCaller {

    private PersonSearchCaller homeFragment;
    private OwnerClubViewContainer parentContainer;
    private Club club;

    public OwnerClubViewHomeFragment(Club club, OwnerClubViewContainer parentContainer, PersonSearchCaller homeFragment) {
        this.club = club;
        this.parentContainer = parentContainer;
        this.homeFragment = homeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_owner_club_view_home2, container, false);

        TextView clubName = root.findViewById(R.id.ownerClubViewTitle);
        clubName.setText(club.getName());
        TextView description = root.findViewById(R.id.ownerClubViewDescription);
        description.setText(club.getDescription());


        ImageView clubImage = root.findViewById(R.id.ownerClubViewImageView);
        Picasso.get().load(R.mipmap.stickman_foreground).into(clubImage);

        FloatingActionButton editButton = root.findViewById(R.id.editOwnerView);

        editButton.setEnabled(true);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setEnabled(false);
                parentContainer.setContainerToFragment(new EditClubFragment(club, OwnerClubViewHomeFragment.this));
            }
        });


        Button inviteOtherUsers = root.findViewById(R.id.inviteOtherUsers);
        inviteOtherUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeFragment.goToFragment(new SearchPeopleFragment(club));
            }
        });

        return root;
    }


    @Override
    public void handleEditDone(Object obj) {
        parentContainer.setContainerToFragment(new OwnerClubViewHomeFragment((Club)obj, parentContainer, homeFragment));
    }
}