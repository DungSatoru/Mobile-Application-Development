package edu.tlu.dungotgk.Models;

import java.util.Date;

public class User {
    private int Id_User;
    private String name;
    private String username;
    private String password;

    private Date createdAt;

    public User() {}

    public User(String name, String username, String password, Date createdAt) {
        this.name = name;
        this.username = username;
        this.password =  password;
        this.createdAt = createdAt;
    }

    public User(int id_User, String name, String username, String password) {
        this.Id_User = id_User;
        this.name = name;
        this.username = username;
        this.password =  password;
    }

    public int getId_User() {
        return Id_User;
    }
    public void setId_User(int Id_User) {
        this.Id_User = Id_User;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password =  password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
