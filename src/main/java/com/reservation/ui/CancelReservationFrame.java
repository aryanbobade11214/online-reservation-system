package com.reservation.ui;

import com.reservation.dao.ReservationDAO;

import javax.swing.*;
import java.awt.*;

public class CancelReservationFrame extends JFrame {

    private JTextField pnrField;
    private String username;

    public CancelReservationFrame(String username) {

        this.username = username;

        setTitle("Online Reservation System - Cancel Reservation");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                "Cancel Reservation",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 26)
        );

        JLabel userLabel = new JLabel(
                "Logged in as: " + username,
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

        JPanel formPanel = new JPanel(
                new GridLayout(2, 2, 15, 15)
        );

        JLabel pnrLabel =
                new JLabel("Enter PNR:");

        pnrField =
                new JTextField();

        JButton cancelButton =
                new JButton("Cancel Ticket");

        JButton backButton =
                new JButton("Back");

        formPanel.add(pnrLabel);
        formPanel.add(pnrField);

        formPanel.add(new JLabel());
        formPanel.add(cancelButton);

        JPanel buttonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        15,
                        5
                )
        );

        buttonPanel.add(backButton);

        mainPanel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        cancelButton.addActionListener(
                e -> cancelTicket()
        );

        backButton.addActionListener(e -> {

            new DashboardFrame(username)
                    .setVisible(true);

            dispose();
        });
    }

    private void cancelTicket() {

        String pnr =
                pnrField
                        .getText()
                        .trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your PNR."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel this ticket?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        ReservationDAO reservationDAO =
                new ReservationDAO();

        boolean success =
                reservationDAO.cancelReservation(
                        pnr,
                        username
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ticket cancelled successfully!"
            );

            pnrField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid PNR or ticket does not belong to this user."
            );
        }
    }
}