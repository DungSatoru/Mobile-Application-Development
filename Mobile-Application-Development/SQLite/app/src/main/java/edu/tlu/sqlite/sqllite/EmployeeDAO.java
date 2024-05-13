package edu.tlu.sqlite.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.tlu.sqlite.model.Employee;

public class EmployeeDAO {
    private SQLiteDatabase db;

    public EmployeeDAO(Context context) {
        DBHelper helper = new DBHelper(context);

        db = helper.getReadableDatabase();
    }

    public List<Employee> get(String sql, String ...selectArgs){
        List<Employee> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()){
            String Id = cursor.getString(cursor.getColumnIndex("Id"));
            String Name = cursor.getString(cursor.getColumnIndex("Name"));
            float Salary = cursor.getFloat(cursor.getColumnIndex("Salary"));

            Employee emp = new Employee(Id, Name, Salary);

            list.add(emp);
        }
        return list;
    }

    public List<Employee> getAll(){
        String sql = "SELECT * FROM NhanVien";
        return get(sql);
    }
    public Employee getById(String Id) {
        String sql = "SELECT * FROM NhanVien WHERE Id = ?";
        List<Employee> list = get(sql, Id);;
        return list.get(0);
    }
    public long insert(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("Id", emp.getId());
        values.put("Name", emp.getName());
        values.put("Salary", emp.getSalary());

        return db.insert("NhanVien", null, values);
    }
    public long update(Employee emp) {
        ContentValues values = new ContentValues();
        values.put("Name", emp.getName());
        values.put("Salary", emp.getSalary());

        return db.update("NhanVien", values, "Id = ?", new String[]{emp.getId()});
    }
    public int delete(String Id) {
        return db.delete("NhanVien", "Id = ?", new String[]{Id});
    }
}
