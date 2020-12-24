package com.example.myLibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myLibrary.constants.ActivityType;
import com.example.myLibrary.constants.BookType;
import com.example.myLibrary.constants.Genre;
import com.example.myLibrary.utils.SpinnerUtils;

import java.util.ArrayList;
import java.util.Objects;

public class EditBookActivity extends AppCompatActivity {

    public static final String ACTIVITY_TYPE = "Activity Type";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String PAGES = "pages";
    public static final String IMAGE_URL = "imageUrl";
    public static final String SHORT_DESC = "shortDesc";
    public static final String LONG_DESC = "longDesc";

    private EditText title, author, pages, imageUrl, shortDesc, longDesc;
    private TextView genre;
    private ActivityType activityType;
    private Spinner genreSpinner;
    private Button btnConfirm, btnCancel;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        title = findViewById(R.id.txt_book_title);
        author = findViewById(R.id.txt_book_author);
        pages = findViewById(R.id.txt_book_pages);
        imageUrl = findViewById(R.id.txt_book_image_url);
        shortDesc = findViewById(R.id.txt_book_short_desc);
        longDesc = findViewById(R.id.txt_book_long_desc);
        genre = findViewById(R.id.txt_genre);
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        TextView editBookTitle = findViewById(R.id.txt_edit_book_title);

        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Set spinner values
        ArrayList<String> genres = SpinnerUtils.getGenres();
        genres.remove(Genre.ALL.getValue());
        ArrayAdapter<String> genresAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, genres);
        genreSpinner.setAdapter(genresAdapter);


        Intent intent = getIntent();
        if (intent != null) {
            activityType = (ActivityType) intent.getSerializableExtra(ACTIVITY_TYPE);
            if (activityType == ActivityType.UpdateBookActivity) {
                // Change layout
                editBookTitle.setText(R.string.edit_book);

                bookId = intent.getIntExtra(BookActivity.BOOK_ID_KEY, -1);
                if (bookId != -1) {
                    Book book = Utils.getBookById(bookId);
                    if (book != null) {
                        int genrePosition = genresAdapter.getPosition(book.getGenre());
                        setBookData(book, genrePosition);
                    }
                }
            } else {
                activityType = ActivityType.AddBookActivity;
            }
        } else {
            activityType = ActivityType.AddBookActivity; // Default to add a book
        }

        handleButton();

    }

    public void setBookData(Book book, int genrePosition) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(Integer.toString(book.getPages()));
        imageUrl.setText(book.getImageUrl());
        shortDesc.setText(book.getShortDesc());
        longDesc.setText(book.getLongDesc());
        genreSpinner.setSelection(genrePosition);
    }

    public void handleButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditBookActivity.this);
                String newTitle = title.getText().toString().trim();
                String newAuthor = author.getText().toString().trim();
                int newPages = Integer.parseInt(pages.getText().toString().trim());
                String newImageUrl = imageUrl.getText().toString().trim();
                String newShortDesc = shortDesc.getText().toString().trim();
                String newLongDesc = longDesc.getText().toString().trim();
                String newGenre = genreSpinner.getSelectedItem().toString();
                Intent resultIntent = new Intent();
                long result;
                if (activityType == ActivityType.AddBookActivity) {
                    result = myDB.addData(new Book(newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc, newGenre));
                } else {
                    result = myDB.updateData(new Book(bookId, newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc, newGenre));
                    resultIntent.putExtra(BookActivity.BOOK_ID_KEY, bookId);
                }

                if (result == -1) {
                    setResult(2, resultIntent);
                } else {
                    Utils.fetchBooks(BookType.AllBooks, Genre.ALL);
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
        super.onBackPressed();
    }
}