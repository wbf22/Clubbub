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

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.squareup.picasso.Picasso;

public class OwnerClubViewHomeFragment extends Fragment {

    public static final String CLUB_KEY = "CLUB";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Club club = (Club) getActivity().getIntent().getSerializableExtra(CLUB_KEY);

        View root = inflater.inflate(R.layout.fragment_owner_club_view_home, container, false);

        EditText clubName = root.findViewById(R.id.ownerClubViewTitle);
        clubName.setText(club.getName());
        EditText description = root.findViewById(R.id.ownerClubViewDescription);
        description.setText(club.getDescription());


        ImageView clubImage = root.findViewById(R.id.ownerClubViewImageView);
        Picasso.get().load(R.mipmap.stickman_foreground).into(clubImage);



        return root;
    }


}
