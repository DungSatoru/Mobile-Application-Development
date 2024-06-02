package edu.tlu.th01.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.tlu.th01.Models.UserLite;
import edu.tlu.th01.Services.DatabaseHelper;

public class UserLiteRepository {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private Context context;

    public UserLiteRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addUser(UserLite user) {
        if (isUsernameExists(user.getUsername())) {
            Toast.makeText(context, "Username đã tồn tại!", Toast.LENGTH_SHORT).show();
        } else {
            open();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_FULLNAME, user.getFullname());
            values.put(DatabaseHelper.COLUMN_USERNAME, user.getUsername());
            values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());

            long result = database.insert(DatabaseHelper.TABLE_USERS, null, values);
            if (result != -1) {
                Toast.makeText(context, "Đăng ký thành công (SQLite)!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Đăng ký thất bại (SQLite)!", Toast.LENGTH_SHORT).show();
            }
            close();
        }
    }

    public boolean isUsernameExists(String username) {
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        close();
        return exists;
    }

    public void deleteUser(int userId) {
        open();
        int result = database.delete(DatabaseHelper.TABLE_USERS, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
        if (result > 0) {
            Toast.makeText(context, "Xóa người dùng thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Xóa người dùng thất bại!", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    public void updateUser(UserLite user) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FULLNAME, user.getFullname());
        values.put(DatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_PASSWORD, user.getPassword());

        int result = database.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(user.getId())});
        if (result > 0) {
            Toast.makeText(context, "User updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "User update failed!", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    public UserLite getUser(int userId) {
        open();
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, null, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            UserLite user = new UserLite(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULLNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD))
            );
            cursor.close();
            close();
            return user;
        }
        close();
        return null;
    }

    public List<UserLite> getAllUsers() {
        open();
        List<UserLite> users = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                UserLite user = new UserLite(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FULLNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PASSWORD))
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
