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

    // =======================================================
    // SAMPLE DATA
    // =======================================================
    private void initializeSampleData() {
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

    // =======================================================
    // TABLE HELPERS
    // =======================================================
    private Table createEmployeeTable() {
        Table table = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX);
        String[] headers = {"ID", "Full Name", "Position", "Department",
                "Hire Date", "Salary", "Email", "Phone", "Status"};

        for (String h : headers) table.addCell(h);
        return table;
    }

    private void addEmployeeToTable(Table table, Employee e) {
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

    // =======================================================
    // ADD EMPLOYEE
    // =======================================================
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

        Employee emp = new Employee(nextId++, fullName, position, department,
                hireDate, salary, email, phone, status);

        employees.add(emp);
        System.out.println("\n✅ Employee added successfully!");

        Table table = createEmployeeTable();
        addEmployeeToTable(table, emp);

        System.out.println(table.render());
    }

    // =======================================================
    // VIEW EMPLOYEE
    // =======================================================
    public void viewEmployee() {
        System.out.println("\n=== VIEW EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        List<Employee> matches = employees.stream()
                .filter(e ->
                        String.valueOf(e.getId()).contains(searchTerm) ||
                                e.getFullName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            System.out.println("❌ No employees found.");
            return;
        }

        Table table = createEmployeeTable();
        matches.forEach(e -> addEmployeeToTable(table, e));

        System.out.println("\n📋 Search Results:");
        System.out.println(table.render());
    }

    // =======================================================
    // UPDATE EMPLOYEE
    // =======================================================
    public void updateEmployee() {
        System.out.println("\n=== UPDATE EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name: ");
        String term = scanner.nextLine();

        Employee emp = findEmployee(term);
        if (emp == null) {
            System.out.println("❌ Employee not found.");
            return;
        }

        System.out.println("\nCurrent Details:");
        Table before = createEmployeeTable();
        addEmployeeToTable(before, emp);
        System.out.println(before.render());

        System.out.println("\nEnter new details (press Enter to keep current value):");

        System.out.print("Full Name [" + emp.getFullName() + "]: ");
        String s = scanner.nextLine();
        if (!s.isEmpty()) emp.setFullName(s);

        System.out.print("Position [" + emp.getPosition() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setPosition(s);

        System.out.print("Department [" + emp.getDepartment() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setDepartment(s);

        System.out.print("Salary [" + emp.getSalary() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setSalary(Double.parseDouble(s));

        System.out.print("Email [" + emp.getEmail() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setEmail(s);

        System.out.print("Phone [" + emp.getPhone() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setPhone(s);

        System.out.print("Status [" + emp.getStatus() + "]: ");
        s = scanner.nextLine();
        if (!s.isEmpty()) emp.setStatus(s);

        System.out.println("✅ Employee updated!");

        Table after = createEmployeeTable();
        addEmployeeToTable(after, emp);

        System.out.println("\n=== UPDATED EMPLOYEE ===");
        System.out.println(after.render());
    }

    // =======================================================
    // REMOVE EMPLOYEE
    // =======================================================
    public boolean removeEmployee() {
        System.out.println("\n=== REMOVE EMPLOYEE ===");
        System.out.print("Enter Employee ID or Name: ");
        String term = scanner.nextLine();

        Employee emp = findEmployee(term);
        if (emp == null) {
            System.out.println("❌ Employee not found.");
            return false;
        }

        System.out.println("\nEmployee to remove:");
        Table table = createEmployeeTable();
        addEmployeeToTable(table, emp);
        System.out.println(table.render());

        System.out.print("Are you sure? (yes/no): ");
        if (!scanner.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("❌ Removal cancelled.");
            return false;
        }

        employees.remove(emp);
        System.out.println("✅ Employee removed!");
        return true;
    }

    // =======================================================
    // LIST ALL EMPLOYEES
    // =======================================================
    public void listAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");

        System.out.println("1. All");
        System.out.println("2. By department");
        System.out.println("3. By status");
        System.out.println("4. By salary range");
        System.out.print("Choose option: ");

        int choice = getIntInput();
        List<Employee> list = new ArrayList<>(employees);

        switch (choice) {
            case 2 -> {
                System.out.print("Department: ");
                String d = scanner.nextLine();
                list = list.stream().filter(e -> e.getDepartment().equalsIgnoreCase(d)).toList();
            }
            case 3 -> {
                System.out.print("Status: ");
                String s = scanner.nextLine();
                list = list.stream().filter(e -> e.getStatus().equalsIgnoreCase(s)).toList();
            }
            case 4 -> {
                System.out.print("Min salary: ");
                double min = getDoubleInput();
                System.out.print("Max salary: ");
                double max = getDoubleInput();
                list = list.stream().filter(e -> e.getSalary() >= min && e.getSalary() <= max).toList();
            }
        }

        if (list.isEmpty()) {
            System.out.println("❌ No employees found.");
            return;
        }

        Table table = createEmployeeTable();
        list.forEach(e -> addEmployeeToTable(table, e));

        System.out.println("\n📊 Employee List:");
        System.out.println(table.render());
        System.out.println("Total: " + list.size());
    }

    // =======================================================
    // SEARCH EMPLOYEES
    // =======================================================
    public void searchEmployees() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.print("Search term: ");
        String term = scanner.nextLine().toLowerCase();

        List<Employee> results = employees.stream()
                .filter(e ->
                        e.getFullName().toLowerCase().contains(term) ||
                                e.getPosition().toLowerCase().contains(term) ||
                                e.getDepartment().toLowerCase().contains(term) ||
                                e.getEmail().toLowerCase().contains(term) ||
                                e.getStatus().toLowerCase().contains(term))
                .toList();

        if (results.isEmpty()) {
            System.out.println("❌ No matches.");
            return;
        }

        Table table = createEmployeeTable();
        results.forEach(e -> addEmployeeToTable(table, e));

        System.out.println("\n🔍 Results (" + results.size() + "):");
        System.out.println(table.render());
    }

    // =======================================================
    // REPORTS
    // =======================================================
    public void generateReports() {
        System.out.println("\n=== EMPLOYEE REPORTS ===");

        long total = employees.size();
        long active = employees.stream().filter(e -> e.getStatus().equals("Active")).count();
        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        double avgSalary = total > 0 ? totalSalary / total : 0;

        Table summary = new Table(2, BorderStyle.UNICODE_DOUBLE_BOX);
        summary.addCell("Metric");
        summary.addCell("Value");
        summary.addCell("Total Employees");
        summary.addCell(String.valueOf(total));
        summary.addCell("Active Employees");
        summary.addCell(String.valueOf(active));
        summary.addCell("Inactive Employees");
        summary.addCell(String.valueOf(total - active));
        summary.addCell("Average Salary");
        summary.addCell(String.format("%.2f", avgSalary));
        summary.addCell("Total Salary Expense");
        summary.addCell(String.format("%.2f", totalSalary));

        System.out.println("\n📊 Overview:");
        System.out.println(summary.render());

        // Department Report
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        Table deptTable = new Table(3, BorderStyle.UNICODE_DOUBLE_BOX);
        deptTable.addCell("Department");
        deptTable.addCell("Employees");
        deptTable.addCell("Avg Salary");

        deptCounts.forEach((dept, count) -> {
            double avg = employees.stream()
                    .filter(e -> e.getDepartment().equals(dept))
                    .mapToDouble(Employee::getSalary).average().orElse(0);

            deptTable.addCell(dept);
            deptTable.addCell(String.valueOf(count));
            deptTable.addCell(String.format("%.2f", avg));
        });

        System.out.println("\n🏢 Department Summary:");
        System.out.println(deptTable.render());
    }

    // =======================================================
    // EXPORT DATA
    // =======================================================
//    public void exportData() {
//        System.out.println("\n=== EXPORT DATA ===");
//        System.out.println("1. CSV");
//        System.out.println("2. JSON");
//        System.out.print("Choose: ");
//
//        int choice = getIntInput();
//        StringBuilder out = new StringBuilder();
//
//        if (choice == 1) {
//            out.append("ID,Full Name,Position,Department,Hire Date,Salary,Email,Phone,Status\n");
//            employees.forEach(e -> out.append(e.toCSV()).append("\n"));
//        } else if (choice == 2) {
//            out.append("[\n");
//            for (int i = 0; i < employees.size(); i++) {
//                out.append(employees.get(i).toJSON());
//                if (i < employees.size() - 1) out.append(",");
//                out.append("\n");
//            }
//            out.append("]");
//        } else {
//            System.out.println("Invalid choice.");
//            return;
//        }
//
//        Table table = new Table(1, BorderStyle.UNICODE_DOUBLE_BOX);
//        table.addCell(out.toString());
//
//        System.out.println("\n📁 Exported Data:");
//        System.out.println(table.render());
//    }

    // =======================================================
    // IMPORT DATA
    // =======================================================
    public void importData() {
        System.out.println("\n=== IMPORT DATA ===");
        System.out.println("Paste data. Type 'END' when done:");

        StringBuilder sb = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            sb.append(line).append("\n");
        }



        System.out.println("\n📥 Received Data:");
        System.out.println(sb.toString());

    }

    // =======================================================
    // HELPERS
    // =======================================================
    private Employee findEmployee(String term) {
        try {
            int id = Integer.parseInt(term);
            return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        } catch (Exception ignored) {
            String lower = term.toLowerCase();
            return employees.stream()
                    .filter(e -> e.getFullName().toLowerCase().contains(lower))
                    .findFirst().orElse(null);
        }
    }

    private LocalDate getDateInput(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println("❌ Invalid date format.");
            }
        }
    }

    private int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("❌ Enter a number: ");
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

    public List<Employee> getEmployees() {
        return employees;
    }
}
