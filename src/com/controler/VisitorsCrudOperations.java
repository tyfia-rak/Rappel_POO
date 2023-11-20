package com.controler;

import com.Model.Visitor;
import com.service.CrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VisitorsCrudOperations implements CrudOperations<Visitor> {

    @Override
    public List<Visitor> findAll() {
        List<Visitor> visitorsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"));
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM visitors");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Visitor visitor = new Visitor();
                visitor.setId(resultSet.getInt("id"));
                visitor.setName(resultSet.getString("name"));
                visitor.setRole(resultSet.getString("role"));
                visitor.setReference(resultSet.getString("reference"));
                visitorsList.add(visitor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return visitorsList;
    }

    @Override
    public List<Visitor> saveAll(List<Visitor> toSave) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            for (Visitor visitor : toSave) {
                String query = "INSERT INTO visitors (name, role, reference) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, visitor.getName());
                    preparedStatement.setString(2, visitor.getRole());
                    preparedStatement.setString(3, visitor.getReference());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Visitor save(Visitor toSave) throws SQLException {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "INSERT INTO visitors (name, role, reference) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, toSave.getName());
                preparedStatement.setString(2, toSave.getRole());
                preparedStatement.setString(3, toSave.getReference());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    toSave.setId(generatedKeys.getInt(1));
                }
            }
            return toSave;
        }
    }

    @Override
    public Visitor delete(Visitor toDelete) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "DELETE FROM visitors WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, toDelete.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toDelete;
    }
}