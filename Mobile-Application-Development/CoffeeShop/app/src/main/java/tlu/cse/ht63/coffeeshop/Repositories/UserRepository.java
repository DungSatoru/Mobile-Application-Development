package tlu.cse.ht63.coffeeshop.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Models.User;
import tlu.cse.ht63.coffeeshop.Services.DatabaseHelper;

public class UserRepository {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public UserRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addUser(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERS_COLUMN_FULLNAME, user.getFullName());
        values.put(DatabaseHelper.USERS_COLUMN_USERNAME, user.getUserName());
        values.put(DatabaseHelper.USERS_COLUMN_PASSWORD, user.getPassWord());

        // Định dạng ngày theo ISO 8601
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createdAtString = dateFormat.format(user.getCreatedAt());
        values.put(DatabaseHelper.USERS_COLUMN_CREATEDAT, createdAtString);
        values.put(DatabaseHelper.USERS_COLUMN_ROLE, user.isRole());

        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        if (result != -1) {
            Toast.makeText(context, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }

        close();
    }

    public boolean isUserNameExists(String userName) {
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.USERS_COLUMN_USERNAME + " = ?", new String[]{userName}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        close();
        return exists;
    }

    public void updateUser(User user) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.USERS_COLUMN_FULLNAME, user.getFullName());
        values.put(DatabaseHelper.USERS_COLUMN_USERNAME, user.getUserName());
        values.put(DatabaseHelper.USERS_COLUMN_PASSWORD, user.getPassWord());

        int result = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.USERS_COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        if (result > 0) {
            Toast.makeText(context, "User updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User update failed!", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    public User getUser(String username, String password) {
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.USERS_COLUMN_USERNAME + " = ? AND " + DatabaseHelper.USERS_COLUMN_PASSWORD + " = ?", new String[]{username, password}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_FULLNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_PASSWORD))
            );
            cursor.close();
            close();
            return user;
        }
        close();
        return null;
    }

    public void updateStatusUser(String username, boolean status) {
        open();
        ContentValues values = new ContentValues();
//        int statusValue = status ? 1:0;
        values.put(DatabaseHelper.USERS_COLUMN_STATUS, status);

        int rowsUpdated = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.USERS_COLUMN_USERNAME + " = ? ", new String[]{username});
        if (rowsUpdated == 0) {
            System.out.println("No user found with the username: " + username);
        }
        close();
    }

    public boolean checkUserCredentials(String username, String password) {
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE " + DatabaseHelper.USERS_COLUMN_USERNAME + " = ? AND " + DatabaseHelper.USERS_COLUMN_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.moveToFirst();
        close();
        return exists;
    }
}
