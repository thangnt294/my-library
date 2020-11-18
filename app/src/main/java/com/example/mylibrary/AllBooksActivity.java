package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mylibrary.constants.ActivityType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecView;
    private BooksRecViewAdapter booksRecViewAdapter;
    private FloatingActionButton btnAddBook;

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

        booksRecViewAdapter.setBookList(Utils.getAllBooks());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}