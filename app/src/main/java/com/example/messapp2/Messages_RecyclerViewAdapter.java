package com.example.messapp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Messages_RecyclerViewAdapter extends RecyclerView.Adapter<Messages_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<MessageModel> messageModels;


    public Messages_RecyclerViewAdapter(Context context, ArrayList<MessageModel> messageModels){
        this.context = context;
        this.messageModels = messageModels;
    }

    public Messages_RecyclerViewAdapter(View view) {
    }

    @NonNull
    @Override
    public Messages_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row,parent,false);
        return new Messages_RecyclerViewAdapter.MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Messages_RecyclerViewAdapter.MyViewHolder holder, int position) {
        MessageModel messageModel = messageModels.get(position);

        holder.tvcontent.setText(messageModel.getMessageContent());
        holder.tvUserId.setText(messageModel.getTime());


        // Check if the user_id is "1240"
        if (MainActivity.userChecked() && "1234".equals(messageModel.getUserId())){
        //if ("1234".equals(messageModel.getUserId())) {
            // Set gravity to "end" for right alignment
            holder.linearLayout.setGravity(Gravity.END);
            // Optionally, change the background color for messages with user_id = "1234"
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.my_dark_primary));
            //holder.tvcontent.setTextColor(context.getResources().getColor(android.R.color.white));
        } else if (MainActivity.userChecked() && "1235".equals(messageModel.getUserId())){
            // Set gravity to "start" for left alignment (default)
            holder.linearLayout.setGravity(Gravity.START);
            // Reset the background color for other messages
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        } else if (!MainActivity.userChecked() && "1235".equals(messageModel.getUserId())){
            // Set gravity to "start" for left alignment (default)
            holder.linearLayout.setGravity(Gravity.END);
            // Reset the background color for other messages
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.my_dark_primary));
        } else if (!MainActivity.userChecked() && "1234".equals(messageModel.getUserId())){
            // Set gravity to "start" for left alignment (default)
            holder.linearLayout.setGravity(Gravity.START);
            // Reset the background color for other messages
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }
    }


    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvcontent;
        TextView tvUserId;

        LinearLayout linearLayout;

        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvcontent = itemView.findViewById(R.id.textView);
            tvUserId = itemView.findViewById(R.id.textView2);
            linearLayout = itemView.findViewById(R.id.linearLayout);
            cardView = itemView.findViewById(R.id.cardView2);
        }
    }
}
