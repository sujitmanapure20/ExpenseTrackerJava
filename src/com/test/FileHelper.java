package com.test;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static void saveToFile(List<Transaction> transactions, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                bw.write(t.toString());
                bw.newLine();
            }
            System.out.println("Data saved to file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadFromFile(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                LocalDate date = LocalDate.parse(parts[3]);
                transactions.add(new Transaction(type, category, amount, date));
            }
            System.out.println("Data loaded from file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

