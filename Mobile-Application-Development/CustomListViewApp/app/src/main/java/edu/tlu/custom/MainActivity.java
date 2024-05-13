package edu.tlu.custom;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.tlu.custom.adapter.EmployeeAdapter;
import edu.tlu.custom.model.Employee;

public class MainActivity extends AppCompatActivity {
    private EmployeeAdapter adapter;
    private List<Employee> list;

    public MainActivity() {
        list = new ArrayList<>();
        list.add(new Employee(1, R.drawable.profile, "Hạ Quang Dũng", "BrSE"));
        list.add(new Employee(2, R.drawable.baseline_account_box_24, "Đỗ Hữu Tuấn", "DevOps"));
        list.add(new Employee(3, R.drawable.baseline_account_box_24, "Lê Đình Tú", "Progamer"));
        list.add(new Employee(4, R.drawable.baseline_account_box_24, "Nguyễn Thị Thơm", "BA"));
        list.add(new Employee(5, R.drawable.baseline_account_box_24, "Nguyễn Thị Hồng Nhung", "BA"));
        list.add(new Employee(6, R.drawable.baseline_account_box_24, "Lê Thị Lý", "Tester"));
        adapter = new EmployeeAdapter(this, list);
    }

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

        ListView lv = findViewById(R.id.lvEmployees);
        lv.setAdapter(adapter);

    }
}