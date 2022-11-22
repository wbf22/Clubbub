package com.freedommuskrats.clubbub.ui.club;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NonMemberClubViewFragment extends Fragment {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private Club club;

    public NonMemberClubViewFragment(Club club) {
        this.club = club;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_member_club_view, container, false);

        TextView clubName = view.findViewById(R.id.nonMemberClubViewTitle);
        clubName.setText(club.getName());
        TextView description = view.findViewById(R.id.nonMemberClubViewDescription);
        description.setText(club.getDescription());
        ImageView clubImage = view.findViewById(R.id.nonMemberClubViewImageView);
        Picasso.get().load(R.mipmap.stickman_foreground).into(clubImage);

        RecyclerView recyclerView = view.findViewById(R.id.nonMemberclubViewRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        RecycleViewAdapter adapter = new RecycleViewAdapter();
        recyclerView.setAdapter(adapter);

        int i = 0;
        List<List<String>> doubledImageUrls = new ArrayList<>();
        while (i + 1 < club.getImageUrls().size()) {
            doubledImageUrls.add(Arrays.asList(club.getImageUrls().get(i), club.getImageUrls().get(i+1) ));
            i++;
        }

        adapter.addItems(doubledImageUrls);

        return view;
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image1;
        private final ImageView image2;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            image1 = itemView.findViewById(R.id.club_image1);
            image2 = itemView.findViewById(R.id.club_image2);

        }

        void bindTile(List<String> imageLocations) {
            if (imageLocations.get(0) == null) {
                Picasso.get().load(R.mipmap.stickman_foreground).into(image1);
            } else {

            }

            if (imageLocations.get(1) == null) {
                Picasso.get().load(R.mipmap.stickman_foreground).into(image2);
            } else {

            }

//            if (isValidURL(club.getImageUrl())) {
//                Picasso.get().load(club.getImageUrl()).into(image);
//            }
        }

    }


    private class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<List<String>> imageUrls = new ArrayList<>();

        void addItems(List<List<String>> newClubs) {
            int startInsertPosition = imageUrls.size();
            imageUrls.addAll(newClubs);
            this.notifyItemRangeInserted(startInsertPosition, newClubs.size());
        }

        void addItem(List<String> url) {
            imageUrls.add(url);
            this.notifyItemInserted(imageUrls.size() - 1);
        }

        void setItems(List<List<String>> newUrls) {
            imageUrls = newUrls;
            this.notifyDataSetChanged();
        }

        void removeItem(List<String> url) {
            int position = imageUrls.indexOf(url);
            imageUrls.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view;

            if (viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.double_image_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.double_image_row, parent, false);
            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder followingHolder, int position) {
            followingHolder.bindTile(imageUrls.get(position));
        }


        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == imageUrls.size() - 1) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
//            if (!presenter.isLoading()) {   // This guard is important for avoiding a race condition in the scrolling code.
//                presenter.loadMoreItems(user);
//            }

        }

        private void addLoadingFooter() {
            addItem(Collections.singletonList("https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
        }

        private void removeLoadingFooter() {
            removeItem(imageUrls.get(imageUrls.size() - 1));
        }



    }


}