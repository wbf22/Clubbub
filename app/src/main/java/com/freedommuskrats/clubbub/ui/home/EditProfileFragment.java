package com.freedommuskrats.clubbub.ui.home;

import static com.freedommuskrats.clubbub.domain.FakeData.updatePerson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.EditCaller;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EditProfileFragment extends Fragment {

    private Person user;
    private EditCaller editCaller;

    public EditProfileFragment(Person user, EditCaller clubCaller) {
        this.user = user;
        this.editCaller = clubCaller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        FloatingActionButton saveButton = view.findViewById(R.id.saveProfile);
        EditText title = view.findViewById(R.id.profileName);
        title.setText(user.getFirstName() + " " + user.getLastName());
        EditText description = view.findViewById(R.id.editProfileDescription);
        if (user.getDescription() == null) {
            List<Club> clubs = user.getClubsMemberOf();
            String desc = "";
            for (Club c : clubs) {
                desc += c.getName() + "\n";
            }
            description.setText(desc);
        } else {
            description.setText(user.getDescription());
        }


        saveButton.setEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveButton.setEnabled(false);

                user = updatePerson(user, title.getText().toString(), description.getText().toString());

                editCaller.handleEditDone(user);
            }
        });

        return view;
    }
}