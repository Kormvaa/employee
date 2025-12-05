package com.company.hrsystem;

import java.util.Scanner;

public class SecurityManager {

    private static final String ADMIN_PASSWORD = "company123";
    private final Scanner scanner;

    public SecurityManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean requireAuthentication(String operation) {
        System.out.print("Enter administrator password to " + operation + ": ");
        String input = scanner.nextLine();
        if (ADMIN_PASSWORD.equals(input)) {
            return true;
        } else {
            System.out.println("❌ Authentication failed. Operation canceled.");
            return false;
        }
    }
}
