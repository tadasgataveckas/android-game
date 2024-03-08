package com.example.audiobook_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.audiobook_app.Adapter.BooksAdapter;
import com.example.audiobook_app.Domain.BooksDomain;
import com.example.audiobook_app.R;

import java.util.ArrayList;

public class CarouselActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterBookList;
    private RecyclerView recyclerViewBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<BooksDomain> items = new ArrayList<>();
        items.add(new BooksDomain("Soul", "Olivia Wilson", "@drawable/b1"));
        items.add(new BooksDomain("Harry Potter", "J.K. Rowling", "@drawable/b2"));
        items.add(new BooksDomain("A Million To One", "Tony Faggioli", "@drawable/b3"));
        items.add(new BooksDomain("Educated", "Tara Westover", "@drawable/b4"));

        recyclerViewBooks =findViewById(R.id.view1);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterBookList = new BooksAdapter(items);
        recyclerViewBooks.setAdapter(adapterBookList);
    }
}