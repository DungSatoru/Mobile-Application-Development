package edu.tlu.sqlite.sqllite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper  {
    public static final String DB_NAME = "Demo6";
    public static final int DB_VERSION = 2;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE NhanVien (" +
                        "Id TEXT PRIMARY KEY, " +
                        "Name TEXT NOT NULL, " +
                        "Salary REAL NOT NULL" +
                      ")";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS NhanVien";
        db.execSQL(sql);
        onCreate(db);
    }
}
