package edu.tlu.slide3;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CountryListActivity extends AppCompatActivity {
    private ListView lvCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_country_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvCountries = findViewById(R.id.lvCountries);
        String[] countries = new String[] {
                "VietNam", "USA", "Japan", "Korea", "China", "Laos", "Cambodia", "France", "Germany", "Thailand", "Malaysia", "The Philipine", "Australia", "Mongolia", "Venezuela", "Russia", "Angola", "Ukrainian", "Jamaica"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);
        lvCountries.setAdapter(adapter);

        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(CountryListActivity.this, "Selected country: " + countries[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}