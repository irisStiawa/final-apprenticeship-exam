package com.lap.roomplanningsystem.model;

import java.util.Arrays;

public class User {
    private int id;
    private boolean active;
    private String title;
    private String firstname;
    private String lastname;
    private String username;
    private String authorization;
    private boolean trainer;
    private String text;
    private boolean textVisable;
    private String phone;
    private boolean phoneVisable;
    private String email;
    private boolean emailVisable;
    private byte[] photo;
    private boolean photoVisable;

    public User(int id, boolean active, String title, String firstname, String lastname, String username, String authorization, boolean trainer, String text, boolean textVisable, String phone, boolean phoneVisable, String email, boolean emailVisable, byte[] photo, boolean photoVisable) {
        this.id = id;
        this.active = active;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.authorization = authorization;
        this.trainer = trainer;
        this.text = text;
        this.textVisable = textVisable;
        this.phone = phone;
        this.phoneVisable = phoneVisable;
        this.email = email;
        this.emailVisable = emailVisable;
        this.photo = photo;
        this.photoVisable = photoVisable;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", active=" + active +
                ", title='" + title + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", authorization='" + authorization + '\'' +
                ", trainer=" + trainer +
                ", text='" + text + '\'' +
                ", textVisable=" + textVisable +
                ", phone='" + phone + '\'' +
                ", phoneVisable=" + phoneVisable +
                ", email='" + email + '\'' +
                ", emailVisable=" + emailVisable +
                ", photo=" + Arrays.toString(photo) +
                ", photoVisable=" + photoVisable +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public boolean isTrainer() {
        return trainer;
    }

    public void setTrainer(boolean trainer) {
        this.trainer = trainer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTextVisable() {
        return textVisable;
    }

    public void setTextVisable(boolean textVisable) {
        this.textVisable = textVisable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneVisable() {
        return phoneVisable;
    }

    public void setPhoneVisable(boolean phoneVisable) {
        this.phoneVisable = phoneVisable;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVisable() {
        return emailVisable;
    }

    public void setEmailVisable(boolean emailVisable) {
        this.emailVisable = emailVisable;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isPhotoVisable() {
        return photoVisable;
    }

    public void setPhotoVisable(boolean photoVisable) {
        this.photoVisable = photoVisable;
    }

    public boolean isActive() {
        return active;
    }

    public String printActiveState(){
        return active? "aktiv" : "inaktiv";

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String printAuthorization() {
        switch (authorization){
            case "admin" -> {
                return "Administrator";
            }
            case "coach" -> {
                return "Trainer";
            }
        }
        return null;
    }
}
