package com.reservation.ui;

import com.reservation.dao.UserDAO;
import com.reservation.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterFrame() {

        setTitle("Online Reservation System - Register");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        JLabel titleLabel = new JLabel(
                "Online Reservation System",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel registerLabel = new JLabel(
                "Create New Account",
                SwingConstants.CENTER
        );

        registerLabel.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        JPanel headingPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        headingPanel.add(titleLabel);
        headingPanel.add(registerLabel);

        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 12, 12)
        );

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel passwordLabel =
                new JLabel("Password:");

        JLabel confirmPasswordLabel =
                new JLabel("Confirm Password:");

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        confirmPasswordField =
                new JPasswordField();

        JButton registerButton =
                new JButton("Register");

        JButton backButton =
                new JButton("Back to Login");

        registerButton.setPreferredSize(
                new Dimension(150, 40)
        );

        backButton.setPreferredSize(
                new Dimension(150, 40)
        );

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        formPanel.add(new JLabel());
        formPanel.add(registerButton);

        formPanel.add(new JLabel());
        formPanel.add(backButton);

        mainPanel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        registerButton.addActionListener(
                e -> register()
        );

        backButton.addActionListener(e -> {

            new LoginFrame()
                    .setVisible(true);

            dispose();
        });
    }

    private void register() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 6 characters."
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match."
            );

            return;
        }

        User user =
                new User(
                        username,
                        password
                );

        UserDAO userDAO =
                new UserDAO();

        boolean registered =
                userDAO.registerUser(user);

        if (registered) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration successful!"
            );

            new LoginFrame()
                    .setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed. " +
                            "Username may already exist."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> new RegisterFrame()
                        .setVisible(true)
        );
    }
}