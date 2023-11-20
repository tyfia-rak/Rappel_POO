package com.controler;

import com.Model.Authors;
import com.Model.Books;
import com.service.CrudOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksCrudOperations implements CrudOperations<Books> {

    @Override
    public List<Books> findAll() {
        List<Books> booksList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"));
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Books book = new Books();
                book.setId(resultSet.getInt("id"));
                book.setBookName(resultSet.getString("bookName"));
                book.setPageNumbers(resultSet.getInt("pageNumbers"));
                book.setTopic(resultSet.getString("topic"));
                book.setReleaseDate(resultSet.getDate("releaseDate"));
                booksList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksList;
    }

    @Override
    public List<Books> saveAll(List<Books> toSave) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            for (Books book : toSave) {
                String query = "INSERT INTO books (bookName, pageNumbers, topic, releaseDate) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, book.getBookName());
                    preparedStatement.setInt(2, book.getPageNumbers());
                    preparedStatement.setString(3, book.getTopic());
                    preparedStatement.setDate(4, new java.sql.Date(book.getReleaseDate().getTime()));
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toSave;
    }

    @Override
    public Books save(Books toSave) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "INSERT INTO books (bookName, pageNumbers, topic, releaseDate) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, toSave.getBookName());
                preparedStatement.setInt(2, toSave.getPageNumbers());
                preparedStatement.setString(3, toSave.getTopic());
                preparedStatement.setDate(4, new java.sql.Date(toSave.getReleaseDate().getTime()));
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
    public Books delete(Books toDelete) {
        try (Connection connection = DriverManager.getConnection(System.getenv("PATH"), System.getenv("USERNAME"), System.getenv("PASSWORD"))) {
            String query = "DELETE FROM books WHERE id = ?";
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
