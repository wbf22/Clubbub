package com.freedommuskrats.clubbub.ui;

import androidx.fragment.app.Fragment;

import com.freedommuskrats.clubbub.domain.Club;

public interface PersonSearchCaller {
    void goToSearch(Club currentClub);
    void goToFragment(Fragment fragment);
}
