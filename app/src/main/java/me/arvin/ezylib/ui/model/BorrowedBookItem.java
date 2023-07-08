package me.arvin.ezylib.ui.model;

public class BorrowedBookItem {
    private String studentId;
    private String bookName;
    private String borrowedDate;
    private String returnDate;

    public BorrowedBookItem(String studentId, String bookName, String borrowedDate, String returnDate) {
        this.studentId = studentId;
        this.bookName = bookName;
        this.borrowedDate = borrowedDate;
        this.returnDate = returnDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBorrowedDate() {
        return borrowedDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
