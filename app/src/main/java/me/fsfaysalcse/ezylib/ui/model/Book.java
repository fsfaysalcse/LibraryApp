package me.fsfaysalcse.ezylib.ui.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

public class Book {
    @PropertyName("book_id")
    private String book_id;

    @PropertyName("bookTitle")
    private String bookTitle;

    @PropertyName("publishYear")
    private String publishYear;

    @PropertyName("author")
    private String author;

    @PropertyName("isBorrowed")
    private boolean isBorrowed;



    public Book(String book_id, String bookTitle, String publishYear, String author, boolean isBorrowed) {
        this.book_id = book_id;
        this.bookTitle = bookTitle;
        this.publishYear = publishYear;
        this.author = author;
        this.isBorrowed = isBorrowed;
    }

    // Getters and setters

    @Exclude
    public String getId() {
        return book_id;
    }

    @Exclude
    public void setId(String id) {
        this.book_id = id;
    }

    @PropertyName("bookTitle")
    public String getBookTitle() {
        return bookTitle;
    }

    @PropertyName("bookTitle")
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @PropertyName("publishYear")
    public String getPublishYear() {
        return publishYear;
    }

    @PropertyName("publishYear")
    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    @PropertyName("author")
    public String getAuthor() {
        return author;
    }

    @PropertyName("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @PropertyName("isBorrowed")
    public boolean isBorrowed() {
        return isBorrowed;
    }

    @PropertyName("isBorrowed")
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
}
