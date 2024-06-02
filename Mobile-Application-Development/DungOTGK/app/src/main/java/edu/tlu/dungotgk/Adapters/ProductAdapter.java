package edu.tlu.dungotgk.Adapters;

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

import edu.tlu.dungotgk.Models.Cart;
import edu.tlu.dungotgk.Models.Product;
import edu.tlu.dungotgk.R;
import edu.tlu.dungotgk.Repositories.CartRepository;

public class ProductAdapter extends ArrayAdapter<Product> {
    CartRepository cartRepository;

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
        // Lấy dữ liệu item cho vị trí này
        Product product = getItem(position);
        ViewHolder viewHolder;

        // Kiểm tra nếu view hiện tại đang được tái sử dụng, nếu không thì inflate view mới
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

        // Đổ dữ liệu vào các view bằng cách sử dụng đối tượng product
        if (product != null) {
            viewHolder.tvNameProduct.setText(product.getName() != null ? product.getName() : "N/A");
            viewHolder.tvDescription.setText(product.getDescription() != null ? product.getDescription() : "Không có mô tả");
            viewHolder.tvPrice.setText(String.format("$%.2f", product.getPrice()));
            viewHolder.ivProduct.setImageURI(product.getImage());

            // Thiết lập OnClickListener cho btnAddToCart
            viewHolder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                    Cart existingCartItem = cartRepository.getCartItemByProductId(product.getId_product());

                    if (existingCartItem != null) {
                        // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
                        existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                        cartRepository.updateCart(existingCartItem);
                        Toast.makeText(getContext(), product.getName() + " đã được cập nhật trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới
                        Cart cart = new Cart();
                        cart.setProductId(product.getId_product());
                        cart.setProductPrice(product.getPrice());
                        cart.setQuantity(1);
                        cartRepository.addToCart(cart);
                        Toast.makeText(getContext(), product.getName() + " đã được thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        return convertView;
    }
}
