package tlu.cse.ht63.coffeeshop.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
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
    RadioButton rbAdmin, rbStaff;

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
        rbAdmin = findViewById(R.id.rbAdmin);
        rbStaff = findViewById(R.id.rbStaff);

        rbStaff.setChecked(true);
    }

    private void eventHandler() {
        rbAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbStaff.setChecked(false);
            }
        });

        rbStaff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rbAdmin.setChecked(false);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                boolean role = rbAdmin.isChecked();

                // Kiểm tra trống
                if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra trùng lặp username trên SQLite
                if (userRepository.isUserNameExists(username)) {
                    Toast.makeText(SignUpActivity.this, "Username đã tồn tại trên SQLite!", Toast.LENGTH_SHORT).show();
                } else {
                    Date date = new Date();
                    User newUser = new User(fullName, username, password, date, role);
                    userRepository.addUser(newUser);

                    // Xóa dữ liệu trên giao diện sau khi thêm người dùng thành công
                    etFullName.setText("");
                    etUsername.setText("");
                    etPassword.setText("");
                    rbAdmin.setChecked(false);
                    rbStaff.setChecked(false);

                    Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
