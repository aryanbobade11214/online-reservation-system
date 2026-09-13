package com.reservation.dao;

import com.reservation.model.Reservation;
import com.reservation.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDAO {

    // =========================
    // BOOK RESERVATION
    // =========================
    public boolean bookReservation(Reservation reservation) {

        String updateSeatsSQL =
                "UPDATE trains SET available_seats = available_seats - 1 " +
                        "WHERE id = ? AND available_seats > 0";

        String insertSQL =
                "INSERT INTO reservations " +
                        "(pnr, username, train_id, passenger_name, age, journey_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            try (PreparedStatement seatStatement =
                         connection.prepareStatement(updateSeatsSQL)) {

                seatStatement.setInt(1, reservation.getTrainId());

                int updatedRows = seatStatement.executeUpdate();

                if (updatedRows == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement insertStatement =
                         connection.prepareStatement(insertSQL)) {

                insertStatement.setString(1, reservation.getPnr());
                insertStatement.setString(2, reservation.getUsername());
                insertStatement.setInt(3, reservation.getTrainId());
                insertStatement.setString(4, reservation.getPassengerName());
                insertStatement.setInt(5, reservation.getAge());

                insertStatement.setDate(
                        6,
                        java.sql.Date.valueOf(
                                reservation.getJourneyDate()
                        )
                );

                insertStatement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // =========================
    // CANCEL RESERVATION
    // =========================
    public boolean cancelReservation(String pnr, String username) {

        String findSQL =
                "SELECT train_id FROM reservations " +
                        "WHERE pnr = ? AND username = ?";

        String deleteSQL =
                "DELETE FROM reservations " +
                        "WHERE pnr = ? AND username = ?";

        String updateSeatsSQL =
                "UPDATE trains SET available_seats = available_seats + 1 " +
                        "WHERE id = ?";

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            connection.setAutoCommit(false);

            int trainId;

            try (PreparedStatement statement =
                         connection.prepareStatement(findSQL)) {

                statement.setString(1, pnr);
                statement.setString(2, username);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (!resultSet.next()) {
                        connection.rollback();
                        return false;
                    }

                    trainId = resultSet.getInt("train_id");
                }
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(deleteSQL)) {

                statement.setString(1, pnr);
                statement.setString(2, username);

                statement.executeUpdate();
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(updateSeatsSQL)) {

                statement.setInt(1, trainId);

                statement.executeUpdate();
            }

            connection.commit();

            return true;

        } catch (SQLException e) {

            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackError) {
                rollbackError.printStackTrace();
            }

            e.printStackTrace();

            return false;

        } finally {

            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // =========================
    // GET USER RESERVATIONS
    // =========================
    public java.util.List<Reservation> getReservationsByUsername(
            String username) {

        java.util.List<Reservation> reservations =
                new java.util.ArrayList<>();

        String sql =
                "SELECT r.*, t.train_name " +
                        "FROM reservations r " +
                        "JOIN trains t ON r.train_id = t.id " +
                        "WHERE r.username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Reservation reservation =
                            new Reservation(
                                    resultSet.getInt("id"),
                                    resultSet.getString("pnr"),
                                    resultSet.getString("username"),
                                    resultSet.getInt("train_id"),
                                    resultSet.getString("passenger_name"),
                                    resultSet.getInt("age"),
                                    resultSet.getDate("journey_date")
                                            .toLocalDate()
                            );

                    // Get train name from trains table
                    reservation.setTrainName(
                            resultSet.getString("train_name")
                    );

                    reservations.add(reservation);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
}