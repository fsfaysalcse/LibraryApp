package me.fsfaysalcse.ezylib.ui.model;

public class Book {
    private String bookTitle;
    private String author;
    private int publishYear;

    public Book(String bookTitle, String author, int publishYear) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publishYear = publishYear;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublishYear() {
        return publishYear;
    }
}
