package com.example.mylibrary;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class Utils {

    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> readingBooks;
    private static ArrayList<Book> finishedBooks;
    private static ArrayList<Book> favoriteBooks;
    private static ArrayList<Book> wishListBooks;

    private static Utils instance;

    private static MyDatabaseHelper myDB;

    private Utils(Context context) {
        initData(context);
    }

    private void initData(Context context) {
        myDB = new MyDatabaseHelper(context);
        allBooks = new ArrayList<Book>();
        readingBooks = new ArrayList<Book>();
        finishedBooks = new ArrayList<Book>();
        favoriteBooks = new ArrayList<Book>();
        wishListBooks = new ArrayList<Book>();
        addMockData();

    }

    public static void getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
    }

    public static Book getBookById(int bookId) {
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

    public static void fetchData() {
        resetAllBooks();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(0));
                String title = cursor.getString(1);
                String author = cursor.getString(2);
                int pages = Integer.parseInt(cursor.getString(3));
                String imageUrl = cursor.getString(4);
                String shortDesc = cursor.getString(5);
                String longDesc = cursor.getString(6);
                Book book = new Book(id, title, author, pages, imageUrl, shortDesc, longDesc);
                addToAllBooks(book);
            }
        }
    }

    public static void addMockData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            myDB.addBook(new Book("1Q84", "Murakami Haruki", 1350, "https://images-na.ssl-images-amazon.com/images/I/41ffQULRlxL._SX298_BO1,204,203,200_.jpg",
                    "A work of maddening brilliance", "The very thought of Aomame's situation will pain anyone stuck in the quicksand of “1Q84.” You, sucker, will wade through nearly 1,000 uneventful pages while discovering a Tokyo that has two moons and is controlled by creatures that emerge from the mouth of a dead goat. "));
            myDB.addBook(new Book("Chess endgame study", "A.J.Roy Croft", 250, "https://kbimages1-a.akamaihd.net/6630cf6b-d534-45d9-a27b-ae022b6b3bdc/1200/1200/False/the-chess-endgame-study-1.jpg",
                    "A must-read book for any chess players", "The endgame study, like the chess problem, eliminates over-the-board competition to concentrate on an invented endgame position carefully plotted to fox, bewilder, challenge, and otherwise stimulate the solver. Unlike problems, studies do not stipulate the number of moves to the solution; they demand a greater knowledge of endgame theory, thus becoming useful tools for actual players while giving \"studyists\" self-contained epiphanies of compositional art.\n" +
                    "A. J. Roycroft, founder of the Chess Endgame Study Circle and editor of its magazine EG, presents this complete introduction to the endgame study."));
            myDB.addBook(new Book("Moby Dick", "Herman MelVille", 927, "https://kbimages1-a.akamaihd.net/ae25aaf3-7841-4b90-a175-8e55ca639064/1200/1200/False/moby-dick-222.jpg",
                    "The book of this century", "Moby-Dick is about everything, a bible written in scrimshaw, an adventure spun in allegory, a taxonomy tripping on acid. It seems to exist outside its own time, much like Don Quixote and Tristram Shandy, the poetry of Emily Dickinson. It is so broad and so deep as to accept any interpretation while also staring back and mocking this man-made desire toward interpretation."));
            myDB.addBook(new Book("To kill a mockingbird", "Harper Lee", 281, "https://images-na.ssl-images-amazon.com/images/I/81gepf1eMqL.jpg",
                    "A classic of modern American literature", "Compassionate, dramatic, and deeply moving, To Kill A Mockingbird takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos."));
        }
    }

    public static boolean addToAllBooks(Book book) {
        return allBooks.add(book);
    }

    public static boolean addToFinishedBooks(Book book) {
        return finishedBooks.add(book);
    }

    public static boolean addToWishListBooks(Book book) {
        return wishListBooks.add(book);
    }

    public static boolean addToReadingBooks(Book book) {
        return readingBooks.add(book);
    }

    public static boolean addToFavoriteBooks(Book book) {
        return favoriteBooks.add(book);
    }

    public static boolean removeFromAllBooks(Book book) {
        return allBooks.remove(book);
    }

    public static boolean removeFromFinishedBooks(Book book) {
        return finishedBooks.remove(book);
    }

    public static boolean removeFromWishListBooks(Book book) {
        return wishListBooks.remove(book);
    }

    public static boolean removeFromReadingBooks(Book book) {
        return readingBooks.remove(book);
    }

    public static boolean removeFromFavoriteBooks(Book book) {
        return favoriteBooks.remove(book);
    }

    public static void resetAllBooks() {
        allBooks.clear();
    }

    public static ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public static ArrayList<Book> getReadingBooks() {
        return readingBooks;
    }

    public static ArrayList<Book> getFinishedBooks() {
        return finishedBooks;
    }

    public static ArrayList<Book> getWishListBooks() {
        return wishListBooks;
    }

    public static ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }
}
