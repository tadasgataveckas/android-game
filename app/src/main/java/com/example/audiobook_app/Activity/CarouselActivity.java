package com.example.audiobook_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.audiobook_app.Adapter.BooksAdapter;
import com.example.audiobook_app.Adapter.ClickListener;
import com.example.audiobook_app.Domain.Book;
import com.example.audiobook_app.R;

import java.util.ArrayList;

/**
 * Example activity to use the carousel
 */
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
        ArrayList<Book> items = new ArrayList<>();
        items.add(new Book("Soul", "Olivia Wilson", "", "@drawable/b1"));
        items.add(new Book("Harry Potter", "J.K. Rowling", "","@drawable/b2"));
        items.add(new Book("A Million To One", "Tony Faggioli", "","@drawable/b3"));
        items.add(new Book("Educated", "Tara Westover", "","@drawable/b4"));

        recyclerViewBooks =findViewById(R.id.view1);
        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterBookList = new BooksAdapter(items);


        ((BooksAdapter) adapterBookList).setOnItemClickListener(new ClickListener<Book>(){
            @Override
            public void onItemClick(Book data) {
                Toast.makeText(CarouselActivity.this, data.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewBooks.setAdapter(adapterBookList);
    }
}