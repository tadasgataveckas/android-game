package com.example.audiobook_app.Domain;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDAO  {

    @Insert
    void insert(Book book);

    @Query("Delete from Book")
    void deleteAll();

    @Query("Select * from Book Order by id ASC")
    List<Book> getAllBooks();
}
