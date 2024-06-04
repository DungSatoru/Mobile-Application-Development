//package tlu.cse.ht63.coffeeshop.Models;
//
//import java.util.Date;
//
//public class User {
//    private int Id_User;
//    private String name;
//    private String username;
//    private String password;
//
//    private Date createdAt;
//
//    public User() {}
//
//    public User(String name, String username, String password, Date createdAt) {
//        this.name = name;
//        this.username = username;
//        this.password =  password;
//        this.createdAt = createdAt;
//    }
//
//    public User(int id_User, String name, String username, String password) {
//        this.Id_User = id_User;
//        this.name = name;
//        this.username = username;
//        this.password =  password;
//    }
//
//    public int getId_User() {
//        return Id_User;
//    }
//    public void setId_User(int Id_User) {
//        this.Id_User = Id_User;
//    }
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public String getPassword() {
//        return password;
//    }
//    public void setPassword(String password) {
//        this.password =  password;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//}


package tlu.cse.ht63.coffeeshop.Models;

import java.util.Date;

public class User {
    private int id;
    private String fullName;
    private String userName;
    private String passWord;
    private Date createdAt;
    private boolean role;
    private boolean status;

    // Constructors

    public User() {}

    public User(String name, String username, String password, Date createdAt) {
        this.fullName = name;
        this.userName = username;
        this.passWord =  password;
        this.createdAt = createdAt;
    }

    public User(int id_User, String name, String username, String password) {
        this.id = id_User;
        this.fullName = name;
        this.userName = username;
        this.passWord =  password;
    }

    public User(int id, String fullName, String userName, String passWord, Date createdAt, boolean role, boolean status) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.passWord = passWord;
        this.createdAt = createdAt;
        this.role = role;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
