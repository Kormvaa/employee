package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ListAllEmployee {

    private final List<Employee> employees;
    private final Scanner scanner;

    public ListAllEmployee(List<Employee> employees, Scanner scanner) {
        this.employees = employees;
        this.scanner = scanner;
    }

    public void listAll() {
        System.out.println("\n=== LIST EMPLOYEES ===");
        System.out.println("Filter by:");
        System.out.println("1. Name");
        System.out.println("2. Position");
        System.out.println("3. Department");
        System.out.println("4. Status");
        System.out.println("5. Salary Range");
        System.out.println("6. All Employees");
        System.out.print("Choose option (1-6): ");

        int choice = getIntInput();
        List<Employee> list = new ArrayList<>(employees);

        switch (choice) {
            case 1 -> { // Name
                System.out.print("Enter a letter or full name: ");
                String input = scanner.nextLine().toLowerCase();
                list = list.stream()
                        .filter(e -> e.getFullName().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case 2 -> { // Position
                System.out.print("Enter position: ");
                String input = scanner.nextLine().toLowerCase();
                list = list.stream()
                        .filter(e -> e.getPosition().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case 3 -> { // Department
                System.out.print("Enter department: ");
                String input = scanner.nextLine().toLowerCase();
                list = list.stream()
                        .filter(e -> e.getDepartment().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case 4 -> { // Status
                System.out.print("Enter status (Active/Inactive): ");
                String input = scanner.nextLine().toLowerCase();
                list = list.stream()
                        .filter(e -> e.getStatus().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case 5 -> { // Salary Range
                System.out.print("Min salary: ");
                double min = getDoubleInput();
                System.out.print("Max salary: ");
                double max = getDoubleInput();
                list = list.stream()
                        .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                        .collect(Collectors.toList());
            }
            case 6 -> { /* All employees, do nothing */ }
            default -> {
                System.out.println("❌ Invalid choice. Showing all employees.");
            }
        }

        if (list.isEmpty()) {
            System.out.println("❌ No employees found.");
            return;
        }

        displayTable(list);
    }

    // ---------------- Helper Methods ----------------

    private void displayTable(List<Employee> list) {
        Table table = new Table(8, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);
        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Position");
        table.addCell("Department");
        table.addCell("Hire Date");
        table.addCell("Salary");
        table.addCell("Email");
        table.addCell("Phone");

        for (Employee e : list) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getFullName());
            table.addCell(e.getPosition());
            table.addCell(e.getDepartment());
            table.addCell(e.getHireDate().toString());
            table.addCell(String.valueOf(e.getSalary()));
            table.addCell(e.getEmail());
            table.addCell(e.getPhone());
        }

        System.out.println("\n📊 Employee List:");
        System.out.println(table.render());
        System.out.println("Total: " + list.size());
    }

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
}
