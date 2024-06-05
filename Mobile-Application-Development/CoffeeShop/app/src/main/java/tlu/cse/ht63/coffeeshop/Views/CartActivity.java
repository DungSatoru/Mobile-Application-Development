package tlu.cse.ht63.coffeeshop.Views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Adapters.CartAdapter;
import tlu.cse.ht63.coffeeshop.Models.Cart;
import tlu.cse.ht63.coffeeshop.Models.Order;
import tlu.cse.ht63.coffeeshop.Models.Product;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.CartRepository;
import tlu.cse.ht63.coffeeshop.Repositories.OrderRepository;
import tlu.cse.ht63.coffeeshop.Services.SessionManager;


public class CartActivity extends AppCompatActivity {
    private ImageView ivBack;
    private CartRepository cartRepository;
    private ArrayList<Cart> cartList;
    private CartAdapter adapter;
    private ListView listView;
    TextView tvTotalMoney, tvApply, btnOrder;
    EditText etVoucher;
    private boolean isApplyVoucher;

    double totalMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        setSystemBarsPadding();
        setupEventHandlers();
    }

    private void init() {
        ivBack = findViewById(R.id.ivBack);
        listView = findViewById(R.id.lvCart);
        tvTotalMoney = findViewById(R.id.tvTotalMoney);
        tvApply = findViewById(R.id.tvApply);
        etVoucher = findViewById(R.id.etVoucher);
        loadCartListView();
        isApplyVoucher = false;
        btnOrder = findViewById(R.id.btnOrder);
    }

    private void setSystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupEventHandlers() {
        ivBack.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);
        });

        tvApply.setOnClickListener(v -> {
            String userNameFromIntent = getIntent().getStringExtra("userName");
            String enteredVoucher = etVoucher.getText().toString();

            if (isApplyVoucher) {
                // Hủy voucher
                isApplyVoucher = false;
                tvApply.setText("Áp dụng");
                etVoucher.setText("");
                updateTotalMoney(); // Gọi phương thức này để cập nhật lại tổng tiền khi voucher bị hủy
            } else {
                // Áp dụng voucher
                if (userNameFromIntent != null && userNameFromIntent.equals(enteredVoucher)) {
                    isApplyVoucher = true;
                    tvApply.setText("Hủy");
                    updateTotalMoney(); // Gọi phương thức này để cập nhật tổng tiền khi voucher được áp dụng
                } else {
                    // Thông báo cho người dùng nếu mã voucher không hợp lệ
                    Toast.makeText(this, "Mã voucher không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOrder.setOnClickListener(v -> {
            SessionManager sessionManager = new SessionManager(this);
            CartRepository cartRepository = new CartRepository(this);

            Order order = new Order();
            order.setUserId(sessionManager.getUserId());
            order.setTotalAmount(totalMoney);
            order.setDate(new Date());

            OrderRepository orderRepository = new OrderRepository(this);
            orderRepository.addOrder(order);

            cartRepository.updateCartIsDone();

            Toast.makeText(this, "Dat hang thanh cong", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CartActivity.this, MainActivity.class);
            startActivity(intent);


        });

    }

    public void loadCartListView() {
        cartRepository = new CartRepository(this);
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        cartList = new ArrayList<>(cartRepository.getAllCartItems(sessionManager.getUserId()));
        adapter = new CartAdapter(this, cartList);
        listView.setAdapter(adapter);

        updateTotalMoney();
    }

    public void updateTotalMoney() {
        totalMoney = 0;
        for (Cart cart : cartList) {
            Product product = adapter.getProductById(cart.getProductId());
            if (product != null) {
                totalMoney += product.getPrice() * cart.getQuantity();
            }
        }
        if (isApplyVoucher) {
            totalMoney = totalMoney * 0.9;
        }
        tvTotalMoney.setText(Double.toString(totalMoney));
    }


}
