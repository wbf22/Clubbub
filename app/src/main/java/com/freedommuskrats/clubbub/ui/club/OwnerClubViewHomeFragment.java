package com.freedommuskrats.clubbub.ui.club;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class OwnerClubViewHomeFragment extends Fragment {

    public static final String CLUB_KEY = "CLUB";

    private Club club;

    public OwnerClubViewHomeFragment(Club club) {
        this.club = club;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_owner_club_view_home, container, false);

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

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.ownerClubContainer, new EditClubFragment(club));
                transaction.commit();
            }
        });



        return root;
    }


}
