# Simple Expense Tracker in Python

expenses = []

while True:
    print("\nExpense Tracker Menu:")
    print("1. Add Expense")
    print("2. View Expenses")
    print("3. Show Total")
    print("4. Exit")

    choice = input("Choose option: ")

    if choice == "1":
        name = input("Enter expense name: ")
        amount = float(input("Enter amount: "))
        expenses.append({"name": name, "amount": amount})
        print("Expense added successfully!")

    elif choice == "2":
        print("\nAll Expenses:")
        for expense in expenses:
            print(f"{expense['name']} - ${expense['amount']}")

    elif choice == "3":
        total = sum(expense["amount"] for expense in expenses)
        print(f"Total Expenses: ${total}")

    elif choice == "4":
        print("Exiting...")
        break

    else:
        print("Invalid option. Try again.")
