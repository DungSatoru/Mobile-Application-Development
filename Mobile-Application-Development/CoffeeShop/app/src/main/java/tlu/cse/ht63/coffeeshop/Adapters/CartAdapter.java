package tlu.cse.ht63.coffeeshop.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tlu.cse.ht63.coffeeshop.Models.Cart;
import tlu.cse.ht63.coffeeshop.Models.Product;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.CartRepository;
import tlu.cse.ht63.coffeeshop.Services.DatabaseHelper;
import tlu.cse.ht63.coffeeshop.Services.SessionManager;
import tlu.cse.ht63.coffeeshop.Views.CartActivity;


public class CartAdapter extends ArrayAdapter<Cart> {
    CartRepository cartRepository;
    CartActivity _cartActivity;

    private static class ViewHolder {
        ImageView ivProduct;
        TextView tvNameProduct;
        TextView tvPrice;
        TextView btnDecrease;
        TextView btnIncrease;
        EditText etQuantity;
    }

    private DatabaseHelper dbHelper;

    public CartAdapter(Context context, ArrayList<Cart> carts) {
        super(context, 0, carts);
        dbHelper = new DatabaseHelper(context);
        cartRepository = new CartRepository(context);
        _cartActivity = (CartActivity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cart cart = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cart, parent, false);
            viewHolder.ivProduct = convertView.findViewById(R.id.ivProduct);
            viewHolder.tvNameProduct = convertView.findViewById(R.id.tvNameProduct);
            viewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            viewHolder.btnDecrease = convertView.findViewById(R.id.btnDecrease);
            viewHolder.btnIncrease = convertView.findViewById(R.id.btnIncrease);
            viewHolder.etQuantity = convertView.findViewById(R.id.etQuantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Đổ dữ liệu vào các view bằng cách sử dụng đối tượng product

        if (cart != null) {
            Product product = getProductById(cart.getProductId());
            if (product != null) {
                viewHolder.tvNameProduct.setText(product.getName());
                viewHolder.tvPrice.setText(String.format("%.2f", product.getPrice()));
                viewHolder.ivProduct.setImageURI(product.getImage());

                viewHolder.etQuantity.setText(String.valueOf(cart.getQuantity()));

                // Tăng số lượng
                viewHolder.btnIncrease.setOnClickListener(v -> {
                    int quantity = Integer.parseInt(viewHolder.etQuantity.getText().toString());
                    quantity++;
                    viewHolder.etQuantity.setText(String.valueOf(quantity));
                    notifyDataSetChanged();
                });

                // Giảm số lượng
                viewHolder.btnDecrease.setOnClickListener(v -> {
                    int quantity = Integer.parseInt(viewHolder.etQuantity.getText().toString());
                    if (quantity > 1) {
                        quantity--;
                        viewHolder.etQuantity.setText(String.valueOf(quantity));
                        notifyDataSetChanged();
                    } else {
                        cartRepository.deleteCartItem(cart.getIdCart());
                        _cartActivity.loadCartListView();
                    }

                });
                // Cập nhật
                viewHolder.etQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // Kiểm tra xem có thay đổi hay không
                        if (!s.toString().isEmpty()) {
                            int quantity = Integer.parseInt(s.toString());
                            if (quantity != cart.getQuantity()) {
                                // Cập nhật CSDL
                                SessionManager sessionManager = new SessionManager(getContext());
                                cart.setQuantity(quantity);
                                cart.setUserId(sessionManager.getUserId());
                                cartRepository.updateCart(cart);
                                _cartActivity.updateTotalMoney();
                            }
                        }
                    }
                });
            }
        }

        return convertView;
    }

    public Product getProductById(int productId) {
        Product product = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS + " WHERE " + DatabaseHelper.PRODUCTS_COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_DESCRIPTION);
            int priceIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_PRICE);
            int imageIndex = cursor.getColumnIndex(DatabaseHelper.PRODUCTS_COLUMN_IMAGE);

            if (idIndex != -1 && nameIndex != -1 && descriptionIndex != -1 && priceIndex != -1 && imageIndex != -1) {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                double price = cursor.getDouble(priceIndex);
                Uri image = Uri.parse(cursor.getString(imageIndex));

                product = new Product(id, name, price, image);
            }
            cursor.close();
        }

        db.close();
        return product;
    }
}
