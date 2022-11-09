package com.freedommuskrats.clubbub.ui.home;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubByName;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubsPersonIsMember;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakeClubs;
import static com.freedommuskrats.clubbub.domain.FakeData.isMember;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentSearchBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.MemberClubView;
import com.freedommuskrats.clubbub.ui.club.NonMemberClubView;
import com.freedommuskrats.clubbub.ui.dashboard.DashboardFragment;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class HomeScreenMembership extends Fragment {


    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private Person user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = defaultPerson(); // TODO pass in after login

        View root = inflater.inflate(R.layout.fragment_home_screen_membership, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.membershipRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter();
        recyclerView.setAdapter(adapter);

        List<Club> clubs = getClubsPersonIsMember(user);
        adapter.addItems(clubs);

        return root;
    }



    private class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView description;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.clubImage);
            title = itemView.findViewById(R.id.clubName);
            description = itemView.findViewById(R.id.clubDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Club club = getClubByName(title.getText().toString());
                    if (isMember(user, club)) {
                        Intent intent = new Intent(getContext(), MemberClubView.class);
                        intent.putExtra(NonMemberClubView.CLUB_KEY, club);
                        startActivity(intent);
                    }
                }
            });
        }

        void bindTile(Club club) {
            title.setText(club.getName());
            description.setText(club.getDescription());
            Picasso.get().load(R.mipmap.stickman_foreground).into(image);
//            if (isValidURL(club.getImageUrl())) {
//                Picasso.get().load(club.getImageUrl()).into(image);
//            }
        }

    }


    private class RecycleViewAdapter extends RecyclerView.Adapter<HomeScreenMembership.ViewHolder> {

        private List<Club> clubs = new ArrayList<>();

        void addItems(List<Club> newClubs) {
            int startInsertPosition = clubs.size();
            clubs.addAll(newClubs);
            this.notifyItemRangeInserted(startInsertPosition, newClubs.size());
        }

        void addItem(Club club) {
            clubs.add(club);
            this.notifyItemInserted(clubs.size() - 1);
        }

        void setItems(List<Club> newClubs) {
            clubs = newClubs;
            this.notifyDataSetChanged();
        }

        void removeItem(Club club) {
            int position = clubs.indexOf(club);
            clubs.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(HomeScreenMembership.this.getContext());
            View view;

            if (viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.tile_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.tile_row, parent, false);
            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder followingHolder, int position) {
            followingHolder.bindTile(clubs.get(position));
        }


        @Override
        public int getItemCount() {
            return clubs.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == clubs.size() - 1) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
//            if (!presenter.isLoading()) {   // This guard is important for avoiding a race condition in the scrolling code.
//                presenter.loadMoreItems(user);
//            }

        }

        private void addLoadingFooter() {
            addItem(new Club("Dummy", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0 ,0 ));
        }

        private void removeLoadingFooter() {
            removeItem(clubs.get(clubs.size() - 1));
        }



    }


    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        RecyclerViewOnScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }


        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

//            if (!presenter.isLoading() && presenter.hasMorePages()) {
//                if ((visibleItemCount + firstVisibleItemPosition) >=
//                        totalItemCount && firstVisibleItemPosition >= 0) {
//                    // Run this code later on the UI thread
//                    final Handler handler = new Handler(Looper.getMainLooper());
//                    handler.postDelayed(() -> {
//                        followingRecyclerViewAdapter.loadMoreItems();
//                    }, 0);
//                }
//            }
        }
    }

}