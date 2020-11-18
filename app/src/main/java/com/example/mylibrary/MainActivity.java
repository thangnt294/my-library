package com.example.mylibrary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks, btnReadingBooks, btnFinishedBooks, btnFavoriteBooks, btnWishList, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initButtonOnClick();

        // Instantiating the Utils class for the first time, in order to avoid null pointer exceptions
        Utils.getInstance();
    }

    private void initButtonOnClick() {
        btnAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
                startActivity(intent);
            }
        });

        btnFinishedBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinishedBooksActivity.class);
                startActivity(intent);
            }
        });

        btnWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WishListBooksActivity.class);
                startActivity(intent);
            }
        });

        btnReadingBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReadingBooksActivity.class);
                startActivity(intent);
            }
        });

        btnFavoriteBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteBooksActivity.class);
                startActivity(intent);
            }
        });

         btnAbout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                 builder.setTitle(getString(R.string.app_name))
                         .setMessage("Developed by Nguyen Toan Thang. Check out my github: ")
                         .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 // Auto-dismiss the dialog
                             }
                         })
                         .setPositiveButton("Visit", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 Intent intent = new Intent(MainActivity.this, WebsiteActivity.class);
                                 intent.putExtra("url", "https://github.com/thangnt294");
                                 startActivity(intent);
                             }
                         })
                         .create()
                         .show();
             }
         });
    }

    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnReadingBooks = findViewById(R.id.btnReadingBooks);
        btnFinishedBooks = findViewById(R.id.btnFinishedBooks);
        btnFavoriteBooks = findViewById(R.id.btnFavoriteBooks);
        btnWishList = findViewById(R.id.btnWishList);
        btnAbout = findViewById(R.id.btnAbout);
    }
}