package tlu.cse.ht63.coffeeshop.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public static final String USERS_COLUMN_ROLE = "role";
    public static final String USERS_COLUMN_STATUS = "status";

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
    public static final String CART_COLUMN_IS_DONE = "isDone"; // Thêm cột mới

    // TABLE ORDERS
    public static final String TABLE_ORDERS = "orders";
    public static final String ORDERS_COLUMN_ID = "id";
    public static final String ORDERS_COLUMN_DATE = "date";
    public static final String ORDERS_COLUMN_USER_ID = "user_id";
    public static final String ORDERS_COLUMN_TOTAL_AMOUNT = "total_amount";


    // CREATE TABLE USERS
    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERS_COLUMN_FULLNAME + " TEXT, " +
                    USERS_COLUMN_USERNAME + " TEXT, " +
                    USERS_COLUMN_PASSWORD + " TEXT, " +
                    USERS_COLUMN_CREATEDAT + " DATE DEFAULT CURRENT_TIMESTAMP, " +
                    USERS_COLUMN_ROLE + " BIT DEFAULT 0, " +
                    USERS_COLUMN_STATUS + " BIT DEFAULT 0" +
                    ");";

    // CREATE TABLE PRODUCTS
    private static final String TABLE_CREATE_PRODUCTS =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    PRODUCTS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PRODUCTS_COLUMN_NAME + " TEXT, " +
                    PRODUCTS_COLUMN_DESCRIPTION + " TEXT, " +
                    PRODUCTS_COLUMN_PRICE + " DOUBLE, " +
                    PRODUCTS_COLUMN_IMAGE + " TEXT " +
                    ");";

    private static final String TABLE_CREATE_CART =
            "CREATE TABLE " + TABLE_CART + " (" +
                    CART_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CART_COLUMN_USER_ID + " INTEGER, " +
                    CART_COLUMN_PRODUCT_ID + " INTEGER, " +
                    CART_COLUMN_QUANTITY + " INTEGER, " +
                    CART_COLUMN_IS_DONE + " BIT DEFAULT 0, " +
                    "FOREIGN KEY(" + CART_COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USERS_COLUMN_ID + "), " +
                    "FOREIGN KEY(" + CART_COLUMN_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + PRODUCTS_COLUMN_ID + ")" +
                    ");";

    // CREATE TABLE ORDERS
    private static final String TABLE_CREATE_ORDERS =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    ORDERS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ORDERS_COLUMN_DATE + " DATE NOT NULL, " +
                    ORDERS_COLUMN_USER_ID + " INTEGER NOT NULL, " +
                    ORDERS_COLUMN_TOTAL_AMOUNT + " REAL NOT NULL" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_USERS);
        db.execSQL(TABLE_CREATE_PRODUCTS);
        db.execSQL(TABLE_CREATE_CART);
        db.execSQL(TABLE_CREATE_ORDERS); // Thêm câu lệnh tạo bảng ORDERS vào hàm onCreate
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS); // Thêm câu lệnh xóa bảng ORDERS vào hàm onUpgrade
        onCreate(db);
    }
}
