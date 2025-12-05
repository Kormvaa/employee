package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ImportData {

    private final List<Employee> employees;
    private final Scanner scanner;
    private int nextId; // To assign IDs to new employees

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ImportData(List<Employee> employees, Scanner scanner, int nextId) {
        this.employees = employees;
        this.scanner = scanner;
        this.nextId = nextId;
    }

    public void importData() {
        System.out.println("\n=== IMPORT EMPLOYEE DATA ===");
        System.out.print("How many employees do you want to add? ");
        int count = getIntInput();

        for (int i = 0; i < count; i++) {
            System.out.println("\nEmployee #" + (i + 1));

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
            System.out.println("✅ Employee added successfully!");

            // Optional: show added employee in table
            Table table = createEmployeeTable();
            addEmployeeToTable(table, emp);
            System.out.println(table.render());
        }
    }

    // ---------------- Helper Methods ----------------

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("❌ Enter a valid number: ");
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

    private LocalDate getDateInput(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Try again (YYYY-MM-DD).");
            }
        }
    }

    private Table createEmployeeTable() {
        Table table = new Table(8, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Position");
        table.addCell("Department");
        table.addCell("Hire Date");
        table.addCell("Salary");
        table.addCell("Email");
        table.addCell("Phone");
        return table;
    }

    private void addEmployeeToTable(Table table, Employee e) {
        table.addCell(String.valueOf(e.getId()));
        table.addCell(e.getFullName());
        table.addCell(e.getPosition());
        table.addCell(e.getDepartment());
        table.addCell(e.getHireDate().toString());
        table.addCell(String.valueOf(e.getSalary()));
        table.addCell(e.getEmail());
        table.addCell(e.getPhone());
    }

    // Optional getter to return updated nextId
    public int getNextId() {
        return nextId;
    }
}
