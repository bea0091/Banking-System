import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class p1_welcomePage extends JFrame {

    /**
     * Screen #1 : Main Menu
     */
    private static void mainMenu() {

        JFrame jframe = new JFrame("Main Menu");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Welcome to the Self Services Banking System!");
        content.add(Box.createRigidArea(new Dimension(250, 30)));
        content.add(label);
        content.add(Box.createRigidArea(new Dimension(250, 30)));

        JButton btn1 = new JButton("1. New Customer");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              newCustomer();
            } 
        });
        content.add(btn1);
        content.add(Box.createRigidArea(new Dimension(250, 25)));

        JButton btn2 = new JButton("2. Customer Login");
        btn2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
              customerLogin();
            } 
        });
        content.add(btn2);
        content.add(Box.createRigidArea(new Dimension(250, 25)));

        JButton btn3 = new JButton("3. Exit");
        btn3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                jframe.dispose();
            } 
        });
        content.add(btn3);

        jframe.setContentPane(content);
        jframe.setVisible(true);

    }

    /**
     * Screen #1-1 : New Customer
     */
    private static void newCustomer() {

        JFrame jframe = new JFrame("New Customer");
        jframe.setSize(600, 500);

        JPanel content = new JPanel();
        content.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel label1 = new JLabel("Name ");
        c.gridx = 0;
        c.gridy = 0;
        content.add(label1, c);
        JTextField txf1 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 0;
        content.add(txf1, c);

        JLabel label2 = new JLabel("Gender ");
        c.gridx = 0;
        c.gridy = 1;
        content.add(label2, c);
        JTextField txf2 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 1;
        content.add(txf2, c);

        JLabel label3 = new JLabel("Age ");
        c.gridx = 0;
        c.gridy = 2;
        content.add(label3, c);
        c.gridx = 1;
        c.gridy = 2;
        JTextField txf3 = new JTextField(10);
        content.add(txf3, c);

        JLabel label4 = new JLabel("Pin ");
        c.gridx = 0;
        c.gridy = 3;
        content.add(label4, c);
        JTextField txf4 = new JTextField(10);
        c.gridx = 1;
        c.gridy = 3;
        content.add(txf4, c);

        JButton btn1 = new JButton("Submit");
        btn1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String name = txf1.getText();
                String gender = txf2.getText();
                String age = txf3.getText();
                String pin = txf4.getText();
                BankingSystem.newCustomer(name, gender, age, pin);
                jframe.dispose();
            } 
        });
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    /**
     * Screen #1-2 : Customer Login
     */
    private static void customerLogin() {

        JFrame jframe = new JFrame("Customer Login");
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

        JLabel label2 = new JLabel("Pin");
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
                String id = txf1.getText();
                String pin = txf2.getText();
                if (id.equals("0") && pin.equals("0")) {
                    p1_administratorPage.administratorMainMenu();
                }
                else if (BankingSystem.login(id, pin)) {
                    p1_customerPage.customerMainMenu(id);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid ID");
                }
            } 
        });
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        content.add(btn1, c);

        jframe.setContentPane(content);
        jframe.setVisible(true);
    }

    public static void main(String[] args) {
        mainMenu();
    }
    
}
