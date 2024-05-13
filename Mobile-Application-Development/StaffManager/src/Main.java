/*
Viết 1 ứng dụng bằng Java quản lý nhân viên:
- Tạo lớp Employee với các thuộc tính:
	+ id
 	+ name
	+ age
	+ department
	+ code
	+ salary rate
- Khởi tạo 10 nhân viên
- Hiển thị danh sách 10 nhân viên này ra console
- Chức năng thêm một nhân viên mới vào danh sách
- Chức năng xóa một nhân viên từ danh sách.
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StaffManagement staffManagement = new StaffManagement();
        staffManagement.initializeStaffs();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add a new staff");
            System.out.println("2. Remove an staff");
            System.out.println("3. Display all staff");
            System.out.println("4. Exit");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    staffManagement.addStaff(scanner);
                    break;
                case 2:
                    staffManagement.removeStaff(scanner);
                    break;
                case 3:
                    staffManagement.displayStaffs();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
