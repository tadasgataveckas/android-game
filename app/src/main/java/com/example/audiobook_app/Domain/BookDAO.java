package com.example.audiobook_app.Domain;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface BookDAO  {

    @Insert
    long insert(Book book);

    @Insert
    void insertAll(List<Book> books);

    @Query("Delete from Book")
    void deleteAll();

    @Query("Select * from Book Order by id ASC")
    List<Book> getAllBooks();

    @Transaction
    @Query("SELECT * FROM Book WHERE id = :book_id")
    public BookWithChapters getBookWithChapters(int book_id);
}
