package com.freedommuskrats.clubbub.ui.dashboard;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;
import static com.freedommuskrats.clubbub.domain.FakeData.filterResults;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubByName;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubsFilterBySearch;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubsLimitedByDistance;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakeClubs;
import static com.freedommuskrats.clubbub.domain.FakeData.isMember;
import static com.freedommuskrats.clubbub.domain.FakeData.isOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.databinding.FragmentSearchBinding;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.MemberClubView;
import com.freedommuskrats.clubbub.ui.club.NonMemberClubView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {

    private FragmentSearchBinding binding;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private Person user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        user = defaultPerson(); // TODO pass in after login

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.searchRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.addItems(getFakeClubs());

        SearchView searchView = root.findViewById(R.id.searchView);

        SeekBar seekBar = root.findViewById(R.id.seekBar);
        TextView distVal = root.findViewById(R.id.distVal);
        distVal.setText(String.valueOf(seekBar.getProgress()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Club> limited = filterResults(getFakeClubs(), searchView.getQuery().toString(), seekBar.getProgress(), user);
                adapter.setItems(limited);
                return false;
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                List<Club> limited = filterResults(getFakeClubs(), searchView.getQuery().toString(), seekBar.getProgress(), user);
                adapter.setItems(limited);
                distVal.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView description;

        /**
         * Creates an instance and sets an OnClickListener for the club's row.
         *
         * @param itemView the view on which the club will be displayed.
         */
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
                    } else if (isOwner(user, club)) {
                        Intent intent = new Intent(getContext(), MemberClubView.class);
                        intent.putExtra(NonMemberClubView.CLUB_KEY, club);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), NonMemberClubView.class);
                        intent.putExtra(NonMemberClubView.CLUB_KEY, club);
                        startActivity(intent);
                    }
                }
            });
        }

        /**
         * Binds the club's data to the view.
         *
         * @param club the club.
         */
        void bindTile(Club club) {
            title.setText(club.getName());
            description.setText(club.getDescription());


            Picasso.get().load(R.mipmap.stickman_foreground).into(image);
//            if (isValidURL(club.getImageUrl())) {
//                Picasso.get().load(club.getImageUrl()).into(image);
//            }
        }

    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Club> clubs = new ArrayList<>();

        /**
         * Adds new clubs to the list from which the RecyclerView retrieves the clubs it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newClubs the clubs to add.
         */
        void addItems(List<Club> newClubs) {
            int startInsertPosition = clubs.size();
            clubs.addAll(newClubs);
            this.notifyItemRangeInserted(startInsertPosition, newClubs.size());
        }

        /**
         * Adds a single club to the list from which the RecyclerView retrieves the clubs it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param club the club to add.
         */
        void addItem(Club club) {
            clubs.add(club);
            this.notifyItemInserted(clubs.size() - 1);
        }

        void setItems(List<Club> newClubs) {
            clubs = newClubs;
            this.notifyDataSetChanged();
        }

        /**
         * Removes a club from the list from which the RecyclerView retrieves the clubs it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param club the club to remove.
         */
        void removeItem(Club club) {
            int position = clubs.indexOf(club);
            clubs.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         * Creates a view holder for a followee to be displayed in the RecyclerView or for a message
         * indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent   the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(DashboardFragment.this.getContext());
            View view;

            if (viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.tile_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.tile_row, parent, false);
            }

            return new ViewHolder(view);
        }

        /**
         * Binds the followee at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param followingHolder the ViewHolder to which the followee should be bound.
         * @param position        the position (in the list of followees) that contains the followee to be
         *                        bound.
         */
        @Override
        public void onBindViewHolder(@NonNull ViewHolder followingHolder, int position) {
            followingHolder.bindTile(clubs.get(position));
        }

        /**
         * Returns the current number of followees available for display.
         *
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return clubs.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == clubs.size() - 1) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
//            if (!presenter.isLoading()) {   // This guard is important for avoiding a race condition in the scrolling code.
//                presenter.loadMoreItems(user);
//            }

        }

        /**
         * Adds a dummy club to the list of clubs so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new Club("Dummy", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png", 0 ,0 ));
        }

        /**
         * Removes the dummy club from the list of clubs so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(clubs.get(clubs.size() - 1));
        }



    }

    /**
     * A scroll listener that detects when the club has scrolled to the bottom of the currently
     * available data.
     */
    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        RecyclerViewOnScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the club has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx           the amount of horizontal scroll.
         * @param dy           the amount of vertical scroll.
         */
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


    public static boolean isValidURL(String url) {

        /* Try creating a valid URL */
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}