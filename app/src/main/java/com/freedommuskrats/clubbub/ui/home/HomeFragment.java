package com.freedommuskrats.clubbub.ui.home;

import static com.freedommuskrats.clubbub.domain.FakeData.addClub;
import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentHomeBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.PersonSearchCaller;
import com.freedommuskrats.clubbub.ui.club.EditCaller;
import com.freedommuskrats.clubbub.ui.club.EditClubFragment;
import com.freedommuskrats.clubbub.ui.club.member.MemberClubViewFragment;
import com.freedommuskrats.clubbub.ui.club.owner.OwnerClubViewFragment;
import com.freedommuskrats.clubbub.ui.search.SearchPeopleFragment;

public class HomeFragment extends Fragment implements EditCaller, PersonSearchCaller {
    public static final String MEMBER = "MEMBER";
    public static final String OWNER = "OWNER";
    public static final String NON_MEMBER = "NON_MEMBER";
    public static final String MAKE_CLUB = "MAKE_CLUB";

    private FragmentHomeBinding binding;
    private Person user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        user = defaultPerson(); //TODO replace with real data

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        FragmentContainerView containerView = root.findViewById(R.id.homeContainerView);


        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.homeFrame, new ClubLists(this));
        transaction.commit();

        return root;
    }


    public void goToClubPage(Club club, String type) {
        if (type.equals(MEMBER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.homeFrame, new MemberClubViewFragment(club, this));
            transaction.commit();
        } else if (type.equals(NON_MEMBER)) {

        } else if (type.equals(OWNER)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.homeFrame, new OwnerClubViewFragment(club, this));
            transaction.commit();
        } else if (type.equals(MAKE_CLUB)) {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            addClub(club, user);
            transaction.replace(R.id.homeFrame, new EditClubFragment(club, this));
            transaction.commit();
        }

    }

    @Override
    public void goToFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFrame, fragment);
        transaction.commit();
    }

    public void goToClubListsProfilePage() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFrame, new ClubLists(this, 1));
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void handleEditDone(Object obj) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFrame, new OwnerClubViewFragment((Club)obj, HomeFragment.this));
        transaction.commit();
    }

    @Override
    public void goToSearch(Club currentClub) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.homeFrame, new SearchPeopleFragment(currentClub));
        transaction.commit();
    }
}