package com.example.admin.appquanan.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.appquanan.R;
import com.example.admin.appquanan.model.Food;
import com.example.admin.appquanan.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 10/19/2017.
 */

public class FoodAdapter extends ArrayAdapter<Food> {
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    private DatabaseReference mDatabase;
    private Context context;
    private int resource;
    private User user;
    private List<Food> arrFood;
    public FoodAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Food> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.arrFood=objects;

    }

    private boolean checkLove ;

    public void setUser(User user) {
        this.user = user;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_gridview,parent,false);
            viewHolder.imgFood = (ImageView) convertView.findViewById(R.id.imgFood);
            viewHolder.tvNameFood = (TextView) convertView.findViewById(R.id.tvNameFood);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            viewHolder.tvTotalLike = (TextView) convertView.findViewById(R.id.tvTotalLike);
            viewHolder.tvTotalCmt = (TextView) convertView.findViewById(R.id.tvTotalCmt);
            viewHolder.tvIconLike = (TextView) convertView.findViewById(R.id.tvIconLike);
            viewHolder.tvIconCmt = (TextView) convertView.findViewById(R.id.tvIconComment);
            viewHolder.tvFavorite = (TextView) convertView.findViewById(R.id.tvFavorite);
            if(user.getRoleId() == 3){
                viewHolder.tvFavorite.setVisibility(View.INVISIBLE);
            } else {
                viewHolder.tvFavorite.setVisibility(View.VISIBLE);
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Typeface face = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");


        Food food = arrFood.get(position);
        viewHolder.tvNameFood.setText(food.getNameFood());
        viewHolder.tvAddress.setText(food.getAddress());
        viewHolder.tvTotalLike.setText(food.getTotalLike()+"");
        viewHolder.tvTotalCmt.setText(food.getTotalCmt()+"");
        viewHolder.tvPrice.setText(food.getPrice() + "đ");
        Picasso.with(context).load(food.getImageFood()).into(viewHolder.imgFood);

        viewHolder.tvIconCmt.setTypeface(face);


        if(food.getCheckLike() == 1 && !(user.getRoleId() == 3)){
            viewHolder.tvIconLike.setTypeface(face);
            viewHolder.tvIconLike.setTextColor(ContextCompat.getColor(context,R.color.checklike));
        } else{
            viewHolder.tvIconLike.setTypeface(face);
            viewHolder.tvIconLike.setTextColor(ContextCompat.getColor(context,R.color.black2));
        }

        if(food.getLove() == 0) {
            checkLove = false;
        } else
            checkLove = true;

        viewHolder.tvFavorite.setTypeface(face);

        if(checkLove){
            viewHolder.tvFavorite.setText(R.string.fa_heart);
            viewHolder.tvFavorite.setTextColor(ContextCompat.getColor(context,R.color.red));
        } else{
            viewHolder.tvFavorite.setText(R.string.fa_heart_o);
            viewHolder.tvFavorite.setTextColor(ContextCompat.getColor(context,R.color.lovedefault));
        }

        viewHolder.tvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Food food = arrFood.get(position);
                    if(food.getLove()==0){
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("food").child(String.valueOf(food.getId()));
                        mDatabase.child("love").setValue(1);
                    } else {
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("food").child(String.valueOf(food.getId()));
                        mDatabase.child("love").setValue(0);
                    }
                }
        });

        return convertView;
    }


    public class ViewHolder{
        TextView tvFavorite;
        ImageView imgFood;
        TextView tvNameFood;
        TextView tvAddress;
        TextView tvPrice;
        TextView tvTotalLike;
        TextView tvTotalCmt;
        TextView tvIconCmt;
        TextView tvIconLike;
    }

}
