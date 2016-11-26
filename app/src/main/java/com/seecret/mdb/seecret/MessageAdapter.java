package com.seecret.mdb.seecret;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kedarthakkar on 11/20/16.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    private static final int VIEW_TYPE_EMPTY_LIST_PLACEHOLDER = 0;
    private static final int VIEW_TYPE_OBJECT_VIEW = 1;
    private Context context;
    private ArrayList<Message> messageList;

    public MessageAdapter(Context context, ArrayList<Message> messages){
        this.context = context;
        messageList = messages;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.isEmpty()) {
            return VIEW_TYPE_EMPTY_LIST_PLACEHOLDER;
        } else {
            return VIEW_TYPE_OBJECT_VIEW;
        }
    }

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = null;
        switch(viewType) {
            case VIEW_TYPE_EMPTY_LIST_PLACEHOLDER:
                LinearLayout layout = new LinearLayout(parent.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextView emptyMessage = new TextView(parent.getContext());
                emptyMessage.setText("No unseen messages");
                emptyMessage.setTypeface(null, Typeface.ITALIC);
                emptyMessage.setGravity(View.TEXT_ALIGNMENT_CENTER);
                emptyMessage.setTextSize(20);
                layout.addView(emptyMessage);
                view = layout;
                break;
            case VIEW_TYPE_OBJECT_VIEW:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
                break;
        }
        return new CustomViewHolder(view);
    }

    public void onBindViewHolder(CustomViewHolder holder, int position){
        if (!messageList.isEmpty()) {
            //cardview background color: light shades of blue
            if(position%2!=0){
                holder.card.setCardBackgroundColor(Color.parseColor("#ccccff"));
            }
            else{
                holder.card.setCardBackgroundColor(Color.parseColor("#e6e6ff"));
            }

            Message currMessage = messageList.get(position);

            holder.name.setText(currMessage.getName());
            holder.lastMessage.setText(currMessage.getLastMessage());
            holder.time.setText(currMessage.getTime());
            holder.imageView.setImageBitmap(currMessage.getBitmap());

            holder.setTag(currMessage.getTag());
        }
    }

    public void setMessages(ArrayList<Message> messages) {messageList = messages;}

    public int getItemCount(){
        if (messageList.size() == 0) {return 1;}
        return messageList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;
        TextView lastMessage;
        TextView time;
        String tag;
        CardView card;

        public void setTag(String tag) {this.tag = tag;}

        public String getTag() {return tag;}

        public CustomViewHolder (View view){

            super(view);
            this.name = (TextView) (view.findViewById(R.id.name));
            this.lastMessage = (TextView) (view.findViewById(R.id.last_message));
            this.time = (TextView) (view.findViewById(R.id.time));
            this.imageView = (ImageView) (view.findViewById(R.id.profile_pic));
            this.card = (CardView) (view.findViewById(R.id.cardview));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TextActivity.class);
                    intent.putExtra("table name", getTag());
                    intent.putExtra("conversation name", name.getText());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

}
