package com.knickglobal.yeloopp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knickglobal.yeloopp.R;
import com.knickglobal.yeloopp.models.FriendsListPojo;

import java.util.List;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendsListViewHolder> {

    private Activity activity;
    private List<FriendsListPojo.FriendsList> friendsList;
    private SelectedUserListener listener;

    public FriendsListAdapter(Activity activity, List<FriendsListPojo.FriendsList> friendsList,
                              SelectedUserListener listener) {
        this.activity = activity;
        this.friendsList = friendsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity)
                .inflate(R.layout.item_friends_list, parent, false);

        return new FriendsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public interface SelectedUserListener {
        void selectedUser(String name);
    }

    class FriendsListViewHolder extends RecyclerView.ViewHolder {
        TextView textAutoComplete;

        FriendsListViewHolder(@NonNull View itemView) {
            super(itemView);
            textAutoComplete = itemView.findViewById(R.id.textAutoComplete);

            textAutoComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.selectedUser(friendsList.get(getAdapterPosition())
                            .getUserDetails().getName());
                }
            });
        }

        void bind(int position) {
            textAutoComplete.setText(friendsList.get(position).getUserDetails().getName());
        }

    }
}