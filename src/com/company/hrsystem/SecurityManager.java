package com.company.hrsystem.utils;

import java.util.Scanner;

public class SecurityManager {
    private static final String ADMIN_PASSWORD = "company123";
    private Scanner scanner;

    public SecurityManager() {
        this.scanner = new Scanner(System.in);
    }

    public boolean authenticate() {
        System.out.print("Enter administrator password: ");
        String input = scanner.nextLine();

        if (input.equals(ADMIN_PASSWORD)) {
            System.out.println("✅ Authentication successful!");
            return true;
        } else {
            System.out.println("❌ Authentication failed!");
            return false;
        }
    }

    public boolean requireAuthentication(String operation) {
        System.out.println("\n🔒 " + operation + " requires administrator access.");
        return authenticate();
    }
}