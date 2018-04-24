package com.damao.tongxunlu.util;


import com.damao.tongxunlu.entity.FriendEntity;

import java.util.Comparator;

/**
 */

public class PinYinComparator implements Comparator<FriendEntity> {
    @Override
    public int compare(FriendEntity o1, FriendEntity o2) {
        if (o1.getPinyin().equals("#")){
            return 1;
        }else if (o2.getPinyin().equals("#")){
            return -1;
        }
        return o1.getPinyin().compareToIgnoreCase(o2.getPinyin());
    }
}
