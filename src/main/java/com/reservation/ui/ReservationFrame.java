package com.reservation.ui;

import com.reservation.dao.ReservationDAO;
import com.reservation.dao.TrainDAO;
import com.reservation.model.Reservation;
import com.reservation.model.Train;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReservationFrame extends JFrame {

    private String username;

    private JComboBox<Train> trainComboBox;
    private JTextField passengerNameField;
    private JTextField ageField;
    private JTextField journeyDateField;

    public ReservationFrame(String username) {

        this.username = username;

        setTitle("Online Reservation System - Make Reservation");
        setSize(600, 500);
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
                        30, 40, 30, 40
                )
        );

        // =========================
        // HEADING
        // =========================
        JLabel titleLabel = new JLabel(
                "Make Reservation",
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

        // =========================
        // FORM PANEL
        // =========================
        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 15, 15)
        );

        JLabel trainLabel =
                new JLabel("Select Train:");

        JLabel passengerLabel =
                new JLabel("Passenger Name:");

        JLabel ageLabel =
                new JLabel("Age:");

        JLabel dateLabel =
                new JLabel("Journey Date:");

        trainComboBox =
                new JComboBox<>();

        passengerNameField =
                new JTextField();

        ageField =
                new JTextField();

        journeyDateField =
                new JTextField();

        journeyDateField.setToolTipText(
                "Format: YYYY-MM-DD"
        );

        formPanel.add(trainLabel);
        formPanel.add(trainComboBox);

        formPanel.add(passengerLabel);
        formPanel.add(passengerNameField);

        formPanel.add(ageLabel);
        formPanel.add(ageField);

        formPanel.add(dateLabel);
        formPanel.add(journeyDateField);

        // Empty row for spacing
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        // =========================
        // BUTTON PANEL
        // =========================
        JPanel buttonPanel = new JPanel(
                new GridLayout(1, 2, 15, 0)
        );

        JButton bookButton =
                new JButton("Book Ticket");

        JButton backButton =
                new JButton("Back");

        bookButton.setPreferredSize(
                new Dimension(150, 40)
        );

        backButton.setPreferredSize(
                new Dimension(150, 40)
        );

        buttonPanel.add(bookButton);
        buttonPanel.add(backButton);

        // =========================
        // ADD TO MAIN PANEL
        // =========================
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

        // =========================
        // LOAD TRAINS
        // =========================
        loadTrains();

        // =========================
        // BOOK BUTTON
        // =========================
        bookButton.addActionListener(
                e -> bookTicket()
        );

        // =========================
        // BACK BUTTON
        // =========================
        backButton.addActionListener(e -> {

            new DashboardFrame(username)
                    .setVisible(true);

            dispose();
        });
    }

    // =========================
    // LOAD TRAINS
    // =========================
    private void loadTrains() {

        trainComboBox.removeAllItems();

        TrainDAO trainDAO =
                new TrainDAO();

        List<Train> trains =
                trainDAO.getAllTrains();

        for (Train train : trains) {

            trainComboBox.addItem(train);
        }
    }

    // =========================
    // BOOK TICKET
    // =========================
    private void bookTicket() {

        Train selectedTrain =
                (Train) trainComboBox
                        .getSelectedItem();

        String passengerName =
                passengerNameField
                        .getText()
                        .trim();

        String ageText =
                ageField
                        .getText()
                        .trim();

        String dateText =
                journeyDateField
                        .getText()
                        .trim();

        // =========================
        // TRAIN VALIDATION
        // =========================
        if (selectedTrain == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a train."
            );

            return;
        }

        // =========================
        // EMPTY FIELD VALIDATION
        // =========================
        if (passengerName.isEmpty()
                || ageText.isEmpty()
                || dateText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields."
            );

            return;
        }

        // =========================
        // NAME VALIDATION
        // =========================
        if (!passengerName.matches(
                "[a-zA-Z ]+")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passenger name should contain only letters."
            );

            return;
        }

        // =========================
        // AGE VALIDATION
        // =========================
        int age;

        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number."
            );

            return;
        }

        if (age <= 0 || age > 120) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age."
            );

            return;
        }

        // =========================
        // DATE VALIDATION
        // =========================
        LocalDate journeyDate;

        try {

            journeyDate =
                    LocalDate.parse(dateText);

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date. Use YYYY-MM-DD."
            );

            return;
        }

        if (journeyDate.isBefore(
                LocalDate.now())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Journey date cannot be in the past."
            );

            return;
        }

        // =========================
        // GENERATE PNR
        // =========================
        String pnr =
                "PNR" + System.currentTimeMillis();

        Reservation reservation =
                new Reservation(
                        pnr,
                        username,
                        selectedTrain.getId(),
                        passengerName,
                        age,
                        journeyDate
                );

        // =========================
        // BOOK RESERVATION
        // =========================
        ReservationDAO reservationDAO =
                new ReservationDAO();

        boolean success =
                reservationDAO.bookReservation(
                        reservation
                );

        // =========================
        // RESULT
        // =========================
        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ticket booked successfully!\n\n"
                            + "PNR: " + pnr
                            + "\nTrain: "
                            + selectedTrain.getTrainName()
                            + "\nPassenger: "
                            + passengerName
                            + "\nAge: " + age
                            + "\nJourney Date: "
                            + journeyDate
            );

            passengerNameField.setText("");
            ageField.setText("");
            journeyDateField.setText("");

            loadTrains();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed. "
                            + "No seats may be available."
            );
        }
    }
}