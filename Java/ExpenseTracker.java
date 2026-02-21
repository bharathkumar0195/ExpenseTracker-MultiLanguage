// ============================================================
// Expense Tracker Application - Java Implementation
// Cross-Language Application Development - Deliverable 2
// ============================================================

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExpenseTracker {

    static String[] CATEGORIES = {"Food", "Transport", "Entertainment", "Utilities", "Other"};

    static class Expense {
        String date;
        double amount;
        String category;
        String description;

        Expense(String date, double amount, String category, String description) {
            this.date = date;
            this.amount = amount;
            this.category = category;
            this.description = description;
        }
    }

    static ArrayList<Expense> expenses = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    // Generate a line of dashes - Java 8 compatible, no String.repeat()
    static String dashes(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    static boolean isValidDate(String date) {
        if (date.length() != 10) return false;
        if (date.charAt(4) != '-' || date.charAt(7) != '-') return false;
        try {
            int month = Integer.parseInt(date.substring(5, 7));
            int day   = Integer.parseInt(date.substring(8, 10));
            return month >= 1 && month <= 12 && day >= 1 && day <= 31;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isValidCategory(String category) {
        for (String c : CATEGORIES) {
            if (c.equals(category)) return true;
        }
        return false;
    }

    static void printExpenses(ArrayList<Expense> list) {
        if (list.isEmpty()) {
            System.out.println("  No expenses to display.");
            return;
        }
        System.out.printf("%n  %-4s %-14s %-12s %-18s %s%n", "#", "Date", "Amount", "Category", "Description");
        System.out.println("  " + dashes(65));
        int i = 1;
        for (Expense e : list) {
            System.out.printf("  %-4d %-14s $%-11.2f %-18s %s%n",
                    i++, e.date, e.amount, e.category, e.description);
        }
        System.out.println();
    }

    static void addExpense() {
        System.out.println("\n--- Add New Expense ---");

        String date = "";
        while (true) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            date = scanner.nextLine().trim();
            if (isValidDate(date)) break;
            System.out.println("  Invalid date format. Please use YYYY-MM-DD (e.g., 2024-11-15).");
        }

        double amount = 0;
        while (true) {
            System.out.print("Enter amount (e.g., 12.50): ");
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount > 0) break;
                System.out.println("  Amount must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid amount. Please enter a number.");
            }
        }

        System.out.print("  Categories: ");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.print(CATEGORIES[i]);
            if (i < CATEGORIES.length - 1) System.out.print(", ");
        }
        System.out.println();
        String category = "";
        while (true) {
            System.out.print("Enter category: ");
            category = scanner.nextLine().trim();
            if (isValidCategory(category)) break;
            System.out.println("  Invalid category. Please choose from the list above.");
        }

        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();

        expenses.add(new Expense(date, amount, category, description));
        System.out.println("  Expense added successfully!");
    }

    static void viewAllExpenses() {
        System.out.println("\n--- All Expenses ---");
        printExpenses(expenses);
    }

    static void filterByDate() {
        System.out.println("\n--- Filter by Date Range ---");

        String startDate = "";
        while (true) {
            System.out.print("Enter start date (YYYY-MM-DD): ");
            startDate = scanner.nextLine().trim();
            if (isValidDate(startDate)) break;
            System.out.println("  Invalid format. Use YYYY-MM-DD.");
        }

        String endDate = "";
        while (true) {
            System.out.print("Enter end date (YYYY-MM-DD): ");
            endDate = scanner.nextLine().trim();
            if (isValidDate(endDate)) break;
            System.out.println("  Invalid format. Use YYYY-MM-DD.");
        }

        ArrayList<Expense> filtered = new ArrayList<Expense>();
        for (Expense e : expenses) {
            if (e.date.compareTo(startDate) >= 0 && e.date.compareTo(endDate) <= 0) {
                filtered.add(e);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("  No expenses found in that date range.");
        } else {
            System.out.println("  Expenses from " + startDate + " to " + endDate + ":");
            printExpenses(filtered);
        }
    }

    static void filterByCategory() {
        System.out.println("\n--- Filter by Category ---");
        System.out.print("  Categories: ");
        for (int i = 0; i < CATEGORIES.length; i++) {
            System.out.print(CATEGORIES[i]);
            if (i < CATEGORIES.length - 1) System.out.print(", ");
        }
        System.out.println();

        String category = "";
        while (true) {
            System.out.print("Enter category: ");
            category = scanner.nextLine().trim();
            if (isValidCategory(category)) break;
            System.out.println("  Invalid category. Please choose from the list above.");
        }

        ArrayList<Expense> filtered = new ArrayList<Expense>();
        for (Expense e : expenses) {
            if (e.category.equals(category)) {
                filtered.add(e);
            }
        }

        if (filtered.isEmpty()) {
            System.out.println("  No expenses found in category '" + category + "'.");
        } else {
            System.out.println("  Expenses in '" + category + "':");
            printExpenses(filtered);
        }
    }

    static void summaryReport() {
        System.out.println("\n--- Summary Report ---");
        if (expenses.isEmpty()) {
            System.out.println("  No expenses to summarize.");
            return;
        }

        HashMap<String, Double> totals = new HashMap<String, Double>();
        for (Expense e : expenses) {
            double current = totals.containsKey(e.category) ? totals.get(e.category) : 0.0;
            totals.put(e.category, current + e.amount);
        }

        System.out.printf("%n  %-22s %10s%n", "Category", "Total");
        System.out.println("  " + dashes(32));

        double overall = 0;
        for (String cat : CATEGORIES) {
            if (totals.containsKey(cat)) {
                System.out.printf("  %-22s $%9.2f%n", cat, totals.get(cat));
                overall += totals.get(cat);
            }
        }
        System.out.println("  " + dashes(32));
        System.out.printf("  %-22s $%9.2f%n", "OVERALL TOTAL", overall);
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("     EXPENSE TRACKER - Java Implementation");
        System.out.println("==================================================");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("  1. Add Expense");
            System.out.println("  2. View All Expenses");
            System.out.println("  3. Filter by Date Range");
            System.out.println("  4. Filter by Category");
            System.out.println("  5. Summary Report");
            System.out.println("  6. Exit");
            System.out.print("\nEnter your choice (1-6): ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1": addExpense();       break;
                case "2": viewAllExpenses();  break;
                case "3": filterByDate();     break;
                case "4": filterByCategory(); break;
                case "5": summaryReport();    break;
                case "6":
                    System.out.println("\nGoodbye! Exiting Expense Tracker.");
                    scanner.close();
                    return;
                default:
                    System.out.println("  Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }
}

