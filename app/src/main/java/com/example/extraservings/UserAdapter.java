package com.example.extraservings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList donation_id, donar_address, food_type, quantity_serves;

    UserAdapter(Activity activity, Context context, ArrayList donation_id, ArrayList donar_address, ArrayList food_type, ArrayList quantity_serves) {
        this.activity = activity;
        this.context = context;
        this.donation_id = donation_id;
        this.donar_address = donar_address;
        this.food_type = food_type;
        this.quantity_serves = quantity_serves;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.status_row, parent, false);

        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.donar_id_txt.setText(String.valueOf(donation_id.get(position)));
        holder.donar_address_txt.setText(String.valueOf(donar_address.get(position)));
        holder.foodType_txt.setText(String.valueOf(food_type.get(position)));
        holder.quantityServes_txt.setText(String.valueOf(quantity_serves.get(position)));
    }

    @Override
    public int getItemCount() {
        return donation_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView donar_id_txt, donar_address_txt, foodType_txt, quantityServes_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            donar_id_txt = itemView.findViewById(R.id.donate_id_txt);
            donar_address_txt = itemView.findViewById(R.id.address_txt);
            foodType_txt = itemView.findViewById(R.id.foodType_txt);
            quantityServes_txt = itemView.findViewById(R.id.quantity_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}