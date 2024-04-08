package com.example.audiobook_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.R;

import java.util.ArrayList;


/**
 * Translates Books domain data to view activity
 */
public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Viewholder> {

    ArrayList<Book> items;
    Context context;
    private ClickListener<Book> clickListener;

    public BooksAdapter(ArrayList<Book> items) {
        this.items = items;
    }
    public void setOnItemClickListener(ClickListener<Book> bookClickListener) {
        this.clickListener = bookClickListener;
    }
    @NonNull
    @Override
    public BooksAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book_list, parent, false);
        context = parent.getContext();

        return new Viewholder(inflator);
    }

    //Using Glide api to load images from drawable for smooth scrolling
    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.Viewholder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.author.setText(items.get(position).getAuthor());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicAddress(), "drawble", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);


        // Set the OnClickListener here
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) { // Check if position is valid
                    clickListener.onItemClick(items.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            author = itemView.findViewById(R.id.authorTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
