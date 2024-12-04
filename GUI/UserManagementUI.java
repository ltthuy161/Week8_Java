package GUI;

import Model.User;
import Service.UserService;
import DAO.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserManagementUI extends JFrame {
    private JTextField nameField, emailField, ageField;
    private JButton addButton, listButton;
    private JTextArea userListArea;
    private UserService userService;

    public UserManagementUI(UserService userService) {
        this.userService = userService;
        initUI();
    }

    private void initUI() {
        setTitle("User Management");
        setSize(500, 500);
        setLayout(new FlowLayout());

        add(new JLabel("Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(20);
        add(emailField);

        add(new JLabel("Age:"));
        ageField = new JTextField(5);
        add(ageField);

        addButton = new JButton("Add User");
        add(addButton);

        listButton = new JButton("List Users");
        add(listButton);

        userListArea = new JTextArea(15, 40);
        add(new JScrollPane(userListArea));

        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                int age = Integer.parseInt(ageField.getText());
                userService.addUser(new User(name, email, age));
                JOptionPane.showMessageDialog(this, "User added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        listButton.addActionListener(e -> {
            try {
                userListArea.setText("");
                for (User user : userService.getAllUsers()) {
                    userListArea.append(user.getId() + " - " + user.getName() + " - " +
                            user.getEmail() + " - " + user.getAge() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver loaded successfully.");

            // Establish connection
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/userdb", // Database URL
                    "postgres", // PostgreSQL username
                    "0803" // PostgreSQL password
            );

            UserDAO userDAO = new UserDAO(connection);
            UserService userService = new UserService(userDAO);
            new UserManagementUI(userService);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage());
        }
    }

}
