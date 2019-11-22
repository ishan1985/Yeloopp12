package com.knickglobal.yeloopp.mvpArchitecture.getFriendsApi;

import com.knickglobal.yeloopp.models.FriendsListPojo;

public interface GetFriendsView {

    void showGetFriendsProgress();

    void hideGetFriendsProgress();

    void onGetFriendsSuccess(FriendsListPojo body);

    void onGetFriendsError(String error);

    void noNetGetFriends(String error);

}
