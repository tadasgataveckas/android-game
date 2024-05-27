package com.example.audiobook_app.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.Domain.Chapter;
import com.example.audiobook_app.Domain.ChapterProgress;
import com.example.audiobook_app.Domain.FavoriteChapter;
import com.example.audiobook_app.Domain.TimeFormatter;
import com.example.audiobook_app.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {
    private List<Chapter> chapters;
    private List<ChapterProgress> chapterProgresses;
    private List<FavoriteChapter> favChapters;

    private ClickListener<Chapter> clickListener;

    public ChapterAdapter(List<Chapter> chapters, List<ChapterProgress> chapterProgresses, List<FavoriteChapter> favChapters) {
        this.chapters = chapters;
        this.chapterProgresses = chapterProgresses;
        this.favChapters = favChapters;
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
        ChapterProgress chapterProgress = chapterProgresses.get(position);
        FavoriteChapter favChapter = null;
        for (FavoriteChapter favoriteChapter : favChapters) {
            if(chapter.getChapterId() == favoriteChapter.chapterId)
            {
                favChapter = favoriteChapter;
                break;
            }
        }

        if(favChapter != null)
        {
            holder.favoriteIcon.setVisibility(View.VISIBLE);
        }
        else {
            holder.favoriteIcon.setVisibility(View.INVISIBLE);
        }

        holder.chapterNumber.setText(chapter.getNumber());
        holder.chapterTitle.setText(chapter.getTitle());


        holder.readProgress.setText(TimeFormatter.formatTime(chapterProgress.lastReadTimestamp));
        if (chapterProgress.isCompleted) {
            holder.chapterItem.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }


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

        ConstraintLayout chapterItem;

        TextView chapterNumber;
        TextView chapterTitle;

        ImageView favoriteIcon;
        TextView readProgress;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterItem = itemView.findViewById(R.id.chapter_item);

            chapterNumber = itemView.findViewById(R.id.chapter_number);
            chapterTitle = itemView.findViewById(R.id.chapter_title);

            readProgress = itemView.findViewById(R.id.read_progress);

            favoriteIcon = itemView.findViewById(R.id.isFavorite);

        }
    }
}
