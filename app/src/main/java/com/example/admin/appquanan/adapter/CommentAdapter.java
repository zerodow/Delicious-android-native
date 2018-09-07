package com.example.admin.appquanan.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.model.Comment;
import com.example.admin.appquanan.model.Food;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by Admin on 10/23/2017.
 */

public class CommentAdapter extends ArrayAdapter {
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    private Context context;
    private int resource;
    private List<Comment> arrCmt;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrCmt = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_cmt, parent, false);
            viewHolder.tvUserCmt = (TextView) convertView.findViewById(R.id.tvUserCmt);
            viewHolder.tvContentCmt = (TextView) convertView.findViewById(R.id.tvContentCmt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommentAdapter.ViewHolder) convertView.getTag();
        }

        Comment cmt = arrCmt.get(position);
        viewHolder.tvUserCmt.setText(user.getNickName());
        viewHolder.tvContentCmt.setText(cmt.getContent());

        return convertView;
    }

    public class ViewHolder {
        TextView tvContentCmt;
        TextView tvUserCmt;
    }
}
