package com.freedommuskrats.clubbub.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.EditCaller;
import com.freedommuskrats.clubbub.ui.club.EditClubFragment;
import com.freedommuskrats.clubbub.ui.club.owner.OwnerClubViewHomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileFragment extends Fragment implements EditCaller {

    private HomeFragment parent;
    private Person currUser;

    public ProfileFragment(HomeFragment parent, Person currUser) {
        this.parent = parent;
        this.currUser = currUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView clubName = root.findViewById(R.id.personName);
        clubName.setText(currUser.getFirstName() + " " + currUser.getLastName());
        TextView description = root.findViewById(R.id.profileDescription);

        if (currUser.getDescription() == null) {
            List<Club> clubs = currUser.getClubsMemberOf();
            String desc = "";
            for (Club c : clubs) {
                desc += c.getName() + "\n";
            }
            description.setText(desc);
        } else {
            description.setText(currUser.getDescription());
        }


        FloatingActionButton editButton = root.findViewById(R.id.editProfile);

        editButton.setEnabled(true);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editButton.setEnabled(false);
                parent.goToFragment(new EditProfileFragment(currUser, ProfileFragment.this));
            }
        });

        return root;
    }

    @Override
    public void handleEditDone(Object obj) {
        parent.goToFragment(new ProfileFragment(parent, (Person) obj));
    }
}