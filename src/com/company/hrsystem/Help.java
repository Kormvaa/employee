package com.company.hrsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Help {

    private final List<Employee> employees;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMATTER;

    public Help(List<Employee> employees, Scanner scanner, DateTimeFormatter formatter) {
        this.employees = employees;
        this.scanner = scanner;
        this.DATE_FORMATTER = formatter;
    }

    // Find employee by ID or name
    public Employee findEmployee(String term) {
        try {
            int id = Integer.parseInt(term);
            return employees.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElse(null);
        } catch (Exception ignored) {
            String lower = term.toLowerCase();
            return employees.stream()
                    .filter(e -> e.getFullName().toLowerCase().contains(lower))
                    .findFirst()
                    .orElse(null);
        }
    }

    // Safely get a date input
    public LocalDate getDateInput(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format.");
            }
        }
    }

    // Safely get int input
    public int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("❌ Enter a number: ");
            }
        }
    }

    // Safely get double input
    public double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("❌ Enter a valid number: ");
            }
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
