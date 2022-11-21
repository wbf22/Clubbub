package com.freedommuskrats.clubbub.ui.club;

import static com.freedommuskrats.clubbub.domain.FakeData.getAnnouncements;
import static com.freedommuskrats.clubbub.domain.FakeData.getChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Post;

import java.util.List;

public class MemberClubViewHomeFragment extends Fragment {

    public static final String CLUB_KEY = "CLUB";

    private Club club;

    public MemberClubViewHomeFragment(Club club) {
        this.club = club;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_member_club_view_home, container, false);

        TextView clubName = root.findViewById(R.id.memberClubViewTitle);
        clubName.setText(club.getName());
        TextView description = root.findViewById(R.id.memberClubViewDescription);
        description.setText(club.getDescription());

        return root;
    }

}
