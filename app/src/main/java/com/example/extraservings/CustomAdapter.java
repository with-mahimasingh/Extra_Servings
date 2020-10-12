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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList donation_id, donar_address, food_type, quantity_serves;
    public OnItemClickListener mListener;
    public MyDatabaseHelper dbHelper;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }


    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    CustomAdapter(Activity activity, Context context, ArrayList donation_id, ArrayList donar_address, ArrayList food_type, ArrayList quantity_serves) {
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
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {


        holder.donar_id_txt.setText(String.valueOf(donation_id.get(position)));
        holder.donar_address_txt.setText(String.valueOf(donar_address.get(position)));
        holder.foodType_txt.setText(String.valueOf(food_type.get(position)));
        holder.quantityServes_txt.setText(String.valueOf(quantity_serves.get(position)));


        //Recyclerview onClickListener
       holder.request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check whether dbHelper needs to be initialized or nt...
                boolean isUpdated=dbHelper.updateToBooked(donation_id.get(position);
                if(isUpdated)
                    Toast.makeText(context, donation_id,get(position)+" booked", Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(context,"Request Sent!", Toast.LENGTH_SHORT).show();
                
                Intent intent = new Intent(view.getContext(), UserActivity.class);
                //intent.putExtra("id", String.valueOf(donation_id.get(position)));
                //intent.putExtra("address", String.valueOf(donar_address.get(position)));
                //intent.putExtra("quantity", String.valueOf(quantity_serves.get(position)));
                //intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1);
                view.getContext().startActivity(intent);
                
        });

       /* watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isAdded=dbManager.addToWatchlist(companyToSave);
                if(isAdded)
                    Toast.makeText(getApplicationContext(), StocksAdapter.companyName+" added to Watchlist", Toast.LENGTH_SHORT).show();

            }
        });*/
    }



    @Override
    public int getItemCount() {
        return donation_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView donar_id_txt, donar_address_txt, foodType_txt, quantityServes_txt;
        LinearLayout mainLayout;
        Button request_button;

        int i= getAdapterPosition();

        MyViewHolder(@NonNull View itemView,final OnItemClickListener mlistener) {
            super(itemView);
            donar_id_txt = itemView.findViewById(R.id.donate_id_txt);
            donar_address_txt = itemView.findViewById(R.id.address_txt);
            foodType_txt = itemView.findViewById(R.id.foodType_txt);
            quantityServes_txt = itemView.findViewById(R.id.quantity_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            request_button= itemView.findViewById(R.id.btn_request);
            //Animate Recyclerview
            //  Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //mainLayout.setAnimation(translate_anim);
            request_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

    }

}
