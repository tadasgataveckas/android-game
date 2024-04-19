package com.example.audiobook_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.Chapter;
import com.example.audiobook_app.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private List<Chapter> chapters;

    private ClickListener<Chapter> clickListener;

    public ChapterAdapter(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void setOnItemClickListener(ClickListener<Chapter> chapterClickListener) {
        this.clickListener = chapterClickListener;
    }

    // constructor and other methods

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        holder.chapterNumber.setText(String.valueOf(chapter.getNumber()));
        holder.chapterTitle.setText(chapter.getTitle());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) { // Check if position is valid
                    clickListener.onItemClick(chapters.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }

    static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView chapterNumber;
        TextView chapterTitle;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapter_number);
            chapterTitle = itemView.findViewById(R.id.chapter_title);
        }
    }
}
