package Cantilever.Task1;
import java.sql.*;
import java.util.*;

public class BankSystem {
    static final String url = "jdbc:mysql://localhost:3306/bank";
    static final String username = "root";
    static final String password = "";
    static Connection conn;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println("1. Create Account");
                System.out.println("2. View Balance");
                System.out.println("3. Deposit");
                System.out.println("4. Withdraw");
                System.out.println("5. Exit");
                int choice = sc.nextInt();

                if (choice == 1) createAccount();
                else if (choice == 2) viewBalance();
                else if (choice == 3) deposit();
                else if (choice == 4) withdraw();
                else break;
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    static void createAccount() throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.next();
        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();
        String q = "INSERT INTO accounts (name, balance) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setString(1, name);
        ps.setDouble(2, balance);
        ps.executeUpdate();
        System.out.println("Account Created");
    }

    static void viewBalance() throws SQLException {
        System.out.print("Enter account id: ");
        int id = sc.nextInt();
        String q = "SELECT balance FROM accounts WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            System.out.println("Balance: " + rs.getDouble(1));
        } else {
            System.out.println("Account not found");
        }
    }

    static void deposit() throws SQLException {
        System.out.print("Enter account id: ");
        int id = sc.nextInt();
        System.out.print("Enter amount: ");
        double amt = sc.nextDouble();
        String q = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setDouble(1, amt);
        ps.setInt(2, id);
        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Deposited");
        else System.out.println("Account not found");
    }

    static void withdraw() throws SQLException {
        System.out.print("Enter account id: ");
        int id = sc.nextInt();
        System.out.print("Enter amount: ");
        double amt = sc.nextDouble();
        String q = "SELECT balance FROM accounts WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(q);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            double bal = rs.getDouble(1);
            if (bal >= amt) {
                q = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
                ps = conn.prepareStatement(q);
                ps.setDouble(1, amt);
                ps.setInt(2, id);
                ps.executeUpdate();
                System.out.println("Withdrawn");
            } else {
                System.out.println("Insufficient balance");
            }
        } else {
            System.out.println("Account not found");
        }
    }
} 
