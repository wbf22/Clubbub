package com.freedommuskrats.clubbub.ui.search;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;
import static com.freedommuskrats.clubbub.domain.FakeData.filterPersonResults;
import static com.freedommuskrats.clubbub.domain.FakeData.filterResults;
import static com.freedommuskrats.clubbub.domain.FakeData.getClubByName;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakeClubs;
import static com.freedommuskrats.clubbub.domain.FakeData.getFakePeople;
import static com.freedommuskrats.clubbub.domain.FakeData.isMember;
import static com.freedommuskrats.clubbub.domain.FakeData.isOwner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.ui.club.owner.OwnerClubViewContainer;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class SearchPeopleFragment extends Fragment {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;



    private Club club;
    private Person user;

    public SearchPeopleFragment(Club club) {
        this.club = club;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = defaultPerson(); // TODO pass in after login

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_items, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.searchRecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setItems(getFakePeople());

        SeekBar seekBar = view.findViewById(R.id.seekBar);
        TextView distVal = view.findViewById(R.id.distVal);
        distVal.setText(String.valueOf(seekBar.getProgress()));

        EditText searcher = view.findViewById(R.id.editTextTextPersonName);

        searcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Person> limited = filterPersonResults(getFakePeople(), searcher.getText().toString(), seekBar.getProgress(), user);
                adapter.setItems(limited);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                List<Person> limited = filterPersonResults(getFakePeople(), searcher.getText().toString(), seekBar.getProgress(), user);
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

        return view;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView title;
        private final TextView description;
        private final TextView memberSince;

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
            memberSince = itemView.findViewById(R.id.clubMemberSince);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        /**
         * Binds the person's data to the view.
         *
         * @param person the person.
         */
        void bindTile(Person person) {
            title.setText(person.getFirstName());
            List<Club> clubs = person.getClubsMemberOf();
            String desc = "";
            for (Club c : clubs) {
                desc += c.getName() + "\n";
            }
            description.setText(desc);

            Picasso.get().load(R.mipmap.stickman_foreground).into(image);
        }

    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Person> people = new ArrayList<>();

        /**
         * Adds new clubs to the list from which the RecyclerView retrieves the clubs it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param people the clubs to add.
         */
        void addItems(List<Person> people) {
            int startInsertPosition = this.people.size();
            this.people.addAll(people);
            this.notifyItemRangeInserted(startInsertPosition, people.size());
        }

        /**
         * Adds a single person to the list from which the RecyclerView retrieves the clubs it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param person the person to add.
         */
        void addItem(Person person) {
            people.add(person);
            this.notifyItemInserted(people.size() - 1);
        }

        void setItems(List<Person> newClubs) {
            people = newClubs;
            this.notifyDataSetChanged();
        }

        /**
         * Removes a person from the list from which the RecyclerView retrieves the clubs it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param person the person to remove.
         */
        void removeItem(Person person) {
            int position = people.indexOf(person);
            people.remove(position);
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
            LayoutInflater layoutInflater = LayoutInflater.from(SearchPeopleFragment.this.getContext());
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
            followingHolder.bindTile(people.get(position));
        }

        /**
         * Returns the current number of followees available for display.
         *
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return people.size();
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
            return (position == people.size() - 1) ? LOADING_DATA_VIEW : ITEM_VIEW;
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
            addItem(new Person(UUID.randomUUID(), "Bob", "bob", 0, 0));
        }

        /**
         * Removes the dummy club from the list of clubs so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(people.get(people.size() - 1));
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