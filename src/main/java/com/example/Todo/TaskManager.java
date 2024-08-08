package com.example.todo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaskManager {
    private Connection connection;

    public TaskManager() {
        try {
            // Cargar las propiedades de la base de datos
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    System.out.println("Sorry, unable to find db.properties");
                    return;
                }
                props.load(input);
            } catch (IOException e) {
                System.out.println("Error loading db.properties: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            String url = props.getProperty("db.url");
            connection = DriverManager.getConnection(url);
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                     "id IDENTITY PRIMARY KEY," +
                     "description VARCHAR(255)," +
                     "completed BOOLEAN)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addTask(Task task) throws SQLException {
        String sql = "INSERT INTO tasks (description, completed) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getDescription());
            stmt.setBoolean(2, task.isCompleted());
            stmt.executeUpdate();
        }
    }

    public void updateTask(Task task) throws SQLException {
        String sql = "UPDATE tasks SET description = ?, completed = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getDescription());
            stmt.setBoolean(2, task.isCompleted());
            stmt.setInt(3, task.getId());
            stmt.executeUpdate();
        }
    }

    public void removeTask(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Task> listTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                boolean completed = rs.getBoolean("completed");
                tasks.add(new Task(id, description, completed));
            }
        }
        return tasks;
    }
}
