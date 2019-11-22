package com.knickglobal.yeloopp.mvpArchitecture.followUsersApi;

import com.knickglobal.yeloopp.models.FollowUsersPojo;

public interface FollowUsersView {

    void showFollowUsersProgress();

    void hideFollowUsersProgress();

    void onFollowUsersSuccess(FollowUsersPojo body);

    void onFollowUsersError(String error);

    void noNetFollowUsers(String error);

}
