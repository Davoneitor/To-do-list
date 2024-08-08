package com.example.task;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TaskManagerTest {
    private TaskManager manager;

    @Before
    public void setUp() throws SQLException {
        manager = new TaskManager();
    }

    @Test
    public void testAddAndListTasks() throws SQLException {
        Task task = new Task(0, "Complete Maven tutorial", false);
        manager.addTask(task);

        List<Task> tasks = manager.listTasks();
        assertTrue(tasks.stream().anyMatch(t -> t.getDescription().equals("Complete Maven tutorial")));
    }

    @Test
    public void testUpdateTask() throws SQLException {
        Task task = new Task(0, "Learn Java", false);
        manager.addTask(task);
        List<Task> tasks = manager.listTasks();
        int idToUpdate = tasks.stream().filter(t -> t.getDescription().equals("Learn Java")).findFirst().get().getId();
        Task updatedTask = new Task(idToUpdate, "Learn Advanced Java", true);
        manager.updateTask(updatedTask);

        tasks = manager.listTasks();
        assertTrue(tasks.stream().anyMatch(t -> t.getDescription().equals("Learn Advanced Java") && t.isCompleted()));
    }

    @Test
    public void testRemoveTask() throws SQLException {
        Task task = new Task(0, "Write unit tests", false);
        manager.addTask(task);
        List<Task> tasks = manager.listTasks();
        int idToRemove = tasks.stream().filter(t -> t.getDescription().equals("Write unit tests")).findFirst().get().getId();
        manager.removeTask(idToRemove);

        tasks = manager.listTasks();
        assertFalse(tasks.stream().anyMatch(t -> t.getDescription().equals("Write unit tests")));
    }
}
