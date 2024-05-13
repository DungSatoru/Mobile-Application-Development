package edu.tlu.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.tlu.sqlite.R;
import edu.tlu.sqlite.model.Employee;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> list;

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_employee_item, null);
        }
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvSalary = convertView.findViewById(R.id.tvSalary);

        Employee employee = list.get(position);
        tvName.setText(employee.getName());
        tvSalary.setText("" + employee.getSalary());

        return convertView;
    }
}
