package edu.tlu.custom.adapter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.tlu.custom.R;
import edu.tlu.custom.model.Employee;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> list;

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    public List<Employee> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < 0) return null;
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
        TextView tvBrief = convertView.findViewById(R.id.tvBrief);
        ImageView ivImage = convertView.findViewById(R.id.ivImage);

        Employee emp = list.get(position);
        tvName.setText(emp.getName());
        tvBrief.setText(emp.getBrief());
        ivImage.setImageResource(emp.getImageId());

        return convertView;
    }
}
