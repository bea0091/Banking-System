import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class p1_administratorPage extends JFrame {
	// Connection properties
	private static String url = "jdbc:db2://127.0.0.1:50000/cs157a";
	private static String username = "db2inst1";
	private static String password = "kenward";
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

    /**
     * Screen #4 : Administrator Main Menu
     */
    public static void administratorMainMenu() {

        JFrame jframe = new JFrame("Administrator Main Menu");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createRigidArea(new Dimension(200, 30)));

        JButton btn1 = new JButton("1. Account Summary");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              accountSummary();
            } 
        });
        content.add(btn1);
        content.add(Box.createRigidArea(new Dimension(200, 25)));

        JButton btn2 = new JButton("2. Report A");
        btn2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                BankingSystem.reportA();
                reportA();
            } 
        });
        content.add(btn2);
        content.add(Box.createRigidArea(new Dimension(200, 25)));

        JButton btn3 = new JButton("3. Report B");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                reportB();
            } 
        });
        content.add(btn3);
        content.add(Box.createRigidArea(new Dimension(200, 25)));

        JButton btn4 = new JButton("4. Exit");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(btn4);

        jframe.setContentPane(content);
        jframe.setVisible(true);

    }

    /**
     * Screen #4-1 : Account Summary
     */
    private static void accountSummary() {

        JFrame jframe = new JFrame("Account Summary");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Customer Id");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String cusId = txf1.getText();
                BankingSystem.accountSummary(cusId);
                accountSummaryResult(cusId);
                jframe.dispose();
            } 
        });
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #4-1-1 : Account Summary Result
     */
    public static void accountSummaryResult(String cusID) {

        JFrame jframe = new JFrame("Account Summary Result");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT number, balance FROM p1.account " + 
							"WHERE id = '" + cusID + "' AND status = 'A'";
			rs = stmt.executeQuery(query);

			int total = 0;
            content.add(Box.createRigidArea(new Dimension(200, 50)));
			JLabel label1 = new JLabel("NUMBER          BALANCE      ");
            content.add(label1);
			JLabel label2 = new JLabel("-----------	----------- ");
            content.add(label2);

			while (rs.next()) {
				int number = rs.getInt(1);
				int balance = rs.getInt(2);
                String padded1 = String.format("%-20s", number);
                String padded2 = String.format("%-20s", balance);
				JLabel label3 = new JLabel(padded1 + padded2);
                content.add(label3);
				total += balance;
			}
			JLabel label4 = new JLabel("-----------------------");
            content.add(label4);
            String padded3 = String.format("%30s", total);
			JLabel label5 = new JLabel("TOTAL" + padded3);
            content.add(label5);

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR - " + e.getMessage());
		}

        JButton btn1 = new JButton("Exit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(Box.createRigidArea(new Dimension(200, 30)));
        content.add(btn1);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #4-2 : Report A
     */
    private static void reportA() {

        JFrame jframe = new JFrame("Report A");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();

			// Get the customer information
			String query = "SELECT p1.customer.id, name, gender, age, SUM(balance) AS total " + 
							"FROM p1.customer JOIN p1.account ON p1.customer.id = p1.account.id AND p1.account.status = 'A' " +
							"GROUP BY p1.customer.id, name, gender, age " +
							"ORDER BY total DESC";
			rs = stmt.executeQuery(query);

            content.add(Box.createRigidArea(new Dimension(150, 50)));
			JLabel label1 = new JLabel("ID                NAME            GENDER   AGE            TOTAL       ");
            content.add(label1);
			JLabel label2 = new JLabel("---------	------------	------ ------ --------- ");
            content.add(label2);

			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);
				int age = rs.getInt(4);
				int total = rs.getInt(5);
                String padded1 = String.format("%-11d", id);
                String padded2 = String.format("%-15s", name);
                String padded3 = String.format("%-7s", gender);
                String padded4 = String.format("%-11d",age);
                String padded5 = String.format("%-11d", total);
                JLabel label3 = new JLabel(padded1 + padded2 + padded3 + padded4 + padded5);
                content.add(label3);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR - " + e.getMessage());
		}

        JButton btn1 = new JButton("Exit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(Box.createRigidArea(new Dimension(200, 30)));
        content.add(btn1);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #4-3 : Report B
     */
    private static void reportB() {

        JFrame jframe = new JFrame("Report B");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Min Age");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Max Age");
        c.gridx = 0;
        c.gridy = 1;
        content.add(label2, c);
        JTextField txf2 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 1;
        content.add(txf2, c);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String min = txf1.getText();
                String max = txf2.getText();
                BankingSystem.reportB(min, max);
                reportBResult(min, max);
            } 
        });
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #4-3-1 : Report B Result
     */
    private static void reportBResult(String min, String max) {

        JFrame jframe = new JFrame("Report B Result");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			String query = "SELECT AVG(total) AS average " +
							"FROM (SELECT p1.customer.id, age, status, SUM(balance) AS total " +
							"FROM p1.customer JOIN p1.account ON p1.customer.id = p1.account.id " +
							"GROUP BY p1.customer.id, age, status) " +
							"WHERE age <= '" + max + "' AND age >= '" + min + "' AND status = 'A'";
			rs = stmt.executeQuery(query);

            content.add(Box.createRigidArea(new Dimension(150, 50)));
			JLabel label1 = new JLabel("AVERAGE    ");
            content.add(label1);
			JLabel label2 = new JLabel("-----------");
            content.add(label2);
			rs.next();
			int average = rs.getInt(1);
			JLabel label3 = new JLabel("%11d", average);
            content.add(label3);

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR - " + e.getMessage());
		}

        JButton btn1 = new JButton("Exit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(Box.createRigidArea(new Dimension(150, 30)));
        content.add(btn1);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

}
