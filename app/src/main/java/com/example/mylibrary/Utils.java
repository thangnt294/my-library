package com.example.mylibrary;

import java.util.ArrayList;

public class Utils {

    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> readingBooks;
    private static ArrayList<Book> finishedBooks;
    private static ArrayList<Book> favoriteBooks;
    private static ArrayList<Book> wishListBooks;

    private static Utils instance;

    private Utils() {
        initData();
    }

    private void initData() {
        allBooks = new ArrayList<Book>();
        readingBooks = new ArrayList<Book>();
        finishedBooks = new ArrayList<Book>();
        favoriteBooks = new ArrayList<Book>();
        wishListBooks = new ArrayList<Book>();
        allBooks.add(new Book(1, "1Q84", "Murakami Haruki", 1350, "https://images-na.ssl-images-amazon.com/images/I/81M0jxrDz5L.jpg",
                "A work of maddening brilliance.", "The very thought of Aomame's situation will pain anyone stuck in the quicksand of “1Q84.” You, sucker, will wade through nearly 1,000 uneventful pages while discovering a Tokyo that has two moons and is controlled by creatures that emerge from the mouth of a dead goat. "));
        allBooks.add(new Book(2, "The myth of Sisyphus", "Albert Camus", 250, "https://m.media-amazon.com/images/I/41UIPIw6v0L.jpg",
                "One of the most influential works of this century.", "In The Myth of Sisyphus Camus elucidates this concept of the absurd. The absurd comes with the realization that the world is not rational: “At this point of his effort man stands face to face with the irrational. He feels within him his longing for happiness and for reason."));
        allBooks.add(new Book(3, "Moby Dick", "Herman MelVille", 927, "https://kbimages1-a.akamaihd.net/ae25aaf3-7841-4b90-a175-8e55ca639064/1200/1200/False/moby-dick-222.jpg",
                "The book of this century", "Moby-Dick is about everything, a bible written in scrimshaw, an adventure spun in allegory, a taxonomy tripping on acid. It seems to exist outside its own time, much like Don Quixote and Tristram Shandy, the poetry of Emily Dickinson. It is so broad and so deep as to accept any interpretation while also staring back and mocking this man-made desire toward interpretation."));
        allBooks.add(new Book(4, "To kill a mockingbird", "Harper Lee", 281, "https://images-na.ssl-images-amazon.com/images/I/81gepf1eMqL.jpg",
                "A classic of modern American literature", "Compassionate, dramatic, and deeply moving, To Kill A Mockingbird takes readers to the roots of human behavior - to innocence and experience, kindness and cruelty, love and hatred, humor and pathos."));
    }

    public static void getInstance() {
        if (instance == null) {
            instance = new Utils();
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
