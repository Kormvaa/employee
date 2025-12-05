package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SearchEmployee {

    private final Scanner scanner;
    private final List<Employee> employees;

    public SearchEmployee(List<Employee> employees, Scanner scanner) {
        this.employees = employees;
        this.scanner = scanner;
    }

    public void search() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.println("Search by:");
        System.out.println("1. ID");
        System.out.println("2. Full Name (default)");
        System.out.println("3. Position");
        System.out.println("4. Department");
        System.out.println("5. Status");
        System.out.print("Choose (1-5, default 2): ");
        String choice = scanner.nextLine();

        String input;
        List<Employee> results;

        switch (choice) {
            case "1" -> { // ID
                System.out.print("Enter Employee ID: ");
                input = scanner.nextLine();
                results = employees.stream()
                        .filter(e -> String.valueOf(e.getId()).equals(input))
                        .collect(Collectors.toList());
            }
            case "3" -> { // Position
                System.out.print("Enter Position: ");
                input = scanner.nextLine().toLowerCase();
                results = employees.stream()
                        .filter(e -> e.getPosition().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case "4" -> { // Department
                System.out.print("Enter Department: ");
                input = scanner.nextLine().toLowerCase();
                results = employees.stream()
                        .filter(e -> e.getDepartment().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            case "5" -> { // Status
                System.out.print("Enter Status (Active/Inactive): ");
                input = scanner.nextLine().toLowerCase();
                results = employees.stream()
                        .filter(e -> e.getStatus().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
            default -> { // Name
                System.out.print("Enter a letter or full name: ");
                input = scanner.nextLine().toLowerCase();
                results = employees.stream()
                        .filter(e -> e.getFullName().toLowerCase().contains(input))
                        .collect(Collectors.toList());
            }
        }

        if (results.isEmpty()) {
            System.out.println("❌ No employees found.");
            return;
        }

        displayTable(results);
    }

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

        list.forEach(e -> {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getFullName());
            table.addCell(e.getPosition());
            table.addCell(e.getDepartment());
            table.addCell(e.getHireDate().toString());
            table.addCell(String.valueOf(e.getSalary()));
            table.addCell(e.getEmail());
            table.addCell(e.getPhone());
        });

        System.out.println("\n🔍 Search Results:");
        System.out.println(table.render());
        System.out.println("Total: " + list.size());
    }
}
