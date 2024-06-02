package edu.tlu.th01.Models;

public class UserLite {
    private int id;
    private String fullname;
    private String username;
    private String password;

    // Constructor không tham số
    public UserLite() {
    }

    public UserLite(String fullname, String username, String password) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }
    public UserLite(int id, String fullname, String username, String password) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho fullname
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    // Getter và Setter cho username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter và Setter cho password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
