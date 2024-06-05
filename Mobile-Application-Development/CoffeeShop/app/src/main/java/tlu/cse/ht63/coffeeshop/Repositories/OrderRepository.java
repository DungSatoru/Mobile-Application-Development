package tlu.cse.ht63.coffeeshop.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Models.Order;
import tlu.cse.ht63.coffeeshop.Services.DatabaseHelper;

public class OrderRepository {
    private DatabaseHelper dbHelper;

    public OrderRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // Định dạng ngày theo ISO 8601
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String createdAtString = dateFormat.format(order.getDate());
        values.put(DatabaseHelper.ORDERS_COLUMN_DATE, createdAtString);
        values.put(DatabaseHelper.ORDERS_COLUMN_USER_ID, order.getUserId());
        values.put(DatabaseHelper.ORDERS_COLUMN_TOTAL_AMOUNT, order.getTotalAmount());
        long id = db.insert(DatabaseHelper.TABLE_ORDERS, null, values);
        db.close();
        return id;
    }


}
