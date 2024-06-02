package edu.tlu.th01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import edu.tlu.th01.Models.User;
import edu.tlu.th01.Models.UserLite;
import edu.tlu.th01.Repository.UserLiteRepository;
import edu.tlu.th01.Repository.UserReponsitory;

public class SignUpActivity extends AppCompatActivity {
    TextView etFullName, etUsername, etPassword;
    Button btnSignup;

    private UserLiteRepository userLiteRepository;
    private UserReponsitory userRepository;

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

        userLiteRepository = new UserLiteRepository(this);
        userRepository = new UserReponsitory();

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Kiểm tra trùng lặp username trên Firebase Firestore
                userRepository.isUsernameExists(username, SignUpActivity.this, new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean exists) {
                        if (exists) {
                            Toast.makeText(SignUpActivity.this, "Username đã tồn tại trên Firebase!", Toast.LENGTH_SHORT).show();
                        } else {
                            User newUser = new User(fullName, username, password);
                            userRepository.addUser(newUser, SignUpActivity.this);

                            etFullName.setText("");
                            etUsername.setText("");
                            etPassword.setText("");

                        }
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "Xảy ra lỗi khi kiểm tra username trên Firebase!", Toast.LENGTH_SHORT).show();
                    }
                });

                // Kiểm tra trùng lặp username trên SQLite
                if (userLiteRepository.isUsernameExists(username)) {
                    Toast.makeText(SignUpActivity.this, "Username đã tồn tại trên SQLite!", Toast.LENGTH_SHORT).show();
                } else {
                    UserLite newUser = new UserLite(fullName, username, password);
                    userLiteRepository.addUser(newUser);

                    etFullName.setText("");
                    etUsername.setText("");
                    etPassword.setText("");
                }
            }
        });
    }
}
