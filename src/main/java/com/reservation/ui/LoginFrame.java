package com.reservation.ui;

import com.reservation.dao.UserDAO;
import com.reservation.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("Online Reservation System - Login");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // =========================
        // TITLE
        // =========================
        JLabel titleLabel = new JLabel(
                "Online Reservation System",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel loginLabel = new JLabel(
                "Login",
                SwingConstants.CENTER
        );

        loginLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        JPanel headingPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        headingPanel.add(titleLabel);
        headingPanel.add(loginLabel);

        // =========================
        // FORM
        // =========================
        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel passwordLabel =
                new JLabel("Password:");

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        JButton loginButton =
                new JButton("Login");

        JButton registerButton =
                new JButton("Register");

        loginButton.setPreferredSize(
                new Dimension(120, 35)
        );

        registerButton.setPreferredSize(
                new Dimension(120, 35)
        );

        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        formPanel.add(new JLabel());
        formPanel.add(loginButton);

        formPanel.add(new JLabel());
        formPanel.add(registerButton);

        // =========================
        // ADD PANELS
        // =========================
        mainPanel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // BUTTON ACTIONS
        // =========================
        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(e -> {

            new RegisterFrame()
                    .setVisible(true);

            dispose();
        });
    }

    // =========================
    // LOGIN
    // =========================
    private void login() {

        String username =
                usernameField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password."
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.loginUser(
                        username,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful! Welcome "
                            + user.getUsername()
            );

            new DashboardFrame(
                    user.getUsername()
            ).setVisible(true);

            dispose();

        } else {

            passwordField.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password."
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new LoginFrame()
                    .setVisible(true);
        });
    }
}