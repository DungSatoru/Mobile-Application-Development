package edu.tlu.dungotgk.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "OTGK_De1.db";
    private static final int DATABASE_VERSION = 1;

    // TABLE USERS
    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_ID = "Id_User";
    public static final String USERS_COLUMN_FULLNAME = "fullName";
    public static final String USERS_COLUMN_USERNAME = "userName";
    public static final String USERS_COLUMN_PASSWORD = "passWord";
    public static final String USERS_COLUMN_CREATEDAT = "createdAt";

    // TABLE PRODUCTS
    public static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCTS_COLUMN_ID = "Id_Product";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_DESCRIPTION = "description";
    public static final String PRODUCTS_COLUMN_PRICE = "price";
    public static final String PRODUCTS_COLUMN_IMAGE = "image";

    // TABLE CART
    public static final String TABLE_CART = "cart";
    public static final String CART_COLUMN_ID = "Id_Cart";
    public static final String CART_COLUMN_USER_ID = "userId";
    public static final String CART_COLUMN_PRODUCT_ID = "productId";
    public static final String CART_COLUMN_QUANTITY = "quantity";




    // CREATE TABLE USERS
    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERS_COLUMN_FULLNAME + " TEXT, " +
                    USERS_COLUMN_USERNAME + " TEXT, " +
                    USERS_COLUMN_PASSWORD + " TEXT, " +
                    USERS_COLUMN_CREATEDAT + " DATE" +
                    ");";

    // CREATE TABLE USERS
    private static final String TABLE_CREATE_PRODUCTS =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    PRODUCTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCTS_COLUMN_NAME + " TEXT, " +
                    PRODUCTS_COLUMN_DESCRIPTION + " TEXT, " +
                    PRODUCTS_COLUMN_PRICE + " DOUBLE, " +
                    PRODUCTS_COLUMN_IMAGE + " TEXT " +
                    ");";

    // CREATE TABLE CART
    private static final String TABLE_CREATE_CART =
            "CREATE TABLE " + TABLE_CART + " (" +
                    CART_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CART_COLUMN_USER_ID + " INTEGER, " +
                    CART_COLUMN_PRODUCT_ID + " INTEGER, " +
                    CART_COLUMN_QUANTITY + " INTEGER, " +
                    "FOREIGN KEY(" + CART_COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USERS_COLUMN_ID + "), " +
                    "FOREIGN KEY(" + CART_COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + PRODUCTS_COLUMN_ID + ")" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_PRODUCTS);
        db.execSQL(TABLE_CREATE_CART);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }
}
