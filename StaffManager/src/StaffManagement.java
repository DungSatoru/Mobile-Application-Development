import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffManagement {
    private List<Staff> staffs = new ArrayList<>();
    private int nextId = 1;

    public void initializeStaffs() {
        for (int i = 0; i < 10; i++) {
            staffs.add(new Staff(nextId++, "Staff" + i, 25 + i, "IT", "E" + i, 1000 + i * 100));
        }
    }

    public void displayStaffs() {
        System.out.println("List of Staffs:");
        for (Staff employee : staffs) {
            System.out.println(employee);
        }
    }

    public void addStaff(Scanner sc) {
        System.out.print("Enter employee name: ");
        sc.next();
        String name = sc.nextLine();
        System.out.print("Enter employee age: ");
        int age = sc.nextInt();
        System.out.print("Enter employee department: ");
        sc.next();
        String department = sc.nextLine();
        System.out.print("Enter employee code: ");
        String code = sc.nextLine();
        System.out.print("Enter employee salary rate:");
        double salaryRate = sc.nextDouble();

        staffs.add(new Staff(nextId++, name, age, department, code, salaryRate));
        System.out.println("Staff added successfully.");
    }

    public void removeStaff(Scanner sc) {
        System.out.println("Enter employee ID to remove:");
        int idToRemove = sc.nextInt();
        boolean removed = false;
        for (Staff employee : staffs) {
            if (employee.getId() == idToRemove) {
                staffs.remove(employee);
                System.out.println("Staff removed successfully.");
                removed = true;
                break;
            }
        }
        if (!removed) {
            System.out.println("Staff with ID " + idToRemove + " not found.");
        }
    }
}
