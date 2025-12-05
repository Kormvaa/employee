package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Scanner;

public class UpdateEmployee {

    private final Scanner scanner;
    private final List<Employee> employees;

    public UpdateEmployee(List<Employee> employees, Scanner scanner) {
        this.employees = employees;
        this.scanner = scanner;
    }

    public void updateEmployee() {
        System.out.println("\n=== UPDATE EMPLOYEE ===");

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

        System.out.println("\nCurrent Details:");
        Table before = createEmployeeTable();
        addEmployeeToTable(before, emp);
        System.out.println(before.render());

        System.out.println("\nEnter new details (press Enter to keep current value):");
        updateFields(emp);

        System.out.println("\n✅ Employee updated successfully!");
        Table after = createEmployeeTable();
        addEmployeeToTable(after, emp);
        System.out.println("\n=== UPDATED EMPLOYEE ===");
        System.out.println(after.render());
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

    private void updateFields(Employee emp) {
        System.out.print("Full Name [" + emp.getFullName() + "]: ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) emp.setFullName(input);

        System.out.print("Position [" + emp.getPosition() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) emp.setPosition(input);

        System.out.print("Department [" + emp.getDepartment() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) emp.setDepartment(input);

        System.out.print("Salary [" + emp.getSalary() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            try {
                emp.setSalary(Double.parseDouble(input));
            } catch (Exception e) {
                System.out.println("❌ Invalid number. Salary unchanged.");
            }
        }

        System.out.print("Email [" + emp.getEmail() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) emp.setEmail(input);

        System.out.print("Phone [" + emp.getPhone() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) emp.setPhone(input);

        System.out.print("Status [" + emp.getStatus() + "]: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) emp.setStatus(input);
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
}
