package com.seecret.mdb.seecret;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by leonk7 on 11/20/16.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.CustomViewHolder> {

    private Context context;
    public ArrayList<Text> textList;

    public TextAdapter(Context context, ArrayList<Text> messages){
        this.context = context;
        this.textList = messages;
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_row_view, parent, false);
        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(final CustomViewHolder holder, int position){

        Text currMsg = textList.get(position);

        holder.message.setText(currMsg.getMessage());
        holder.timestamp.setText(currMsg.getTimestamp());
    }

    public int getItemCount(){
        return textList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        TextView timestamp;


        public CustomViewHolder(View view){
            super(view);

            this.message = (TextView) view.findViewById(R.id.msg);
            this.timestamp = (TextView) view.findViewById(R.id.timestamp);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}