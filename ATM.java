import java.util.*;

class User {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: ₹" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrew: ₹" + amount);
        return true;
    }

    public boolean transfer(User recipient, double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        recipient.balance += amount;
        transactionHistory.add("Transferred ₹" + amount + " to " + recipient.userId);
        recipient.transactionHistory.add("Received ₹" + amount + " from " + this.userId);
        return true;
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("Transaction History:");
            for (String t : transactionHistory) {
                System.out.println("- " + t);
            }
        }
    }
}

class Bank {
    private Map<String, User> users;

    public Bank() {
        users = new HashMap<>();

        users.put("user1", new User("user1", "1234"));
        users.put("user2", new User("user2", "4321"));
    }

    public User authenticate(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.checkPin(pin)) {
            return user;
        }
        return null;
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public Set<String> getAllUserIds() {
        return users.keySet();
    }
}
class ATMInterface {
    private Scanner sc = new Scanner(System.in);
    private Bank bank;

    public ATMInterface(Bank bank) {
        this.bank = bank;
    }

    public void start() {
        System.out.print("Enter User ID: ");
        String userId = sc.nextLine();
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine();

        User currentUser = bank.authenticate(userId, pin);
        if (currentUser != null) {
            System.out.println("\n✅ Login successful!");
            showMenu(currentUser);
        } else {
            System.out.println("\n❌ Invalid credentials. Try again.");
        }
    }

    private void showMenu(User user) {
        int choice;
        do {
            System.out.println("\n===== ATM Menu =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    user.showTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ₹");
                    double wAmt = sc.nextDouble();
                    if (user.withdraw(wAmt)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ₹");
                    double dAmt = sc.nextDouble();
                    user.deposit(dAmt);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    sc.nextLine();
                    System.out.print("Enter recipient User ID: ");
                    String recipientId = sc.nextLine();
                    User recipient = bank.getUser(recipientId);
                    if (recipient != null && !recipient.getUserId().equals(user.getUserId())) {
                        System.out.print("Enter amount to transfer: ₹");
                        double tAmt = sc.nextDouble();
                        if (user.transfer(recipient, tAmt)) {
                            System.out.println("Transfer successful.");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                    } else {
                        System.out.println("Invalid recipient.");
                    }
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 5);
    }
}

public class ATM {
    public static void main(String[] args) {
        Bank bank = new Bank();
        ATMInterface atm = new ATMInterface(bank);
        atm.start();
    }
}

