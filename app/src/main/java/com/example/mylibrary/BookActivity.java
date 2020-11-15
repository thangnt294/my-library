package com.example.mylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookNameInfo, txtAuthorNameInfo, txtPagesInfo, txtLongDescInfo;
    private Button btnAddToWishListBooks, btnAddToReadingBooks, btnAddToFinishedBooks, btnAddToFavoriteBooks;
    private ImageView imgBookInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initViews();

        // Get the book from BooksRecViewAdapter
        int bookId = -1;
        Intent intent = getIntent();
        if (intent != null) {
            bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
        }

        if (bookId != -1) {
            Book book = Utils.getBookById(bookId);
            if (book != null) {
                setData(book);

                handleFinishedBooks(book);
                handleWishListBooks(book);
                handleReadingBooks(book);
                handleFavoriteBooks(book);
            }
        }
    }

    /**
     * Disable the "Add to finished books" button if the book is already finished
     * Add the book to finished books if not
     * @param book The current book
     */
    private void handleFinishedBooks(final Book book) {
        ArrayList<Book> finishedBooks = Utils.getFinishedBooks();

        AtomicBoolean existInFinishedBooks = new AtomicBoolean(false);

        for (Book finishedBook : finishedBooks) {
            if (finishedBook.getId() == book.getId()) {
                existInFinishedBooks.set(true);
                break;
            }
        }

        if (existInFinishedBooks.get()) {
            btnAddToFinishedBooks.setEnabled(false);
        } else {
            btnAddToFinishedBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToFinishedBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to finished books", Toast.LENGTH_SHORT).show();
                        btnAddToFinishedBooks.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, FinishedBooksActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     * @param book The current book
     */
    private void handleWishListBooks(final Book book) {
        ArrayList<Book> wishListBooks = Utils.getWishListBooks();

        AtomicBoolean existInWishListBooks = new AtomicBoolean(false);

        for (Book wishListBook : wishListBooks) {
            if (wishListBook.getId() == book.getId()) {
                existInWishListBooks.set(true);
                break;
            }
        }

        if (existInWishListBooks.get()) {
            btnAddToWishListBooks.setEnabled(false);
        } else {
            btnAddToWishListBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToWishListBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to wish list books", Toast.LENGTH_SHORT).show();
                        btnAddToWishListBooks.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, WishListBooksActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     * @param book The current book
     */
    private void handleReadingBooks(final Book book) {
        ArrayList<Book> readingBooks = Utils.getReadingBooks();

        AtomicBoolean existInReadingBooks = new AtomicBoolean(false);

        for (Book readingBook : readingBooks) {
            if (readingBook.getId() == book.getId()) {
                existInReadingBooks.set(true);
                break;
            }
        }

        if (existInReadingBooks.get()) {
            btnAddToReadingBooks.setEnabled(false);
        } else {
            btnAddToReadingBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToReadingBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to reading books", Toast.LENGTH_SHORT).show();
                        btnAddToReadingBooks.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, ReadingBooksActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Same logic as the handleFinishedBooks method
     * @param book The current book
     */
    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getFavoriteBooks();

        AtomicBoolean existInFavoriteBooks = new AtomicBoolean(false);

        for (Book favoriteBook : favoriteBooks) {
            if (favoriteBook.getId() == book.getId()) {
                existInFavoriteBooks.set(true);
                break;
            }
        }

        if (existInFavoriteBooks.get()) {
            btnAddToFavoriteBooks.setEnabled(false);
        } else {
            btnAddToFavoriteBooks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.addToFavoriteBooks(book)) {
                        Toast.makeText(BookActivity.this, "Added to favorite", Toast.LENGTH_SHORT).show();
                        btnAddToFavoriteBooks.setEnabled(false);

//                        Intent intent = new Intent(BookActivity.this, FavoriteBooksActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong. Please try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Set the book data to the views
     * @param book The current book
     */
    private void setData(Book book) {
        txtBookNameInfo.setText(book.getName());
        txtAuthorNameInfo.setText(book.getAuthor());
        txtPagesInfo.setText(String.valueOf(book.getPages()));
        txtLongDescInfo.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap()
                .load(book.getImageUrl())
                .into(imgBookInfo);
    }

    private void initViews() {
        txtAuthorNameInfo = findViewById(R.id.txtAuthorNameInfo);
        txtBookNameInfo = findViewById(R.id.txtBookNameInfo);
        txtPagesInfo = findViewById(R.id.txtPagesInfo);
        txtLongDescInfo = findViewById(R.id.txtLongDescInfo);

        btnAddToWishListBooks = findViewById(R.id.btnAddToWishListBooks);
        btnAddToReadingBooks = findViewById(R.id.btnAddToReadingBooks);
        btnAddToFinishedBooks = findViewById(R.id.btnAddToFinishedBooks);
        btnAddToFavoriteBooks = findViewById(R.id.btnAddToFavoriteBooks);

        imgBookInfo = findViewById(R.id.imgBookInfo);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}