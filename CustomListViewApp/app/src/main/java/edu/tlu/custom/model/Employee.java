package edu.tlu.custom.model;

import android.media.Image;
import android.widget.ImageView;

import edu.tlu.custom.R;

public class Employee {
    private int employeeId;
    private int imageId;
    private String name;
    private String brief;


    public Employee() {
    }


    public Employee(int employeeId, int imageId, String name, String brief) {
        this.employeeId = employeeId;
        this.imageId = imageId;
        this.name = name;
        this.brief = brief;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

}
