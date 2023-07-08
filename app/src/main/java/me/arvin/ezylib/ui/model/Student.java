package me.arvin.ezylib.ui.model;
import com.google.firebase.firestore.PropertyName;

public class Student {
    @PropertyName("userId")
    private String userId;
    @PropertyName("studentId")
    private String studentId;
    @PropertyName("fullName")
    private String fullName;
    @PropertyName("email")
    private String email;
    @PropertyName("phone")
    private String phone;

    public Student() {
        // Empty constructor needed for Firestore
    }

    public Student(String userId, String studentId, String fullName, String email, String phone) {
        this.userId = userId;
        this.studentId = studentId;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    @PropertyName("userId")
    public String getUserId() {
        return userId;
    }

    @PropertyName("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("studentId")
    public String getStudentId() {
        return studentId;
    }

    @PropertyName("studentId")
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @PropertyName("fullName")
    public String getFullName() {
        return fullName;
    }

    @PropertyName("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("phone")
    public String getPhone() {
        return phone;
    }

    @PropertyName("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
