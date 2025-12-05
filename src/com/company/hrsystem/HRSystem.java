package com.company.hrsystem;

import java.io.*;
import java.util.*;
import org.nocrala.tools.texttablefmt.Table;
import org.nocrala.tools.texttablefmt.BorderStyle;

public class HRSystem {

    private List<Employee> employees = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private int nextId = 1;

    private final String DATA_FILE = "employees.dat";

    public static void main(String[] args) {
        new HRSystem().start();
    }

    public void start() {
        loadEmployees(); // Load employees from file
        if (employees.isEmpty()) {
            // Generate 50 random employees if file empty
            employees.addAll(RandomEmployeeGenerator.generateRandomEmployees(50, nextId));
            nextId += 50;
        } else {
            // Update nextId to avoid duplicate IDs
            nextId = employees.stream().mapToInt(Employee::getId).max().orElse(0) + 1;
        }

        displayWelcomeMessage();

        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            processChoice(choice);
        }

        saveEmployees(); // Save before exit
        scanner.close();
        System.out.println("Thank you for using the Employee Management System!");
    }

    private void displayWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("    EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("Welcome! Your HR Management Solution\n");
    }

    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add New Employee");
        System.out.println("2. View Employee Details");
        System.out.println("3. Update Employee Information");
        System.out.println("4. Remove Employee");
        System.out.println("5. List All Employees");
        System.out.println("6. Search Employees");
        System.out.println("7. Generate Reports");
        System.out.println("8. Import Data");
        System.out.println("0. Exit");
        System.out.print("Choose an option (0-8): ");
    }

    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1 -> { // Add Employee
                AddEmployee adder = new AddEmployee(employees, nextId, scanner);
                adder.addEmployee();
                nextId++;
            }
            case 2 -> { // View Employee
                ViewEmployee viewer = new ViewEmployee(employees, scanner);
                viewer.viewEmployee();
            }
            case 3 -> { // Update Employee
                UpdateEmployee updater = new UpdateEmployee(employees, scanner);
                updater.updateEmployee();
            }
            case 4 -> { // Remove Employee
                RemoveEmployee remover = new RemoveEmployee(employees, scanner);
                remover.removeEmployee();
            }
            case 5 -> { // List All Employees
                ListAllEmployee lister = new ListAllEmployee(employees, scanner);
                lister.listAll();
            }
            case 6 -> { // Search Employees
                SearchEmployee searcher = new SearchEmployee(employees, scanner);
                searcher.search();
            }
            case 7 -> { // Generate Reports
                GenerateReport reporter = new GenerateReport(employees);
                reporter.generate();
            }
            case 8 -> { // Import Data
                ImportData importer = new ImportData(employees, scanner, nextId);
                importer.importData();
                nextId = importer.getNextId();
            }
            case 0 -> running = false;
            default -> System.out.println("❌ Invalid choice. Please select 0-8.");
        }
    }

    // -------------------- Save & Load --------------------

    private void saveEmployees() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(employees);
            System.out.println("✅ Employee data saved successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void loadEmployees() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            employees = (List<Employee>) in.readObject();
            System.out.println("✅ Employee data loaded. Total: " + employees.size());
        } catch (Exception e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
        }
    }
}
