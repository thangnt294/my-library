package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mylibrary.constants.ActivityType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecView;
    private BooksRecViewAdapter booksRecViewAdapter;
    private FloatingActionButton btnAddBook;

    private MyDatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        btnAddBook = findViewById(R.id.btn_add_book);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllBooksActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        booksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.AllBooksActivity);
        booksRecView = findViewById(R.id.booksRecView);
        booksRecView.setAdapter(booksRecViewAdapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        myDB = new MyDatabaseHelper(this);
        storeDataInArrays();
        booksRecViewAdapter.setBookList(Utils.getAllBooks());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    void storeDataInArrays() {
        Utils.resetAllBooks();
        Cursor cursor = myDB.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = Integer.parseInt(cursor.getString(3));
                String imageUrl = cursor.getString(4);
                String shortDesc = cursor.getString(5);
                String longDesc = cursor.getString(6);
                Book book = new Book(id, title, author, pages, imageUrl, shortDesc, longDesc);
                Utils.addToAllBooks(book);
            }
        }
    }
}