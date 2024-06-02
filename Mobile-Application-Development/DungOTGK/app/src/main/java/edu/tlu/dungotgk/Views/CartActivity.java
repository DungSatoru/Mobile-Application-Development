package edu.tlu.dungotgk.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import edu.tlu.dungotgk.Adapters.CartAdapter;
import edu.tlu.dungotgk.Models.Cart;
import edu.tlu.dungotgk.R;
import edu.tlu.dungotgk.Repositories.CartRepository;

public class CartActivity extends AppCompatActivity {
    private ImageView ivBack;
    private CartRepository cartRepository;
    private ArrayList<Cart> cartList;
    private CartAdapter adapter;
    private ListView listView;
    TextView tvTotalMoney;

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
        loadCartListView();

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
    }

    public void loadCartListView() {
        cartRepository = new CartRepository(this);
        cartList = new ArrayList<>(cartRepository.getAllCartItems());
        adapter = new CartAdapter(this, cartList);
        listView.setAdapter(adapter);

        double totalMoney = 0;

    }

}
