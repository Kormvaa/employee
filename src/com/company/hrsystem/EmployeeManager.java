package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeManager {

    private List<Employee> employees;
    private int nextId;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Scanner scanner;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.nextId = 1;
        initializeSampleData(); // still called automatically
    }

    // ✔ Changed to boolean
    private boolean initializeSampleData() {
        try {

            return true; // success
        } catch (Exception e) {
            System.out.println("❌ Failed to load sample data: " + e.getMessage());
            return false;
        }
    }

    private Table createEmployeeTable() {
        Table table = new Table(9, BorderStyle.UNICODE_DOUBLE_BOX);
        String[] headers = {
                "ID", "Full Name", "Position", "Department",
                "Hire Date", "Salary", "Email", "Phone", "Status"
        };

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
}
