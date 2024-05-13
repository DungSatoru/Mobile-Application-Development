package edu.tlu.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import edu.tlu.sqlite.adapter.EmployeeAdapter;
import edu.tlu.sqlite.model.Employee;
import edu.tlu.sqlite.sqllite.EmployeeDAO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EmployeeAdapter employeeAdapter;
    private ListView lvEmployee;
    private String employeeId;

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
//
//        DBHelper dbHelper = new DBHelper(this);
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//
//        database.close();

        findViewById(R.id.btnAdd).setOnClickListener(this);
        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);

        lvEmployee = findViewById(R.id.lvEmployee);
        EmployeeDAO dao = new EmployeeDAO(this);
        List<Employee> list = dao.getAll();

        employeeAdapter = new EmployeeAdapter(this, list);
        lvEmployee.setAdapter(employeeAdapter);
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee = list.get(position);
                employeeId = employee.getId();
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddorEditEmployeeActivity.class);
        if(v.getId() == R.id.btnAdd) {
            startActivity(intent);
        }
        else if(v.getId() == R.id.btnEdit) {
            if (employeeId == null) {
                Toast.makeText(this, "Employee ID must be selected!", Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("id", employeeId);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }
        else if (v.getId() == R.id.btnDelete) {
            Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
        }
    }
}


