package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylibrary.constants.ActivityType;
import com.example.mylibrary.constants.BookType;

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
    private TextView editBookTitle;
    private ActivityType activityType;
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
        editBookTitle = findViewById(R.id.txt_edit_book_title);

        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);

        // Enable the back button on top of the screen
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            activityType = (ActivityType) intent.getSerializableExtra(ACTIVITY_TYPE);
            if (activityType == ActivityType.UpdateBookActivity) {
                editBookTitle.setText(R.string.edit_book);
                bookId = intent.getIntExtra(BookActivity.BOOK_ID_KEY, -1);
                if (bookId != -1) {
                    Book book = Utils.getBookById(bookId);
                    if (book != null) {
                        setBookData(book);
                    }
                }
            } else {
                activityType = ActivityType.AddBookActivity;
            }
        } else {
            activityType = ActivityType.AddBookActivity; // Default to add a book
        }

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
                Intent resultIntent = new Intent();
                long result;
                if (activityType == ActivityType.AddBookActivity) {
                    result = myDB.addData(new Book(newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc));
                } else {
                    result = myDB.updateData(new Book(bookId, newTitle, newAuthor, newPages, newImageUrl, newShortDesc, newLongDesc));
                    resultIntent.putExtra(BookActivity.BOOK_ID_KEY, bookId);
                }

                if (result == -1) {
                    setResult(2, resultIntent);
                } else {
                    Utils.fetchBooks(BookType.AllBooks);
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

    public void setBookData(Book book) {
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        pages.setText(Integer.toString(book.getPages()));
        imageUrl.setText(book.getImageUrl());
        shortDesc.setText(book.getShortDesc());
        longDesc.setText(book.getLongDesc());
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