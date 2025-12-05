package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AddEmployee {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final Scanner scanner = new Scanner(System.in);

    private final List<Employee> employees;
    private int nextId;

    // Constructor
    public AddEmployee(List<Employee> employees, int startId, Scanner scanner) {
        this.employees = employees;
        this.nextId = startId;
    }

    /**
     * Public method to add a new employee.
     * Returns the created employee object.
     */
    public Employee addEmployee() {
        System.out.println("\n=== ADD NEW EMPLOYEE ===");

        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();

        System.out.print("Position: ");
        String position = scanner.nextLine();

        System.out.print("Department: ");
        String department = scanner.nextLine();

        LocalDate hireDate = getDateInput("Hire Date (YYYY-MM-DD): ");

        System.out.print("Salary: ");
        double salary = getDoubleInput();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Status (Active/Inactive): ");
        String status = scanner.nextLine();

        Employee emp = new Employee(
                nextId++, fullName, position, department,
                hireDate, salary, email, phone, status
        );

        employees.add(emp);
        System.out.println("\n✅ Employee added successfully!");

        printEmployee(emp);

        return emp;
    }

    // Helper methods ──────────────────────────────────────────────────

    private LocalDate getDateInput(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Try again.");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("❌ Enter a valid number: ");
            }
        }
    }

    private void printEmployee(Employee emp) {
        Table table = new Table(8, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Position");
        table.addCell("Department");
        table.addCell("Hire Date");
        table.addCell("Salary");
        table.addCell("Email");
        table.addCell("Phone");

        table.addCell(String.valueOf(emp.getId()));
        table.addCell(emp.getFullName());
        table.addCell(emp.getPosition());
        table.addCell(emp.getDepartment());
        table.addCell(emp.getHireDate().toString());
        table.addCell(String.valueOf(emp.getSalary()));
        table.addCell(emp.getEmail());
        table.addCell(emp.getPhone());

        System.out.println(table.render());
    }

    // Optional getter
    public List<Employee> getEmployees() {
        return employees;
    }
}
