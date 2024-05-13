package edu.tlu.sqlite.model;

public class Employee {
    private String Id, Name;
    private float Salary;

    public Employee() {
    }

    public Employee(String id, String name, float salary) {
        Id = id;
        Name = name;
        Salary = salary;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public float getSalary() {
        return Salary;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSalary(float salary) {
        Salary = salary;
    }
}
