package tlu.cse.ht63.coffeeshop.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Models.Cart;
import tlu.cse.ht63.coffeeshop.Services.DatabaseHelper;

public class CartRepository {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private Context context;

    public CartRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addToCart(Cart cart) {
        open();
        ContentValues values = createCartContentValues(cart);
        db.insert(DatabaseHelper.TABLE_CART, null, values);
        close();
    }

    public void updateCart(Cart cart) {
        open();
        ContentValues values = createCartContentValues(cart);
        String selection = DatabaseHelper.CART_COLUMN_ID + " = ? AND " +  DatabaseHelper.CART_COLUMN_USER_ID + " = ?"  ;
        String[] selectionArgs = {String.valueOf(cart.getIdCart()), String.valueOf(cart.getUserId())};
        db.update(DatabaseHelper.TABLE_CART, values, selection, selectionArgs);
        close();
    }

    public List<Cart> getAllCartItems(int userId) {
        List<Cart> cartItems = new ArrayList<>();
        open();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_CART, null);
        Cursor cursor = db.rawQuery("SELECT * FROM  " + DatabaseHelper.TABLE_CART + " WHERE " + DatabaseHelper.CART_COLUMN_USER_ID + " = ? AND " + DatabaseHelper.CART_COLUMN_IS_DONE + " = ?", new String[]{String.valueOf(userId), "0"});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Cart cart = createCartFromCursor(cursor);
                    if (cart != null) {
                        cartItems.add(cart);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        close();
        return cartItems;
    }

    public void deleteCartItem(int cartId) {
        open();
        db.delete(DatabaseHelper.TABLE_CART, DatabaseHelper.CART_COLUMN_ID + " = ?", new String[]{String.valueOf(cartId)});
        close();
    }

    private ContentValues createCartContentValues(Cart cart) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CART_COLUMN_USER_ID, cart.getUserId());
        values.put(DatabaseHelper.CART_COLUMN_PRODUCT_ID, cart.getProductId());
        values.put(DatabaseHelper.CART_COLUMN_QUANTITY, cart.getQuantity());
        return values;
    }

    public void updateCartIsDone() {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.CART_COLUMN_IS_DONE, 1);
        db.update(DatabaseHelper.TABLE_CART, values, DatabaseHelper.CART_COLUMN_IS_DONE + " = 0", null);
        close();
    }

    private Cart createCartFromCursor(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_ID);
        if (idIndex == -1) {
            return null;
        }
        int idCart = cursor.getInt(idIndex);

        int userIdIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_USER_ID);
        int userId = userIdIndex != -1 ? cursor.getInt(userIdIndex) : 0;

        int productIdIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_PRODUCT_ID);
        int productId = productIdIndex != -1 ? cursor.getInt(productIdIndex) : 0;

        int quantityIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_QUANTITY);
        int quantity = quantityIndex != -1 ? cursor.getInt(quantityIndex) : 0;

        return new Cart(idCart, userId, productId, quantity);
    }

    public Cart getCartItemByProductId(int idProduct, int USER_ID) {
        Cart cartItem = null;
        open();
        Cursor cursor = db.query(DatabaseHelper.TABLE_CART, null,
                DatabaseHelper.CART_COLUMN_PRODUCT_ID + " = ? AND " + DatabaseHelper.CART_COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(idProduct), String.valueOf(USER_ID)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_ID);
                int userIdIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_USER_ID);
                int productIdIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_PRODUCT_ID);
                int quantityIndex = cursor.getColumnIndex(DatabaseHelper.CART_COLUMN_QUANTITY);

                int id = cursor.getInt(idIndex);
                int userId = cursor.getInt(userIdIndex);
                int productId = cursor.getInt(productIdIndex);
                int quantity = cursor.getInt(quantityIndex);

                cartItem = new Cart(id, userId, productId, quantity);
            }
            cursor.close();
        }
        close();
        return cartItem;
    }
}
