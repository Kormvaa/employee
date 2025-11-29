package com.company.hrsystem;

import com.company.hrsystem.utils.SecurityManager;

import java.util.Scanner;

public class HRSystem {
    private EmployeeManager employeeManager;
    private SecurityManager securityManager;
    private Scanner scanner;
    private boolean running;

    public HRSystem() {
        this.employeeManager = new EmployeeManager();
        this.securityManager = new SecurityManager();
        this.scanner = new Scanner(System.in);
        this.running = true;
    }

    public void start() {
        displayWelcomeMessage();

        while (running) {
            displayMenu();
            int choice = getMenuChoice();
            processChoice(choice);
        }

        scanner.close();
        System.out.println("Thank you for using the Employee Management System!");
    }

    private void displayWelcomeMessage() {
        System.out.println("=========================================");
        System.out.println("    EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("=========================================");
        System.out.println("Welcome! Your HR Management Solution");
        System.out.println();
    }

    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1.  Add New Employee");
        System.out.println("2.  View Employee Details");
        System.out.println("3.  Update Employee Information");
        System.out.println("4.  Remove Employee");
        System.out.println("5.  List All Employees");
        System.out.println("6.  Search Employees");
        System.out.println("7.  Generate Reports");
        System.out.println("8.  Export Data");
        System.out.println("9.  Import Data");
        System.out.println("10. Display Help");
        System.out.println("0.  Exit");
        System.out.print("Choose an option (0-10): ");
    }

    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processChoice(int choice) {
        switch (choice) {
            case 1:
                employeeManager.addEmployee();
                break;
            case 2:
                employeeManager.viewEmployee();
                break;
            case 3:
                employeeManager.updateEmployee();
                break;
            case 4:
                if (securityManager.requireAuthentication("Remove Employee")) {
                    employeeManager.removeEmployee();
                }
                break;
            case 5:
                employeeManager.listAllEmployees();
                break;
            case 6:
                employeeManager.searchEmployees();
                break;
            case 7:
                employeeManager.generateReports();
                break;
            case 8:
                if (securityManager.requireAuthentication("Export Data")) {
                    employeeManager.exportData();
                }
                break;
            case 9:
                if (securityManager.requireAuthentication("Import Data")) {
                    employeeManager.importData();
                }
                break;
            case 10:
                displayHelp();
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("❌ Invalid choice. Please select 0-10.");
        }
    }

    private void displayHelp() {
        System.out.println("\n=== HELP & INSTRUCTIONS ===");
        System.out.println("1. Add New Employee: Create a new employee record");
        System.out.println("2. View Employee: Search and view employee details by ID or name");
        System.out.println("3. Update Employee: Modify existing employee information");
        System.out.println("4. Remove Employee: Delete employee record (requires password)");
        System.out.println("5. List All Employees: View all employees with filtering options");
        System.out.println("6. Search Employees: Search across all employee fields");
        System.out.println("7. Generate Reports: View statistics and department headcount");
        System.out.println("8. Export Data: Export to CSV or JSON format (requires password)");
        System.out.println("9. Import Data: Import employee data (requires password)");
        System.out.println("10. Help: Display this help message");
        System.out.println("0. Exit: Close the application");
        System.out.println("\n🔒 Security Note:");
        System.out.println("   - Administrator password: company123");
        System.out.println("   - Password required for: Remove, Export, Import operations");
    }

    public static void main(String[] args) {
        HRSystem hrSystem = new HRSystem();
        hrSystem.start();
    }
}