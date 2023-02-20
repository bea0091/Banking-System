import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();					          // Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										                  // Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				      // Load the driver
			url = props.getProperty("jdbc.url");					          // Load the url
			username = props.getProperty("jdbc.username");			    // Load the username
			password = props.getProperty("jdbc.password");			    // Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
		System.out.println(":: CREATE NEW CUSTOMER - RUNNING");

		try {
			if (!nameIsValid(name)) throw new CustomException("INVALID NAME");
			if (!genderIsValid(gender)) throw new CustomException("INVALID GENDER");
			if (!ageIsValid(age)) throw new CustomException("INVALID AGE");
			if (!pinIsValid(pin)) throw new CustomException("INVALID PIN");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "INSERT INTO p1.customer (name, gender, age, pin) " + 
							"VALUES ('" + name + "', '" + gender + "', '" + age + "', '" + pin + "')";
			stmt.execute(query);

			query = "SELECT IDENTITY_VAL_LOCAL() FROM p1.customer";
			rs = stmt.executeQuery(query);
			rs.next();
			int id = rs.getInt(1);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
			System.out.println("Customer id : " + id);
		} catch (CustomException e) {
			System.out.println(":: CREATE NEW CUSTOMER - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: CREATE NEW CUSTOMER - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * 
	 * Login customer
	 * @param id customer id
	 * @param pin customer pin
	 * @return boolean
	 */
	public static boolean login(String id, String pin)
	{
		System.out.println(":: LOGIN CUSTOMER - RUNNING");
		try {
			if (!idIsValid(id)) throw new CustomException("INVALID ID");
			if (!pinIsValid(pin)) throw new CustomException("INVALID PIN");
			if (!customerExists(id)) throw new CustomException("CUSTOMER NOT EXIST");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT pin FROM p1.customer " +
							"WHERE id = '" + id + "' AND pin = '" + pin + "'";
			rs = stmt.executeQuery(query);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: LOGIN CUSTOMER - SUCCESS");
			return true;
		} catch (CustomException e) {
			System.out.println(":: LOGIN CUSTOMER - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: LOGIN CUSTOMER - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
		return false;
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
		System.out.println(":: OPEN ACCOUNT - RUNNING");
		try {
			if (!idIsValid(id)) throw new CustomException("INVALID CUSTOMER ID");
			if (!typeIsValid(type)) throw new CustomException("INVALID TYPE");
			if (!amountIsValid(amount)) throw new CustomException("INVALID AMOUNT");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "INSERT INTO p1.account (id, balance, type, status) " + 
							"VALUES ('" + id + "', '" +  amount +"', '" + type + "', 'A')";
			stmt.executeUpdate(query);

			query = "SELECT IDENTITY_VAL_LOCAL() FROM p1.customer";
			rs = stmt.executeQuery(query);
			rs.next();
			int accNum = rs.getInt(1);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: OPEN ACCOUNT - SUCCESS");
			System.out.println("Account number : " + accNum);
		} catch (CustomException e) {
			System.out.println(":: OPEN ACCOUNT - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: OPEN ACCOUNT - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
		System.out.println(":: CLOSE ACCOUNT - RUNNING");
		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "UPDATE p1.account SET status = 'I', balance = '0' " + 
							"WHERE number = '" + accNum + "'" ;
			stmt.executeUpdate(query);

			stmt.close();
			con.close();
			System.out.println(":: CLOSE ACCOUNT - SUCCESS");
		} catch (Exception e) {
			System.out.println(":: CLOSE  ACCOUNT - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
		System.out.println(":: DEPOSIT - RUNNING");
		try {
			if (!amountIsValid(amount)) throw new CustomException("INVALID AMOUNT");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			// Get the current balance
			String query = "SELECT balance FROM p1.account WHERE number = '" + accNum + "'";
			rs = stmt.executeQuery(query);
			int currBalance = 0;
			while (rs.next()) {
				currBalance = rs.getInt(1);
				break;
			}

			// Deposit given amount into an account
			int newBalance = currBalance += Integer.parseInt(amount);
			query = "UPDATE p1.account SET balance = '" + newBalance + "' WHERE number = '" + accNum + "'";
			stmt.executeUpdate(query);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: DEPOSIT - SUCCESS");
		} catch (CustomException e) {
			System.out.println(":: DEPOSIT - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: DEPOSIT - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
		System.out.println(":: WITHDRAW - RUNNING");
		try {
			if (!amountIsValid(amount)) throw new CustomException("INVALID AMOUNT");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			// Get the current balance
			String query = "SELECT balance FROM p1.account WHERE number = '" + accNum + "'";
			rs = stmt.executeQuery(query);
			int currBalance = 0;
			while (rs.next()) {
				currBalance = rs.getInt(1);
				break;
			}

			int newBalance = currBalance - Integer.parseInt(amount);

			// Check pre-condition before execute the withdrawal
			if (newBalance < 0) {
				System.out.println(":: WITHDRAW - ERROR - NOT ENOUGH FUNDS");
				return;

			// Withdraw given amount from an account
			} else {
				query = "UPDATE p1.account SET balance = '" + newBalance + "' " + "WHERE number = '" + accNum + "'";
				stmt.executeUpdate(query);
			}

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: WITHDRAW - SUCCESS");
		} catch (CustomException e) {
			System.out.println(":: WITHDRAW - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: WITHDRAW - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
		System.out.println(":: TRANSFER - RUNNING");

		try {
			if (!amountIsValid(amount)) throw new CustomException("INVALID AMOUNT");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			// Get the source account balance
			String query = "SELECT balance FROM p1.account WHERE number = '" + srcAccNum + "'";
			rs = stmt.executeQuery(query);
			int srcBalance = 0;
			while (rs.next()) {
				srcBalance = rs.getInt(1);
				break;
			}

			// Check pre-condition before execute the transfer
			if (srcBalance < Integer.parseInt(amount)) {
				System.out.println(":: TRANSFER - ERROR - NOT ENOUGH FUNDS");
				return;
			}

			// Subtract the amount from source account
			int newBalance = srcBalance - Integer.parseInt(amount);
			query = "UPDATE p1.account SET balance = " + "'" + newBalance
							+ "' WHERE number = '" + srcAccNum+ "'";
			stmt.executeUpdate(query);

			// Get the destination account balance
			query = "SELECT balance FROM p1.account WHERE number = '" + destAccNum + "'";
			rs = stmt.executeQuery(query);
			int destBalance = 0;
			while (rs.next()) {
				destBalance = rs.getInt(1);
				break;
			}

			// Add the amount to destination account
			newBalance = destBalance + Integer.parseInt(amount);
			query = "UPDATE p1.account SET balance = " + "'" + newBalance
							+ "' WHERE number = '" + destAccNum+ "'";
			stmt.executeUpdate(query);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: TRANSFER - SUCCESS");
		} catch (CustomException e) {
			System.out.println(":: TRANSFER - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: TRANSFER  - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) 
	{
		System.out.println(":: ACCOUNT SUMMARY - RUNNING");
		try {
			if (!idIsValid(cusID)) throw new CustomException("INVALID CUSTOMER ID");
			if (!customerExists(cusID)) throw new CustomException("CUSTOMER NOT EXIST");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT number, balance FROM p1.account " + 
							"WHERE id = '" + cusID + "' AND status = 'A'";
			rs = stmt.executeQuery(query);

			int total = 0;
			System.out.println("NUMBER      BALANCE     ");
			System.out.println("-----------	----------- ");
			while (rs.next()) {
				int number = rs.getInt(1);
				int balance = rs.getInt(2);
				System.out.printf("%11d %11d \n", number, balance);
				total += balance;
			}
			System.out.println("-----------------------");
			System.out.printf("TOTAL %17d\n", total);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
		} catch (CustomException e) {
			System.out.println(":: ACCOUNT SUMMARY - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: ACCOUNT SUMMARY - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
		System.out.println(":: REPORT A - RUNNING");
		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			// Get the customer information
			String query = "SELECT p1.customer.id, name, gender, age, SUM(balance) AS total " + 
							"FROM p1.customer JOIN p1.account ON p1.customer.id = p1.account.id AND p1.account.status = 'A' " +
							"GROUP BY p1.customer.id, name, gender, age " +
							"ORDER BY total DESC";
			rs = stmt.executeQuery(query);

			System.out.println("ID          NAME            GENDER AGE         TOTAL       ");
			System.out.println("-----------	---------------	------ ----------- ----------- ");
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				int age = rs.getInt(4);
				int total = rs.getInt(5);
				System.out.printf("%11d %-15s %-7s %11d %11d\n", id, name, gender, age, total);
			}

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: REPORT A - SUCCESS");
		} catch (Exception e) {
			System.out.println(":: REPORT A - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
		System.out.println(":: REPORT B - RUNNING");
		try {
			if (!ageIsValid(min) || !ageIsValid(max)) throw new CustomException("INVALID AGE");
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT AVG(total) AS average " +
							"FROM (SELECT p1.customer.id, age, status, SUM(balance) AS total " +
							"FROM p1.customer JOIN p1.account ON p1.customer.id = p1.account.id " +
							"GROUP BY p1.customer.id, age, status) " +
							"WHERE age <= '" + max + "' AND age >= '" + min + "' AND status = 'A'";
			rs = stmt.executeQuery(query);

			System.out.println("AVERAGE    ");
			System.out.println("-----------");
			rs.next();
			int average = rs.getInt(1);
			System.out.printf("%11d\n", average);

			rs.close();
			stmt.close();
			con.close();
			System.out.println(":: REPORT B - SUCCESS");
		} catch (CustomException e) {
			System.out.println(":: REPORT B - ERROR - " + e.getMessage());
		} catch (Exception e) {
			System.out.println(":: REPORT B - ERROR - " + e.getMessage());
			e.getStackTrace();
		}
	}

    //------------------------------------------------- 
	// Validation Functions for Exception Handling
	//------------------------------------------------- 

	/**
	* Check if variable 'Id' is in a valid format.
	*/
	public static boolean idIsValid(String id) {
		return Integer.parseInt(id) >= 0;
	}

	/**
	* Check if variable 'Name' is in a valid format.
	*/
	public static boolean nameIsValid(String name) {
		return name.length() <= 15 && name != null;
	}

	/**
	* Check if variable 'Gender' is in a valid format.
	*/
	public static boolean genderIsValid(String gender) {
		return gender.equals("M") || gender.equals("F");
	}

	/**
	* Check if variable 'Age' is in a valid format.
	*/
	public static boolean ageIsValid(String age) {
		return Integer.parseInt(age) >= 0;
	}

	/**
	* Check if variable 'Pin' is in a valid format.
	*/
	public static boolean pinIsValid(String pin) {
		try { 
			Integer.parseInt(pin);
			return true;
		} catch (NumberFormatException e) { 
			return false;
		} 
	}

	/**
	* Check if variable 'Balance' is in a valid format.
	*/
	public static boolean balanceIsValid(String balance) {
		return Integer.parseInt(balance) >= 0;
	}

	/**
	* Check if variable 'Type' is in a valid format.
	*/
	public static boolean typeIsValid(String type) {
		return type.equals("C") || type.equals("S");
	}

	/**
	* Check if variable 'Status' is in a valid format.
	*/
	public static boolean statusIsValid(String status) {
		return status.equals("A") || status.equals("I");
	}

	/**
	* Check if variable 'Amount' is in a valid format.
	*/
	public static boolean amountIsValid(String amount) {
		try { 
			Integer.parseInt(amount);
			return true;
		} catch (NumberFormatException e) { 
			return false;
		} 
	}

	/**
	* Check if the customer exists in the database.
	*/
	public static boolean customerExists(String id) {
		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT id FROM p1.customer WHERE id = '" + id + "'" ;
			rs = stmt.executeQuery(query);

			if (rs.next())
				return true;

			rs.close();
			stmt.close();
			con.close();
			return false;
		} catch (SQLException e) {
			return false;
		}
	}

}
