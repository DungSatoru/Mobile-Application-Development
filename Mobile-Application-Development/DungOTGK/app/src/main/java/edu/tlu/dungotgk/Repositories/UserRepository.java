package edu.tlu.dungotgk.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import edu.tlu.dungotgk.Models.User;
import edu.tlu.dungotgk.Services.DatabaseHelper;

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
        values.put(DatabaseHelper.USERS_COLUMN_FULLNAME, user.getName());
        values.put(DatabaseHelper.USERS_COLUMN_USERNAME, user.getUsername());
        values.put(DatabaseHelper.USERS_COLUMN_PASSWORD, user.getPassword());

        // Định dạng ngày theo ISO 8601
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createdAtString = dateFormat.format(user.getCreatedAt());
        values.put(DatabaseHelper.USERS_COLUMN_CREATEDAT, createdAtString);

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
        values.put(DatabaseHelper.USERS_COLUMN_FULLNAME, user.getName());
        values.put(DatabaseHelper.USERS_COLUMN_USERNAME, user.getUsername());
        values.put(DatabaseHelper.USERS_COLUMN_PASSWORD, user.getPassword());

        int result = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.USERS_COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId_User())});
        if (result > 0) {
            Toast.makeText(context, "User updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User update failed!", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    public User getUser(int userId) {
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.USERS_COLUMN_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);
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

    public List<User> getAllUsers() {
        open();
        List<User> users = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_FULLNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USERS_COLUMN_PASSWORD))
                );
                users.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }
        close();
        return users;
    }
}
