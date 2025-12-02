package com.company.hrsystem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
    private int id;
    private String fullName;
    private String position;
    private String department;
    private LocalDate hireDate;
    private double salary;
    private String email;
    private String phone;
    private String status; // Active/Inactive

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Employee(int id, String fullName, String position, String department,
                    LocalDate hireDate, double salary, String email, String phone, String status) {

        this.id = id;
        this.fullName = fullName;
        this.position = position;
        this.department = department;
        this.hireDate = hireDate;
        this.salary = salary;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Position: %s | Department: %s | " +
                        "Hire Date: %s | Salary: $%.2f | Email: %s | Phone: %s | Status: %s",
                id, fullName, position, department, hireDate.format(DATE_FORMATTER),
                salary, email, phone, status
        );
    }

    public String toCSV() {
        return String.format("%d,%s,%s,%s,%s,%.2f,%s,%s,%s",
                id, fullName, position, department, hireDate.format(DATE_FORMATTER),
                salary, email, phone, status);
    }

    public String toJSON() {
        return String.format(
                "{\"id\":%d,\"fullName\":\"%s\",\"position\":\"%s\",\"department\":\"%s\"," +
                        "\"hireDate\":\"%s\",\"salary\":%.2f,\"email\":\"%s\",\"phone\":\"%s\",\"status\":\"%s\"}",
                id, fullName, position, department, hireDate.format(DATE_FORMATTER),
                salary, email, phone, status);
    }
}