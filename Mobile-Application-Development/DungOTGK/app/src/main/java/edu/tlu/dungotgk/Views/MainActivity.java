package edu.tlu.dungotgk.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import edu.tlu.dungotgk.Adapters.ProductAdapter;
import edu.tlu.dungotgk.Models.Product;
import edu.tlu.dungotgk.R;
import edu.tlu.dungotgk.Repositories.ProductRepository;

public class MainActivity extends AppCompatActivity {
    ImageView ivCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        eventHandler();

    }

    private void init() {
        ivCart = findViewById(R.id.ivCart);

        ProductRepository productRepository = new ProductRepository(this);
//        for (int i = 0; i < 12; i++) {
//            String resourceName = "ip" + i%6;
//            double price = 1000000*i; // Giả sử giá tiền nằm trong khoảng từ 0 đến 100000000
//            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
//            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resourceId);
//            productRepository.addProduct(new Product("Samsung Galaxy S70", "Dung lượng 128GB", price, uri));
//        }

        List<Product> products = productRepository.getAllProducts();
        ArrayList<Product> productList = new ArrayList<>(products);

        // Khởi tạo adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        // Khởi tạo ListView và thiết lập adapter
        ListView listView = findViewById(R.id.lvProduct);
        listView.setAdapter(adapter);
    }


    private void eventHandler() {
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }


        });
    }

}