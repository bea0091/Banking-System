import java.util.Scanner;

public class p1 {

    private static Scanner sc;
    private static String thisId;
    private static String thisAccNum;

    /**
     * Main function to start the program
     * @param args args
     */
    public static void main(String[] args) {
        System.out.println(":: PROGRAM START");
        if (args.length < 1) {
            System.out.println("Need database properties filename");
        } else {
            BankingSystem.init(args[0]);
            BankingSystem.testConnection();
            sc = new Scanner(System.in);
            mainMenu();
        }
        System.out.println(":: PROGRAM END");
    }

    /**
     * Main menu
     */
    private static void mainMenu() {
        System.out.println("=====================$=$=$=$=$====================");
        System.out.println("Welcome to SSBS - the Self Service Banking System!");
        System.out.println("=====================$=$=$=$=$====================");
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("  1. New Customer");
            System.out.println("  2. Customer Login");
            System.out.println("  3. Exit");
            System.out.print("Enter a number to choose from the menu of options: ");
            String choice = sc.next();
            if (choice.equals("1"))
                newCustomer();
            else if (choice.equals("2"))
                login();
            else if (choice.equals("3"))
                break;
            else
                System.out.println("Invalid input. Please try again.");
        }
        System.out.println("Goodbye!");
    }

    /**
     * Register new customer
     */
    private static void newCustomer() {
        System.out.println("\nLet's create a customer profile for you.");
        System.out.print("Please enter your name (from 1 to 15 chars): ");
        String name = sc.next();
        System.out.print("Please enter your gender (M or F): ");
        String gender = sc.next();
        System.out.print("Please enter your age: ");
        String age = sc.next();
        System.out.print("Please enter your desired pin code: ");
        String pin = sc.next();
        BankingSystem.newCustomer(name, gender, age, pin);
    }

    /**
     * Login
     */
    private static void login() {
        System.out.print("\nPlease enter your customer id to login: ");
        String id = sc.next();
        System.out.print("Please enter your pin code: ");
        String pin = sc.next();
        if (id.equals("0") && pin.equals("0")) {
            System.out.println("Admin panel ON");
            adminMenu();
        }
        else if (BankingSystem.login(id, pin)) {
            thisId = id;
            customerMenu();
        }
    }

    /**
     * Customer menu
     */
    private static void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("  1. Open Account");
            System.out.println("  2. Close Account");
            System.out.println("  3. Deposit");
            System.out.println("  4. Withdraw");
            System.out.println("  5. Transfer");
            System.out.println("  6. Account Summary");
            System.out.println("  7. Exit");
            System.out.print("Enter a number to choose from the menu of options: ");
            String choice = sc.next();
            if (choice.equals("1"))
                openAccount();
            else if (choice.equals("2"))
                closeAccount();
            else if (choice.equals("3"))
                deposit();
            else if (choice.equals("4"))
                withdraw();
            else if (choice.equals("5"))
                transfer();
            else if (choice.equals("6"))
                BankingSystem.accountSummary(thisId);
            else if (choice.equals("7")) {
                System.out.println("Logged out");
                break;
            }
            else
                System.out.println("Invalid input. Please try again.");
        }
    }

    /**
     * Admin menu
     */
    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("  1. Account Summary for a Customer");
            System.out.println("  2. Report A");
            System.out.println("  3. Report B");
            System.out.println("  4. Exit");
            System.out.print("Enter a number to choose from the menu of options: ");
            String choice = sc.next();
            if (choice.equals("1"))
                accountSummaryForCustomer();
            else if (choice.equals("2"))
                BankingSystem.reportA();
            else if (choice.equals("3"))
                reportB();
            else if (choice.equals("4")) {
                System.out.println("Admin panel OFF");
                break;
            }
            else
                System.out.println("Invalid input. Please try again.");
        }
    }

    /**
     * Open new account
     */
    private static void openAccount() {
        System.out.print("\nPlease enter the type of account you want to open (C or S): ");
        String type = sc.next();
        System.out.print("Please enter the initial amount in your account: ");
        String amount = sc.next();
        BankingSystem.openAccount(thisId, type, amount);
    }

    /**
     * Close account
     */
    private static void closeAccount() {
        System.out.print("\nPlease enter the account number you want to close: ");
        String accNum = sc.next();
        BankingSystem.closeAccount(accNum);
    }

    /**
     * Deposit to account
     */
    private static void deposit() {
        System.out.print("\nPlease enter the account number you want to deposit to: ");
        String accNum = sc.next();
        System.out.print("Please enter the amount you want to deposit: ");
        String amount = sc.next();
        BankingSystem.deposit(accNum, amount);
    }

    /**
     * Withdraw from account
     */
    private static void withdraw() {
        System.out.print("\nPlease enter the account number you want to withdraw from: ");
        String accNum = sc.next();
        System.out.print("Please enter the amount you want to withdraw: ");
        String amount = sc.next();
        BankingSystem.withdraw(accNum, amount);
    }

    /**
     * Transfer from one account to another
     */
    private static void transfer() {
        System.out.print("\nPlease enter the account number you want to transfer from: ");
        String srcAccNum = sc.next();
        System.out.print("\nPlease enter the account number you want to transfer to: ");
        String destAccNum = sc.next();
        System.out.print("Please enter the amount you want to transfer: ");
        String amount = sc.next();
        BankingSystem.transfer(srcAccNum, destAccNum, amount);
    }

    /**
     * Display account summary for the chosen customer
     */
    private static void accountSummaryForCustomer() {
        System.out.print("Please enter the customer id for the summary: ");
        String id = sc.next();
        BankingSystem.accountSummary(id);
    }

    /**
     * Display report B
     */
    private static void reportB() {
        System.out.print("\nPlease enter min age: ");
        String min = sc.next();
        System.out.print("Please enter max age: ");
        String max = sc.next();
        BankingSystem.reportB(min, max);
    }

}
