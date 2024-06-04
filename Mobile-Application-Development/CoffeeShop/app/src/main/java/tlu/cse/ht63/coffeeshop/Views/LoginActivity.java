package tlu.cse.ht63.coffeeshop.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import tlu.cse.ht63.coffeeshop.Models.User;
import tlu.cse.ht63.coffeeshop.R;
import tlu.cse.ht63.coffeeshop.Repositories.UserRepository;
import tlu.cse.ht63.coffeeshop.Services.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private UserRepository userRepository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setClickListeners();

        // Kiểm tra nếu người dùng đã đăng nhập, chuyển hướng đến MainActivity
        if (sessionManager.isLoggedIn()) {
            startMainActivity(sessionManager.getUsername(), sessionManager.getFullName());
        }
    }

    private void initViews() {
        userRepository = new UserRepository(this);
        sessionManager = new SessionManager(getApplicationContext());

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void setClickListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSignUp();
            }
        });
    }

    private void login() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if (userRepository.checkUserCredentials(username, password)) {
            User user = userRepository.getUser(username, password);

            userRepository.updateStatusUser(username, true);
            sessionManager.setLoggedIn(user.getId(), user.getUserName(), user.getFullName(), true);
            if (user != null) {
                startMainActivity(username, user.getFullName());
            } else {
                showToast("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        } else {
            showToast("Tên đăng nhập hoặc mật khẩu không chính xác!");
        }
    }

    private void startMainActivity(String username, String fullName) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("fullName", fullName);
        intent.putExtra("userName", username);
        startActivity(intent);
        finish();
    }

    private void navigateToSignUp() {
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
