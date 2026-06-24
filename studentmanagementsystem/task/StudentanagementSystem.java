import java.util.ArrayList;
import java.util.Scanner;

class Student {
    int id;
    String name;
    String rollNo;
    String course;
    int year;
    double cgpa;
    String email;
    String phone;

    Student(int id, String name, String rollNo, String course,
            int year, double cgpa, String email, String phone) {
        this.id = id;
        this.name = name;
        this.rollNo = rollNo;
        this.course = course;
        this.year = year;
        this.cgpa = cgpa;
        this.email = email;
        this.phone = phone;
    }

    void display() {
        System.out.println("--------------------------------");
        System.out.println("ID      : " + id);
        System.out.println("Name    : " + name);
        System.out.println("Roll No : " + rollNo);
        System.out.println("Course  : " + course);
        System.out.println("Year    : " + year);
        System.out.println("CGPA    : " + cgpa);
        System.out.println("Email   : " + email);
        System.out.println("Phone   : " + phone);
    }
}

public class StudentManagementSystem {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextId = 1;

    public static void addStudent() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Roll No: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Year: ");
        int year = Integer.parseInt(sc.nextLine());

        System.out.print("Enter CGPA: ");
        double cgpa = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        students.add(new Student(
                nextId++, name, rollNo, course,
                year, cgpa, email, phone));

        System.out.println("Student Added Successfully!");
    }

    public static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No Students Found!");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    public static void searchStudent() {
        System.out.print("Enter Roll No: ");
        String roll = sc.nextLine();

        boolean found = false;

        for (Student s : students) {
            if (s.rollNo.equalsIgnoreCase(roll)) {
                s.display();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Student Not Found!");
        }
    }

    public static void updateStudent() {
        System.out.print("Enter Roll No to Update: ");
        String roll = sc.nextLine();

        for (Student s : students) {

            if (s.rollNo.equalsIgnoreCase(roll)) {

                System.out.print("New Name: ");
                s.name = sc.nextLine();

                System.out.print("New Course: ");
                s.course = sc.nextLine();

                System.out.print("New Year: ");
                s.year = Integer.parseInt(sc.nextLine());

                System.out.print("New CGPA: ");
                s.cgpa = Double.parseDouble(sc.nextLine());

                System.out.print("New Email: ");
                s.email = sc.nextLine();

                System.out.print("New Phone: ");
                s.phone = sc.nextLine();

                System.out.println("Student Updated Successfully!");
                return;
            }
        }

        System.out.println("Student Not Found!");
    }

    public static void deleteStudent() {
        System.out.print("Enter Roll No to Delete: ");
        String roll = sc.nextLine();

        for (Student s : students) {
            if (s.rollNo.equalsIgnoreCase(roll)) {
                students.remove(s);
                System.out.println("Student Deleted Successfully!");
                return;
            }
        }

        System.out.println("Student Not Found!");
    }

    public static void main(String[] args) {

        students.add(new Student(
                nextId++, "Ananya Sharma",
                "CS2401", "Computer Science",
                2, 8.7,
                "ananya@college.edu",
                "9876543210"));

        students.add(new Student(
                nextId++, "Rohan Mehta",
                "CS2402", "Information Technology",
                3, 7.4,
                "rohan@college.edu",
                "9876543211"));

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    System.out.println("Thank You!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}