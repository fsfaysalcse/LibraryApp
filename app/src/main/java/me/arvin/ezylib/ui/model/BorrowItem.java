package me.arvin.ezylib.ui.model;

public class BorrowItem {
    private String bookId;
    private String bookTitle;
    private String borrowDate;
    private String returnDate;
    private String studentId;

    public BorrowItem(String bookId, String bookTitle, String borrowDate, String returnDate, String studentId) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.studentId = studentId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

