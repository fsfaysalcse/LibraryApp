package me.arvin.ezylib.ui.model;

import com.google.firebase.firestore.PropertyName;

public class Admin {
    @PropertyName("id")
    private String id;
    @PropertyName("fullName")
    private String fullName;
    @PropertyName("email")
    private String email;
    @PropertyName("phone")
    private String phone;

    public Admin() {
        // Empty constructor needed for Firestore
    }

    public Admin(String id, String fullName, String email, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
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

