package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManager {
    private List<Employee> employees;
    private int nextId;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Scanner scanner;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.nextId = 1;
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Create sample employees
        employees.add(new Employee(nextId++, "John Doe", "Software Engineer", "IT",
                LocalDate.of(2022, 3, 15), 7500.0, "john.doe@company.com", "097-586-473-82", "Active"));

        employees.add(new Employee(nextId++, "Jane Smith", "HR Manager", "HR",
                LocalDate.of(2020, 6, 20), 8500.0, "jane.smith@company.com", "096-768-567", "Active"));

        employees.add(new Employee(nextId++, "Robert Johnson", "Financial Analyst", "Finance",
                LocalDate.of(2021, 11, 5), 6800.0, "robert.johnson@company.com", "010-934-792", "Active"));

        employees.add(new Employee(nextId++, "Emily Davis", "Marketing Specialist", "Marketing",
                LocalDate.of(2023, 1, 10), 6200.0, "emily.davis@company.com", "017-924-855", "Active"));

        employees.add(new Employee(nextId++, "Michael Wilson", "Operations Manager", "Operations",
                LocalDate.of(2019, 8, 12), 7800.0, "michael.wilson@company.com", "087-654-003", "Inactive"));
    }

    // Add new employee
    public void addEmployee() {
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

        Employee newEmployee = new Employee(nextId++, fullName, position, department,
                hireDate, salary, email, phone, status);
        employees.add(newEmployee);

        System.out.println("\n✅ Employee added successfully!");

        Table table = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX);

        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Position");
        table.addCell("Department");
        table.addCell("Hire Date");
        table.addCell("Salary");
        table.addCell("Email");
        table.addCell("Phone");
        table.addCell("Status");

        table.addCell(String.valueOf(newEmployee.getId()));
        table.addCell(newEmployee.getFullName());
        table.addCell(newEmployee.getPosition());
        table.addCell(newEmployee.getDepartment());
        table.addCell(newEmployee.getHireDate().toString());
        table.addCell(String.valueOf(newEmployee.getSalary()));
        table.addCell(newEmployee.getEmail());
        table.addCell(newEmployee.getPhone());
        table.addCell(newEmployee.getStatus());

        System.out.println("\n=== NEW EMPLOYEE ADDED ===");
        System.out.println(table.render());

        System.out.println("Employee ID: " + newEmployee.getId());
        System.out.println("Details: " + newEmployee);
    }

    // View employee by ID or name
    public void viewEmployee() {
        System.out.println("\n=== VIEW EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name: ");
        String searchTerm = scanner.nextLine().toLowerCase();


        List<Employee> matches = new ArrayList<>();
        for (Employee emp : employees) {

            boolean idMatch = String.valueOf(emp.getId()).contains(searchTerm);
            boolean nameMatch = emp.getFullName().toLowerCase().contains(searchTerm);

            if (idMatch || nameMatch) {
                matches.add(emp);
            }
        }

        if (matches.isEmpty()) {
            System.out.println("❌ No employees match your search.");
            return;
        }

        // Table with headers (9 columns)
        Table table = new Table(9, BorderStyle.UNICODE_BOX_DOUBLE_BORDER);

        table.addCell("ID");
        table.addCell("Full Name");
        table.addCell("Position");
        table.addCell("Department");
        table.addCell("Hire Date");
        table.addCell("Salary");
        table.addCell("Email");
        table.addCell("Phone");
        table.addCell("Status");

        // Add each matched employee
        for (Employee emp : matches) {
            table.addCell(String.valueOf(emp.getId()));
            table.addCell(emp.getFullName());
            table.addCell(emp.getPosition());
            table.addCell(emp.getDepartment());
            table.addCell(emp.getHireDate().format(DATE_FORMATTER));
            table.addCell(String.valueOf(emp.getSalary()));
            table.addCell(emp.getEmail());
            table.addCell(emp.getPhone());
            table.addCell(emp.getStatus());
        }

        System.out.println("\n📋 Search Results:");
        System.out.println(table.render());
    }



    // Update employee
    public void updateEmployee() {
        System.out.println("\n=== UPDATE EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name to update: ");
        String searchTerm = scanner.nextLine();

        Employee employee = findEmployee(searchTerm);
        if (employee == null) {
            System.out.println("❌ Employee not found.");
            return;
        }

        System.out.println("\nCurrent Details:");
        System.out.println(employee);
        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Full Name [" + employee.getFullName() + "]: ");
        String fullName = scanner.nextLine();
        if (!fullName.isEmpty()) employee.setFullName(fullName);

        System.out.print("Position [" + employee.getPosition() + "]: ");
        String position = scanner.nextLine();
        if (!position.isEmpty()) employee.setPosition(position);

        System.out.print("Department [" + employee.getDepartment() + "]: ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) employee.setDepartment(department);

        System.out.print("Salary [" + employee.getSalary() + "]: ");
        String salaryInput = scanner.nextLine();
        if (!salaryInput.isEmpty()) employee.setSalary(Double.parseDouble(salaryInput));

        System.out.print("Email [" + employee.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) employee.setEmail(email);

        System.out.print("Phone [" + employee.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) employee.setPhone(phone);

        System.out.print("Status [" + employee.getStatus() + "]: ");
        String status = scanner.nextLine();
        if (!status.isEmpty()) employee.setStatus(status);

        System.out.println("✅ Employee updated successfully!");
    }

    // Remove employee
    public boolean removeEmployee() {
        System.out.println("\n=== REMOVE EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name to remove: ");
        String searchTerm = scanner.nextLine();

        Employee employee = findEmployee(searchTerm);
        if (employee == null) {
            System.out.println("❌ Employee not found.");
            return false;
        }

        System.out.println("\nEmployee to remove:");
        System.out.println(employee);
        System.out.print("Are you sure you want to remove this employee? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            employees.remove(employee);
            System.out.println("✅ Employee removed successfully!");
            return true;
        } else {
            System.out.println("❌ Removal cancelled.");
            return false;
        }
    }

    // List all employees with filtering
    public void listAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");

        System.out.println("Filter options:");
        System.out.println("1. All employees");
        System.out.println("2. By department");
        System.out.println("3. By status");
        System.out.println("4. By salary range");
        System.out.print("Choose option (1-4): ");

        int choice = getIntInput();
        List<Employee> filtered = new ArrayList<>(employees);

        switch (choice) {



            case 2:
                System.out.print("Enter department: ");
                String dept = scanner.nextLine();
                filtered = filtered.stream()
                        .filter(e -> e.getDepartment().equalsIgnoreCase(dept))
                        .collect(Collectors.toList());
                break;
            case 3:
                System.out.print("Enter status (Active/Inactive): ");
                String status = scanner.nextLine();
                filtered = filtered.stream()
                        .filter(e -> e.getStatus().equalsIgnoreCase(status))
                        .collect(Collectors.toList());
                break;
            case 4:
                System.out.print("Min salary: ");
                double min = getDoubleInput();
                System.out.print("Max salary: ");
                double max = getDoubleInput();
                filtered = filtered.stream()
                        .filter(e -> e.getSalary() >= min && e.getSalary() <= max)
                        .collect(Collectors.toList());
                break;
        }

        if (filtered.isEmpty()) {
            System.out.println("❌ No employees found.");
            return;
        }

        Table table = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX);

        for (Employee e : filtered) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getFullName());
            table.addCell(e.getPosition());
            table.addCell(e.getDepartment());
            table.addCell(e.getHireDate().format(DATE_FORMATTER));
            table.addCell(String.valueOf(e.getSalary()));
            table.addCell(e.getEmail());
            table.addCell(e.getPhone());
            table.addCell(e.getStatus());
        }

        System.out.println("\n📊 Employee List:");
        System.out.println(table.render());
        System.out.println("Total employees: " + filtered.size());
    }


    // Search employees
    public void searchEmployees() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.print("Enter search term (name, position, department, etc.): ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<Employee> results = employees.stream()
                .filter(emp ->
                        emp.getFullName().toLowerCase().contains(searchTerm) ||
                                emp.getPosition().toLowerCase().contains(searchTerm) ||
                                emp.getDepartment().toLowerCase().contains(searchTerm) ||
                                emp.getEmail().toLowerCase().contains(searchTerm) ||
                                emp.getStatus().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        Table table = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX);


        for (Employee e : results) {
            table.addCell(String.valueOf(e.getId()));
            table.addCell(e.getFullName());
            table.addCell(e.getPosition());
            table.addCell(e.getDepartment());
            table.addCell(e.getHireDate().format(DATE_FORMATTER));
            table.addCell(String.valueOf(e.getSalary()));
            table.addCell(e.getEmail());
            table.addCell(e.getPhone());
            table.addCell(e.getStatus());
        }

        if (results.isEmpty()) {
            System.out.println("❌ No employees found matching your search.");
        } else {
            System.out.println("\n🔍 Search Results (" + results.size() + " found):");
            for (Employee emp : results) {
                System.out.println(emp);
            }
        }
    }

    // Generate reports
    public void generateReports() {
        System.out.println("\n=== REPORTS ===");

        // Basic statistics
        long totalEmployees = employees.size();
        long activeEmployees = employees.stream().filter(emp -> "Active".equals(emp.getStatus())).count();
        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        double avgSalary = totalEmployees > 0 ? totalSalary / totalEmployees : 0;

        System.out.println("📊 Employee Statistics:");
        System.out.println("• Total Employees: " + totalEmployees);
        System.out.println("• Active Employees: " + activeEmployees);
        System.out.println("• Inactive Employees: " + (totalEmployees - activeEmployees));
        System.out.printf("• Average Salary: $%.2f%n", avgSalary);
        System.out.printf("• Total Salary Expense: $%.2f%n", totalSalary);

        // Department headcount
        System.out.println("\n🏢 Department Headcount:");
        Map<String, Long> deptCount = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        deptCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    long count = entry.getValue();
                    double deptAvgSalary = employees.stream()
                            .filter(emp -> emp.getDepartment().equals(entry.getKey()))
                            .mapToDouble(Employee::getSalary)
                            .average()
                            .orElse(0.0);
                    System.out.printf("• %s: %d employees (Avg Salary: $%.2f)%n",
                            entry.getKey(), count, deptAvgSalary);
                });

        // Salary distribution
        System.out.println("\n💰 Salary Distribution:");
        System.out.println("• < $50,000: " + employees.stream().filter(emp -> emp.getSalary() < 50000).count());
        System.out.println("• $50,000 - $75,000: " + employees.stream().filter(emp -> emp.getSalary() >= 50000 && emp.getSalary() < 75000).count());
        System.out.println("• $75,000 - $100,000: " + employees.stream().filter(emp -> emp.getSalary() >= 75000 && emp.getSalary() < 100000).count());
        System.out.println("• ≥ $100,000: " + employees.stream().filter(emp -> emp.getSalary() >= 100000).count());
    }

    // Export data
    public void exportData() {
        System.out.println("\n=== EXPORT DATA ===");
        System.out.println("1. Export to CSV");
        System.out.println("2. Export to JSON");
        System.out.print("Choose format (1-2): ");

        int choice = getIntInput();
        StringBuilder exportData = new StringBuilder();

        if (choice == 1) {
            // CSV header
            exportData.append("ID,Full Name,Position,Department,Hire Date,Salary,Email,Phone,Status\n");
            for (Employee emp : employees) {
                exportData.append(emp.toCSV()).append("\n");
            }
        } else if (choice == 2) {
            // JSON format
            exportData.append("[\n");
            for (int i = 0; i < employees.size(); i++) {
                exportData.append(employees.get(i).toJSON());
                if (i < employees.size() - 1) exportData.append(",");
                exportData.append("\n");
            }
            exportData.append("]");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("\n📁 Exported Data:");
        System.out.println("============================================================================================");
        System.out.println(exportData.toString());
        System.out.println("============================================================================================");
        System.out.println("✅ Data exported successfully! You can copy this data to a file.");
    }

    // Import data (simplified - from console input)
    public void importData() {
        System.out.println("\n=== IMPORT DATA ===");
        System.out.println("Note: This is a simplified import. In a real application, you would read from files.");
        System.out.println("Paste your data here (type 'END' on a new line to finish):");

        StringBuilder importData = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            importData.append(line).append("\n");
        }

        System.out.println("Data received for import:");
        System.out.println(importData.toString());
        System.out.println("✅ Import functionality would process this data in a full implementation.");
    }

    // Helper methods
    private Employee findEmployee(String searchTerm) {
        try {
            int id = Integer.parseInt(searchTerm);
            return employees.stream()
                    .filter(emp -> emp.getId() == id)
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            // Search by name
            return employees.stream()
                    .filter(emp -> emp.getFullName().toLowerCase().contains(searchTerm.toLowerCase()))
                    .findFirst()
                    .orElse(null);
        }
    }

    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format. Please use YYYY-MM-DD.");
            }
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Please enter a number: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input. Please enter a number: ");
            }
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}