package tlu.cse.ht63.coffeeshop.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tlu.cse.ht63.coffeeshop.Adapters.ProductAdapter;
import tlu.cse.ht63.coffeeshop.Models.Product;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.ProductRepository;
import tlu.cse.ht63.coffeeshop.Repositories.UserRepository;
import tlu.cse.ht63.coffeeshop.Services.SessionManager;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCart;
    private FloatingActionButton btnLogOut;
    private String userName;
    private String fullName;
    private TextView tvUserName;
    private TextView tvFullName;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupInsets();
        initViews();
        loadUserData();
//        initProductRepository();
        setupListView();
        setupEventHandlers();
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        ivCart = findViewById(R.id.ivCart);
        btnLogOut = findViewById(R.id.btnLogOut);
        tvFullName = findViewById(R.id.tvFullName);
        tvUserName = findViewById(R.id.tvUserName);
    }

    private void loadUserData() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        userName = sessionManager.getFullName();
        fullName = sessionManager.getUsername();
        tvFullName.setText(userName);
        tvUserName.setText(fullName);
        userRepository = new UserRepository(this);
    }

    private void initProductRepository() {
        ProductRepository productRepository = new ProductRepository(this);
        String[] productNames = {
                "Hazelnut Macchiato", "Ristretto Bianco", "Asian Dolce Latte", "Cà phê Americano",
                "Cà phê sữa", "Cà phê Mocha", "Cà phê sữa có Hương vị", "Cà phê sữa Không thức uống có Hương vị",
                "Cappuccino", "Caramel Macchiato", "Espresso", "Espresso Con Panna"
        };

        double[] prices = {
                5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000, 15000, 16000
        };

        String[] descriptions = {
                "Sự kết hợp ngon miệng của espresso và siro hạt phỉ với sữa hấp và lớp bọt nhẹ.",
                "Trải nghiệm cà phê đậm đà và phong phú với kết thúc mượt mà, hoàn hảo cho những người yêu thích cà phê.",
                "Hỗn hợp độc đáo của espresso với sữa có đường, độc quyền tại Châu Á.",
                "Một Americano cổ điển với espresso đậm đà và nước nóng cho hương vị mượt mà và đậm đà.",
                "Cà phê Việt Nam truyền thống với sữa đặc có đường và cà phê robusta.",
                "Sự pha trộn ngon miệng của espresso, sữa hấp và siro sô-cô-la, được phủ kem tươi.",
                "Cà phê thơm ngon với sữa đặc có đường và nhiều hương vị để lựa chọn.",
                "Cà phê mịn màng với sữa đặc có đường và không có hương vị bổ sung, cho trải nghiệm cà phê nguyên chất.",
                "Cà phê Ý cổ điển với tỷ lệ bằng nhau của espresso, sữa hấp và sữa tạo bọt.",
                "Espresso đậm đà và kem với siro caramel, sữa hấp và lớp bọt nhẹ.",
                "Espresso mạnh mẽ và đậm đà, hoàn hảo cho một cú đẩy caffeine nhanh chóng.",
                "Espresso sang trọng được phủ lớp kem tươi cho món tráng miệng béo ngậy và thú vị."
        };

        for (int i = 0; i < productNames.length; i++) {
            String resourceName = "coffee" + (i + 1);
            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
            productRepository.addProduct(new Product(productNames[i], descriptions[i], prices[i], uri));
        }
    }

    private void setupListView() {
        ProductRepository productRepository = new ProductRepository(this);
        List<Product> products = productRepository.getAllProducts();
        ArrayList<Product> productList = new ArrayList<>(products);
        ProductAdapter adapter = new ProductAdapter(this, productList);
        ListView listView = findViewById(R.id.lvProduct);
        listView.setAdapter(adapter);
    }

    private void setupEventHandlers() {
        ivCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        });

        btnLogOut.setOnClickListener(v -> {
            SessionManager sessionManager = new SessionManager(getApplicationContext());
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            userRepository.updateStatusUser(sessionManager.getUsername(), false);
            sessionManager.logout(); // Đăng xuất người dùng
            startActivity(intent);
            finish(); // Kết thúc MainActivity để người dùng không thể quay lại bằng nút back
        });
    }
}