package com.freedommuskrats.clubbub.ui.club;

import static com.freedommuskrats.clubbub.domain.FakeData.defaultPerson;
import static com.freedommuskrats.clubbub.domain.FakeData.getAnnouncements;
import static com.freedommuskrats.clubbub.domain.FakeData.getChat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freedommuskrats.clubbub.R;
import com.freedommuskrats.clubbub.domain.Club;
import com.freedommuskrats.clubbub.domain.Person;
import com.freedommuskrats.clubbub.domain.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementChatFragment extends Fragment {

    public static final int ANNOUNCEMENT = 0;
    public static final int CHAT = 1;
    private static final String CLUB_KEY = "CLUB";

    private int announcementOrChat = 1;
    private boolean isOwner;
    private Person user;
    private RecycleViewAdapter adapter;
    private List<Post> posts;

    public AnnouncementChatFragment(int announcementOrChat, boolean isOwner) {
        this.announcementOrChat = announcementOrChat;
        this.isOwner = isOwner;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        user = defaultPerson(); // TODO pass in after login

        View root = inflater.inflate(R.layout.fragment_announcements_or_chat, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.announcementChatRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecycleViewAdapter();
        recyclerView.setAdapter(adapter);


        if (this.announcementOrChat == ANNOUNCEMENT) {
            posts = getAnnouncements();
        } else {
            posts = getChat();
        }

        adapter.addItems(posts);
        scrollToEnd(recyclerView, posts.size() - 1);

        EditText message = root.findViewById(R.id.message);

        if (announcementOrChat == ANNOUNCEMENT && !isOwner) {
            LinearLayout messageBox = root.findViewById(R.id.messageBox);
            messageBox.setEnabled(false);
            messageBox.setVisibility(View.INVISIBLE);
        }



        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollToEnd(recyclerView, posts.size() - 1);
            }
        });

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mes = message.getText().toString();
                if (mes.length() > 0) {
                    if (mes.charAt(mes.length() - 1) == '\n') {
                        posts.add(new Post(user.getFirstName(), message.getText().toString()));
                        adapter.setItems(posts);
                        message.setText("");
                        scrollToEnd(recyclerView, posts.size() - 1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return root;
    }

    private void scrollToEnd(RecyclerView recyclerView, int endIndex) {
        if (endIndex > 0) {
            recyclerView.smoothScrollToPosition(endIndex);
        }
    }

    private void removeItemFromRecycler(Post post) {
        adapter.removeItem(post);
        posts.remove(post);
    }
    

    private class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView personName;
        private final TextView messageText;
        private final ImageView deleteMessage;
        private Post thisPost;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            personName = itemView.findViewById(R.id.messagePerson);
            messageText = itemView.findViewById(R.id.messageText);
            deleteMessage = itemView.findViewById(R.id.deleteMessageIcon);

            if (isOwner) {
                deleteMessage.setEnabled(true);
                deleteMessage.setVisibility(View.VISIBLE);
                deleteMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeItemFromRecycler(thisPost);
                    }
                });
            }

        }

        void bindTile(Post post) {
            thisPost = post;
            personName.setText(post.getPersonName());
            messageText.setText(post.getText());


        }

    }


    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private class RecycleViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Post> posts = new ArrayList<>();

        void addItems(List<Post> newPosts) {
            int startInsertPosition = posts.size();
            posts.addAll(newPosts);
            this.notifyItemRangeInserted(startInsertPosition, newPosts.size());
        }

        void addItem(Post post) {
            posts.add(post);
            this.notifyItemInserted(posts.size() - 1);
        }

        void setItems(List<Post> newPosts) {
            posts = newPosts;
            this.notifyDataSetChanged();
        }

        void removeItem(Post post) {
            int position = posts.indexOf(post);
            posts.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            View view;

            if (viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.message_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.message_row, parent, false);
            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder followingHolder, int position) {
            followingHolder.bindTile(posts.get(position));
        }


        @Override
        public int getItemCount() {
            return posts.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == posts.size() - 1) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
//            if (!presenter.isLoading()) {   // This guard is important for avoiding a race condition in the scrolling code.
//                presenter.loadMoreItems(user);
//            }

        }

        private void addLoadingFooter() {
            addItem(new Post("Person", "message"));
        }

        private void removeLoadingFooter() {
            removeItem(posts.get(posts.size() - 1));
        }



    }




}
