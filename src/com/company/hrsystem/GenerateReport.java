package com.company.hrsystem;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerateReport {

    private final List<Employee> employees;

    public GenerateReport(List<Employee> employees) {
        this.employees = employees;
    }

    // Method called from HRSystem
    public void generate() {
        System.out.println("\n=== EMPLOYEE REPORTS ===");

        long total = employees.size();
        long active = employees.stream().filter(e -> e.getStatus().equalsIgnoreCase("Active")).count();
        double totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        double avgSalary = total > 0 ? totalSalary / total : 0;

        // Overview Table
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

        // Department Breakdown
        Map<String, Long> deptCounts = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        Table deptTable = new Table(3, BorderStyle.UNICODE_DOUBLE_BOX);
        deptTable.addCell("Department");
        deptTable.addCell("Employees");
        deptTable.addCell("Avg Salary");

        deptCounts.forEach((dept, count) -> {
            double avg = employees.stream()
                    .filter(e -> e.getDepartment().equals(dept))
                    .mapToDouble(Employee::getSalary)
                    .average()
                    .orElse(0);
            deptTable.addCell(dept);
            deptTable.addCell(String.valueOf(count));
            deptTable.addCell(String.format("%.2f", avg));
        });

        System.out.println("\n🏢 Department Summary:");
        System.out.println(deptTable.render());
    }
}
