package com.freedommuskrats.clubbub.ui.club;

import static com.freedommuskrats.clubbub.domain.FakeData.updateClub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditClubFragment extends Fragment {


    private Club club;

    public EditClubFragment(Club club) {
        this.club = club;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_club, container, false);

        FloatingActionButton saveButton = view.findViewById(R.id.saveOwnerView);
        EditText title = view.findViewById(R.id.editClubViewTitle);
        title.setText(club.getName());
        EditText description = view.findViewById(R.id.editClubViewDescription);
        description.setText(club.getDescription());


        saveButton.setEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setEnabled(false);

                updateClub(club, title.getText().toString(), description.getText().toString());
                club.setName(title.getText().toString());
                club.setDescription(description.getText().toString());

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.editClubContainer, new OwnerClubViewHomeFragment(club));
                transaction.commit();
            }
        });

        return view;
    }
}