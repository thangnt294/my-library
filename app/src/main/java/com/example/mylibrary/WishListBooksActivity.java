package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;

import java.util.ArrayList;
import java.util.Objects;

public class WishListBooksActivity extends AppCompatActivity {

    private RecyclerView wishListBooksRecView;
    private BooksRecViewAdapter wishListBooksRecViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_books);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        wishListBooksRecView = findViewById(R.id.wishListBooksRecView);
        wishListBooksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.WishListBooksActivity);
        wishListBooksRecView.setAdapter(wishListBooksRecViewAdapter);
        wishListBooksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.WishListBooks);
        wishListBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.WishListBooks));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.fetchBooks(BookType.WishListBooks);
        wishListBooksRecViewAdapter.setBookList(Utils.getBookList(BookType.WishListBooks));
    }
}