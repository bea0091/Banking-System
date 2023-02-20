import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class p1_customerPage extends JFrame {

    /**
     * Screen #3 : Customer Main Menu
     */
    public static void customerMainMenu(String id) {

        JFrame jframe = new JFrame("Customer Main Menu");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(Box.createRigidArea(new Dimension(200, 30)));

        JButton btn1 = new JButton("1. Open Account");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              openAccount();
            } 
        });
        content.add(btn1);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn2 = new JButton("2. Close Account");
        btn2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                closeAccount();
            } 
        });
        content.add(btn2);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn3 = new JButton("3. Deposit");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                deposit();
            } 
        });
        content.add(btn3);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn4 = new JButton("4. Withdraw");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                withdraw();
            } 
        });
        content.add(btn4);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn5 = new JButton("5. Transfer");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                transfer();
            } 
        });
        content.add(btn5);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn6 = new JButton("6. Account Summary");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                accountSummary(id);
            } 
        });
        content.add(btn6);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        JButton btn7 = new JButton("7. Exit");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(btn7);
        content.add(Box.createRigidArea(new Dimension(200, 10)));

        jframe.setContentPane(content);
        jframe.setVisible(true);

    }

    /**
     * Screen #3-1 : Open Account
     */
    private static void openAccount() {

        JFrame jframe = new JFrame("Open Account");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Customer ID");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Account Type");
        c.gridx = 0;
        c.gridy = 1;
        content.add(label2, c);
        JTextField txf2 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 1;
        content.add(txf2, c);

        JLabel label3 = new JLabel("Balance");
        c.gridx = 0;
        c.gridy = 2;
        content.add(label3, c);
        JTextField txf3 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 2;
        content.add(txf3, c);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String id = txf1.getText();
                String type = txf2.getText();
                String balance = txf3.getText();
                BankingSystem.openAccount(id, type, balance);
                jframe.dispose();
            } 
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #3-2 : Close Account
     */
    private static void closeAccount() {

        JFrame jframe = new JFrame("Close Account");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Accout Number");
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
                String number = txf1.getText();
                BankingSystem.closeAccount(number);
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
     * Screen #3-3 : Deposit
     */
    private static void deposit() {

        JFrame jframe = new JFrame("Desposit");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Accout Number");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Deposit Amount");
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
                String number = txf1.getText();
                String amount = txf2.getText();
                BankingSystem.deposit(number, amount);
                jframe.dispose();
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
     * Screen #3-4 : Withdraw
     */
    private static void withdraw() {

        JFrame jframe = new JFrame("Withdraw");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Accout Number");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Withdraw Amount");
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
                String number = txf1.getText();
                String amount = txf2.getText();
                BankingSystem.withdraw(number, amount);
                jframe.dispose();
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
     * Screen #3-5 : Transfer
     */
    private static void transfer() {

        JFrame jframe = new JFrame("Transfer");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Source Accout Number");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Destination Account Number");
        c.gridx = 0;
        c.gridy = 1;
        content.add(label2, c);
        JTextField txf2 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 1;
        content.add(txf2, c);

        JLabel label3 = new JLabel("Transfer Amount");
        c.gridx = 0;
        c.gridy = 2;
        content.add(label3, c);
        c.gridx = 1;
        c.gridy = 2;
        JTextField txf3 = new JTextField(10);
        content.add(txf3, c);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String srcAccNum = txf1.getText();
                String destAccNum = txf2.getText();
                String amount = txf3.getText();
                BankingSystem.transfer(srcAccNum, destAccNum, amount);
                jframe.dispose();
            } 
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #3-6 : Account Summary
     */
    private static void accountSummary(String cusID) {
        p1_administratorPage.accountSummaryResult(cusID);
    }

}
