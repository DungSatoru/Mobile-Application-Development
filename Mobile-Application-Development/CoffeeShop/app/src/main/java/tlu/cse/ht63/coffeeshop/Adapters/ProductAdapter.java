package tlu.cse.ht63.coffeeshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tlu.cse.ht63.coffeeshop.Models.Cart;
import tlu.cse.ht63.coffeeshop.Models.Product;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.CartRepository;
import tlu.cse.ht63.coffeeshop.Services.SessionManager;

public class ProductAdapter extends ArrayAdapter<Product> {
    private final CartRepository cartRepository;

    // Mẫu ViewHolder để tối ưu hóa ListView
    private static class ViewHolder {
        ImageView ivProduct;
        TextView tvNameProduct;
        TextView tvDescription;
        TextView tvPrice;
        Button btnAddToCart;
    }

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        cartRepository = new CartRepository(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product product = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_product, parent, false);
            viewHolder.ivProduct = convertView.findViewById(R.id.ivProduct);
            viewHolder.tvNameProduct = convertView.findViewById(R.id.tvNameProduct);
            viewHolder.tvDescription = convertView.findViewById(R.id.tvDescription);
            viewHolder.tvPrice = convertView.findViewById(R.id.tvPrice);
            viewHolder.btnAddToCart = convertView.findViewById(R.id.btnAddtoCart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (product != null) {
            viewHolder.tvNameProduct.setText(product.getName() != null ? product.getName() : "N/A");
            viewHolder.tvDescription.setText(product.getDescription() != null ? product.getDescription() : "Không có mô tả");
            viewHolder.tvPrice.setText(String.format("%.0f", product.getPrice()));
            viewHolder.ivProduct.setImageURI(product.getImage());

            viewHolder.btnAddToCart.setOnClickListener(v -> {
                SessionManager sessionManager = new SessionManager(getContext());
                int userId = sessionManager.getUserId();
                Cart existingCartItem = cartRepository.getCartItemByProductId(product.getId_product(), userId);
                if (existingCartItem != null) {
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                    cartRepository.updateCart(existingCartItem);
                    Toast.makeText(getContext(), product.getName() + " đã được cập nhật trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    cart.setProductId(product.getId_product());
                    cart.setProductPrice(product.getPrice());
                    cart.setQuantity(1);
                    cartRepository.addToCart(cart);
                    Toast.makeText(getContext(), product.getName() + " đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return convertView;
    }
}