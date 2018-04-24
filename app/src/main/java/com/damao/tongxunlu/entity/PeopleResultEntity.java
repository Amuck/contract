package com.damao.tongxunlu.entity;

import java.util.ArrayList;

/**
 * Created by chenlong on 2018/3/8.
 *
 */

public class PeopleResultEntity extends BaseEntity{
    private ArrayList<FriendEntity> friends = new ArrayList<>();

    public ArrayList<FriendEntity> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<FriendEntity> friends) {
        this.friends = friends;
    }
}
