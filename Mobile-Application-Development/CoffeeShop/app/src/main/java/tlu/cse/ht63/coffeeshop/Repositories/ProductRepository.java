package tlu.cse.ht63.coffeeshop.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Models.Product;
import tlu.cse.ht63.coffeeshop.Services.DatabaseHelper;


public class ProductRepository {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private Context context;

    public ProductRepository(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = getProductFromCursor(cursor);
                products.add(product);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return products;
    }

    private Product getProductFromCursor(Cursor cursor) {
        Product product = new Product();
        int idIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_NAME);
        int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_DESCRIPTION);
        int priceIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_PRICE);
        int imageIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_IMAGE);

        if (idIndex != -1) {
            product.setId_product(cursor.getInt(idIndex));
        }
        if (nameIndex != -1) {
            product.setName(cursor.getString(nameIndex));
        }
        if (descriptionIndex != -1) {
            product.setDescription(cursor.getString(descriptionIndex));
        }
        if (priceIndex != -1) {
            product.setPrice(cursor.getDouble(priceIndex));
        }
        if (imageIndex != -1) {
            product.setImage(Uri.parse(cursor.getString(imageIndex)));
        }
        return product;
    }


    public void addProduct(Product product) {
        open();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.PRODUCTS_COLUMN_NAME, product.getName());
        values.put(DatabaseHelper.PRODUCTS_COLUMN_DESCRIPTION, product.getDescription());
        values.put(DatabaseHelper.PRODUCTS_COLUMN_PRICE, product.getPrice());
        values.put(DatabaseHelper.PRODUCTS_COLUMN_IMAGE, product.getImage().toString());

        long result = db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
        if (result != -1) {
            Toast.makeText(context, "Thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Thất bại!", Toast.LENGTH_SHORT).show();
        }
        close();
    }

}
