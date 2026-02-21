import java.util.ArrayList;
import java.util.Scanner;

// Expense class to store expense details
class Expense {
    String name;
    double amount;

    Expense(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }
}

public class ExpenseTracker {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Expense> expenses = new ArrayList<>();

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Show Total");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 1) {
                System.out.print("Enter expense name: ");
                String name = scanner.nextLine();

                System.out.print("Enter amount: ");
                double amount = scanner.nextDouble();

                expenses.add(new Expense(name, amount));
                System.out.println("Expense added successfully!");

            } else if (choice == 2) {
                System.out.println("\nAll Expenses:");
                for (Expense e : expenses) {
                    System.out.println(e.name + " - $" + e.amount);
                }

            } else if (choice == 3) {
                double total = 0;
                for (Expense e : expenses) {
                    total += e.amount;
                }
                System.out.println("Total Expenses: $" + total);

            } else if (choice == 4) {
                System.out.println("Exiting...");
                break;

            } else {
                System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}
