# ============================================================
# Expense Tracker Application - Python Implementation
# Cross-Language Application Development - Deliverable 2
# ============================================================

from datetime import datetime

# ─── PREDEFINED CATEGORIES ───────────────────────────────────
CATEGORIES = ["Food", "Transport", "Entertainment", "Utilities", "Other"]

# ─── DATA LAYER ──────────────────────────────────────────────
# All expenses are stored as a list of dictionaries
expenses = []


# ─── CORE FUNCTIONS ──────────────────────────────────────────

def add_expense():
    """Prompt the user to enter a new expense and store it."""
    print("\n--- Add New Expense ---")

    # Get and validate date
    while True:
        date_input = input("Enter date (YYYY-MM-DD): ").strip()
        try:
            datetime.strptime(date_input, "%Y-%m-%d")  # Validate format
            break
        except ValueError:
            print("  Invalid date format. Please use YYYY-MM-DD (e.g., 2024-11-15).")

    # Get and validate amount
    while True:
        amount_input = input("Enter amount (e.g., 12.50): ").strip()
        try:
            amount = float(amount_input)
            if amount <= 0:
                print("  Amount must be greater than zero.")
            else:
                break
        except ValueError:
            print("  Invalid amount. Please enter a number.")

    # Get category
    print("  Categories:", ", ".join(CATEGORIES))
    while True:
        category = input("Enter category: ").strip().capitalize()
        if category in CATEGORIES:
            break
        print(f"  Invalid category. Choose from: {', '.join(CATEGORIES)}")

    # Get description
    description = input("Enter description: ").strip()

    # Build the expense dictionary and add it to the list
    expense = {
        "date": date_input,
        "amount": amount,
        "category": category,
        "description": description
    }
    expenses.append(expense)
    print(f"  Expense added successfully!")


def view_all_expenses():
    """Display all recorded expenses."""
    print("\n--- All Expenses ---")
    if not expenses:
        print("  No expenses recorded yet.")
        return
    print_expenses(expenses)


def filter_by_date():
    """Filter and display expenses within a date range."""
    print("\n--- Filter by Date Range ---")

    while True:
        start_input = input("Enter start date (YYYY-MM-DD): ").strip()
        try:
            start_date = datetime.strptime(start_input, "%Y-%m-%d")
            break
        except ValueError:
            print("  Invalid format. Use YYYY-MM-DD.")

    while True:
        end_input = input("Enter end date (YYYY-MM-DD): ").strip()
        try:
            end_date = datetime.strptime(end_input, "%Y-%m-%d")
            break
        except ValueError:
            print("  Invalid format. Use YYYY-MM-DD.")

    # Use a list comprehension to filter expenses by date range
    filtered = [
        e for e in expenses
        if start_date <= datetime.strptime(e["date"], "%Y-%m-%d") <= end_date
    ]

    if not filtered:
        print("  No expenses found in that date range.")
    else:
        print(f"  Expenses from {start_input} to {end_input}:")
        print_expenses(filtered)


def filter_by_category():
    """Filter and display expenses by category."""
    print("\n--- Filter by Category ---")
    print("  Categories:", ", ".join(CATEGORIES))

    while True:
        category = input("Enter category: ").strip().capitalize()
        if category in CATEGORIES:
            break
        print(f"  Invalid category. Choose from: {', '.join(CATEGORIES)}")

    # List comprehension to filter by matching category
    filtered = [e for e in expenses if e["category"] == category]

    if not filtered:
        print(f"  No expenses found in category '{category}'.")
    else:
        print(f"  Expenses in '{category}':")
        print_expenses(filtered)


def summary_report():
    """Display total expenses per category and an overall total."""
    print("\n--- Summary Report ---")
    if not expenses:
        print("  No expenses to summarize.")
        return

    # Use a dictionary to accumulate totals per category
    totals = {}
    for expense in expenses:
        cat = expense["category"]
        totals[cat] = totals.get(cat, 0) + expense["amount"]

    print(f"  {'Category':<20} {'Total':>10}")
    print("  " + "-" * 32)
    for category, total in sorted(totals.items()):
        print(f"  {category:<20} ${total:>9.2f}")
    print("  " + "-" * 32)
    overall = sum(e["amount"] for e in expenses)
    print(f"  {'OVERALL TOTAL':<20} ${overall:>9.2f}")


# ─── HELPER FUNCTION ─────────────────────────────────────────

def print_expenses(expense_list):
    """Neatly print a list of expense dictionaries."""
    print(f"\n  {'#':<4} {'Date':<14} {'Amount':>10}  {'Category':<16} {'Description'}")
    print("  " + "-" * 65)
    for i, e in enumerate(expense_list, start=1):
        print(f"  {i:<4} {e['date']:<14} ${e['amount']:>9.2f}  {e['category']:<16} {e['description']}")
    print()


# ─── MAIN MENU ───────────────────────────────────────────────

def main():
    """Main loop that displays the menu and handles user input."""
    print("=" * 50)
    print("     EXPENSE TRACKER - Python Implementation")
    print("=" * 50)

    while True:
        print("\nMain Menu:")
        print("  1. Add Expense")
        print("  2. View All Expenses")
        print("  3. Filter by Date Range")
        print("  4. Filter by Category")
        print("  5. Summary Report")
        print("  6. Exit")

        choice = input("\nEnter your choice (1-6): ").strip()

        if choice == "1":
            add_expense()
        elif choice == "2":
            view_all_expenses()
        elif choice == "3":
            filter_by_date()
        elif choice == "4":
            filter_by_category()
        elif choice == "5":
            summary_report()
        elif choice == "6":
            print("\nGoodbye! Exiting Expense Tracker.")
            break
        else:
            print("  Invalid choice. Please enter a number between 1 and 6.")


# ─── ENTRY POINT ─────────────────────────────────────────────
if __name__ == "__main__":
    main()
