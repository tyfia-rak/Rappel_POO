package com.controler;

import com.Model.Authors;
import com.service.CrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorsCrudOperations implements CrudOperations<Authors> {

    @Override
    public List<Authors> findAll() {
        List<Authors> authorsList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"));
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM authors");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Authors author = new Authors();
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                author.setSex(resultSet.getBoolean("sex"));
                authorsList.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorsList;
    }

    @Override
    public List<Authors> saveAll(List<Authors> toSave) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            for (Authors author : toSave) {
                String query = "INSERT INTO authors (name, sex) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, author.getName());
                    preparedStatement.setBoolean(2, author.isSex());
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Authors save(Authors toSave) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "INSERT INTO authors (name, sex) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, toSave.getName());
                preparedStatement.setBoolean(2, toSave.isSex());
                preparedStatement.executeUpdate();

                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    toSave.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Authors delete(Authors toDelete) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "DELETE FROM authors WHERE id = ?";
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
