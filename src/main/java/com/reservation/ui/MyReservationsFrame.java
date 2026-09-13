package com.reservation.ui;

import com.reservation.dao.ReservationDAO;
import com.reservation.model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MyReservationsFrame extends JFrame {

    private String username;

    private JTextArea reservationArea;

    public MyReservationsFrame(String username) {

        this.username = username;

        setTitle("Online Reservation System - My Reservations");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // MAIN PANEL
        // =========================
        JPanel mainPanel = new JPanel(
                new BorderLayout(15, 15)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 25, 30
                )
        );

        // =========================
        // HEADING
        // =========================
        JLabel titleLabel = new JLabel(
                "My Reservations",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        JLabel userLabel = new JLabel(
                "Reservations for: " + username,
                SwingConstants.CENTER
        );

        userLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JPanel headingPanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        headingPanel.add(titleLabel);
        headingPanel.add(userLabel);

        // =========================
        // RESERVATION AREA
        // =========================
        reservationArea = new JTextArea();

        reservationArea.setEditable(false);

        reservationArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );

        reservationArea.setLineWrap(false);

        reservationArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(reservationArea);

        // =========================
        // BACK BUTTON
        // =========================
        JButton backButton =
                new JButton("Back to Dashboard");

        backButton.setPreferredSize(
                new Dimension(200, 40)
        );

        // =========================
        // ADD COMPONENTS
        // =========================
        mainPanel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                backButton,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =========================
        // LOAD RESERVATIONS
        // =========================
        loadReservations();

        // =========================
        // BACK ACTION
        // =========================
        backButton.addActionListener(e -> {

            new DashboardFrame(username)
                    .setVisible(true);

            dispose();
        });
    }

    // =========================
    // LOAD RESERVATIONS
    // =========================
    private void loadReservations() {

        ReservationDAO reservationDAO =
                new ReservationDAO();

        List<Reservation> reservations =
                reservationDAO.getReservationsByUsername(
                        username
                );

        if (reservations.isEmpty()) {

            reservationArea.setText(
                    "No reservations found."
            );

            return;
        }

        StringBuilder output =
                new StringBuilder();

        for (Reservation reservation : reservations) {

            output.append(
                    "==================================================\n"
            );

            output.append(
                    "PNR           : "
            ).append(
                    reservation.getPnr()
            ).append("\n");

            output.append(
                    "Passenger     : "
            ).append(
                    reservation.getPassengerName()
            ).append("\n");

            output.append(
                    "Age           : "
            ).append(
                    reservation.getAge()
            ).append("\n");

            output.append(
                    "Train         : "
            ).append(
                    reservation.getTrainName()
            ).append("\n");

            output.append(
                    "Journey Date  : "
            ).append(
                    reservation.getJourneyDate()
            ).append("\n");

            output.append(
                    "==================================================\n\n"
            );
        }

        reservationArea.setText(
                output.toString()
        );

        // Start from top
        reservationArea.setCaretPosition(0);
    }
}