package edu.tlu.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.tlu.sqlite.model.Employee;
import edu.tlu.sqlite.sqllite.EmployeeDAO;

public class AddorEditEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmployeeId, etEmployeeName, etEmployeeSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addor_edit_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEmployeeId = findViewById(R.id.etId);
        etEmployeeName = findViewById(R.id.etName);
        etEmployeeSalary = findViewById(R.id.etSalary);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnList).setOnClickListener(this);

        readEmployee();
    }

    private void readEmployee() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");

        if (bundle == null) {
            return;
        }

        String id = bundle.getString("id");

        EmployeeDAO dao = new EmployeeDAO(this);
        Employee employee = dao.getById(id);
        etEmployeeId.setText(employee.getId());
        etEmployeeName.setText(employee.getName());
         etEmployeeSalary.setText("" + employee.getSalary());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave){
            EmployeeDAO dao = new EmployeeDAO(this);
            Employee employee = new Employee();
            employee.setId(etEmployeeId.getText().toString());
            employee.setName(etEmployeeName.getText().toString());
            employee.setSalary(Float.parseFloat(etEmployeeSalary.getText().toString()));

            dao.insert(employee);
            Toast.makeText(this, "New employee has been saved!", Toast.LENGTH_SHORT).show();

        }
    }
}