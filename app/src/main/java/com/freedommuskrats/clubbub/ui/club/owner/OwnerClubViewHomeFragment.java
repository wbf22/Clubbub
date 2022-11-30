package com.freedommuskrats.clubbub.ui.club.owner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.ui.club.EditClubCaller;
import com.freedommuskrats.clubbub.ui.club.EditClubFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class OwnerClubViewHomeFragment extends Fragment implements EditClubCaller {

    private OwnerClubViewContainer parentContainer;
    private Club club;

    public OwnerClubViewHomeFragment(Club club, OwnerClubViewContainer parentContainer) {
        this.club = club;
        this.parentContainer = parentContainer;
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

        return root;
    }


    @Override
    public void handleEditClubDone(Club club) {
        parentContainer.setContainerToFragment(new OwnerClubViewHomeFragment(club, parentContainer));
    }
}