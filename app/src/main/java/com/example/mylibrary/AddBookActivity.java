package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddBookActivity extends AppCompatActivity {

    private EditText title, author, pages, imageUrl, shortDesc, longDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        title = findViewById(R.id.txt_book_title);
        author = findViewById(R.id.txt_book_author);
        pages = findViewById(R.id.txt_book_pages);
        imageUrl = findViewById(R.id.txt_book_image_url);
        shortDesc = findViewById(R.id.txt_book_short_desc);
        longDesc = findViewById(R.id.txt_book_long_desc);

        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper db = new MyDatabaseHelper(AddBookActivity.this);
                db.addBook(title.getText().toString().trim(),
                        author.getText().toString().trim(),
                        Integer.parseInt(pages.getText().toString().trim()),
                        imageUrl.getText().toString().trim(),
                        shortDesc.getText().toString().trim(),
                        longDesc.getText().toString().trim());
                onBackPressed();
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
    public void onBackPressed() {
        super.onBackPressed();
    }
}