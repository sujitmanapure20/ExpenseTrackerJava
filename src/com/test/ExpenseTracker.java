package com.test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExpenseTracker {
    private static List<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILENAME = "transactions.txt";

    public static void main(String[] args) {
        System.out.println("Welcome to Expense Tracker!");

        while (true) {
            System.out.println("\n1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Save Data to File");
            System.out.println("5. Load Data from File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: addTransaction("income"); break;
                case 2: addTransaction("expense"); break;
                case 3: showMonthlySummary(); break;
                case 4: FileHelper.saveToFile(transactions, FILENAME); break;
                case 5: transactions = FileHelper.loadFromFile(FILENAME); break;
                case 6: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addTransaction(String type) {
        scanner.nextLine(); // consume newline
        System.out.print("Enter category (" + (type.equals("income") ? "salary/business" : "food/rent/travel") + "): ");
        String category = scanner.nextLine().toLowerCase();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();

        scanner.nextLine(); // consume newline
        System.out.print("Enter date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateStr);

        transactions.add(new Transaction(type, category, amount, date));
        System.out.println("Transaction added successfully.");
    }

    private static void showMonthlySummary() {
        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();

        for (Transaction t : transactions) {
            String month = t.getDate().getYear() + "-" + String.format("%02d", t.getDate().getMonthValue());
            if (t.getType().equals("income")) {
                incomeMap.put(month, incomeMap.getOrDefault(month, 0.0) + t.getAmount());
            } else {
                expenseMap.put(month, expenseMap.getOrDefault(month, 0.0) + t.getAmount());
            }
        }

        Set<String> months = new HashSet<>();
        months.addAll(incomeMap.keySet());
        months.addAll(expenseMap.keySet());

        System.out.println("\n----- Monthly Summary -----");
        for (String month : months) {
            double income = incomeMap.getOrDefault(month, 0.0);
            double expense = expenseMap.getOrDefault(month, 0.0);
            System.out.println("Month: " + month);
            System.out.println("  Income : " + income);
            System.out.println("  Expense: " + expense);
            System.out.println("  Balance: " + (income - expense));
        }
    }
}

