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
import com.example.audiobook_app.Domain.BooksDomain;
import com.example.audiobook_app.R;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.Viewholder> {

    ArrayList<BooksDomain> items;
    Context context;

    public BooksAdapter(ArrayList<BooksDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public BooksAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_book_list, parent, false);
        context = parent.getContext();

        return new Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.Viewholder holder, int position) {
        holder.title.setText(items.get(position).getTitle());
        holder.author.setText(items.get(position).getAuthor());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicAddress(), "drawble", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);

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
