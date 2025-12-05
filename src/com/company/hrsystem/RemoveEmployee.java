package com.company.hrsystem;

import java.util.List;
import java.util.Scanner;

public class RemoveEmployee {

    private final List<Employee> employees;
    private final Scanner scanner;

    public RemoveEmployee(List<Employee> employees, Scanner scanner) {
        this.employees = employees;
        this.scanner = scanner;
    }

    public void removeEmployee() {
        System.out.println("\n=== REMOVE EMPLOYEE ===");

        // Ask for search type
        System.out.println("Search employee by:");
        System.out.println("1. ID");
        System.out.println("2. Full Name");
        System.out.print("Choose (1-2, default 2): ");
        String choice = scanner.nextLine();
        String term;

        if ("1".equals(choice)) {
            System.out.print("Enter Employee ID: ");
            term = scanner.nextLine();
        } else {
            System.out.print("Enter Full Name: ");
            term = scanner.nextLine();
        }

        Employee emp = findEmployee(term, choice);
        if (emp == null) {
            System.out.println("❌ Employee not found.");
            return;
        }

        employees.remove(emp);
        System.out.println("✅ Employee removed successfully!");
    }

    private Employee findEmployee(String term, String choice) {
        for (Employee e : employees) {
            if ("1".equals(choice) && String.valueOf(e.getId()).equals(term)) {
                return e;
            } else if (!"1".equals(choice) && e.getFullName().equalsIgnoreCase(term)) {
                return e;
            }
        }
        return null;
    }
}
