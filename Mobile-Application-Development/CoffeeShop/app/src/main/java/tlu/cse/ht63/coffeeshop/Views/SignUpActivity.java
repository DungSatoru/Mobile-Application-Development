package tlu.cse.ht63.coffeeshop.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import tlu.cse.ht63.coffeeshop.Models.User;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.UserRepository;

public class SignUpActivity extends AppCompatActivity {
    TextView etFullName, etUsername, etPassword;
    Button btnSignup;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        eventHandler();

    }

    private void init() {
        userRepository = new UserRepository(this);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignUp);
    }

    private void eventHandler() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Kiểm tra trùng lặp username trên SQLite
                if (userRepository.isUserNameExists(username)) {
                    Toast.makeText(SignUpActivity.this, "Username đã tồn tại trên SQLite!", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    User newUser = new User(fullName, username, password, date);
                    userRepository.addUser(newUser);

                    etFullName.setText("");
                    etUsername.setText("");
                    etPassword.setText("");
                }
            }
        });
    }
}