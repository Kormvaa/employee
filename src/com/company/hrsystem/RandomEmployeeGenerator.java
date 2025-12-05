package com.company.hrsystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEmployeeGenerator {

    private static final String[] FIRST_NAMES = {"John", "Jane", "Mike", "Alice", "Robert", "Emma", "David", "Olivia"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Davis", "Miller", "Wilson", "Taylor", "Anderson"};
    private static final String[] POSITIONS = {"Developer", "Manager", "Analyst", "Designer", "Tester", "Support"};
    private static final String[] DEPARTMENTS = {"Sales", "IT", "HR", "Marketing", "Finance", "Support"};
    private static final String[] STATUS = {"Active", "Inactive"};

    private static final Random random = new Random();

    public static List<Employee> generateRandomEmployees(int count, int startId) {
        List<Employee> employees = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int id = startId + i;

            String fullName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] + " " +
                    LAST_NAMES[random.nextInt(LAST_NAMES.length)];

            String position = POSITIONS[random.nextInt(POSITIONS.length)];
            String department = DEPARTMENTS[random.nextInt(DEPARTMENTS.length)];

            LocalDate hireDate = LocalDate.now().minusDays(random.nextInt(3650));

            // Salary as integer number only
            int salary = 30000 + random.nextInt(900); // 30,000 to 120,000

            String email = fullName.toLowerCase().replace(" ", ".") + "@company.com";
            String phone = String.format("+885-%03d-%04d", random.nextInt(1000), random.nextInt(10000));
            String status = STATUS[random.nextInt(STATUS.length)];

            employees.add(new Employee(id, fullName, position, department, hireDate, salary, email, phone, status));
        }

        return employees;
    }
}
