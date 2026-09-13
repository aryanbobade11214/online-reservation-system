package com.reservation.ui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private String username;

    public DashboardFrame(String username) {

        this.username = username;

        setTitle("Online Reservation System - Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // MAIN PANEL
        // =========================
        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 40, 30, 40
                )
        );

        // =========================
        // HEADING
        // =========================
        JLabel titleLabel = new JLabel(
                "Online Reservation System",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + username + "!",
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(
                new Font("Arial", Font.PLAIN, 18)
        );

        JPanel headingPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        headingPanel.add(titleLabel);
        headingPanel.add(welcomeLabel);

        // =========================
        // BUTTON PANEL
        // =========================
        JPanel buttonPanel = new JPanel(
                new GridLayout(4, 1, 15, 15)
        );

        JButton reservationButton =
                new JButton("Make Reservation");

        JButton myReservationsButton =
                new JButton("My Reservations");

        JButton cancelButton =
                new JButton("Cancel Reservation");

        JButton logoutButton =
                new JButton("Logout");

        // Make buttons bigger
        Dimension buttonSize =
                new Dimension(250, 45);

        reservationButton.setPreferredSize(buttonSize);
        myReservationsButton.setPreferredSize(buttonSize);
        cancelButton.setPreferredSize(buttonSize);
        logoutButton.setPreferredSize(buttonSize);

        buttonPanel.add(reservationButton);
        buttonPanel.add(myReservationsButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);

        // =========================
        // ADD PANELS
        // =========================
        mainPanel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =========================
        // MAKE RESERVATION
        // =========================
        reservationButton.addActionListener(e -> {

            new ReservationFrame(username)
                    .setVisible(true);

            dispose();
        });

        // =========================
        // MY RESERVATIONS
        // =========================
        myReservationsButton.addActionListener(e -> {

            new MyReservationsFrame(username)
                    .setVisible(true);

            dispose();
        });

        // =========================
        // CANCEL RESERVATION
        // =========================
        cancelButton.addActionListener(e -> {

            new CancelReservationFrame(username)
                    .setVisible(true);

            dispose();
        });

        // =========================
        // LOGOUT
        // =========================
        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice == JOptionPane.YES_OPTION) {

                new LoginFrame()
                        .setVisible(true);

                dispose();
            }
        });
    }
}