package com.example.admin.appquanan;

import com.example.admin.appquanan.model.Comment;

import java.util.Comparator;

/**
 * Created by Admin on 5/17/2018.
 */

public class TimeComparator implements Comparator<Comment> {
    @Override
    public int compare(Comment o1, Comment o2) {
        if (Long.parseLong(o1.getTime()) == Long.parseLong(o2.getTime()))
            return 0;
        else if (Long.parseLong(o1.getTime()) > Long.parseLong(o2.getTime()))
            return 1;
        else
            return -1;
    }
}
