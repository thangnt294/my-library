package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String FINISHED_BOOKS_KEY = "finished_books";
    private static final String WISH_LIST_BOOKS_KEY = "wish_list_books";
    private static final String READING_BOOKS_KEY = "reading_books";
    private static final String FAVORITE_BOOKS_KEY = "favorite_books";

    private static Utils instance;

    private Utils(Context context) {

        initData();

    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "1Q84", "Murakami Haruki", 1350, "https://images-na.ssl-images-amazon.com/images/I/81M0jxrDz5L.jpg",
                "A work of maddening brilliance.", "The very thought of Aomame's situation will pain anyone stuck in the quicksand of “1Q84.” You, sucker, will wade through nearly 1,000 uneventful pages while discovering a Tokyo that has two moons and is controlled by creatures that emerge from the mouth of a dead goat. "));
        books.add(new Book(2, "The myth of Sisyphus", "Albert Camus", 250, "https://m.media-amazon.com/images/I/41UIPIw6v0L.jpg",
                "One of the most influential works of this century.", "In The Myth of Sisyphus Camus elucidates this concept of the absurd. The absurd comes with the realization that the world is not rational: “At this point of his effort man stands face to face with the irrational. He feels within him his longing for happiness and for reason."));
        books.add(new Book(3, "Moby Dick", "Herman MelVille", 927, "https://kbimages1-a.akamaihd.net/ae25aaf3-7841-4b90-a175-8e55ca639064/1200/1200/False/moby-dick-222.jpg",
                "The book of this century.", "Moby-Dick is about everything, a bible written in scrimshaw, an adventure spun in allegory, a taxonomy tripping on acid. It seems to exist outside its own time, much like Don Quixote and Tristram Shandy, the poetry of Emily Dickinson. It is so broad and so deep as to accept any interpretation while also staring back and mocking this man-made desire toward interpretation."));
        books.add(new Book(4, "To kill a mockingbird", "Harper Lee", 281, "https://images-na.ssl-images-amazon.com/images/I/81gepf1eMqL.jpg",
                "A classic of modern American literature.", "Compassionate, dramatic, and deeply moving, To Kill A Mockingbird takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos."));

    }

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public Book getBookById(int bookId) {
        ArrayList<Book> books = getAllBooks();
        if (books != null) {
            for (Book book : books) {
                if (book.getId() == bookId) {
                    return book;
                }
            }
        }
        return null;
    }

    public boolean addToFinishedBooks(Book book) {
        ArrayList<Book> finishedBooks = getFinishedBooks();
        if (finishedBooks != null) {
            if (finishedBooks.add(book)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FINISHED_BOOKS_KEY);
                editor.putString(FINISHED_BOOKS_KEY, gson.toJson(finishedBooks));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWishListBooks(Book book) {
        ArrayList<Book> wishListBooks = getWishListBooks();
        if (wishListBooks != null) {
            if (wishListBooks.add(book)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WISH_LIST_BOOKS_KEY);
                editor.putString(WISH_LIST_BOOKS_KEY, gson.toJson(wishListBooks));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToReadingBooks(Book book) {
        ArrayList<Book> readingBooks = getReadingBooks();
        if (readingBooks != null) {
            if (readingBooks.add(book)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(READING_BOOKS_KEY);
                editor.putString(READING_BOOKS_KEY, gson.toJson(readingBooks));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavoriteBooks(Book book) {
        ArrayList<Book> favoriteBooks = getFavoriteBooks();
        if (favoriteBooks != null) {
            if (favoriteBooks.add(book)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS_KEY);
                editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(favoriteBooks));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromFinishedBooks(Book book) {
        ArrayList<Book> finishedBooks = getFinishedBooks();
        if (finishedBooks != null) {
            for (Book finishedBook : finishedBooks) {
                if (finishedBook.getId() == book.getId()) {
                    if (finishedBooks.remove(finishedBook)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FINISHED_BOOKS_KEY);
                        editor.putString(FINISHED_BOOKS_KEY, gson.toJson(finishedBooks));
                        editor.commit();
                        return true;
                    }
                    break;
                }
            }

        }
        return false;
    }

    public boolean removeFromWishListBooks(Book book) {
        ArrayList<Book> wishListBooks = getWishListBooks();
        if (wishListBooks != null) {
            for (Book wishListBook : wishListBooks) {
                if (wishListBook.getId() == book.getId()) {
                    if (wishListBooks.remove(wishListBook)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(WISH_LIST_BOOKS_KEY);
                        editor.putString(WISH_LIST_BOOKS_KEY, gson.toJson(wishListBooks));
                        editor.commit();
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public boolean removeFromReadingBooks(Book book) {
        ArrayList<Book> readingBooks = getReadingBooks();
        if (readingBooks != null) {
            for (Book readingBook : readingBooks) {
                if (readingBook.getId() == book.getId()) {
                    if (readingBooks.remove(readingBook)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(READING_BOOKS_KEY);
                        editor.putString(READING_BOOKS_KEY, gson.toJson(readingBooks));
                        editor.commit();
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public boolean removeFromFavoriteBooks(Book book) {
        ArrayList<Book> favoriteBooks = getFavoriteBooks();
        if (favoriteBooks != null) {
            for (Book favoriteBook : favoriteBooks) {
                if (favoriteBook.getId() == book.getId()) {
                    if (favoriteBooks.remove(favoriteBook)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_BOOKS_KEY);
                        editor.putString(FAVORITE_BOOKS_KEY, gson.toJson(favoriteBooks));
                        editor.commit();
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public ArrayList<Book> getAllBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getReadingBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(READING_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getFinishedBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(FINISHED_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getWishListBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(WISH_LIST_BOOKS_KEY, null), type);
    }

    public ArrayList<Book> getFavoriteBooks() {
        Type type = new TypeToken<ArrayList<Book>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS_KEY, null), type);
    }
}
