package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;
import com.example.mylibrary.constants.Genre;
import com.example.mylibrary.utils.SpinnerUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecView;
    private BooksRecViewAdapter booksRecViewAdapter;
    private Spinner allBooksGenreSpinner;
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
                Intent intent = new Intent(AllBooksActivity.this, EditBookActivity.class);
                intent.putExtra(EditBookActivity.ACTIVITY_TYPE, ActivityType.AddBookActivity);
                startActivityForResult(intent, 1);
            }
        });

        allBooksGenreSpinner = (Spinner) findViewById(R.id.allBooksGenreSpinner);
        ArrayList<String> genres = SpinnerUtils.getGenres();
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        allBooksGenreSpinner.setAdapter(genresAdapter);

        allBooksGenreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.fetchBooks(BookType.AllBooks, Genre.valueOf(TextUtils.join("_", genres.get(position).toUpperCase().split(" "))));
                booksRecViewAdapter.setBookList(Utils.getBookList(BookType.AllBooks));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        booksRecViewAdapter = new BooksRecViewAdapter(this, ActivityType.AllBooksActivity);
        booksRecView = findViewById(R.id.booksRecView);
        booksRecView.setAdapter(booksRecViewAdapter);
        booksRecView.setLayoutManager(new LinearLayoutManager(this));

        Utils.fetchBooks(BookType.AllBooks, Genre.ALL);
        booksRecViewAdapter.setBookList(Utils.getBookList(BookType.AllBooks));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                recreate();
                Toast.makeText(this, R.string.add_success, Toast.LENGTH_SHORT).show();
            } else if (resultCode == 2) {
                Toast.makeText(this, R.string.general_error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.fetchBooks(BookType.AllBooks, Genre.ALL);
        booksRecViewAdapter.setBookList(Utils.getBookList(BookType.AllBooks));
        allBooksGenreSpinner.setSelection(0);
    }
}